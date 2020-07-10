import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * Find the average time between Transaction(s) : (Question 2)
 * 
 * @implNote taken assumption that all required information is there in input data 
 * @author Sai Kumar Nakirekanti
 *
 */
public class TransactionsAverageTime {
	
	public static void main(String[] args) {
		try {
		      File myObj = new File("input.txt");
		      Scanner myReader = new Scanner(myObj);
		      String[] time;
		      Date currentDate = null;
		      HashMap<String, Date[]> map = new HashMap<>();
		      String line="";
		      
		      while (myReader.hasNextLine()) {
		    	  
		    	line = myReader.nextLine();
		    	
		    	if(!line.isEmpty()) {
		    		String[] arr=  line.trim().split(",");
		        	time = arr[2].trim().split("[^0-9]");

			        if(arr[2].contains("pm")) {
			        	 currentDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(arr[1].trim().replaceAll("[^0-9]+", "-")+ " "+
			        			 (Integer.valueOf(time[0])+ 12) + ":" + time[1]+ ":00");
			        } else {
			        	 currentDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(arr[1].trim().replaceAll("[^0-9]+", "-")+ " "+
			        			time[0] + ":" + time[1]+ ":00");
			        }
				    
				    if(arr[3].trim().contains("start")) {
				    	
				    	Date[] dates = map.get(arr[0].trim());
				    	if(dates == null) {
				    		dates = new Date[2];
				    	}
				    	dates[0] = currentDate;
			    		map.put(arr[0].trim(), dates);
				    	
				    } else {
				    	
				    	Date[] dates = map.get(arr[0].trim());
				    	if(dates == null) {
				    		dates = new Date[2];
				    	}
				    	dates[1] = currentDate;
			    		map.put(arr[0].trim(), dates);
				    }
		    	}
		      }
		      myReader.close();
		      
		      
		      for (Map.Entry<String,Date[]> entry : map.entrySet()) {
		          
		    	  Date[] dates = entry.getValue();
		    	  if(dates[0] != null && dates[1] !=null) {
			    	  differenceOfDates(dates[0], dates[1], entry.getKey());
		    	  } else if(dates[0] == null) {
		    		  System.out.println("Transaction data is incorrect (Starting time missing) :"+entry.getKey());
		    	  } else if(dates[1] == null) {
		    		  System.out.println("Transaction data is incorrect (Ending time missing) :"+entry.getKey());
		    	  }
		    	} 
		      
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (ParseException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * display time difference between dates (dt2-dt1)
	 * @param dt1
	 * @param dt2
	 * @param key
	 * @author Sai Kumar Nakirekanti
	 */
	public static void differenceOfDates(Date dt1, Date dt2, String key) {

	        long diff = dt2.getTime() - dt1.getTime();
	        long diffSeconds = diff / 1000 % 60;
	        long diffMinutes = diff / (60 * 1000) % 60;
	        long diffHours = diff / (60 * 60 * 1000) % 24;
	        int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));
	        
	        System.out.println("Transaction  = "+key + ":  diffInDays = "+diffInDays + " : diffHours = "+diffHours +
	        		" : diffMinutes = "+diffMinutes + " : diffSeconds = "+ diffSeconds);
	}
}
