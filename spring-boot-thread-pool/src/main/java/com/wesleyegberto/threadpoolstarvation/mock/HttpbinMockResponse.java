package com.wesleyegberto.threadpoolstarvation.mock;

import java.util.Map;

public class HttpbinMockResponse {
	private String origin;
	private String url;
	private Map<String, String> headers;

	public String getOrigin() {
		return origin;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}
