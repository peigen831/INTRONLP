package common;

public class Date {
	
	private int year;
	private int month;
	private int day;
	
	public Date() {
		setYear(-1);
		setMonth(-1);
		setDay(-1);
	}
	
	public Date(int day, int month, int year) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}
	
	public Date(int day, String month, int year) {
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
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setMonth(String month) {
		this.month = 12;
		switch (month) {
			case "January":   this.month--;
			case "February":  this.month--;
			case "March":     this.month--;
			case "April":     this.month--;
			case "May":       this.month--;
			case "June":      this.month--;
			case "July":      this.month--;
			case "August":    this.month--;
			case "September": this.month--;
			case "October":   this.month--;
			case "November":  this.month--;
			case "December":  this.month--;
			default:          this.month = -1;
		}
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
}
