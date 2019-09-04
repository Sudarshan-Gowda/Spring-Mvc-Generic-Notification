package com.star.sud.ntfn.device;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.star.sud.gen.dao.GenericDao;
import com.star.sud.model.TNtfnDevice;
import com.star.sud.model.TNtfnTemplate;
import com.star.sud.ntfn.dto.NotificationParam;
import com.star.sud.ntfn.dto.NotificationStatus;

/**
 * @author User
 *
 */
public abstract class AbstractDevice<M> implements IDevice {

	// Static Attributes
	/////////////////////
	private static Logger log = Logger.getLogger(AbstractDevice.class);

	// Attributes
	/////////////
	private M device;

	@Autowired
	@Qualifier("ntfnTemplateDao")
	private GenericDao<TNtfnTemplate, String> ntfnTemplateDao;

	@Autowired
	@Qualifier("ntfnDeviceDao")
	private GenericDao<TNtfnDevice, String> ntfnDeviceDao;

	// Abstract Methods
	////////////////////
	/**
	 * Creates the device for notification
	 * 
	 * @param param
	 * @throws Exception
	 */
	protected abstract void createDevice(NotificationParam param) throws Exception;

	/**
	 * Post initialize the notification device based on template and device
	 * configuration
	 * 
	 * @param param                Notification parameter
	 * @param notificationTemplate Notification template carrying the template and
	 *                             subject
	 * @param notficationDevice    Notification device carrying the device
	 *                             configuration
	 * @throws Exception
	 */
	protected abstract void postInit(NotificationParam param, TNtfnTemplate notificationTemplate,
			TNtfnDevice notficationDevice) throws Exception;

	// Interface Methods
	/////////////////////


	/* (non-Javadoc)
	 * @see com.star.sud.ntfn.device.IDevice#initDevice(com.star.sud.ntfn.dto.NotificationParam)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public NotificationStatus initDevice(NotificationParam param) {
		log.debug("initDevice():...");
		// TODO Auto-generated method stub
		NotificationStatus notificationStatus = new NotificationStatus();
		try {
			if (null == param)
				throw new Exception("param null");

			// Initialize the notification template
			TNtfnTemplate notificationTemplate = this.getNotificationTemplate(param);

			// Initialize the notification configuration
			TNtfnDevice notficationDevice = this.getNotificationDevice(param, notificationTemplate);

			// Creates the device
			this.createDevice(param);

			// Post initialization of the device
			this.postInit(param, notificationTemplate, notficationDevice);
		} catch (Exception ex) {
			notificationStatus.setStatus(NotificationStatus.STATUS.EXCEPTION);
			notificationStatus.setMessage(ex.getMessage());
			log.error("initDevice", ex);
		}
		return notificationStatus;
	}

	// Protected Methods
	////////////////////
	/**
	 * @param field
	 * @param template
	 * @return
	 * @throws Exception
	 */
	protected String updateContent(NotificationParam param, NotificationParam.FIELDS field, String template)
			throws Exception {
		log.debug("updateContent");

		HashMap<String, String> hmFields = null;
		try {
			if (null == template || template.isEmpty()) {
				return template;
			}

			switch (field) {
			case SUBJECT:
				hmFields = param.getSubjectFields();
				break;

			case CONTENT:
				hmFields = param.getContentFeilds();
				break;
			}
			if (null != hmFields) {
				for (String key : hmFields.keySet()) {
					String value = hmFields.get(key);
					template = template.replaceAll(key, value);
				}
			}
			return template;
		} catch (Exception ex) {
			log.error("updateContent", ex);
			throw ex;
		}
	}

	// Helper Methods
	/////////////////
	/**
	 * Retrieve the notification template based on notification parameter
	 * 
	 * @param param Notification parameter
	 * @return Notification template mapped by appsCode and templateId of the
	 *         notification parameter
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	private TNtfnTemplate getNotificationTemplate(NotificationParam param) throws Exception {
		log.debug("getNotificationTemplate():...");

		try {
			if (null == param)
				throw new Exception("param null");

			// Recover template
			TNtfnTemplate notificationTemplate = ntfnTemplateDao.find(param.getTemplateId());
			if (null == notificationTemplate) {
				throw new Exception("Invalid template");
			}
			Hibernate.initialize(notificationTemplate.getTNtfnChnType());
			return notificationTemplate;
		} catch (Exception ex) {
			log.error("getNotificationTemplate", ex);
			throw ex;
		}
	}

	/**
	 * Loads template and device configuration
	 * 
	 * @param notificationParam
	 * @throws Exception
	 */
	/**
	 * Retrieve the notification device that holds the notification configuration
	 * 
	 * @param param                Notification parameter
	 * @param notificationTemplate Notification template associated with the
	 *                             notification parameter
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	private TNtfnDevice getNotificationDevice(NotificationParam param, TNtfnTemplate notificationTemplate)
			throws Exception {
		log.debug("getNotificationDevice():...");

		try {
			String channelId = notificationTemplate.getTNtfnChnType().getNchntypeId();
			log.debug("channelId: " + channelId);
			String hql = "FROM TNtfnDevice o WHERE o.TNtfnChnType.nchntypeId = '" + channelId + "'";
			List<TNtfnDevice> devices = ntfnDeviceDao.getByQuery(hql);
			if (devices.size() > 0) {
				TNtfnDevice device = devices.get(0);
				log.debug("deviceConfig: " + device.getNdevConfig());
				return device;
			} else {
				throw new Exception("Invalid device");
			}
		} catch (Exception ex) {
			log.error("getNotificationDevice", ex);
			throw ex;
		}
	}

	// Properties
	/////////////
	/**
	 * @return the device
	 */
	public M getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(M device) {
		this.device = device;
	}
}
