package com.appviewx.connector.email.action;

import java.util.HashMap;
import java.util.Map;

import javax.mail.AuthenticationFailedException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appviewx.connector.email.pojos.EmailSender;
import com.appviewx.connector.email.service.EmailServiceProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class SendEmailAction.
 *
 * @author mageshwaran.p
 */
@RestController
public class SendEmailAction {

	@Autowired
	private EmailServiceProcessor emailServiceProcessor;
	
	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailAction.class);

	@PostMapping("/send-mail")
	protected Map<String, Object> execute(@RequestBody Map<String, Object> payload, HttpServletResponse response)
			throws Exception {

		LOGGER.info("Send email via controller has been triggered");
		Object body = payload.get("payload");
		ObjectMapper mapper = new ObjectMapper();
		EmailSender emailSender = mapper.convertValue(body, EmailSender.class);

		Map<String, Object> map = new HashMap<>();
		try {
		map.put("response", emailServiceProcessor.sendEmail(emailSender));
		} catch (AuthenticationFailedException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("response", "EMAIL_AUTHENTICATION_FAILURE");
		} catch (Exception e) {
			LOGGER.error("Error occured while connecting to smtp server {}", e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("response", "EMAIL_CONNECTION_FAILURE");
		}
		return map;
	}
}
