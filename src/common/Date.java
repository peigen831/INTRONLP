package common;

import java.util.HashMap;
import java.util.Map;

public class Date {
	
	private int year;
	private int month;
	private int day;
	
	private Map<String, String> months;
	
	public Date() {
		this(-1, "-1", -1);
	}
	
	public Date(int day, int month, int year) {
		this(day, month + "", year);
	}
	
	public Date(int day, String month, int year) {
		initializeMonths();
		setYear(year);
		setMonth(month);
		setDay(day);
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonthNumber() {
		return month;
	}
	
	public String getMonthName() {
		return getMonthName(month);
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setMonth(String month) {
		this.month = getMonthNumber(month);
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	private void initializeMonths() {
		months = new HashMap<>();
		months.put("0", "January");
		months.put("1", "February");
		months.put("2", "March");
		months.put("3", "April");
		months.put("4", "May");
		months.put("5", "June");
		months.put("6", "July");
		months.put("7", "August");
		months.put("8", "September");
		months.put("9", "October");
		months.put("10", "November");
		months.put("11", "December");
		months.put("January", "0");
		months.put("February", "1");
		months.put("March", "2");
		months.put("April", "3");
		months.put("May", "4");
		months.put("June", "5");
		months.put("July", "6");
		months.put("August", "7");
		months.put("September", "8");
		months.put("October", "9");
		months.put("November", "10");
		months.put("December", "11");
	}
	
	private int getMonthNumber(String month) {
		return Integer.parseInt(months.get(month));
	}
	
	private String getMonthName(int nMonth) {
		return months.get("" + nMonth);
	}
}
