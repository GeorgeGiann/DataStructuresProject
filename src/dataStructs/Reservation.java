package dataStructs;

import java.util.Date;
/**
 * Description: Class describing reservations
 * @author Gsqaured
 *
 */
public class Reservation {
	
	private String name;
	private Date date;
	private int stayDurationDays;
	
	/**
	 * Description: Constructor 
	 * @param initName
	 * @param initDate
	 * @param initNum
	 */
	public Reservation(String initName,Date initDate,int initNum){
		this.name = initName;
		this.date = initDate;
		this.stayDurationDays = initNum;
	}
	
	/**
	 * Description: get reservation name
	 * @return String
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Description: get reservation date
	 * @return Date
	 */
	public Date getDate(){
		return this.date;
	}
	
	/**
	 * Description: return the duration of the reservation
	 * @return int
	 */
	public int getDurationDays(){
		return this.stayDurationDays;
	}
}
