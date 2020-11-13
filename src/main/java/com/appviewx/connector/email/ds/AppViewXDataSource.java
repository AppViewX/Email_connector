package com.appviewx.connector.email.ds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.appviewx.connector.email.error.AppRuntimeException;
import com.appviewx.connector.email.error.ErrorCode;

/**
 * The Class AppViewXDataSource.
 * 
 * @author nareshkumar.r
 */
public class AppViewXDataSource implements DataSource {

	/** holder to have stream. */
	private byte[] bytes;

	/** attachment type. */
	private String attachmentType;

	/**
	 * Instantiates a new app view x data source.
	 * 
	 * @param bytes
	 *            the bytes
	 * @param attachmentType
	 *            default constructor
	 */
	public AppViewXDataSource(byte[] bytes, String attachmentType) {
		this.bytes = bytes;
		this.attachmentType = attachmentType;
	}

	/**
	 * to get the content type.
	 * 
	 * @return the content type
	 */
	@Override
	public String getContentType() {
		return attachmentType;
	}

	/**
	 * to get input stream.
	 * 
	 * @return the input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		InputStream stream = null;
		try {
			if (bytes != null) {
				stream = new ByteArrayInputStream(bytes);
			}
		} catch (Exception e) {
			throw new AppRuntimeException(ErrorCode.DATASOURCE_EMPTY_ERROR,
					ErrorCode.DATASOURCE_EMPTY_ERROR.getCodeDescription());
		}
		return stream;
	}

	/**
	 * to get name of the stream.
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return null;
	}

	/**
	 * to get output stream.
	 * 
	 * @return the output stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		final OutputStream stream = new ByteArrayOutputStream();
		try {
			if (bytes != null) {
				stream.write(bytes);
			}
		} catch (Exception e) {
			throw new AppRuntimeException(ErrorCode.DATASOURCE_EMPTY_ERROR,
					ErrorCode.DATASOURCE_EMPTY_ERROR.getCodeDescription());
		}
		return stream;
	}

}
