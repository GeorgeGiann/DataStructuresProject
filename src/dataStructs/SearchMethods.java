package dataStructs;


//imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
//import java.util.List;

/**
 * Description: class implementing search method and the experiments
 * @author Gsquared
 *
 */
public class SearchMethods {
	
	public static int NumberOfHotels = 0;
	public static ArrayList<Hotel> HotelList = new ArrayList<Hotel>();
	public static RedBlackTree tree = new RedBlackTree();
	public static Trie DigTrie = new Trie();
	public static int comparisons;
	public static List<Integer> Ids = new ArrayList<>();
	public static Integer IDtable[];
	public static int[][] IdRandomSet = new int[1000][10];
	
	public static double LinearTime;
	public static double BinaryTime;
	public static double InterpTime;
	public static double RBtreeTime;
	
	public static long LinearComps;
	public static long BinaryComps;
	public static long InterpComps;
	public static long RBtreeComps;
	
	public static final String LinearSt = "Linear Search";
	public static final String BinarySt = "Binary Search";
  	public static final String InterpSt = "Interpolation Search";
	public static final String RBtreeSt = "Red Black Tree Search";
	public static final String CompSt = "Comparisons";
	public static final String TimeSt = "Time";
	//private Scanner in;
	
 	public static void displayMenu(){
		System.out.println("    MENU ");
		System.out.println("1. Load Hotels and Reservations from file ");
		System.out.println("2. Save Hotels and Reservations to file ");
		System.out.println("3. Add a Hotel ");
		System.out.println("4. Search and display by hotel id ");
		System.out.println("5. Display all Hotels of specific stars category and number of reservations");
		System.out.println("6. Display Reservations by surname search");
		System.out.println("7. Exit");
		System.out.println("8. Execute Experiments and get flowcharts");
		System.out.println("Select a number between 1 and 8");
		
	}
	
	//data manipulation functions
	public static void addHotel(){
		Scanner input = new Scanner(System.in);
		ArrayList<Reservation> reserveList = new ArrayList<Reservation>();
		String reservName, name,  dateString;
		Date date = null;
		int stars, numOfRooms, answer,duration,id;
		
		id = HotelList.get(HotelList.size()-1).getId() +1;
		System.out.println("Give hotel name: ");
		name = input.next();
		System.out.println("Give stars: ");
		stars = input.nextInt();
		System.out.println("Give number of rooms: ");
		numOfRooms = input.nextInt();
		System.out.println("Do you want to add reservations? 1 for yes / 0 for no ");
		answer = input.nextInt();
		while(answer == 1)	{
			System.out.println("Give name for reservation: ");
			reservName = input.next();
			System.out.println("Give date (dd/MM/yyyy): ");
			dateString = input.next();
			try {
    			date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Give stay duration: ");
			duration = input.nextInt();
			reserveList.add(new Reservation(reservName,date,duration));
			System.out.println("Do you want to add reservations? 1 for yes / 0 for no ");
			answer = input.nextInt();
		}	
		Reservation[] reserveTable =  reserveList.toArray(new Reservation[reserveList.size()]);
		NumberOfHotels +=1;
		HotelList.add(new Hotel(id,name,stars,numOfRooms,reserveTable));
		Ids.add(id);
		tree.insert(id);
		//input.close();
	}
	
	
	/**
	 * Description: method that reads form files and stores to memory
	 * @param pathInput
	 * @throws IOException
	 */
	public static void loadFromFile(String pathInput) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(pathInput));
		String line;
        String buffstring = "";
        line = input.readLine();
       // ArrayList<Hotel> readHotelList= new ArrayList<Hotel>();
        int i=0;
        //read first lines - Dataset rows number
        while( line.charAt(i) != ';'){
        	buffstring += line.charAt(i);
        	i++;
        }
        NumberOfHotels += Integer.parseInt(buffstring);
        int id ;
        String name = "";
        int stars = 0;
        int numberOfRooms = 0;
        //read every row
        for(int hotelNum=0;hotelNum<NumberOfHotels;hotelNum++){
        	//create list to store reservations
        	ArrayList<Reservation> reserveList = new ArrayList<Reservation>();
        	buffstring = "";
        	i=0;
        	line = input.readLine();
        	
        	//read chars till first ; - id
        	while( line.charAt(i) != ';'){
	        	buffstring += line.charAt(i);
	        	i++;
	        }
        	id = Integer.parseInt(buffstring);
        	buffstring = "";
        	//read hotel name
        	i++;
        	//read chars till second ; - hotel name
        	while( line.charAt(i) != ';'){
	        	buffstring += line.charAt(i);
	        	i++;
	        }
        	name = buffstring;
        	buffstring = "";
        	//read chars till third ; - stars
        	i++;
        	while( line.charAt(i) != ';'){
	        	buffstring += line.charAt(i);
	        	i++;
	        }
        	stars =Integer.parseInt(buffstring);
        	buffstring = "";
        	//read chars till forth ; - number of rooms
        	i++;
        	while( line.charAt(i) != ';'){
	        	buffstring += line.charAt(i);
	        	i++;
	        }
        	numberOfRooms = Integer.parseInt(buffstring);
        	buffstring = "";
        	//read reservations Loop
        	String nameRes;
        	//Date date;
        	int stayDur;
        	i++;
        	// read till end of line
        	while(i<line.length()){
        		//read till first ; after last reservation - reservation name
        		while( line.charAt(i) != ';'){
        			buffstring += line.charAt(i);
    	        	i++;
    	        }
        		nameRes = buffstring;
        		buffstring = "";
        		i++;
        		//read till second ; after last reservation - date
        		while( line.charAt(i) != ';'){
        			buffstring += line.charAt(i);
    	        	i++;
    	        }
        		Date date = null;
        		//date string being read from file is transformed to formal java date format 
        		try {
        			date = new SimpleDateFormat("dd/MM/yyyy").parse(buffstring);
				} catch (ParseException e) {
					e.printStackTrace();
				}
        		buffstring = "";
        		i++;
        		while( line.charAt(i) != ';'){
        			buffstring += line.charAt(i);
    	        	i++;
    	        	if(i>=line.length()) break;
    	        }
        		stayDur = Integer.parseInt(buffstring);
        		buffstring = "";
        		i++;
        		//add a new element to reservations' list
        		reserveList.add(new Reservation(nameRes,date,stayDur));
        	} 	//end reading 
        	//cast ArrayList of reservations to array
        	Reservation[] reserveTable =  reserveList.toArray(new Reservation[reserveList.size()]);
        	//Add a new element to HotelList 
        	HotelList.add(new Hotel(id,name,stars,numberOfRooms,reserveTable));
        	//add hotel's id to Ids list 
        	Ids.add(id);
        }
        input.close();
        //store Ids' list to a table
        IDtable = Ids.toArray(new Integer[Ids.size()]);
        //sort IDtable so as to use directly for searching
		Arrays.sort(IDtable);
        //return readHotelList;
	}
	
	
	/**
	 * Description method that writes to file everything is saved in HotelList
	 * @param outputPath
	 * @throws IOException
	 */
	public static void SaveToFile(String outputPath) throws IOException {
		File outputFile = new File(outputPath);
        FileWriter fwriter = new FileWriter(outputFile);
        int style = DateFormat.MEDIUM;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        //write dataset rows' number
        fwriter.write(Integer.toString(NumberOfHotels)+";");
        //jump to next line
        fwriter.write(System.getProperty("line.separator"));
        int hotels = HotelList.size();
        //loop through every hotel in HotelList
        for(int i=0;i<hotels;i++){
        	//and write to file its attributes and its reservations' attributes
        	fwriter.write(Integer.toString(HotelList.get(i).getId())+";");
        	fwriter.write(HotelList.get(i).getName()+";");
        	fwriter.write(Integer.toString(HotelList.get(i).getStars())+";");
        	fwriter.write(Integer.toString(HotelList.get(i).getNumberOfRooms())+";");
        	Reservation[] reserveTable = HotelList.get(i).getReservations();
        	int reservations = reserveTable.length;
        	for(int j=0;j<reservations;j++){
        		fwriter.write(reserveTable[j].getName()+";");
        		fwriter.write(df.format(reserveTable[j].getDate()).toString()+";");
        		fwriter.write(Integer.toString( reserveTable[j].getDurationDays()));
        		if(j==reservations-1) fwriter.write(System.getProperty("line.separator"));
        		else fwriter.write(";");
        			
        	}
        	
        }
        IDtable = Ids.toArray(new Integer[Ids.size()]);
		Arrays.sort(IDtable);
        fwriter.close();
	}
	
	/*
	 * search functions
	*/
	
	/**
	 * Description: Search hotels by selection criteria 1)Stars 
	 * 2)Number of rooms
	 * 
	 * @param
	 * @return
	 */
	public static void searchByCategory(){
		// Display category choices
		System.out.println("Select category: ");
		System.out.println("1. Stars ");
		System.out.println("2. Number of rooms ");
		// read user choice
		Scanner input = new Scanner(System.in);
		int select = input.nextInt();
		if(select==1){ //search stars
			//read number of stars
			System.out.println("Select stars number: ");
			int stars = input.nextInt();
			int flag =0;
			//loop through every hotel and display #stars hotel atributes
			System.out.println("Hotels with "+stars+" :");
			for(int i=0;i<NumberOfHotels;i++){
				if(HotelList.get(i).getStars()==stars){
					flag = 1;
					System.out.println("ID "+HotelList.get(i).getId()+"  name: "+HotelList.get(i).getName()+
					" number of rooms: "+HotelList.get(i).getNumberOfRooms());
				}
			}
			if(flag==0) System.out.println("No hotels with: " +stars+" was found");
		}else if(select==2){ //search number of rooms
			//read number of rooms
			System.out.println("Select number of rooms: ");
			int numRooms = input.nextInt();
			int flag =0;
			System.out.println("Hotels with "+numRooms+" :");
			//loop through every hotel and display hotel attributes for every hotel that
			//has numRooms rooms
			for(int i=0;i<NumberOfHotels;i++){
				if(HotelList.get(i).getNumberOfRooms()==numRooms){
					flag = 1;
					System.out.println("ID "+HotelList.get(i).getId()+"  name: "+HotelList.get(i).getName()+
					" stars: "+HotelList.get(i).getStars());
				}
			}
			if(flag==0) System.out.println("No hotels with: " +numRooms+" was found");
		}
	}
	
	/**
	 * Description: Linear search method
	 * @param keyName
	 */
	public static void searchByName(String keyName){
		int flag =0;
		System.out.println("Name: "+keyName);
		//comparisons =0;
		//loop every hotel and display hotel attributes for every hotel with keyName name
		for(int i=0;i<NumberOfHotels;i++){
			//comparisons ++;
			Reservation[] reservationTable = HotelList.get(i).getReservations();
			for(int j=0;j<reservationTable.length;j++){
				if(reservationTable[j].getName().equals(keyName) ){
					flag =1 ;
					System.out.println("Hotel name"+HotelList.get(i).getName()+" date: "+reservationTable[j].getDate()
							+" duratio: "+reservationTable[j].getDurationDays());
				}
			}
		}
		if (flag==0) System.out.println("Was not found");
	}
	
	/**
	 * Description Method implementing binary search
	 * @param keyID
	 * @return index of the element
	 */
	public static int BinarySearch(int keyID){
		
		int n = NumberOfHotels;
		int notFound=1;
		int lowerBound = 0;
		int upperBound =  n-1;
		int midPoint=-1;
		
		//Arrays.sort(IDs);
		
		while(notFound==1){
			comparisons++;
			if(upperBound < lowerBound){
				
				break;
			}
			midPoint = lowerBound + ( upperBound - lowerBound ) / 2;
			if(IDtable[midPoint]<keyID){
				lowerBound = midPoint + 1;
			} else if (IDtable[midPoint]>keyID){
				upperBound = midPoint - 1;
			} else if(IDtable[midPoint]==keyID){
				//System.out.println(keyID+" was found at: "+midPoint);
				notFound=0;
			}
		}
		return midPoint;
	}
	
	/**
	 * Description Method implementing linear search
	 * @param keyID
	 */
	public static void searchIdLinear(int keyID){
		//int flag =0;
		for(int i=0;i<NumberOfHotels;i++){
			comparisons++;
			if(HotelList.get(i).getId()==keyID){
				break;
			}
		}
		
	}
	
	/**
	 * Description: method implementing Interpolation search
	 * @param keyID
	 * @return index of the the searching element
	 */
	public static int InterpolationSearch(int keyID){
		
		int n = NumberOfHotels;
		int notFound=1;
		int lowerBound = 0;
		int upperBound =  n-1;
		int midPoint=-1;
		
		
		
		while(notFound ==1 ){
			
			comparisons++;
			if(lowerBound >= upperBound || IDtable[lowerBound]==IDtable[upperBound]){
			//	System.out.println(keyID+" not found");
				midPoint=-1;
				break;
			} 
			
			midPoint = lowerBound + (( upperBound - lowerBound ) / (IDtable[upperBound]-IDtable[lowerBound]))*(keyID-IDtable[lowerBound]);
			if(IDtable[midPoint]==keyID){
			//	System.out.println(keyID+" was found at: "+midPoint);				
				notFound=0;
			} else if(IDtable[midPoint]<keyID) lowerBound = midPoint+1;
			  else if(IDtable[midPoint]>keyID) upperBound = midPoint-1;
			
		
		}
		
		return midPoint;
	}

	/**
	 * Description: Red Black Tree intitialization method
	 */
	public static void createRBtree(){
		int[] Ids = new int[NumberOfHotels];
		for(int i=0;i<NumberOfHotels;i++){
			Ids[i]=HotelList.get(i).getId();
		}
		//RedBlackTree tree = new RedBlackTree();
		for(int i=0;i<NumberOfHotels;i++){
			tree.insert(Ids[i]);
		}
	}
	
	/**
	 * Description: method for Trie initialization
	 */
	public static void createTrie(){
		for(int i=0; i<NumberOfHotels;i++){
			for(int j=0;j<HotelList.get(i).getReservations().length;j++){
				DigTrie.addInfo(HotelList.get(i).getReservation(j).getName(), 
						HotelList.get(i).getName(), 
						HotelList.get(i).getReservation(j).getDate(), 
						HotelList.get(i).getReservation(j).getDurationDays());
			}
		}
	}
	
	/**
	 * Description: method that creates a Random Set
	 *  id number in the range of min-max IDtable
	 */
	public static void createRandomIds(){
		for(int rep=0;rep<1000;rep++){
			for(int i=0;i<10;i++){
				IdRandomSet[rep][i] = IDtable[0] +(int)(Math.random()*IDtable[IDtable.length-1]);
			}
		}
		//return IdRandomSet;
	}
	
	/**
	 * Description: method that carries experiment for Binary search
	 */
	public static void expBinary(){
		double[] times = new double[10];
		int[] comps = new int[10];
		for(int i=0;i<10;i++){
			for(int rep=0;rep<1000;rep++){			
				comparisons=0;
				double start = System.currentTimeMillis( );
				BinarySearch(IdRandomSet[rep][i]);
				double end = System.currentTimeMillis( );
				double diff = end - start;
				times[i] +=diff;
				comps[i]+=(long)comparisons;
			}
			times[i] /=1000;
			BinaryTime += times[i];
			comps[i]/=1000;
			BinaryComps += comps[i];
		}
		BinaryTime/=10;
		BinaryComps/=10;
	}
	
	/**
	 * Description: method that carries experiment for Interpolation search
	 */
	public static void expInterpolation(){
		double[] times = new double[10];
		int[] comps = new int[10];
		for(int i=0;i<10;i++){
			for(int rep=0;rep<1000;rep++){
				comparisons=0;
				//System.out.println(rep);
				double start = System.currentTimeMillis( );
				InterpolationSearch(IdRandomSet[rep][i]);
				double end = System.currentTimeMillis( );
				double diff = end - start;
				times[i] +=diff;
				comps[i]+=comparisons;
			}
			times[i]/=1000;
			InterpTime += times[i];
			comps[i]/=1000;
			InterpComps += comps[i];
		}
		InterpTime/=10;
		InterpComps/=10;
	}
	
	/**
	 * Description method that carries experiment for Linear Search
	 */
	public static void expLinear(){
		double[] times = new double[10];
		int[] comps = new int[10];
		for(int i=0;i<10;i++){
			for(int rep=0;rep<1000;rep++){
				comparisons=0;
				double start = System.currentTimeMillis( );
				searchIdLinear(IdRandomSet[rep][i]);
				double end = System.currentTimeMillis( );
				double diff = end - start;
				times[i] +=diff;
				comps[i]+=comparisons;
			}
			times[i]/=1000;
			LinearTime += times[i];
			comps[i]/=1000;
			LinearComps +=comps[i];
		}
		LinearTime/=10;
		LinearComps/=10;
	}
	
	 
	/**
	 * Description: method that carries experiment for RedBlack Tree search
	 */
	public static void expRBsearch(){
		double[] times = new double[10];
		int[] comps = new int[10];
		for(int i=0;i<10;i++){
			for(int rep=0;rep<1000;rep++){
				tree.comparisons=0;
				double start = System.currentTimeMillis( );
				tree.search(IdRandomSet[rep][i]);
				double end = System.currentTimeMillis( );
				double diff = end - start;
				times[i] +=diff;
				//System.out.print(tree.comparisons+" ");
				comps[i]+=tree.comparisons;
			}
			times[i]/=1000;
			RBtreeTime += times[i];
			comps[i]/=1000;
			RBtreeComps += comps[i];
		}
		RBtreeTime/=10;
		RBtreeComps/=10;
	}

	/**
	 * Description: Method for execution and comparison of the search methods
	 * @throws IOException
	 */
	public static void NumericIdSearches() throws IOException{
		createRandomIds();
		expLinear();
		System.out.println("Linear Search: Time: "+LinearTime + " Comps: "+LinearComps);
		expBinary();
		System.out.println("Binary Search: Time: "+BinaryTime + " Comps: "+BinaryComps);
		expInterpolation();
		System.out.println("Interpolation Search: Time: "+InterpTime + " Comps: "+InterpComps);
		expRBsearch();
		System.out.println("RBtree Search: Time: "+RBtreeTime + " Comps: "+RBtreeComps);
				
		final DefaultCategoryDataset Tdataset = new DefaultCategoryDataset( );
		final DefaultCategoryDataset Sdataset = new DefaultCategoryDataset( );
		Tdataset.addValue(LinearTime,LinearSt,TimeSt);
		Sdataset.addValue(LinearComps,LinearSt,CompSt);
		Tdataset.addValue(BinaryTime,BinarySt,TimeSt);
		Sdataset.addValue(BinaryComps,BinarySt,CompSt);
		Tdataset.addValue(InterpTime,InterpSt,TimeSt);
		Sdataset.addValue(InterpComps,InterpSt,CompSt);
		Tdataset.addValue(RBtreeTime,RBtreeSt,TimeSt);
		Sdataset.addValue(RBtreeComps,RBtreeSt,CompSt);
		
		JFreeChart barChart = ChartFactory.createBarChart("Chart","Time","Search Method",Tdataset,PlotOrientation.VERTICAL, 
		         true, true, false);
		int width = 640; /* Width of the image */
	    int height = 480; /* Height of the image */ 
	    File BarChartTime = new File( "TimeChart.jpeg" ); 
	    ChartUtilities.saveChartAsJPEG( BarChartTime , barChart , width , height );
	    
	    barChart = ChartFactory.createBarChart("Chart","Comparisons","Search Method",Sdataset,PlotOrientation.VERTICAL, 
		         true, true, false);
	     /* Height of the image */ 
	    File BarChartComp = new File( "CompChart.jpeg" ); 
	    ChartUtilities.saveChartAsJPEG( BarChartComp , barChart , width , height );
	}

	
	
	public static void main(String[] args) throws IOException {
	
		String pathInput;
		if (args.length==0)  pathInput = "data.csv";
		else pathInput = args[0]; 
		
		System.out.println(pathInput);
		
		displayMenu();
		Scanner in = new Scanner(System.in);
		int menuInput = in.nextInt();
		while(menuInput<1 || menuInput >7) {
			displayMenu();
			in = new Scanner(System.in);
			menuInput = in.nextInt();
		}
		
		while(menuInput != 7){
			if(menuInput ==1 ) {
			    loadFromFile(pathInput);
				createRBtree();
				createTrie();
			}else if (menuInput ==2){
				SaveToFile("data2.csv");
			}else if (menuInput ==3) {
				addHotel();
			} else if (menuInput ==4) {
				System.out.println("Give Id: ");
				int key = in.nextInt();
				System.out.println("Select search method:");
				System.out.println("1. Linear");
				System.out.println("2. Binary");
				System.out.println("3. Interpolation");
				System.out.println("4. RedBlack Tree Search");
				int search = in.nextInt();
				if(search ==1 )	searchIdLinear(key);
				else if(search == 2) {
					int point = BinarySearch(key);
					if(point>-1)
					System.out.println("ID "+key+" belongs to Hotel with name: "+HotelList.get(point).getName()+" stars: "
							+ HotelList.get(point).getStars()+" and number of rooms: "+HotelList.get(point).getNumberOfRooms());
				} else if(search == 3){
					int point = InterpolationSearch(key);
					if(point>-1)
						System.out.println("ID "+key+" belongs to Hotel with name: "+HotelList.get(point).getName()+" stars: "
								+ HotelList.get(point).getStars()+" and number of rooms: "+HotelList.get(point).getNumberOfRooms());
				} else if (search == 4){
					RedBlackNode nodeRes = tree.search(key);
					if(nodeRes==null) System.out.println("Not found");
				}
			}else if (menuInput ==5){
				searchByCategory();
			}else if (menuInput ==6) {
				System.out.println("Give name: ");
				String name = in.next();
				System.out.println("1. Linear Search");
				System.out.println("2. Digital Trie Search");
				int search = in.nextInt();
				if(search==1) searchByName(name);
				else if(search==2) {
					TrieNode res = DigTrie.searchString(name);
					System.out.println(res.hotelname+"\n "+res.date+"\n "+res.duration);
				}
			}else if(menuInput ==8){
				NumericIdSearches();
							
			}
			displayMenu();
			menuInput = in.nextInt();	
			while(menuInput<1 || menuInput >8) {
				displayMenu();
				//in = new Scanner(System.in);
				menuInput = in.nextInt();
			}
		}
		in.close();
		
		
	}//end main
		
}//end class setup

