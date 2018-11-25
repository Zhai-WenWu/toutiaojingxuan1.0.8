package mvp.cn.util;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
	public static SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat HHmmssNoColon = new SimpleDateFormat("HHmmss");
	public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static SimpleDateFormat MMddYYYYHHmmss = new SimpleDateFormat("MMddyyyyHHmmss");
	public static SimpleDateFormat MMddHHmmss = new SimpleDateFormat("MMddHHmmss");
	public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	public static SimpleDateFormat shortyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	public static SimpleDateFormat yyyy_MM_dde = new SimpleDateFormat("yyyy-MM-dd E");
	public static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static SimpleDateFormat yyyy_MM_dd_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	public static String getCurrentDateTimeyyyyMMddHHmmss() {
		return yyyyMMddHHmmss.format(new Date());
	}

	public static String getHHmmss() {
		return HHmmss.format(new Date());
	}

	/**
	 * 将这种类型yyyy-MM-dd HH:mm:ss的时间转化为long类型的
	 * @param serverTimeString
	 * @return
	 */
	public static long getMillisecondsFromString(String serverTimeString){
		if(null != serverTimeString && !"".equals(serverTimeString)){
			return 0;
		}
		
		long millisecond;
		try {
			millisecond = yyyy_MM_dd_HHmmss.parse(serverTimeString).getTime();
			return millisecond;
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 将毫秒数转化成yyyy-MM-dd HH:mm:ss类型的日期
	 * @param milliseconds
	 * @return
	 */
	public static String getStringDateFromMilliseconds(long milliseconds){
		if(milliseconds == 0){
			return "";
		}
		String string;
		Date date = new Date(milliseconds);
		string = yyyy_MM_dd_HHmmss.format(date);
		return string;
	}
	
	/**
	 * 将毫秒数转化成yyyy-MM-dd HH:mm:ss类型的日期
	 * @param milliseconds
	 * @return
	 */
	public static String getOnlyDateFromMilliseconds(long milliseconds){
		if(milliseconds == 0){
			return "";
		}
		String string;
		Date date = new Date(milliseconds);
		string = yyyyMMdd.format(date);
		return string;
	}
	
	public static String getTime(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				return getCurrentDateTimeyyyyMMddHHmmss();
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return yyyyMMddHHmmss.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return getCurrentDateTimeyyyyMMddHHmmss();
		}
	}
	
	public static String getyyyy_MM_ddTime(Date date){
		return yyyyMMdd.format(date);
	}
	
	
	public static String getTimeyyyy_MM_dd_HH_mm_ss(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				return yyyy_MM_dd_HHmmss.format(new Date());
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return yyyy_MM_dd_HHmmss.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return yyyy_MM_dd_HHmmss.format(new Date());
		}
	}

	/**
	 * 将"yyyy-MM-dd HH:mm:ss"转换成Date
	 * @param timeStr
	 * @return
     */
	public static Date getDateFromyyyy_MM_dd_HHmmss(String timeStr){
		Date date = new Date();
		try {
			if(!TextUtils.isEmpty(timeStr)){
				date = yyyy_MM_dd_HHmmss.parse(timeStr);
			}
		}catch (ParseException e){
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据毫秒数 获取天数
	 * @param millisSeconds
	 * @return
	 */
	public static String getStringFromMillisSeconds(long millisSeconds){
		String string = "";
		long days = millisSeconds/1000/60/60/24;
		long hours = (millisSeconds - days*24*60*60*1000)/1000/60/60;
		long mins = (millisSeconds - days*24*60*60*1000 - hours*60*60*1000)/1000/60;
		long seconds = (millisSeconds - days*24*60*60*1000 - hours*60*60*1000 - mins*60*1000)/1000;
		string = days+"天"+hours+"小时"+mins+"分钟"+seconds+"秒";
		return string;
	}
	
	public static String getTimeMMddYYYYHHmmss(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				return MMddYYYYHHmmss.format(new Date());
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return MMddYYYYHHmmss.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return MMddYYYYHHmmss.format(new Date());
		}
	}
	public static String getTimeMMddHHmmss(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				return MMddHHmmss.format(new Date());
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return MMddHHmmss.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return MMddHHmmss.format(new Date());
		}
	}
	
	public static long getTimeLong(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				return new Date().getTime();
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				return timeSum;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date().getTime();
		}
	}
	
	public static String getDate(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				Date date = new Date();
				return shortyyyyMMdd.format(date);
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return shortyyyyMMdd.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return getDate(new Date());
		}
	}

	public static String getDateyyyy_MM_dd(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				Date date = new Date();
				return yyyyMMdd.format(date);
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return yyyyMMdd.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return getDate(new Date());
		}
	}
	
	public static String getOnlytime(String timeStr, long time) {
		try {
			if(TextUtils.isEmpty(timeStr)){
				return HHmmssNoColon.format(new Date());
			}else{
				long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
				Date date = new Date(timeSum);
				return HHmmssNoColon.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return HHmmssNoColon.format(new Date());
		}
	}
	public static String getOnlytime(Date date) {
		return HHmmssNoColon.format(date);
	}
	
	public static String getDate(Date date) {
		return yyyyMMdd.format(date);
	}

	public static String getMMddHHmmssDate(Date date) {
		return MMddHHmmss.format(date);
	}

	public static String getTime() {
		return HHmmss.format(new Date());
	}

	public static Long getCurrentMilliseconds() {
		return (new Date().getTime());
	}

	public static String formatDate(String date) {
		try {
			Date d = yyyyMMdd.parse(date);
			return yyyyMMdd.format(d);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDate(SimpleDateFormat sdf, String date) {
		try {
			Date d = sdf.parse(date);
			String result = sdf.format(d);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date addDate(Date dt, int num) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DATE, num);// 你要加减的日
		Date result = rightNow.getTime();
		return result;
	}
	
	/**
	 * 获取当前时间的字符串用来给图片命名
	 * @return
	 */
	public static String getCurrentTimeForPicName(){
		Date date = new Date();
		String format = yyyyMMdd_HHmmss.format(date);
		return format;
	}

	private static boolean isUse;// 是否已经调用过了
	private static boolean isUseTime;// 是否已经调用过了

	/**
	 * 选择日期对话框：
	 */
	public static void showDatePickerDialog(final Context context, String initBirth, final OnSelectDateListener onSelectDateListener, final boolean isTodayMore) {
		if (StringUtil.isNullOrEmpty(initBirth)) {
			initBirth = getOnlyDateFromMilliseconds(System.currentTimeMillis());
		}
		int chooseYear1 = 0;
		int chooseMonth1 = 0;
		int chooseDay1 = 0;
		try {
			chooseYear1 = Integer.parseInt(initBirth.substring(0, 4));
			if ("0".equals(initBirth.charAt(5) + "")) {
				chooseMonth1 = Integer.parseInt(initBirth.substring(6, 7));
			} else {
				chooseMonth1 = Integer.parseInt(initBirth.substring(5, 7));
			}
			if ("0".equals(initBirth.charAt(8) + "")) {
				chooseDay1 = Integer.parseInt(initBirth.substring(9, 10));
			} else {
				chooseDay1 = Integer.parseInt(initBirth.substring(8, 10));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		isUse = false;

		/**
		 * 系统日期选择的dialog
		 */
		DatePickerDialog datePicker = new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				String mYear = year + "";
				String month =  addZreoIfLessThanTen(monthOfYear + 1);
				String day =  addZreoIfLessThanTen(dayOfMonth);
				if (!isUse) {
					try {
						if (isTodayMore) {
							String date = mYear + "-" + month + "-" + day;
							if (!yyyyMMdd.format(new Date()).equals(date) && yyyyMMdd.parse(date).getTime() < (new Date()).getTime()) {
								Toast.makeText(context, "不能小于当前日期", Toast.LENGTH_SHORT).show();
								isUse = true;
								return;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (onSelectDateListener != null) {
						onSelectDateListener.onSelectDate(mYear, month, day);
					}
					isUse = true;
				}
			}
		}, chooseYear1, (chooseMonth1 - 1), chooseDay1);
		datePicker.show();
	}

	/**
	 * 选择时间对话框：
	 */
	public static void showTimePickerDialog(Context context, final OnSelectTimeListener onSelectTimeListener) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		isUseTime = false;
		/**
		 * 系统日期选择的dialog
		 */
		TimePickerDialog timePicker = new TimePickerDialog(context, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String mHour =  addZreoIfLessThanTen(hourOfDay);
				String mMinute = addZreoIfLessThanTen(minute);

				if (!isUseTime) {
					if (onSelectTimeListener != null) {
						onSelectTimeListener.onSelectTime(mHour, mMinute);
					}
					isUseTime = true;
				}
			}
		}, hour, minute, true);

		LogUtil.log("sdgdfg");
		timePicker.show();
	}

	public interface OnSelectDateListener {
		public void onSelectDate(String year, String month, String day);
	}

	public interface OnSelectTimeListener {
		public void onSelectTime(String hour, String minute);
	}
	
	/**
	 * 如果i小于10，添�?后生成string
	 * 
	 * @param i
	 * @return
	 */
	public static String addZreoIfLessThanTen(int i) {
		String string = "";
		if (i < 10) {
			string = "0" + i;
		} else {
			string = i + "";
		}
		return string;
	}

	/**
	 * 显示几个小时前,几分钟前
	 * 
	 * @param timeStr
	 *            "2015-05-12T14:00:17.17"
	 * @return
	 */
	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();

		try {
			long t = yyyy_MM_dd_HHmmss.parse(timeStr).getTime();

			long time = System.currentTimeMillis() -  t ;
			long mill = (long) Math.ceil(time / 1000);// 秒前

			long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

			long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

			long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

			if (day - 2 > 0) {
				return timeStr ;
			} else if (day - 1 > 0) {
				sb.append(day + "天");
			} else if (hour - 1 > 0) {
				if (hour >= 24) {
					sb.append("1天");
				} else {
					sb.append(hour + "小时");
				}
			} else if (minute - 1 > 0) {
				if (minute == 60) {
					sb.append("1小时");
				} else {
					sb.append(minute + "分钟");
				}
			}
//			else if (mill - 1 > 0) {
//				if (mill == 60) {
//					sb.append("1分钟");
//				} else {
//					sb.append(mill + "秒");
//				}
//			} 
			else {
				sb.append("刚刚");
			}
			if (!sb.toString().equals("刚刚")) {
				sb.append("前");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
