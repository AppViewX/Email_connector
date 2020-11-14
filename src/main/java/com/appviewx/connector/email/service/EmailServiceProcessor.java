
package com.appviewx.connector.email.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appviewx.connector.email.pojos.EmailConfig;
import com.appviewx.connector.email.pojos.EmailSender;
import com.appviewx.connector.email.pojos.EmailTemplate;

@Component
public class EmailServiceProcessor {

	@Autowired
	private MailServerInstanceFactory mailServerFactory;

	public List<Map<String, String>> readMails(String protocol, String host, String userName, String passwd,
			String patternStr) {
		return mailServerFactory.getInstance(protocol).retrieveMail(host, userName, passwd, patternStr);
	}

	public boolean sendEmail(EmailSender emailSender) throws Exception {
		return mailServerFactory.getMailSender(emailSender.getEmailConfig().isAuthEnabled())
				.sendEmail(emailSender.getEmailTemplate(), emailSender.getEmailConfig());
	}

	/**
	 * Send email.
	 *
	 * @param emailTemplate
	 *            the email template
	 * @throws AvxServiceException
	 *             .
	 * @return boolean
	 */
	public boolean sendEmail(EmailTemplate emailTemplate, EmailConfig emailConfig, boolean isAuthEnabled)
			throws Exception {
		return mailServerFactory.getMailSender(isAuthEnabled).sendEmail(emailTemplate, emailConfig);
	}
}
