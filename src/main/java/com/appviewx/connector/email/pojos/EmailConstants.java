package com.appviewx.connector.email.pojos;

import java.util.regex.Pattern;

/**
 * @author mageshwaran.p
 *
 */
public final class EmailConstants {

	/**
	 *
	 */
	public static final StringBuilder ATTACHMENT_TYPE_PDF = new StringBuilder("application/pdf");
	/**
	 *
	 */
	public static final StringBuilder AUDIT_REPORT_FILE_NAME = new StringBuilder("AuditReport.pdf");

	/**
	 *
	 */
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	/**
	 *
	 */
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";

	public static final String MAIL_SMTP_CONN_TIMEOUT = "mail.smtp.connectiontimeout";

	public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";

	public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

	public static final String MAIL_TLS_ENABLED = "mail.smtp.starttls.enable";

	public static final String PROTOCOL_SMTP = "smtp";

	public static final String MAIL_SMTP_SOCKETFACTORY = "mail.smtp.socketFactory.class";

	public static final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";

	public static final String MAIL_SSL_ENABLED = "mail.smtp.ssl.enable";

	// Default SMTP connection timeout is 60 seconds.
	public static final int DEFAULT_CONN_TIMEOUT = 60000;

	// Default SMTP I/O read timeout is 60 seconds.
	public static final int DEFAULT_IO_TIMEOUT = 60000;

	public static final String AUTH = "true";

	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

	public static final Pattern IP_REGEX_PATTERN = Pattern
			.compile("(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))");

	private EmailConstants() {

	}
}
