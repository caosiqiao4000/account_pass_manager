package mobile.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.siqi.geli.util.AppSetting;

/**
 * 
 * @author: 
 * @createTime: 2012-1-12 下午2:24:16
 * @version: 1.0
 * @desc :HttpClient 公共方法
 */
public class SyncHttpClient {

	private final String TAG = "SyncHttpClient";
	// 超时等待
	private static final int CONNECTION_TIMEOUT = 10000;
	// 字符集
	public static final String CONTENT_CHARSET = "UTF-8";

	/**
	 * Using POST method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPost(String url, String queryString) throws Exception {
		String responseData = null;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		// 设置超时
		httpPost.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(CONNECTION_TIMEOUT));
		// 设置字符集
		httpPost.addRequestHeader("Content-Type", "text/html; charset=utf-8");
		if (queryString != null && !queryString.equals("")) {
			httpPost.setRequestEntity(new ByteArrayRequestEntity(queryString
					.getBytes()));
		}
		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				Log.e(TAG,
						"HttpPost Method failed: " + httpPost.getStatusLine());
			}
			// Read the response body.
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

	/**
	 * post by pairs
	 * 
	 * @param url
	 * @param pairs
	 * @return
	 * @throws Exception
	 */
	// public String httpPost(String url, List<BasicNameValuePair> parameters)
	// throws Exception {
	public ResponseStateRecore httpPost(String url,
			List<BasicNameValuePair> parameters) throws Exception {
		String responseData = null;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		// 设置超时
		httpPost.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(CONNECTION_TIMEOUT));
		// 设置字符集
		httpPost.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET);
		// String systime = Util.getSysNowTime();
		// parameters.add(new Parameter("systime", systime));
		// String authenticator = EncryptUtils.getAuthenticator(parameters);
		// parameters.add(new Parameter("authenticator", authenticator));
		StringBuilder urllog = new StringBuilder(url);
		// 添加参数
		if (null != parameters && parameters.size() > 0) {
			for (Iterator<BasicNameValuePair> iter = parameters.iterator(); iter
					.hasNext();) {
				BasicNameValuePair para = (BasicNameValuePair) iter.next();
				httpPost.addParameter(
						para.getName() == null ? "" : para.getName(),
						para.getValue() == null ? "" : para.getValue());
				urllog.append(para.getName() + "=" + para.getValue() + "&");
			}
			Log.i(TAG, " url= " + urllog.substring(0, urllog.length() - 1));
		}
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				Log.e(TAG,
						"HttpPost Method failed: " + httpPost.getStatusLine());
			}
			// Read the response body.
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}
		return new ResponseStateRecore(statusCode, responseData);
	}

	/**
	 * 生成MD5 必须与后台的参数顺序个数一致
	 * 
	 * @param sourceMd5Str
	 * @return
	 */
	public static String generateMd5(String sourceMd5Str) {
		// jwinsrv jws = new jwinsrv();
		// return jws.JgetMd5(sourceMd5Str, sourceMd5Str.length());
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(sourceMd5Str).toLowerCase();
	}
	
	/**
	 * 生成MD5 支持中文
	 * @param sourceMd5Str
	 * @return
	 */
	public static String generateMd5ByChinese(String sourceMd5Str) {
		return MD5.md5(sourceMd5Str).toLowerCase();
	}
	
	public static String generateMd5(File file) throws IOException{
		String result = null;
		if (file.exists()) {
			try {
				FileInputStream in = new FileInputStream(file);
				FileChannel ch =in.getChannel();
				MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(byteBuffer);
				result = String.format("%032X", new BigInteger(1, md5.digest()));
			} catch (Exception e) {
				result = result.substring(0, 32);
			}
		}
		return result;
	}

	public ResponseStateRecore httpGet(String url, String queryString)
			throws Exception {
		String responseData = null;
		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}
		if (AppSetting.DEBUG) {
			Log.i(TAG, "get url= " + url);
		}
		HttpClient httpClient = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		httpGet.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(CONNECTION_TIMEOUT));
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(httpGet);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpGet Method failed: "
						+ httpGet.getStatusLine());
			}
			// Read the response body.
			responseData = httpGet.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpGet.releaseConnection();
			httpClient = null;
		}
		return new ResponseStateRecore(statusCode, responseData);
	}

	public String httpPostOpenUrl(String url, List parameters) throws Exception {
		String responseData = null;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		// 设置超时
		httpPost.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(CONNECTION_TIMEOUT));
		// 设置字符集
		httpPost.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET);
		// String systime = Util.getSysNowTime();
		// parameters.add(new Parameter("systime", systime));
		// parameters.add(new Parameter("appId", "10000"));
		// String authenticator = EncryptUtils.getAuthenticator(parameters);
		// parameters.add(new Parameter("authenticator", authenticator));
		// 添加参数
		for (Iterator iter = parameters.iterator(); iter.hasNext();) {
			// Parameter para = (Parameter) iter.next();
			//
			// httpPost.addParameter(para.mName == null ? "" : para.mName,
			// para.mValue == null ? "" : para.mValue);
		}
		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: "
						+ httpPost.getStatusLine());
			}
			// Read the response body.
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

	// 下载
	public boolean downLoadByUrl(String url, String saveStr) {
		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		try {
			client.executeMethod(httpGet);

			InputStream in = httpGet.getResponseBodyAsStream();

			FileOutputStream out = new FileOutputStream(new File(saveStr));

			byte[] b = new byte[128];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
				out.flush();
			}
			in.close();
			out.close();
			return true;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
			return false;
		}
	}
}
