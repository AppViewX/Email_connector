package com.appviewx.connector.email.pojos;

public class EmailConfig {

	/** The host. */
	private String host;

	/** The port. */
	private String port;

	/** The send address. */
	private String sendAddress;

	/** The is auth enabled. */
	private boolean isAuthEnabled;

	/** The username. */
	private String username;

	/** The password. */
	private String password;
	
	private boolean tlsEnabled;

	/**
	 * SMTP connection timeout is configurable. By default it is 60 Seconds.
	 */
	private int connectionTimeout = EmailConstants.DEFAULT_CONN_TIMEOUT;

	/**
	 * SMTP I/O read timeout is configurable. By default it is 60 Seconds.
	 */
	private int readIOTimeout = EmailConstants.DEFAULT_IO_TIMEOUT;
	
	private String mailSearchPattern;
	
	private String protocol;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public boolean isAuthEnabled() {
		return isAuthEnabled;
	}

	public void setAuthEnabled(boolean isAuthEnabled) {
		this.isAuthEnabled = isAuthEnabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getReadIOTimeout() {
		return readIOTimeout;
	}

	public void setReadIOTimeout(int readIOTimeout) {
		this.readIOTimeout = readIOTimeout;
	}

	public String getMailSearchPattern() {
		return mailSearchPattern;
	}

	public void setMailSearchPattern(String mailSearchPattern) {
		this.mailSearchPattern = mailSearchPattern;
	}

	public final String getProtocol() {
		return protocol;
	}

	public final void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isTlsEnabled() {
		return tlsEnabled;
	}

	public void setTlsEnabled(boolean tlsEnabled) {
		this.tlsEnabled = tlsEnabled;
	}

}
