package com.seppia.android.project_seppia.http;

import android.os.Build;

import com.seppia.android.project_seppia.utils.CheckUtils;
import com.seppia.android.project_seppia.utils.Encoding;
import com.seppia.android.project_seppia.utils.IOUtils;
import com.seppia.android.project_seppia.utils.LogUtils;
import com.seppia.android.project_seppia.utils.SdkVersion;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Http操作
 */
public class HttpHelper {

	private static final String TAG = HttpHelper.class.getName();

	public static final int DEFAULT_CONNECTION_TIMEOUT = 20*1000; // 默认连接超时
	public static final int DEFAULT_SOCKET_TIMEOUT = 20*1000; // 默认处理操作的超时
	public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192; // 默认Socket处理的缓存大小

	private static HttpClient mClient;
	private static boolean mUseGzip;

	/*--------------------------------------------------------------------------
	| 基础操作
	--------------------------------------------------------------------------*/
	public static final void shutdownDefaultClient() {
		try {
			if (mClient != null && mClient.getConnectionManager() != null) {
				mClient.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static final void shutdownClient(HttpClient client) {
		try {
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().closeExpiredConnections();
				client.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static HttpResponse doRequest(HttpRequestBase request) {
		return doRequest(request, getHttpClient());
	}

	public static HttpResponse doRequest(HttpRequestBase request, HttpClient client) {
		try {
			if (mUseGzip) {
				request.addHeader("Accept-Encoding", "gzip");
			}
			// 发送请求
			client.getConnectionManager().closeExpiredConnections();
			HttpResponse httpResponse = client.execute(request);
			return httpResponse;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/*--------------------------------------------------------------------------
	| Get请求
	--------------------------------------------------------------------------*/
	public static HttpGet createGet(String url, List<NameValuePair> pairList) {
		return createGet(url, pairList, HTTP.UTF_8);
	}

	public static HttpGet createGet(String url, List<NameValuePair> pairList, String encoding) {
		try {
			url = createUrlWithParams(url, pairList, encoding);
			HttpGet request = new HttpGet(url);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpResponse doGet(String url, List<NameValuePair> pairList) {
		return doGet(url, pairList, HTTP.UTF_8);
	}

	public static HttpResponse doGet(String url, List<NameValuePair> pairList, String encoding) {
		return doRequest(createGet(url, pairList, encoding));
	}

	public static HttpResponse doGet(String url, List<NameValuePair> pairList, int connectionTimeout, int soTimeout) {
		return doGet(url, pairList, HTTP.UTF_8, connectionTimeout, soTimeout);
	}

	public static HttpResponse doGet(String url, List<NameValuePair> pairList, String encoding, int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpGet request = createGet(url, pairList, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	/*--------------------------------------------------------------------------
	| Post请求
	--------------------------------------------------------------------------*/
	public static HttpPost createPost(String url, List<NameValuePair> pairList) {
		return createPost(url, pairList, HTTP.UTF_8);
	}

	public static HttpPost createPost(String url, List<NameValuePair> pairList, String encoding) {
		try {
			url = url.trim();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairList, encoding);
			request.setEntity(entity);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpPost createPost(String url, List<NameValuePair> pairList, InputStream is) {
		return createPost(url, pairList, is, HTTP.UTF_8);
	}

	public static HttpPost createPost(String url, List<NameValuePair> pairList, InputStream is, String encoding) {
		try {
			url = url.trim();
			HttpPost request = new HttpPost(url);
			InputStreamEntity entity = new InputStreamEntity(is, is.available());
			request.setEntity(entity);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpPost createJsonPost(String url, List<NameValuePair> pairList) {
		return createJsonPost(url, pairList, HTTP.UTF_8);
	}

	public static HttpPost createJsonPost(String url, List<NameValuePair> pairList, String encoding) {
		try {
			url = url.trim();
			HttpPost request = new HttpPost(url);
			request.setHeader("accept", "application/json");
			request.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
			if (pairList == null)return request; 
			StringEntity se = new StringEntity(createJsonRequest(pairList), encoding);
			se.setContentType("application/json; charset=utf-8");
			request.setEntity(se);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpPost createJsonPostWithParams(String url, List<NameValuePair> pairList, List<NameValuePair> urlParamsList) {
		return createJsonPostWithParams(url, pairList, urlParamsList, HTTP.UTF_8);
	}

	public static HttpPost createJsonPostWithParams(String url, List<NameValuePair> pairList, List<NameValuePair> urlParamsList, String encoding) {
		try {
			url = url.trim();
			url = createUrlWithParams(url, urlParamsList, encoding);
			HttpPost request = new HttpPost(url);
			request.setHeader("accept", "application/json");
			request.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
			StringEntity se = new StringEntity(createJsonRequest(pairList), encoding);
			se.setContentType("application/json; charset=utf-8");
			request.setEntity(se);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpPost createJsonPostWithParams(String url, List<NameValuePair> urlParamsList, String encoding) {
		try {
			url = url.trim();
			HttpEntity paramatersEntity = new UrlEncodedFormEntity(urlParamsList,"UTF-8");
			HttpPost request = new HttpPost(url);
			request.setEntity(paramatersEntity);
			request.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList) {
		return doPost(url, pairList, HTTP.UTF_8);
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, String encoding) {
		try {
			HttpPost request = createPost(url, pairList, encoding);
			return doRequest(request);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, int connectionTimeout, int soTimeout) {
		return doPost(url, pairList, HTTP.UTF_8, connectionTimeout, soTimeout);
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, String encoding, int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpPost request = createPost(url, pairList, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, InputStream is) {
		return doPost(url, pairList, HTTP.UTF_8);
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, InputStream is, String encoding) {
		try {
			HttpPost request = createPost(url, pairList, is, encoding);
			return doRequest(request);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, InputStream is, int connectionTimeout, int soTimeout) {
		return doPost(url, pairList, is, HTTP.UTF_8, connectionTimeout, soTimeout);
	}

	public static HttpResponse doPost(String url, List<NameValuePair> pairList, InputStream is, String encoding, int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpPost request = createPost(url, pairList, is, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	public static HttpResponse doPostJson(String url, List<NameValuePair> pairList) {
		return doPostJson(url, pairList, HTTP.UTF_8);
	}

	public static HttpResponse doPostJson(String url, List<NameValuePair> pairList, String encoding) {
		try {
			HttpPost request = createJsonPost(url, pairList, encoding);
			return doRequest(request);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpResponse doPostJson(String url, List<NameValuePair> pairList, int connectionTimeout, int soTimeout) {
		return doPostJson(url, pairList, HTTP.UTF_8, connectionTimeout, soTimeout);
	}

	public static HttpResponse doPostJson(String url, List<NameValuePair> pairList, String encoding, int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpPost request = createJsonPost(url, pairList, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	/*--------------------------------------------------------------------------
	| 其他
	--------------------------------------------------------------------------*/
	public static boolean isUseGzip() {
		return mUseGzip;
	}

	public static void setUseGzip(boolean useGzip) {
		mUseGzip = useGzip;
	}

	public static void setUserAgent(String userAgent) {
		try {
			HttpProtocolParams.setUserAgent(mClient.getParams(), userAgent);
		} catch (Exception e) {
			log(e);
		}
	}

	public static void setPortForScheme(String name, int port) {
		try {
			Scheme scheme = new Scheme(name, PlainSocketFactory.getSocketFactory(), port);
			mClient.getConnectionManager().getSchemeRegistry().register(scheme);
		} catch (Exception e) {
			log(e);
		}
	}

	public static synchronized HttpClient getHttpClient() {
		try {
			if (mClient == null) {
				mClient = createHttpClient();
			}
			return mClient;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static HttpClient createHttpClient() {
		return createHttpClient(createSchemeRegistry(), createHttpParams(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT));
	}

	public static HttpClient createHttpClient(SchemeRegistry schReg, HttpParams params) {
		try {
			final ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			HttpClient client = new DefaultHttpClient(conMgr, params);
			return client;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	public static String dealWithGzipResponse(HttpResponse httpResponse) {
		InputStream is = null;
		try {
			if (httpResponse == null) {
				return "";
			}
			if (httpResponse.getEntity().getContentEncoding() != null 
					&& httpResponse.getEntity().getContentEncoding().getValue() != null
					&& httpResponse.getEntity().getContentEncoding().getValue().contains("gzip")) {
				is = new GZIPInputStream(httpResponse.getEntity().getContent());
			}
			else {
				is = httpResponse.getEntity().getContent();
			}
			byte[] data = IOUtils.readBytes(is);
			if (data == null) {
				return "";
			}
			String charSet = EntityUtils.getContentCharSet(httpResponse.getEntity());
			if (CheckUtils.isEmpty(charSet)) {
				return new String(data, Encoding.UTF8);
			}
			return new String(data, charSet);
		} catch (Exception e) {
			log(e);
			return "";
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				log(e);
			}
		}
	}

	private static SchemeRegistry createSchemeRegistry() {
		try {
			// 设置HttpClient支持HTTP和HTTPS两种模式
			final SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			if (Integer.parseInt(Build.VERSION.SDK) >= SdkVersion.ECLAIR_MR1) {
				schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			} else {
				// used to work around a bug in Android 1.6:
				// http://code.google.com/p/android/issues/detail?id=1946
				schReg.register(new Scheme("https", new EasySSLSocketFactory(), 443));
			}
			return schReg;
		} catch (Exception e) {
			log(e);
			return new SchemeRegistry();
		}
	}

	private static HttpParams createHttpParams(int connectionTimeout, int soTimeout) {
		try {
			final BasicHttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
			HttpConnectionParams.setSoTimeout(params, soTimeout);
			HttpConnectionParams.setSocketBufferSize(params, DEFAULT_SOCKET_BUFFER_SIZE);
			HttpClientParams.setRedirecting(params, false);
			// Turn off stale checking. Our connections break all the time anyway,
			// and it's not worth it to pay the penalty of checking every time.
			HttpConnectionParams.setStaleCheckingEnabled(params, false);
			return params;
		} catch (Exception e) {
			log(e);
			return new BasicHttpParams();
		}
	}

	public static String createJsonRequest(List<NameValuePair> pairList) {
		try {
			JSONStringer jStringer = new JSONStringer();
			jStringer.object();
			for (NameValuePair nv : pairList) {
				if (nv.getValue() != null) {
					Object value;
					try {
						value = new JSONArray(nv.getValue());
					} catch (JSONException e) {
						try {
							value = new JSONObject(nv.getValue());
						} catch (JSONException ex) {
							value = nv.getValue();
						}
					}
					jStringer.key(nv.getName()).value(value);
				}
			}
			jStringer.endObject();
			return jStringer.toString();
		} catch (Exception e) {
			log(e);
			return "";
		}
	}

	private static String createUrlWithParams(String url, List<NameValuePair> pairList, String encoding) {
		try {
			url = url.trim();
			if (pairList != null && pairList.size() > 0) {
				String query = URLEncodedUtils.format(pairList, encoding);
				if (!url.endsWith("?")) {
					url += "?";
				}
				url += query;
			}
			log(url);
			return url;
		} catch (Exception e) {
			log(e);
			return url;
		}
	}

	private static void log(String msg) {
		LogUtils.logD(TAG, msg);
	}

	private static void log(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
