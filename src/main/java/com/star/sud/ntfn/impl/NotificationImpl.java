package com.star.sud.ntfn.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.star.sud.dao.NtfnTemplateDao;
import com.star.sud.model.TNtfnChnType;
import com.star.sud.model.TNtfnTemplate;
import com.star.sud.ntfn.INotify;
import com.star.sud.ntfn.device.IDevice;
import com.star.sud.ntfn.dto.NotificationParam;
import com.star.sud.ntfn.dto.NotificationStatus;
import com.star.sud.ntfn.dto.ServiceStatus.STATUS;

public class NotificationImpl implements INotify {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(NotificationImpl.class);

	// Attributes
	//////////////
	@Autowired
	@Qualifier("ntfnTemplateDao")
	private NtfnTemplateDao templateDao;

	@Autowired
	@Qualifier("htmlEmailDevice")
	private IDevice htmlEmailDevice;

	// Interface Methods
	////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.star.sud.ntfn.INotify#notifyAsyn(com.star.sud.ntfn.dto.NotificationParam)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public NotificationStatus notifyAsyn(NotificationParam param) {

		NotificationStatus notificationStatus = new NotificationStatus();

		// TODO Auto-generated method stub
		log.debug("notifyAsyn():..");
		try {
			if (null == param)
				throw new Exception("param null");

			// Retrieve channel type
			TNtfnChnType channelType = this.getChannelType(param);
			if (null == channelType)
				throw new Exception("Channel type not found");
			String channelId = channelType.getNchntypeId();
			log.debug("channelId: " + channelId);

			// Maps the device
			IDevice notificationDevice = null;
			switch (channelId) {
			case "CHN_TYPE_EMAIL":
				notificationDevice = htmlEmailDevice;
				break;

			default:
				break;
			}
			if (null == notificationDevice)
				throw new Exception("Device not found");

			notificationStatus = notificationDevice.initDevice(param);
			notificationDevice.sendASyn();
			log.info("notifyAsyn returns");
		} catch (Exception ex) {
			notificationStatus.setStatus(STATUS.FAILED);
			notificationStatus.setMessage(ExceptionUtils.getFullStackTrace(ex));
			notificationStatus.setException(ex.getMessage());

			log.error("notifyAsyn", ex);
		}
		return notificationStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.ntfn.INotify#getStatus(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public NotificationStatus getStatus(String sessionId) {
		// TODO Auto-generated method stub
		log.debug("getStatus():..");
		try {
			return null;
		} catch (Exception ex) {
			log.error("getStatus", ex);
			throw ex;
		}
	}

 
	/* (non-Javadoc)
	 * @see com.star.sud.ntfn.INotify#notifySyn(com.star.sud.ntfn.dto.NotificationParam)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public NotificationStatus notifySyn(NotificationParam param) {
		// TODO Auto-generated method stub
		log.debug("notifySyn():..");

		NotificationStatus notificationStatus = new NotificationStatus();
		try {
			if (null == param)
				throw new Exception("param null");
			log.debug(param.toXML());

			TNtfnChnType channelType = this.getChannelType(param);
			if (null == channelType)
				throw new Exception("Channel type not found");
			String channelId = channelType.getNchntypeId();
			log.debug("channelId: " + channelId);

			IDevice notificationDevice = null;
			switch (channelId) {
			case "CHN_TYPE_EMAIL":
				notificationDevice = htmlEmailDevice;
				break;

			default:
				break;
			}
			if (null == notificationDevice)
				throw new Exception("Device not found");

			// Initialize the device and sends the notification
			notificationDevice.initDevice(param);
			notificationStatus = notificationDevice.sendSyn();

		} catch (Exception ex) {
			log.error("notifySyn", ex);
			notificationStatus.setStatus(STATUS.FAILED);
			notificationStatus.setMessage(ExceptionUtils.getFullStackTrace(ex));
			notificationStatus.setException(ex.getMessage());
		}
		return notificationStatus;
	}

	// Helper Methods
	/////////////////
	/**
	 * @param param notification parameter
	 * @return Notification channel or null;
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	private TNtfnChnType getChannelType(NotificationParam param) throws Exception {
		log.debug("getChannelType");

		try {
			if (null == param)
				throw new Exception("param null");

			TNtfnTemplate notificationTemplate = templateDao.find(param.getTemplateId());
			if (null != notificationTemplate) {
				Hibernate.initialize(notificationTemplate.getTNtfnChnType());
				TNtfnChnType channelType = notificationTemplate.getTNtfnChnType();
				return channelType;
			}
			return null;
		} catch (Exception ex) {
			log.error("getChannelType", ex);
			throw ex;
		}
	}
}
