package com.seppia.android.project_seppia.http;

import java.security.cert.*;

import javax.net.ssl.*;

/**
 * used to work around a bug in Android 1.6:
 * http://code.google.com/p/android/issues/detail?id=1946
 */
public class TrivialTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}
