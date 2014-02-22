package com.siqiao.sdk.pull_listview.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siqiao.sdk.common.other.util.SharedPreferencesUtil;
import com.siqiao.sdk.common.util.DateAndStringUtil;
import com.siqiao.sdk.customlistview.R;


public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView mHeaderImage;
	private final ProgressBar mHeaderProgress;
	private final TextView mHeaderText;
	private final TextView mTimeText;

	private String mPullLabel;
	private String mRefreshingLabel;
	private String mReleaseLabel;
	private SharedPreferencesUtil spUtil;

	private final Animation mRotateAnimation, mResetRotateAnimation;

	public LoadingLayout(Context context, int mode, String releaseLabel,
			String pullLabel, String refreshingLabel, TypedArray attrs,
			String tag) {
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, this);
		mHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		mHeaderImage = (ImageView) header
				.findViewById(R.id.pull_to_refresh_image);
		mHeaderProgress = (ProgressBar) header
				.findViewById(R.id.pull_to_refresh_progress);
		mTimeText = (TextView) header
				.findViewById(R.id.pull_to_refresh_updated_time);

		final Interpolator interpolator = new LinearInterpolator();
		mRotateAnimation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setInterpolator(interpolator);
		mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);

		mResetRotateAnimation = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mResetRotateAnimation.setInterpolator(interpolator);
		mResetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);

		mReleaseLabel = releaseLabel;
		mPullLabel = pullLabel;
		mRefreshingLabel = refreshingLabel;

		switch (mode) {
		case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
			mHeaderImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
			break;
		case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
		default:
			mHeaderImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
			break;
		}

		if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextColor)) {
			final int color = attrs.getColor(
					R.styleable.PullToRefresh_ptrHeaderTextColor, Color.BLACK);
			setTextColor(color);
		}
		spUtil = new SharedPreferencesUtil(context, SharedPreferencesUtil.S_H_SERVICEADDRS_FLAG);

		if (!DateAndStringUtil.isEmpty(tag)) {
			String time = spUtil.getString(tag, "");
			if (!DateAndStringUtil.isEmpty(time)) {
				mTimeText.setText("上次更新时间:" + time);
			} else {
				mTimeText.setText("上次更新时间:" + DateAndStringUtil.getNowPullTime());
				spUtil.saveString(tag, DateAndStringUtil.getNowPullTime());
			}
		}
	}

	public void reset(String tag) {
		mHeaderText.setText(mPullLabel);
		mHeaderImage.setVisibility(View.VISIBLE);
		mHeaderProgress.setVisibility(View.GONE);
		if (!DateAndStringUtil.isEmpty(tag)) {
			spUtil.saveString(tag, DateAndStringUtil.getNowPullTime());
		}
	}

	public void releaseToRefresh() {
		mHeaderText.setText(mReleaseLabel);
		mHeaderImage.clearAnimation();
		mHeaderImage.startAnimation(mRotateAnimation);
	}

	public void setPullLabel(String pullLabel) {
		mPullLabel = pullLabel;
	}

	public void refreshing(String tag) {
		mHeaderText.setText(mRefreshingLabel);
		mHeaderImage.clearAnimation();
		mHeaderImage.setVisibility(View.GONE);
		mHeaderProgress.setVisibility(View.VISIBLE);
		if (!DateAndStringUtil.isEmpty(tag)) {
			String time = spUtil.getString(tag, "");
			if (!DateAndStringUtil.isEmpty(time)) {
				mTimeText.setText("上次更新时间:" + time);
			} else {
				mTimeText.setText("上次更新时间:" + DateAndStringUtil.getNowPullTime());
			}
		}
		spUtil.saveString(tag, DateAndStringUtil.getNowPullTime());
	}

	public void setRefreshingLabel(String refreshingLabel) {
		mRefreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		mReleaseLabel = releaseLabel;
	}

	public void pullToRefresh() {
		mHeaderText.setText(mPullLabel);
		mHeaderImage.clearAnimation();
		mHeaderImage.startAnimation(mResetRotateAnimation);
	}

	public void setTextColor(int color) {
		mHeaderText.setTextColor(color);
	}

}
