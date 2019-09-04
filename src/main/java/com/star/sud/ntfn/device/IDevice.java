
package com.star.sud.ntfn.device;

import org.springframework.scheduling.annotation.Async;

import com.star.sud.ntfn.dto.NotificationParam;
import com.star.sud.ntfn.dto.NotificationStatus;

/**
 * @author User
 *
 */
public interface IDevice {

	/**
	 * Initialize device based on notification parameter
	 * 
	 * @param param Notification parameter
	 * @throws Exception
	 */
	NotificationStatus initDevice(NotificationParam param) throws Exception;

	/**
	 * Sends notification in asynchronous mode
	 * 
	 * @return Notification session id
	 * @throws Exception
	 */
	@Async
	void sendASyn() throws Exception;

	/**
	 * Sends notification in synchronous mode
	 * 
	 * @return Notification status
	 * @throws Exception
	 */
	NotificationStatus sendSyn() throws Exception;
}
