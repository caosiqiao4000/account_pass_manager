package com.kress.gprs;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GprsTestActivity extends Activity {
	private ConnectivityManager mCM;

	private Button openButton;
	private Button shutButton, btn_submit;
	private TextView tv_statu;
	private SharedPreferencesUtil shardUtil;
	private String sharedCommonStr = "gprsOpenPass";
	private String gprsPass = "gprsopenPass";
	private EditText et_one, et_two;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mCM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		openButton = (Button) findViewById(R.id.btnOpen);
		shutButton = (Button) findViewById(R.id.btnShut);
		tv_statu = (TextView) findViewById(R.id.tv_statu_value);
		openButton.setOnClickListener(btnListener);
		shutButton.setOnClickListener(btnListener);
		shardUtil = new SharedPreferencesUtil(GprsTestActivity.this,
				sharedCommonStr);
		et_one = (EditText) findViewById(R.id.et_one);
		if ("".equals(shardUtil.getString(gprsPass, ""))) {
			openButton.setVisibility(View.GONE);
			showToaskUtil("还未设置GPRS的打开密码,请先在下方设置...");
			btn_submit = (Button) findViewById(R.id.btn_submit);
			btn_submit.setOnClickListener(btnListener);
			et_two = (EditText) findViewById(R.id.et_two);
			et_two.setVisibility(View.VISIBLE);
			btn_submit.setVisibility(View.VISIBLE);
		}
		showPromptInfo();

	}

	private void showToaskUtil(String promptStr) {
		Toast.makeText(GprsTestActivity.this, promptStr, Toast.LENGTH_LONG).show();
	}

	private Button.OnClickListener btnListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOpen:
				final String c = et_one.getText().toString();
				if (TextUtils.isEmpty(c)) {
					showToaskUtil("请在下方输入打开GPRS的密码");
					return;
				}
				final String d = shardUtil.getString(gprsPass, "");
				if (c.equals(d)) {
					gprsEnable(true);
				} else {
					showToaskUtil("输入的密码不正确...");
				}
				break;
			case R.id.btnShut:

				gprsEnable(false);
				break;
			case R.id.btn_submit:
				final String a = et_one.getText().toString();
				final String b = et_two.getText().toString();
				if (TextUtils.isEmpty(a) || TextUtils.isEmpty(a)
						|| a.length() < 4) {
					showToaskUtil("请输入密码,且不得少于4位数");
				}
				if (!a.equals(b)) {
					showToaskUtil("两次输入的密码不一致");
				}
				shardUtil.saveString(gprsPass, a);
				et_one.setText("");
				et_two.setVisibility(View.GONE);
				btn_submit.setVisibility(View.GONE);
				openButton.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}

	};

	// �򿪻�ر�GPRS
	private boolean gprsEnable(boolean bEnable) {
		Object[] argObjects = null;
		// 判断GPRS状态
		boolean isOpen = gprsIsOpenMethod("getMobileDataEnabled");
		if (isOpen == !bEnable) {
			setGprsEnable("setMobileDataEnabled", bEnable);
		}

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				showPromptInfo();
			}
		}, 3000);

		return isOpen;
	}

	int a = 0;

	private void showPromptInfo() {
		boolean gprsStu;
		if (gprsIsOpenMethod("getMobileDataEnabled")) {
			tv_statu.setText(R.string.gprs_opening);
			gprsStu = true;
		} else {
			gprsStu = false;
			tv_statu.setText(R.string.gprs_close);
		}
		Log.i("aaaa", "click num is " + a++ + "gprs stuta is " + gprsStu);
	}

	// ���GPRS�Ƿ��
	private boolean gprsIsOpenMethod(String methodName) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = null;
		Object[] argObject = null;

		Boolean isOpen = false;
		try {
			Method method = cmClass.getMethod(methodName, argClasses);

			isOpen = (Boolean) method.invoke(mCM, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isOpen;
	}

	// ����/�ر�GPRS
	private void setGprsEnable(String methodName, boolean isEnable) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = new Class[1];
		argClasses[0] = boolean.class;

		try {
			Method method = cmClass.getMethod(methodName, argClasses);
			method.invoke(mCM, isEnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}