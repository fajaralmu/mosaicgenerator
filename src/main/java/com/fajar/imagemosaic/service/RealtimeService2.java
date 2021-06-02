package com.fajar.imagemosaic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fajar.imagemosaic.models.WebResponse;

@Service
public class RealtimeService2 {
	Logger log = LoggerFactory.getLogger(RealtimeService2.class);

	@Autowired
	private SimpMessagingTemplate webSocket;
 

	public boolean sendUpdateSession(Object payload) {

		webSocket.convertAndSend("/wsResp/sessions", payload);

		return true;
	}
	int counter = 0;
	public void sendProgress(double progress, String requestId) {
		log.info("Send progress ({}): {} ({})", counter, Math.ceil(progress), requestId);
		sendProgress(requestId, WebResponse.builder() .percentage(progress).build());
		counter++;
	}

	public void sendProgress(String requestId, WebResponse WebResponse) {
		webSocket.convertAndSend("/wsResp/progress/" + requestId, WebResponse);
	}

	public void sendMessageChatToClient(WebResponse response, String requestId) {
		webSocket.convertAndSend("/wsResp/messages/" + requestId, response);
	}

	public void sendChatMessageToAdmin(WebResponse response) {
		webSocket.convertAndSend("/wsResp/adminmessages", response);
	}

}
