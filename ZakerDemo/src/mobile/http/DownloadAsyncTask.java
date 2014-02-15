package mobile.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

import com.siqi.geli.util.AppSetting;
import com.siqi.geli.util.SharedPreferencesUtil;

public class DownloadAsyncTask extends AsyncTask<Void, Integer, Boolean> {

	// private int endPos; // >>>>结束点
	// private int doneSize;// >>>>完成点
	private String fileUrl;// >>>>下载路径（网络地址）

	// private int fileType; // >>>>>下载的文件类型

	private File savefile;
	private String originKey;// 以前已经下载的文件名
	private SharedPreferencesUtil s_h_util;
	// 是否只是下载 true只下载不校验MD5
	private boolean isOnlyDown;

	// private long lastnoticetime = 0; // >>>>最后一次下载通知时间
	// private final static int noticeTime = 1000;// 通知进度时间间隔 ,每次通知间隔2000毫秒

	/**
	 * 
	 * @param url
	 *            下载地址
	 * @param file
	 *            要保存替换的文件
	 * @param startPosition
	 * @param endPosition
	 * @param mContext
	 */
	public DownloadAsyncTask(String url, File savefile) {

		// 修改获取已经下载字节数获取方式this.doneSize = mDownloadJob.getDoneSize();
		this.fileUrl = url;
		this.savefile = savefile;
		isOnlyDown = true;
		// >>>>>>>判断文件是否存在,不存在创建
		// if (!savefile.exists())
		// savefile.mkdirs();

		// >>>>>>>>fullName格式为 本地保存路径 + 文件名称 +“-”+ 资源id
		// >>>>>验证文件是否下载过：文件名+“-”+资源id，如果相同则为下载过，如果不相同则为未下载
		if (AppSetting.DEBUG) {
			Log.i(AppSetting.LOG_TAG, "DownloadAsyncTask url = " + url
					+ "保存 savefile =  " + savefile.getAbsolutePath());
		}
	}

	public DownloadAsyncTask(String originKey, String url, File savefile,
			SharedPreferencesUtil s_h_util) {

		// 修改获取已经下载字节数获取方式this.doneSize = mDownloadJob.getDoneSize();
		this.fileUrl = url;
		this.savefile = savefile;
		this.s_h_util = s_h_util;
		this.originKey = originKey;
		isOnlyDown = false;
		// >>>>>>>判断文件是否存在,不存在创建
		// if (!savefile.exists())
		// savefile.mkdirs();

		// >>>>>>>>fullName格式为 本地保存路径 + 文件名称 +“-”+ 资源id
		// >>>>>验证文件是否下载过：文件名+“-”+资源id，如果相同则为下载过，如果不相同则为未下载
		if (AppSetting.DEBUG) {
			Log.i(AppSetting.LOG_TAG, "DownloadAsyncTask url = " + url
					+ "保存 savefile =  " + savefile.getAbsolutePath());
		}
	}

	private void downloadException() {
		if (AppSetting.DEBUG) {
			Log.d(AppSetting.LOG_TAG, "下载失败==>");
		}
	}

	private void downloadSuccess() {
		if (AppSetting.DEBUG) {
			Log.i(AppSetting.LOG_TAG, "下载完成==>");
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		HttpURLConnection connection = null;
		// FileOutputStream fos = null;
		RandomAccessFile randomAccessFile = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		URL url = null;
		try {
			url = new URL(fileUrl);
		} catch (MalformedURLException e1) {
			if (AppSetting.DEBUG) {
				Log.e(AppSetting.LOG_TAG, "==>MalformedURLException");
			}
			return false;
		}

		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("GET");
			// 设置范围，格式为Range：bytes x-y;
			// connection.setRequestProperty("Range", "bytes=" + doneSize + "-"
			// + endPos);
			connection.connect();

			// final String downFileMd5 = connection
			// .getHeaderField("Content-MD5");
			// if (null != downFileMd5) {
			// if (AppSetting.DEBUG) {
			// }
			// if (downFileMd5.equals(originFileMd5)) {
			// return false;
			// }
			// }

			// randomAccessFile.seek(doneSize);

			// 将要下载的文件写到保存在保存路径下的文件中

			if (!isOnlyDown) {
				Map<String, List<String>> heads = connection.getHeaderFields();
				final String downFileLastModified = String.valueOf(connection
						.getLastModified());
				final String originFileLastModifiTime = s_h_util.getString(
						originKey, "0");
				Log.w(AppSetting.LOG_TAG, "DownloadAsyncTask 文件名= "
						+ originKey + " downFileLastModified = "
						+ downFileLastModified + " 已经下载的文件的修改时间 = "
						+ originFileLastModifiTime);
				if (downFileLastModified.equals(originFileLastModifiTime)) {
					return false;
				} else {// 删除过时文件
					savefile.delete();
				}
				s_h_util.saveString(originKey, downFileLastModified);
			}
			is = connection.getInputStream();
			randomAccessFile = new RandomAccessFile(savefile, "rwd");
			bis = new BufferedInputStream(is);

			byte[] buffer = new byte[1024 * 2];
			int length = -1;
			// lastnoticetime = System.currentTimeMillis();

			// int slen = connection.getContentLength();
			while ((length = bis.read(buffer)) != -1) {
				// doneSize += length;
				// String txtstring = new String(buffer, "UTF-8");
				// randomAccessFile.seek(doneSize);
				// randomAccessFile.write(txtstring.getBytes("UTF-8"));
				// randomAccessFile.seek(doneSize);
				randomAccessFile.write(buffer);
				// if (doneSize == endPos
				// || System.currentTimeMillis() - lastnoticetime >=
				// noticeTime)
				// {
				// 下载过程中
				// lastnoticetime = System.currentTimeMillis();
				// }
			}
		} catch (Exception e) {
			// 发送下载失败消息
			if (AppSetting.DEBUG) {
				e.printStackTrace();
			}
			savefile.delete();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
				if (bis != null) {
					bis.close();
					bis = null;
				}
				randomAccessFile.close();
				connection.disconnect();
				// Log.i(TAG, "下载完成:endPos==>" + endPos + "|||doneSize:"
				// + doneSize);
				// >>>>>>>>>下载完成进度设置
				// if (endPos == doneSize) {
				// downloadSuccess();
				// }
			} catch (Exception e) {
				downloadException();
			}
		}
		return true;
	}
}
