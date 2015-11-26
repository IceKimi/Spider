package Com.Rantron.TaoBao.Spider.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	
	public static String toLocalTime(long unix) {
		Long timestamp = unix * 1000;
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}
	
	public static String toLocalDate(long unix) {
		Long timestamp = unix * 1000;
		String date = new SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date(timestamp));
		return date;
	}
	
	public static String toLocalYear(long unix) {
		Long timestamp = unix * 1000;
		String date = new SimpleDateFormat("yyyy")
				.format(new java.util.Date(timestamp));
		return date;
	}
	
	public static String toLocalMonth(long unix) {
		Long timestamp = unix * 1000;
		String date = new SimpleDateFormat("MM")
				.format(new java.util.Date(timestamp));
		return date;
	}
	public static String toLocalDay(long unix) {
		Long timestamp = unix * 1000;
		String date = new SimpleDateFormat("dd")
				.format(new java.util.Date(timestamp));
		return date;
	}

	public static String toLocalhour(long unix) {
		Long timestamp = unix * 1000;
		String date = new SimpleDateFormat("HH")
				.format(new java.util.Date(timestamp));
		return date;
	}
	
	public static int dayForWeek(String pTime)  {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date tmpDate;
		Calendar calendar = null;
		try {
			tmpDate = format.parse(pTime);
		
		
		calendar = Calendar.getInstance();
		calendar.setTime(tmpDate);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	public static long toUnixTime(String local){
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long unix = 0l;
	    try {
	        unix = df.parse(local).getTime();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return unix;
	}
	
	public static long toUnixDate(String local){
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    long unix = 0l;
	    try {
	        unix = df.parse(local).getTime();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return unix/1000;
	}
	
	public static long GetCurrentUnixDate()
	{
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.MILLISECOND, 0); 
		return  (cal.getTimeInMillis()/1000); 
	}
	
	
	
}
