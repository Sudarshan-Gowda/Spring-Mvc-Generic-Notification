package com.star.sud.ntfn.device.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.star.sud.model.TNtfnDevice;
import com.star.sud.model.TNtfnTemplate;
import com.star.sud.ntfn.constant.NotificationConstant;
import com.star.sud.ntfn.device.AbstractDevice;
import com.star.sud.ntfn.dto.NotificationParam;
import com.star.sud.ntfn.dto.NotificationStatus;

@Component
public class HtmlEmailDeviceImpl extends AbstractDevice<HtmlEmail> {

	// Static Attributes
	///////////////////
	private static Logger log = Logger.getLogger(HtmlEmailDeviceImpl.class);

	// Interface Methods
	////////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.ntfn.device.IDevice#sendASyn()
	 */
	@Async
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void sendASyn() {
		// TODO Auto-generated method stub
		log.info("sendASyn():...");

		NotificationStatus notificationStatus = new NotificationStatus();
		try {
			this.getDevice().send();
		} catch (Exception ex) {
			log.error("sendASyn", ex);
			notificationStatus.setStatus(NotificationStatus.STATUS.EXCEPTION);
			notificationStatus.setMessage(ex.getMessage());
		}
		log.info("sendASyn ends");
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.ntfn.device.IDevice#sendSyn()
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public NotificationStatus sendSyn() {
		log.info("sendSyn():- Notification Function Started");

		NotificationStatus notificationStatus = new NotificationStatus();
		try {
			this.getDevice().send();
			log.info("sendSyn():- Notification Sent Successfully");
		} catch (Exception ex) {
			log.error("sendSyn", ex);
			notificationStatus.setStatus(NotificationStatus.STATUS.EXCEPTION);
			notificationStatus.setMessage(ex.getMessage());
		}

		return notificationStatus;
	}

	// Override Methods
	///////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.star.sud.ntfn.device.AbstractDevice#createDevice(com.star.sud.ntfn.dto.
	 * NotificationParam)
	 */
	protected void createDevice(NotificationParam param) throws Exception {
		// TODO Auto-generated method stub
		log.debug("createDevice");
		try {
			// creates the concrete device
			this.setDevice(new HtmlEmail());
		} catch (Exception ex) {
			log.error("createDevice", ex);
			throw ex;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.ntfn.device.AbstractDevice#postInit(com.star.sud.ntfn.dto.
	 * NotificationParam, com.star.sud.model.TNtfnTemplate,
	 * com.star.sud.model.TNtfnDevice)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected void postInit(NotificationParam param, TNtfnTemplate notificationTemplate, TNtfnDevice notficationDevice)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("postInit");

		try {
			String deviceConfig = notficationDevice.getNdevConfig();
			log.debug("deviceConfig: " + deviceConfig);

			// Device configuration
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> mConfig = mapper.readValue(deviceConfig, new TypeReference<Map<String, String>>() {
			});
			this.getDevice().setHostName(mConfig.get("HOST"));
			this.getDevice().setSmtpPort(Integer.valueOf(mConfig.get("PORT")));
			this.getDevice()
					.setAuthenticator(new DefaultAuthenticator(mConfig.get("USERNAME"), mConfig.get("PASSWORD")));
			this.getDevice().setSSLOnConnect(false);
			if (mConfig.containsKey("SSL") && mConfig.get("SSL").equalsIgnoreCase("true")) {
				this.getDevice().setSSLOnConnect(true);
			}
			this.getDevice().setStartTLSRequired(false);
			if (mConfig.containsKey("TLS_REQ") && mConfig.get("TLS_REQ").equalsIgnoreCase("true")) {
				this.getDevice().setStartTLSRequired(true);
			}
			this.getDevice().setStartTLSEnabled(false);
			if (mConfig.containsKey("TLS") && mConfig.get("TLS").equalsIgnoreCase("true")) {
				this.getDevice().setStartTLSEnabled(true);
			}
			// Sender
			if (!StringUtils.isEmpty(param.getFrom())) {
				this.getDevice().setFrom(param.getFrom());
			} else {
				this.getDevice().setFrom(NotificationConstant.SYS_DFT_EMAIL);
			}
			// Subject
			String subject = notificationTemplate.getNtplSubject();
			if (!StringUtils.isEmpty(subject)) {
				this.getDevice().setSubject(subject);
				if (null != param.getSubjectFields()) {
					this.getDevice().setSubject(super.updateContent(param, NotificationParam.FIELDS.SUBJECT, subject));
				}
			}
			// Content
			boolean bHtmlContent = notificationTemplate.getTNtfnCntType().getNcnttypeId()
					.equalsIgnoreCase("CNT_TYPE_HTML");
			String content = notificationTemplate.getNtplTempalte();
			boolean bFields = (param.getContentFeilds() == null);
			if (!StringUtils.isEmpty(content)) {
				if (!bFields) {
					content = super.updateContent(param, NotificationParam.FIELDS.CONTENT, content);
				}
				if (bHtmlContent) {
					this.getDevice().setHtmlMsg(content);
				} else {
					this.getDevice().setTextMsg(content);
				}
			}
			// Recipients
			List<String> mailList = param.getRecipients();
			if (null != mailList && !mailList.isEmpty()) {
				Collection<InternetAddress> recipientList = this.getMailList(mailList);
				this.getDevice().setTo(recipientList);
			}
			// CCs
			mailList = param.getCcs();
			if (null != mailList && !mailList.isEmpty()) {
				Collection<InternetAddress> ccList = this.getMailList(mailList);
				this.getDevice().setCc(ccList);
			}
			// Bccs
			mailList = param.getBccs();
			if (null != mailList && !mailList.isEmpty()) {
				Collection<InternetAddress> bccList = this.getMailList(mailList);
				this.getDevice().setBcc(bccList);
			}
			// Attachments
			List<String> attachements = param.getAttachments();
			if (null != attachements && !attachements.isEmpty()) {
				for (String attachment : attachements) {
					EmailAttachment mailAttachment = new EmailAttachment();
					mailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
					mailAttachment.setPath(attachment);
					this.getDevice().attach(mailAttachment);
				}
			}
		} catch (Exception ex) {
			log.error("postInit", ex);
			throw ex;
		}
	}

	// Helper Methods
	/////////////////
	/**
	 * Convert list of string to Internet Address
	 * 
	 * @param addressList
	 * @return
	 * @throws Exception
	 */
	private Collection<InternetAddress> getMailList(List<String> addressList) throws Exception {
		log.debug("getMailList");

		Collection<InternetAddress> mailAddrresses = new ArrayList<>();
		try {
			if (null == addressList || addressList.isEmpty())
				return mailAddrresses;

			for (String address : addressList) {
				InternetAddress inetAddress = new InternetAddress(address);
				mailAddrresses.add(inetAddress);
			}
			return mailAddrresses;
		} catch (Exception ex) {
			log.error("getMailList", ex);
			throw ex;
		}
	}

}
