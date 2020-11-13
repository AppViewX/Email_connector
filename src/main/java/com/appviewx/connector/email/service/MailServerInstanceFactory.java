package com.appviewx.connector.email.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailServerInstanceFactory {

	@Autowired
	private Map<String, AbstractEmailReadService> msType;

	@Autowired
	private Map<String, AbstractMailAdapter> mailAdapter;

	public AbstractMailAdapter getMailSender(boolean isAuthEnabled) {
		if (isAuthEnabled) {
			return mailAdapter.get("AUTH-SERVICE");
		} else {
			return mailAdapter.get("NO-AUTH-SERVICE");
		}
	}

	/**
	 * This method returns the requested server types instance.
	 * 
	 * @param serverType
	 *            the serverType.
	 * @return the instance
	 */
	public AbstractEmailReadService getInstance(String mailServerType) {
		return msType.get(mailServerType + "-MAPPING-SERVICE");
	}

}
