package com.appviewx.connector.email.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appviewx.connector.email.pojos.EmailConfig;
import com.appviewx.connector.email.service.EmailServiceProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ReadEmailAction.
 *
 * @author mageshwaran.p
 */
@RestController
public class ReadEmailAction {

	@Autowired
	private EmailServiceProcessor emailServiceProcessor;
	
	@PostMapping("/read-mail")
	public List<Map<String, String>> execute(@RequestBody Map<String, Object> payload) {
		
		Object body = payload.get("payload");
		ObjectMapper mapper = new ObjectMapper();
		EmailConfig requestData = mapper.convertValue(body, EmailConfig.class);

		return emailServiceProcessor.readMails(requestData.getProtocol(), requestData.getHost(),
				requestData.getUsername(), requestData.getPassword(), requestData.getMailSearchPattern());
	}
}
