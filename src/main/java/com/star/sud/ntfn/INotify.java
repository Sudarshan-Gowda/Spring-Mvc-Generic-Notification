package com.star.sud.ntfn;

import com.star.sud.ntfn.dto.NotificationParam;
import com.star.sud.ntfn.dto.NotificationStatus;

public interface INotify {

	/**
	 * Asynchronous notification
	 * 
	 * @param param
	 * @throws Exception
	 */
	NotificationStatus notifyAsyn(NotificationParam param) throws Exception;

	/**
	 * Get status of asynchronous notification
	 * 
	 * @param sessionId
	 * @return
	 */
	NotificationStatus getStatus(String sessionId);

	/**
	 * Synchronous notification
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	NotificationStatus notifySyn(NotificationParam param) throws Exception;
}
