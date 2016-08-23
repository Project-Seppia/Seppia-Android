package com.seppia.android.project_seppia.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.seppia.android.project_seppia.R;

import java.util.Calendar;

/**
 * 弹出框工具类
 * 
 */
public final class DialogUtils {
	private static final String TAG = DialogUtils.class.getName();
	
	/**
	 * 确定是否退出app？
	 */
	public static void showConfirmExitAppDialog(final Activity activity) {
		if (activity == null) {
			throw new NullPointerException("activity can't be null");
		}
		DialogUtils.showAlert(activity, true,
				activity.getString(R.string.app_name),
				activity.getString(R.string.exit_message),
				activity.getString(R.string.dialog_ok),
				activity.getString(R.string.dialog_cancel),
				true,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						ActivityUtils.exitApp(activity);
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						return;
					}
				});
	}

	/********************************* 通用方法 ***********************************/
	/**
	 * 创建提示 对话框
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param listerner
	 */
	public static void showAlert(Context context, String title, String msg,
			DialogInterface.OnClickListener listerner) {
		try {
			Builder builder = new Builder(context);
			builder.setCancelable(false);
			builder.setTitle(title);
			builder.setMessage(msg);
			builder.setPositiveButton(android.R.string.ok, listerner);
			builder.show();
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * 自定义提示
	 * 
	 * @param ctx
	 * @param msg
	 */
	public static void showAlert(Context ctx, boolean isTwoButton,
			String title, String msg, String firstBtnName,
			String SecoundBtnName, boolean cancelable, OnClickListener... listerner) {
		try {
			// 创建提示框
			Builder builder = new Builder(ctx);
			builder.setCancelable(false);
			builder.setMessage(msg);
			builder.setTitle(title);
			// builder.setIcon(0);//去除标题图片
			builder.setPositiveButton(firstBtnName, listerner[0]);
			if (isTwoButton) {
				builder.setNegativeButton(SecoundBtnName, listerner[1]);
			}
			if(cancelable){
				builder.setCancelable(cancelable);
			}
			builder.show();
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * 创建确认对话框
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param listerner
	 */
	public static void showConfirmDialog(Context context, String title,
			String msg, DialogInterface.OnClickListener... listerner) {
		showConfirmDialog(context, title, msg, null, null, listerner);
	}

	/**
	 * 创建确认对话框
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param textOK
	 * @param textCancel
	 * @param listener
	 */
	public static void showConfirmDialog(Context context, String title,
			String msg, String textOK, String textCancel,
			DialogInterface.OnClickListener... listener) {
		try {
			Builder builder = new Builder(context);
			builder.setCancelable(false);
			builder.setTitle(title);
			builder.setMessage(msg);
			String ok = textOK == null ? context.getString(android.R.string.ok)
					: textOK;
			String cancel = textCancel == null ? context
					.getString(android.R.string.cancel) : textCancel;
			if (listener != null && listener.length > 0) {
				builder.setPositiveButton(ok, listener[0]);
				if (listener.length > 1) {
					builder.setNegativeButton(cancel, listener[1]);
				} else {
					builder.setNegativeButton(cancel, null);
				}
			} else {
				builder.setPositiveButton(ok, null);
			}
			builder.show();
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * 显示带CheckBox的确认框
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param checkText
	 * @param listerner
	 */
	public static void showConfirmAndCheckDialog(Context context, String title,
			String msg, String checkText,
			final OnConfirmAndCheckDialogClickListener... listerner) {
		showConfirmAndCheckDialog(context, title, msg, null, null, checkText,
				listerner);
	}

	/**
	 * 显示带CheckBox的确认框
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param textOK
	 * @param textCancel
	 * @param checkText
	 * @param listerner
	 */
	public static void showConfirmAndCheckDialog(Context context, String title,
			String msg, String textOK, String textCancel, String checkText,
			final OnConfirmAndCheckDialogClickListener... listerner) {
		try {
			LinearLayout view = new LinearLayout(context);
			view.setOrientation(LinearLayout.VERTICAL);
			view.setPadding(5, 5, 5, 5);
			TextView tvMsg = new TextView(context);
			tvMsg.setText(msg);
			tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			tvMsg.setTextColor(Color.WHITE);
			final CheckBox cbCheck = new CheckBox(context);
			cbCheck.setText(checkText);
			cbCheck.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			cbCheck.setTextColor(Color.WHITE);
			view.addView(tvMsg, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			view.addView(cbCheck, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			Builder builder = new Builder(context);
			builder.setCancelable(false);
			builder.setTitle(title);
			builder.setView(view);
			String ok = textOK == null ? context.getString(android.R.string.ok)
					: textOK;
			String cancel = textCancel == null ? context
					.getString(android.R.string.cancel) : textCancel;
			if (listerner != null && listerner.length > 0) {
				builder.setPositiveButton(ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								listerner[0].onClick(dialog, which,
										cbCheck.isChecked());
							}
						});
				if (listerner.length > 1) {
					builder.setNegativeButton(cancel,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									listerner[1].onClick(dialog, which,
											cbCheck.isChecked());
								}
							});
				} else {
					builder.setNegativeButton(cancel, null);
				}
			} else {
				builder.setPositiveButton(ok, null);
			}
			builder.show();
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * 显示自定义内容的对话框
	 * 
	 * @param context
	 * @param title
	 * @param view
	 * @param textLeft
	 * @param textRight
	 * @param listerner
	 */
	public static void showDialog(Context context, String title, View view,
			String textLeft, String textRight,
			DialogInterface.OnClickListener... listerner) {
		try {
			Builder builder = new Builder(context);
			builder.setCancelable(false);
			builder.setTitle(title);
			builder.setView(view);
			if (listerner != null && listerner.length > 0) {
				builder.setPositiveButton(textLeft, listerner[0]);
				if (listerner.length > 1) {
					builder.setNegativeButton(textRight, listerner[1]);
				} else {
					builder.setNegativeButton(textRight, null);
				}
			} else {
				builder.setPositiveButton(textLeft, null);
			}
			builder.show();
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * 显示设置日期对话框
	 * 
	 * @param context
	 * @param callBack
	 */
	public static void showDatePickerDialog(Context context,
			DatePickerDialog.OnDateSetListener callBack) {
		Calendar calendar = Calendar.getInstance();
		showDatePickerDialog(context, callBack, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 显示设置日期对话框
	 * 
	 * @param context
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 */
	public static void showDatePickerDialog(Context context,
			DatePickerDialog.OnDateSetListener callBack, int year,
			int monthOfYear, int dayOfMonth) {
		new DatePickerDialog(context, callBack, year, monthOfYear - 1,
				dayOfMonth).show();
	}

	/**
	 * 显示设置时间对话框
	 * 
	 * @param context
	 * @param callBack
	 */
	public static void showTimePickerDialog(Context context,
			TimePickerDialog.OnTimeSetListener callBack) {
		Calendar calendar = Calendar.getInstance();
		showTimePickerDialog(context, callBack,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true);
	}

	/**
	 * 显示设置时间对话框
	 * 
	 * @param context
	 * @param callBack
	 * @param is24HourView
	 */
	public static void showTimePickerDialog(Context context,
			TimePickerDialog.OnTimeSetListener callBack, boolean is24HourView) {
		Calendar calendar = Calendar.getInstance();
		showTimePickerDialog(context, callBack,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), is24HourView);
	}

	/**
	 * 显示设置时间对话框
	 * 
	 * @param context
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 */
	public static void showTimePickerDialog(Context context,
			TimePickerDialog.OnTimeSetListener callBack, int hourOfDay,
			int minute) {
		showTimePickerDialog(context, callBack, hourOfDay, minute, true);
	}

	/**
	 * 显示设置时间对话框
	 * 
	 * @param context
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 * @param is24HourView
	 */
	public static void showTimePickerDialog(Context context,
			TimePickerDialog.OnTimeSetListener callBack, int hourOfDay,
			int minute, boolean is24HourView) {
		new TimePickerDialog(context, callBack, hourOfDay, minute, is24HourView)
				.show();
	}

	/**
	 * 显示 长提示
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToastLong(Context context, String text) {
		Toast mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
		// ToastUtils.showMsg(context, text, Toast.LENGTH_LONG);
	}

	/**
	 * 显示 长提示
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showToastLong(Context context, int resId) {
		Toast mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
		// ToastUtils.showMsg(context, resId, Toast.LENGTH_LONG);
	}

	/**
	 * 显示 短提示
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToastShort(Context context, String text) {
		Toast mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
		// ToastUtils.showMsg(context, text, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示 短提示
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showToastShort(Context context, int resId) {
		Toast mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
		// ToastUtils.showMsg(context, resId, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示状态栏消息提示
	 * 
	 * @param context
	 * @param title
	 * @param info
	 * @param jumpClass
	 * @param id
	 */
	public static void showNotification(Context context, String title,
			String info, Class<?> jumpClass, int id) {
		Notification notification = new Notification(R.mipmap.icon, title,
				System.currentTimeMillis());
		/*
		 * Notification.FLAG_ONGOING_EVENT:
		 * 将此通知放到通知栏的"Ongoing"即"正在运行"组中，此时点击"清除通知"不能清除此消息
		 * Notification.FLAG_NO_CLEAR: 表明在点击了通知栏中的"清除通知"后，此通知不清除
		 * Notification.FLAG_AUTO_CANCEL: 表明在点击后，此通知自动清除
		 */
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(context, jumpClass);
		intent.putExtra("id", String.valueOf(notification.when));
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// setLatestEventInfo 在api-23已经删除
//		notification.setLatestEventInfo(context, title, info, contentIntent);
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(id, notification);
	}

	/**
	 * 取消显示状态栏消息提示
	 * 
	 * @param context
	 * @param id
	 */
	public static void cancelNotification(Context context, int id) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(id);
	}

	/**
	 * 
	 * @param context
	 * @param msg
	 *            提示文字
	 * @param listerner
	 *            等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context,
			String msg, DialogInterface.OnCancelListener listerner) {
		return showProgressDialog(context, msg, true, 100, listerner);
	}

	/**
	 * 显示等待框
	 * 
	 * @param context
	 * @param msg
	 *            提示文字
	 * @param isIndeterminate
	 *            进度是否不确定
	 * @param max
	 *            最大进度
	 * @param listerner
	 *            等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context,
			String msg, boolean isIndeterminate, int max,
			DialogInterface.OnCancelListener listerner) {
		ProgressDialog pdlg = createProgressDialog(context, msg,
				isIndeterminate, max, listerner);
		try {
			pdlg.show();
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
		return pdlg;
	}

	/**
	 * 创建等待框
	 * 
	 * @param context
	 * @param msg
	 *            提示文字
	 * @param listerner
	 *            等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context,
			String msg, DialogInterface.OnCancelListener listerner) {
		return createProgressDialog(context, msg, true, 100, listerner);
	}

	/**
	 * 创建等待框
	 * 
	 * @param context
	 * @param msg
	 *            提示文字
	 * @param isIndeterminate
	 *            进度是否不确定
	 * @param max
	 *            最大进度
	 * @param listerner
	 *            等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context,
			String msg, boolean isIndeterminate, int max,
			DialogInterface.OnCancelListener listerner) {
		ProgressDialog pdlg = new ProgressDialog(context);
		pdlg.setTitle("");
		pdlg.setMessage(msg);
		pdlg.setIndeterminate(isIndeterminate);
		pdlg.setCanceledOnTouchOutside(false); // 4.0下默认为true，必须显式设为false
		if (isIndeterminate) {
			pdlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		} else {
			pdlg.setMax(max);
			pdlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		}
		pdlg.setOnCancelListener(listerner);
		// pdlg.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.frame_loading));
		return pdlg;
	}

	/**
	 * 取消显示等待框
	 * 
	 * @param pdlg
	 */
	public static void dismissProgressDialog(ProgressDialog pdlg) {
		try {
			if (pdlg != null) {
				pdlg.dismiss();
			}
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * 显示下拉弹出气泡
	 * 
	 * @param context
	 * @param parent
	 *            父View
	 * @param child
	 *            弹出气泡的内容
	 * @param width
	 *            气泡宽度
	 * @param height
	 *            气泡高度
	 * @param xoff
	 *            相对于父View的X偏移
	 * @param yoff
	 *            相对于父View的Y偏移
	 * @param listener
	 *            弹出层消失的监听
	 */
	public static void showPopupDropDown(Context context, View parent,
			View child, int width, int height, int xoff, int yoff,
			final PopupWindow.OnDismissListener listener) {
		try {
			if (parent == null) {
				return;
			}
			// 弹出层显示的内容
			final PopupWindow popMain = new PopupWindow(child, width, height);
			popMain.setBackgroundDrawable(new BitmapDrawable());
			popMain.setOutsideTouchable(true);
			popMain.setFocusable(true);
			popMain.setClippingEnabled(true);
			popMain.setOnDismissListener(new PopupWindow.OnDismissListener() {
				@Override
				public void onDismiss() {
					if (listener != null) {
						listener.onDismiss();
					}
				}
			});
			popMain.showAsDropDown(parent, xoff, yoff);
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
		}
	}

	/**
	 * WheelDialog消失的监听
	 * 
	 * @author wfc
	 */
	public interface OnWheelDialogDismissListener {

		/**
		 * WheelDialog消失时调用此方法
		 */
		public void onDismiss(int value);
	}

	public interface OnConfirmAndCheckDialogClickListener {

		/**
		 * 点击时调用此方法
		 */
		public void onClick(DialogInterface dialog, int which, boolean isChecked);
	}

}
