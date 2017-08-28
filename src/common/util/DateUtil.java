package common.util;

public class DateUtil {
	private static long startTime;
	private static long endTime;
	public static void setStartTime(){
		startTime = System.currentTimeMillis()/1000;
	}
	public static void setEndTime(){
		endTime = System.currentTimeMillis()/1000;
	}
	public static long getBetweenTime(){
		System.out.println(endTime - startTime +"秒");
		return endTime - startTime;
	}
}
