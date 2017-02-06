package dataStructs;

/**
 * Description Class for Hotel objects
 * @author Gsquared
 *
 */
public class Hotel {

	private int id;
	private String name;
	private int stars;
	private int numberOfRooms;
	private Reservation[] reservationsList; 
	
	/**
	 * Description Constructor
	 * @param initID
	 * @param initName
	 * @param initStars
	 * @param initNum
	 * @param initReserv
	 */
	public Hotel(int initID,String initName,int initStars,int initNum
			,Reservation[] initReserv
			){
		this.id = initID;
		this.name = initName;
		this.stars = initStars;
		this.numberOfRooms = initNum;
		this.reservationsList = initReserv;
	}
	/**
	 * 
	 * @return int
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getStars(){
		return this.stars;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getNumberOfRooms(){
		return this.numberOfRooms;
	}
	
	/**
	 * 
	 * @return Reservation[]
	 */
	public Reservation[] getReservations(){
		return this.reservationsList;
	}
	
	/**
	 * 
	 * @param i
	 * @return Reservation
	 */
	public Reservation getReservation(int i){
		return this.reservationsList[i];
	}
			
}
