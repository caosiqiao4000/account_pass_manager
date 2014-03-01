package com.shai.manage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.PassSetting;

/**
 * 
 * @author Administrator
 * 
 */
public class CustomDialogFragment extends DialogFragment {
	int mNum;
	private Activity activity;
	private LayoutInflater inflater;
	private String title;
	private String message;
	private String positiveButtonText;
	private String negativeButtonText;
	private View contentView;
	private DialogInterface.OnClickListener positiveButtonClickListener,
			negativeButtonClickListener;

	public CustomDialogFragment() {
		super();
	}

	public static CustomDialogFragment newInstance(Activity activity,
			int num) {
		CustomDialogFragment frag = new CustomDialogFragment();
		frag.activity = activity;
		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		frag.setArguments(args);
		return frag;
	}

	/**
	 * Set the Dialog message from String
	 * 
	 * @param title
	 * @return
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Set the Dialog message from resource
	 * 
	 * @param title
	 * @return
	 */
	public void setMessage(int message) {
		this.message = (String) activity.getText(message);
	}

	/**
	 * Set the Dialog title from resource
	 * 
	 * @param title
	 * @return
	 */
	public void setTitle(int title) {
		this.title = (String) activity.getText(title);
	}

	/**
	 * Set the Dialog title from String
	 * 
	 * @param title
	 * @return
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set a custom content view for the Dialog. If a message is set, the
	 * contentView is not added to the Dialog...
	 * 
	 * @param v
	 * @return
	 */
	public void setContentView(View v) {
		this.contentView = v;
	}

	/**
	 * Set the positive button resource and it's listener
	 * 
	 * @param positiveButtonText
	 * @param listener
	 * @return
	 */
	public void setPositiveButton(int positiveButtonText,
			DialogInterface.OnClickListener listener) {
		this.positiveButtonText = (String) activity.getText(positiveButtonText);
		this.positiveButtonClickListener = listener;
	}

	/**
	 * Set the positive button text and it's listener
	 * 
	 * @param positiveButtonText
	 * @param listener
	 * @return
	 */
	public void setPositiveButton(String positiveButtonText,
			DialogInterface.OnClickListener listener) {
		this.positiveButtonText = positiveButtonText;
		this.positiveButtonClickListener = listener;
	}

	/**
	 * Set the negative button resource and it's listener
	 * 
	 * @param negativeButtonText
	 * @param listener
	 * @return
	 */
	public void setNegativeButton(int negativeButtonText,
			DialogInterface.OnClickListener listener) {
		this.negativeButtonText = (String) activity.getText(negativeButtonText);
		this.negativeButtonClickListener = listener;
	}

	/**
	 * Set the negative button text and it's listener
	 * 
	 * @param negativeButtonText
	 * @param listener
	 * @return
	 */
	public void setNegativeButton(String negativeButtonText,
			DialogInterface.OnClickListener listener) {
		this.negativeButtonText = negativeButtonText;
		this.negativeButtonClickListener = listener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments().getInt("num");
		// Pick a style based on the num.
		int style = DialogFragment.STYLE_NORMAL, theme = 0;
		switch ((mNum - 1) % 6) {
		case 1:
			style = DialogFragment.STYLE_NO_TITLE;
			break;
		case 2:
			style = DialogFragment.STYLE_NO_FRAME;
			break;
		case 3:
			style = DialogFragment.STYLE_NO_INPUT;
			break;
		case 4:
			style = DialogFragment.STYLE_NORMAL;
			break;
		case 5:
			theme = R.style.Dialog;
			break;
		}
		switch ((mNum - 1) % 6) {
		case 1:
			theme = android.R.style.Theme_Holo;
			break;
		case 2:
			theme = android.R.style.Theme_Holo_Light_Dialog;
			break;
		case 3:
			theme = android.R.style.Theme_Holo_Light;
			break;
		case 4:
			theme = android.R.style.Theme_Holo_Light_Panel;
			break;
		case 5:
			theme = android.R.style.Theme_Holo_Light;
			break;
		}
		setStyle(style, theme);
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (PassSetting.Debug) {
			Log.e(PassSetting.Debug_flag, "CustomDialogFragment onCreateView: ");
		}
		final Dialog dialog = getDialog();
		View layout = inflater.inflate(R.layout.dialog_custom_alert, null);
		// dialog.addContentView(layout, new LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		// set the dialog title
		((TextView) layout.findViewById(R.id.title)).setText(title);
		// set the confirm button
		if (positiveButtonText != null) {
			((Button) layout.findViewById(R.id.positiveButton))
					.setText(positiveButtonText);
			if (positiveButtonClickListener != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								positiveButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_POSITIVE);
							}
						});
			}
		} else {
			// if no confirm button just set the visibility to GONE
			layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
		}
		// set the cancel button
		if (negativeButtonText != null) {
			Button negativeBtn = ((Button) layout
					.findViewById(R.id.negativeButton));
			negativeBtn.setText(negativeButtonText);
			if (negativeButtonClickListener != null) {
				negativeBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						negativeButtonClickListener.onClick(dialog,
								DialogInterface.BUTTON_NEGATIVE);
					}
				});
			} else {
				negativeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dismiss(); // 默认关闭对话框
					}
				});
			}
		} else {
			// if no confirm button just set the visibility to GONE
			layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
		}
		// set the content message
		if (message != null) {
			((TextView) layout.findViewById(R.id.message)).setText(message);
		} else if (contentView != null) {
			// if no message set
			// add the contentView to the dialog body
			((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
			((LinearLayout) layout.findViewById(R.id.content)).addView(
					contentView, new LayoutParams(LayoutParams.FILL_PARENT,
							LayoutParams.FILL_PARENT));
		}
//		 ((Dialog) dialog).setContentView(layout);
		// return dialog;
		return layout;
	}

	// @Override
	// public AlertDialog onCreateDialog(Bundle savedInstanceState) {
	// // instantiate the dialog with the custom Theme
	// // AlertDialog dialog = new AlertDialog(getActivity(),R.style.Dialog);
	// AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	// final AlertDialog dialog = builder.create();
	// inflater = activity.getLayoutInflater();
	// View layout = inflater.inflate(R.layout.dialog_custom_alert, null);
	// dialog.addContentView(layout, new LayoutParams(
	// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	// set the dialog title
	// ((TextView) layout.findViewById(R.id.title)).setText(title);
	// set the confirm button
	// if (positiveButtonText != null) {
	// ((Button) layout.findViewById(R.id.positiveButton))
	// .setText(positiveButtonText);
	// if (positiveButtonClickListener != null) {
	// ((Button) layout.findViewById(R.id.positiveButton))
	// .setOnClickListener(new View.OnClickListener() {
	// public void onClick(View v) {
	// positiveButtonClickListener.onClick(dialog,
	// DialogInterface.BUTTON_POSITIVE);
	// }
	// });
	// }
	// } else {
	// // if no confirm button just set the visibility to GONE
	// layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
	// }
	// // set the cancel button
	// if (negativeButtonText != null) {
	// Button negativeBtn = ((Button) layout
	// .findViewById(R.id.negativeButton));
	// negativeBtn.setText(negativeButtonText);
	// if (negativeButtonClickListener != null) {
	// negativeBtn.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// negativeButtonClickListener.onClick(dialog,
	// DialogInterface.BUTTON_NEGATIVE);
	// }
	// });
	// } else {
	// negativeBtn.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View v) {
	// dismiss(); // 默认关闭对话框
	// }
	// });
	// }
	// } else {
	// // if no confirm button just set the visibility to GONE
	// layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
	// }
	// // set the content message
	// if (message != null) {
	// ((TextView) layout.findViewById(R.id.message)).setText(message);
	// } else if (contentView != null) {
	// // if no message set
	// // add the contentView to the dialog body
	// ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
	// ((LinearLayout) layout.findViewById(R.id.content)).addView(
	// contentView, new LayoutParams(LayoutParams.FILL_PARENT,
	// LayoutParams.FILL_PARENT));
	// }
	// dialog.setContentView(layout);
	// return dialog;
	// }
}
