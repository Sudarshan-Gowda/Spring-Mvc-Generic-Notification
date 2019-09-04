package com.star.sud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.star.sud.ntfn.INotify;
import com.star.sud.ntfn.constant.NotificationConstant;
import com.star.sud.ntfn.dto.NotificationParam;
import com.star.sud.ntfn.dto.NotificationStatus;
import com.star.sud.ntfn.dto.ServiceStatus.STATUS;
import com.star.sud.service.INotificationService;

public class NotificationServiceImpl implements INotificationService {

	// Static Attributes
	///////////////////////
	private static final Logger log = Logger.getLogger(NotificationServiceImpl.class);

	// Attributes
	//////////////
	@Autowired
	@Qualifier("notification")
	private INotify notification;

	@Override
	public NotificationStatus sendNotification(String email, String name) {

		NotificationStatus satus = null;
		try {

			NotificationParam param = new NotificationParam();
			param.setTemplateId(NotificationConstant.TEMPLATE1);

			ArrayList<String> recipients = new ArrayList<String>();
			recipients.add(email);
			param.setRecipients(recipients);

			HashMap<String, String> contentFields = new HashMap<String, String>();
			contentFields.put(":userName", name);

			param.setContentFeilds(contentFields);

			satus = notification.notifySyn(param);
			return satus;
		} catch (Exception e) {
			log.error("sendNotification", e);
			satus = new NotificationStatus();
			satus.setStatus(STATUS.FAILED);
			return satus;
		}
	}

}
