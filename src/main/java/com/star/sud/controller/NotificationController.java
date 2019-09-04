package com.star.sud.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.star.sud.dto.SendNotification;
import com.star.sud.ntfn.dto.NotificationStatus;
import com.star.sud.ntfn.dto.ServiceStatus.STATUS;
import com.star.sud.service.INotificationService;

@Controller
public class NotificationController implements MessageSourceAware {

	// Static Attributes
	//////////////////////
	private static final Logger log = Logger.getLogger(NotificationController.class);

	// Attributes
	/////////////////
	@Autowired
	@Qualifier("notificationService")
	private INotificationService notificationService;

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected HttpServletRequest request;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getLandingPage(Model model) {
		log.info("getLandingPage");
		try {
			model.addAttribute("sendNotification", new SendNotification());

		} catch (Exception e) {
			log.error("getLandingPage", e);
		}

		return "home";

	}

	@RequestMapping(value = "/sendNotification", method = RequestMethod.POST)
	public String sendNotification(Model model, @ModelAttribute SendNotification dto) {
		log.info("sendNotification");
		try {

			if (null == dto)
				throw new Exception("dto param is null or empty");

			if (null == dto.getEmail())
				throw new Exception("email param is null or empty");

			if (null == dto.getName())
				throw new Exception("name param is null or empty");

			NotificationStatus status = notificationService.sendNotification(dto.getEmail(), dto.getName());
			if (status != null && status.getStatus().equals(STATUS.SUCCESS)) {
				model.addAttribute("msgsuccess",
						messageSource.getMessage("message.add.success", new Object[] {}, request.getLocale()));
				model.addAttribute("isDisabled", Boolean.TRUE);
			} else {
				model.addAttribute("msgdanger",
						messageSource.getMessage("message.add.failure", new Object[] {}, request.getLocale()));
			}
			model.addAttribute("sendNotification", dto);

		} catch (Exception e) {
			log.error("sendNotification", e);
			model.addAttribute("msgdanger",
					messageSource.getMessage("message.add.failure", new Object[] {}, request.getLocale()));
		}

		return "home";

	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		try {
			if (null == messageSource)
				throw new Exception("messageSource param null");

			this.messageSource = messageSource;
		} catch (Exception ex) {
			log.error("setMessageSource", ex);
		}
	}

}
