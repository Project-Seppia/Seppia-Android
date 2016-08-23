package com.seppia.android.project_seppia.task.frame;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.seppia.android.project_seppia.utils.DialogUtils;
import com.seppia.android.project_seppia.utils.LogUtils;

/**
 * 异步请求任务基本类
 */
public abstract class BaseTask<T> extends AsyncTask<Object, Integer, T> {

	protected ProgressDialog mPdlg; // Loading框
	private Context mContext;
	private String mDlgMsg; // Loading时显示的提示文字
	private boolean mIndeterminate = true; // Loading的进度是否不确定
	private int mMax = 100; // Loading的进度最大值
	private boolean mShowLoading = false; // 是否显示等待框

	/*--------------------------------------------------------------------------
	| 抽象方法
	--------------------------------------------------------------------------*/
	abstract protected void onPreTask();

	abstract protected T onInTask(Object... obj);

	abstract protected void onPostTask(T result);

	/*--------------------------------------------------------------------------
	| 构造方法
	--------------------------------------------------------------------------*/
	public BaseTask(Context context, ProgressDialog dlg) {
		mContext = context;
		mPdlg = dlg;
	}

	public BaseTask(Context context) {
		this(context, false, "", true, 100);
	}

	public BaseTask(Context context, boolean showLoading, String msg) {
		this(context, showLoading, msg, true, 100);
	}

	public BaseTask(Context context, boolean showLoading, String msg,
			boolean indeterminate, int max) {
		mContext = context;
		mShowLoading = showLoading;
		mDlgMsg = msg;
		mIndeterminate = indeterminate;
		mMax = max;
	}

	/*--------------------------------------------------------------------------
	| 重写父类方法
	--------------------------------------------------------------------------*/
	@Override
	protected void onPreExecute() {
		try {
			if (mDlgMsg != null && mShowLoading) {
				mPdlg = DialogUtils.showProgressDialog(mContext, mDlgMsg,
						mIndeterminate, mMax,
						new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								cancel(true);
							}
						});
			}
			onPreTask();
		} catch (Exception e) {
			log(e);
		}
	}

	@Override
	protected T doInBackground(Object... obj) {
		try {
			return onInTask(obj);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	@Override
	protected void onPostExecute(T result) {
		try {
			DialogUtils.dismissProgressDialog(mPdlg);
			if (isCancelled()) {
				return;
			}
			onPostTask(result);
		} catch (Exception e) {
			log(e);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... p) {
		try {
			if (isCancelled()) {
				return;
			}
		} catch (Exception e) {
			log(e);
		}
	}

	/*--------------------------------------------------------------------------
	| 其他
	--------------------------------------------------------------------------*/
	public Context getContext() {
		return mContext;
	}

	public boolean cancelTask() {
		dismissProgressDialog();
		return cancel(true);
	}

	protected void showProgressDialog() {
		try {
			if (mPdlg != null && !mPdlg.isShowing()) {
				mPdlg.show();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	protected void dismissProgressDialog() {
		try {
			if (mPdlg != null && mPdlg.isShowing()) {
				mPdlg.dismiss();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	protected void setProgress(int progress) {
		mPdlg.setProgress(progress);
	}

	protected void log(String msg) {
		LogUtils.logD(getClass().getName(), msg);
	}

	protected void log(Throwable tr) {
		LogUtils.logE(getClass().getName(), tr.getLocalizedMessage(), tr);
	}
}
