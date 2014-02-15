package mobile.http;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.util.IUICallBackInterface;
import com.siqi.geli.util.Util;

/**
 * 
 * 异步刷新ui类
 * 
 * @author
 * 
 */
public class SeverSupportAsyncTask extends AsyncTask<Object, Object, Object> {
	private Context context;
	private IUICallBackInterface callBackInterface;
	// request url
	private String url;
	// Parameter
	private List<BasicNameValuePair> paras;
	// pdShow isShow ?
	private boolean pdShow;
	// ProgressDialog show message
	private String pdMsg;
	// 提交方式 true postHTTP
	private boolean postOrgetHttp;
	// ProgressDialog
	private Dialog popupDialog;
	// get ?之后的请求字符串
	private String requestByGetStr;

	private int caseKey;
	protected SyncHttpClient http = new SyncHttpClient();

	@Override
	protected Object doInBackground(Object... params) {
		if (!Util.checkNetworkIsActive(context)) {
			return HttpReqCode.no_network;
		}
		try {
			if (postOrgetHttp) {
				return http.httpPost(url, paras);
			} else {
				return http.httpGet(url, requestByGetStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return HttpReqCode.error;
		}
	}

	/***
	 * 
	 * SeverSupportTask
	 * 
	 * @param context
	 * @param pdShow
	 *            //是否显示等待 Dialog
	 * @param paras
	 *            // post参数 get时传 null
	 * @param url
	 * @param callBackInterface
	 *            //回调接口
	 * @param postOrgetHttp
	 *            // true postHTTP, false get request
	 * @param requestByGetStr
	 *            // get 参数 ?之后的请求字符串
	 */
	public SeverSupportAsyncTask(Context context,
			IUICallBackInterface callBackInterface, String url,
			List<BasicNameValuePair> paras, boolean pdShow, String pdMsg,
			int caseKey, boolean postOrgetHttp, String requestByGetStr) {
		this.context = context;
		this.callBackInterface = callBackInterface;
		this.url = url;
		this.paras = paras;
		this.pdShow = pdShow;
		this.pdMsg = pdMsg;
		this.caseKey = caseKey;
		this.postOrgetHttp = postOrgetHttp;
		this.requestByGetStr = requestByGetStr;
	}

	@Override
	protected void onPostExecute(Object result) {
		if (pdShow) {
			popupDialog.dismiss();
		}
		if (callBackInterface != null) {
			callBackInterface.uiCallBack(result, this.caseKey);
		}
	}

	@Override
	protected void onPreExecute() {
		if (pdShow) {
			popupDialog = new Dialog(context,
					android.R.style.Theme_Translucent_NoTitleBar);
			popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			popupDialog.setContentView(R.layout.sh_loading_layout);
			TextView txtMsg = (TextView) popupDialog
					.findViewById(R.id.sh_left_textView);
			txtMsg.setText(pdMsg);
			popupDialog.show();
		}
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
	}
}
