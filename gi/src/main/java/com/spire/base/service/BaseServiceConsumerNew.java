package com.spire.base.service;

import com.spire.base.controller.Logging;
import com.spire.base.helper.FileHelper;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import spire.commons.userservice.bean.LoginRequestBean;
import spire.commons.userservice.bean.LoginResponseBean;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Class to frame all HTTP methods
 */

public abstract class BaseServiceConsumerNew extends
		ReadingServiceEndPointsProperties {
	public BaseServiceConsumerNew() {
		super();
	}

	Logger logger = Logger.getLogger(FileHelper.class);
	String endPointURL = null;
	Invocation.Builder invocationBuilder = null;
	public boolean NO_HEADERS = true;
	Properties properties = null;
	protected boolean MULTI_FORM_DATA = false;
	Client client = null;
	String hostName = null;
	LoginResponseBean loginResponse;
	public String PROPERTIES_FILE_NAME = "properties/default_headers.properties";
	MultivaluedMap<String, Object> multivaluedMap = null;

	public enum Headers {
		tenantId, realmName, userId, Authorization
	}

	/**
	 * Create Builder Instance by Service End-Point URL.
	 */

	protected void prepareInvocation(String url) {
		logger.info("***** Calling Prepare Invocation !!! *****");

		if (MULTI_FORM_DATA)
			// multiform-data type requests
			client = ClientBuilder.newBuilder()
					.register(MultiPartFeature.class).build();
		else
			// for application/json type requests
			client = ClientBuilder.newClient();
		client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		WebTarget target = client.target(url);
		invocationBuilder = target.request();

		// headers not required !!!
		if (!NO_HEADERS) {
			return;
		}
		// setting headers
		else {
			logger.info("***** Loading Headers !!! *****");
			this.invocationBuilder = invocationBuilder.headers(multivaluedMap);
			logger.info("***** Headers Loaded Successfully !!! *****");
		}
	}

	/**
	 * GET Operation
	 */

	protected Response executeGET(String URL) {
		prepareInvocation(URL);
		logger.info("***** Calling GET !!! *****");
		return invocationBuilder.get();
	}

	/**
	 * POST Operation - with Entity
	 */

	protected Response executePOST(String URL, Entity<?> entity) {
		prepareInvocation(URL);
		logger.info("***** Calling POST !!! *****");
		return invocationBuilder.post(entity);
	}

	/**
	 * POST Operation - without Entity
	 */
	protected Response executePOSTWithoutEntity(String URL) {
		prepareInvocation(URL);
		logger.info("***** Calling POST !!! *****");
		return invocationBuilder.post(null);
	}

	/**
	 * DELETE Operation
	 */

	private Response executeDELETE(String URL) {
		prepareInvocation(URL);
		logger.info("***** Calling DELETE !!! *****");
		return invocationBuilder.delete();
	}

	/**
	 * PUT Operation - with entity
	 */

	protected Response executePUT(String URL, Entity<?> entity) {
		prepareInvocation(URL);
		logger.info("***** Calling PUT !!! *****");
		return invocationBuilder.put(entity);
	}

	/**
	 * PATCH Operation - with entity
	 */

	protected Response executePATCH(String URL, Entity<?> entity) {
		prepareInvocation(URL);
		logger.info("***** Calling PATCH !!! *****");
		return invocationBuilder.method("PATCH", entity);
	}

	/**
	 * Setting Headers
	 */

	protected MultivaluedMap<String, Object> getHeaders() {
		MultivaluedMap<String, Object> multivaluedMap = new MultivaluedHashMap<String, Object>();
		if (loginResponse != null) {
			Logging.log("Setting Headers from the service call !!!");
			properties.setProperty(Headers.Authorization.toString(), "Bearer "
					+ loginResponse.getTokenId());
			properties.setProperty(Headers.realmName.toString(),
					loginResponse.getRealmName());
			properties.setProperty(Headers.tenantId.toString(),
					String.valueOf(loginResponse.getTenantId()));
			properties.setProperty(Headers.userId.toString(),
					String.valueOf(loginResponse.getUserId()));
		} else {
			Logging.log("Authentication Failed !!!!");
		}
		Set<Object> keys = properties.keySet();
		logger.info("HEADERS ADDING TO THE SERVICE \n");
		for (Object k : keys) {
			String key = (String) k;
			String value = getPropertyValue(key);
			multivaluedMap.putSingle(key, value);
			logger.info("KEY >> \"" + key + "\"" + "\t" + "VALUE >> \"" + value
					+ "\"");
		}
		return multivaluedMap;
	}

	/**
	 * Based on property name ,will get the corresponding value from properties
	 * file.
	 */

	private String getPropertyValue(String name) {
		if (properties == null) {
			throw new RuntimeException(" properties instance is not invoked");
		}
		String value = properties.getProperty(name);
		return value;

	}

	/**
	 * Setting properties
	 */

//	protected LoginResponseBean getUserToken(String userId, String password) {
//		LoginRequestBean loginRequestBean = new LoginRequestBean();
//		loginRequestBean.setUserId(userId);
//		loginRequestBean.setPassword(password);
//		String URL = getServiceEndPoint("TALENT_USER_SERVICE");
//		WebTarget target = ClientBuilder.newClient().target(URL);
//		Response response = target.request().post(
//				Entity.entity(loginRequestBean, MediaType.APPLICATION_JSON));
//		if (response.getStatus() == 200) {
//			loginResponse = response.readEntity(LoginResponseBean.class);
//			Logging.log("userId >>>>" + loginResponse.getUserId());
//			Logging.log("tenantId  >>>>" + loginResponse.getTenantId());
//			Logging.log("realmName  >>>>" + loginResponse.getRealmName());
//			Logging.log("Authorization  >>>>" + loginResponse.getTokenId());
//			setDynamicProperties();
//			return loginResponse;
//		} else {
//			return null;
//		}
//		
//	}
	public LoginResponseBean getUserToken(String userId, String password,String hostName) {
		LoginRequestBean loginRequestBean = new LoginRequestBean();
		loginRequestBean.setUserId(userId);
		loginRequestBean.setPassword(password);
		String URL = getServiceEndPoint("TALENT_USER_SERVICE").replaceAll("hostAddress", hostName);
		WebTarget target = ClientBuilder.newClient().target(URL);
		Response response = target.request().post(
				Entity.entity(loginRequestBean, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			loginResponse = response.readEntity(LoginResponseBean.class);
			Logging.log("userId >>>>" + loginResponse.getUserId());
			Logging.log("tenantId  >>>>" + loginResponse.getTenantId());
			Logging.log("realmName  >>>>" + loginResponse.getRealmName());
			Logging.log("Authorization  >>>>" + loginResponse.getTokenId());
			setDynamicProperties();
			return loginResponse;
		} else {
			return null;
		}
		//setDynamicProperties();
		
	}
	/**
	 * Reading properties file from src/main/resources
	 * 
	 * @return
	 */
	protected Properties readingProperties() {
		InputStream is = null;
		properties = new Properties();
		is = ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE_NAME);
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	protected void setDynamicProperties() {
		if (this.properties == null) {
			this.properties = readingProperties();
			multivaluedMap = getHeaders();
		}
	}

}
