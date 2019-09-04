package com.star.sud.service;

import com.star.sud.ntfn.dto.NotificationStatus;

public interface INotificationService {

	NotificationStatus sendNotification(String email, String name);

}
