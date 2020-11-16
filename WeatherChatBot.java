package com.DUNDERMIFFLIN.MIFFLIN;

import java.util.*;
//import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.io.File;
import java.util.Locale;
//
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class MIFFLIN  
{
	static String API_KEY = "df860a741a6aefc404b9f0f5e7844617";
 
    static String urlString; 
    static String city1;
    static String city2;
    static String city3;
    static String city4;
    static String city5;
    static ArrayList<String> myArray;
    static String[] print = {city1, city2, city3, city4, city5};
    private static final boolean TRACE_MODE = false;
    static String botName = "super";
    static String botResponse = "";
    
    public static void main( String[] args )
    {

    	
    	
    	try {
    		 
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);
            bot.writeAIMLFiles();
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";
 
            while(true) {
                System.out.print("Human : ");
                textLine = IOUtils.readInputTextLine();
                if ((textLine == null) || (textLine.length() < 1))
                    textLine = MagicStrings.null_input;
                if (textLine.equals("q")) {
                    System.exit(0);
                } else if (textLine.equals("wq")) {
                    bot.writeQuit();
                    System.exit(0);
                }  
                else if (textLine.equals("I am looking for a vacation") || textLine.equals("I am bored")) {
                	System.out.println("Robot: Are you planning a visit?");                    
                }
                if (textLine.equals("yes")) {
                	selectCities();
                    
                }
                if (textLine.equals("I would like that")  || textLine.equals("sure")) {
    	    		for (String i: myArray) {
    	    			Weather(i);
    	    		}
    	    	}
                
                else {
                    String request = textLine;
                    if (MagicBooleans.trace_mode)
                        System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                    String response = chatSession.multisentenceRespond(request);
                    while (response.contains("&lt;"))
                        response = response.replace("&lt;", "<");
                    while (response.contains("&gt;"))
                        response = response.replace("&gt;", ">");
                    
                    botResponse = response;
                    System.out.println("Robot : " + response);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void Weather(String Loc) {
    	urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + Loc + "&APPID=" + API_KEY + "&units=metric";	// or Galvin or Metric
    	
    	try {
        	StringBuilder result = new StringBuilder();
        	URL url = new URL(urlString);
        	URLConnection conn = url.openConnection();
        	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	String line;
        	 
        	while ((line = rd.readLine()) != null) {
        		result.append(line);
        		
        	}
       
        	rd.close();
        	//System.out.println(result);
        
        	Map<String, Object> respMap = jsonToMap(result.toString());
        	Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
        	Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

        	System.out.println("City: " + Loc + "\n"+"Current Temperature: " + mainMap.get("temp") + " celcius" + "\n" + "Current Humidity: " + mainMap.get("humidity")+("%") + "\n" + "Current wind Speed: " + windMap.get("speed") + " Km/hrs" + "\n" + "Wind Angle: " + windMap.get("deg")+(" deg"));
        	
        	System.out.println();
        	System.out.println("Robot: Would you like clothing suggestions ?");
        	Scanner kb = new Scanner (System.in);
        	String enter = kb.nextLine();
        	if (enter.equals("why not") || enter.equals("yep") || enter.equals("sure")) {
        		Double temp = (Double) mainMap.get("temp");
        		if(temp > 10 && temp < 20) {
        			System.out.println();
                	System.out.println("Sweater, thin jeans, t shirts");
        		}
        		else if(temp > 20) {
        			System.out.println();
                	System.out.println(" shots, tanktops");
        		}
        		else if(temp < 10) {
        			System.out.println();
                	System.out.println(" jackets, scarfs, jumpers, hoodies");
        		}
        		else if(temp < 0) {
        			System.out.println();
                	System.out.println(" Thick jackets, Wool scarfs, jumpers, hoodies, trousers, jumpers");
        		}
        	}
        } 
        catch(IOException e) {
        	System.out.println(e.getMessage());
        }
    }
    
    public static Map<String, Object> jsonToMap(String str){
    	Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {}.getType());
    	return map;
    }
    
    public String getApi() {
    	return this.API_KEY;
    }
    public String getUrl() { 
    	return this.urlString;
    }
    

    
    public static ArrayList<String> selectCities() {
    	myArray = new ArrayList<String>(); 
    	Scanner kb = new Scanner (System.in);
    	System.out.println();
    	System.out.println("Robot: how many cities are you gonna visit?");
    	int myInt = kb.nextInt(); 
    		
    		 
    		 for (int i = 0; i < myInt + 1; i++) {
    			 System.out.println(); 
    			 System.out.println("Robot: Which countries are you visiting?");
    			 String cities = kb.nextLine();
    			 myArray.add(cities);
    		 }
    		 
    		 System.out.println();System.out.println();System.out.println();
    		 
    		
 	    	System.out.println();
 	    	System.out.println("You selected: ");
 	    	System.out.println();
 	    	
 	    	for (String i:  myArray) { 
   			 System.out.println(i);
   		 }
 	    	
 	    	System.out.println("Do you want weather report for the cities? ");
 	    	
    	
    	return myArray;
    }
    
  
    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
    
    
    public static String getResponseBot(String textLine ) {
    	String resourcesPath = getResourcesPath();
		Bot bot = new Bot("super", resourcesPath);
		Chat chatSession = new Chat(bot);
        String request = textLine;
        if (MagicBooleans.trace_mode)
            System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
        String response = chatSession.multisentenceRespond(request);
        return response;   
    }
   
}


























//package com.DUNDERMIFFLIN.MIFFLIN;
//
//import java.io.File;
//import java.util.Locale;
//
//import org.alicebot.ab.Bot;
//import org.alicebot.ab.Chat;
//import org.alicebot.ab.History;
//import org.alicebot.ab.MagicBooleans;
//import org.alicebot.ab.MagicStrings;
//import org.alicebot.ab.utils.IOUtils;
// 
//public class MIFFLIN {
//    private static final boolean TRACE_MODE = false;
//    static String botName = "super";
//    static String botResponse = "";
// 
//    @SuppressWarnings("rawtypes")
//	public static void main(String[] args) {
//    	
//    	//System.out.println(getResponseBot("what is java"));
//    	
//    	String[] countryCodes = Locale.getISOCountries();
//    	//System.out.println(countryCodes.length);
//    	
//    	
//    	
//    	/*for (int i =0; i <  countryCodes.length; i++) {
//
//    		String countryCode = countryCodes[i];
//    	    Locale obj = new Locale("", countryCode);
//
//    	    System.out.println("Country Index = " + i + ", Country Code = " + obj.getCountry() 
//    	        + ", Country Name = " + obj.getDisplayCountry());
//    	    
//    	  //System.out.println(obj.getDisplayCountry(obj.FRENCH));
//    	    
//    	}*/
//    	
//    	
//    	
//
//    	/*String countryCode = countryCodes[0];
//    	Locale obj = new Locale("", countryCode);
//
//    	System.out.println("Country Code = " + obj.getCountry() 
//    	+ ", Country Name = " + obj.getDisplayCountry());
//    	*/
//    	
//    	
//    	
//       try {
// 
//            String resourcesPath = getResourcesPath();
//            System.out.println(resourcesPath);
//            MagicBooleans.trace_mode = TRACE_MODE;
//            Bot bot = new Bot("super", resourcesPath);
//            bot.writeAIMLFiles();
//            Chat chatSession = new Chat(bot);
//            bot.brain.nodeStats();
//            String textLine = "";
// 
//            while(true) {
//                System.out.print("Human : ");
//                textLine = IOUtils.readInputTextLine();
//                if ((textLine == null) || (textLine.length() < 1))
//                    textLine = MagicStrings.null_input;
//                if (textLine.equals("q")) {
//                    System.exit(0);
//                } else if (textLine.equals("wq")) {
//                    bot.writeQuit();
//                    System.exit(0);
//                } else {
//                    String request = textLine;
//                    if (MagicBooleans.trace_mode)
//                        System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
//                    String response = chatSession.multisentenceRespond(request);
//                    while (response.contains("&lt;"))
//                        response = response.replace("&lt;", "<");
//                    while (response.contains("&gt;"))
//                        response = response.replace("&gt;", ">");
//                    
//                    botResponse = response;
//                    System.out.println("Robot : " + response);
//                }
//                
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
// 
//    private static String getResourcesPath() {
//        File currDir = new File(".");
//        String path = currDir.getAbsolutePath();
//        path = path.substring(0, path.length() - 2);
//        System.out.println(path);
//        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
//        return resourcesPath;
//    }
//    
//    
//    public static String getResponseBot(String textLine ) {
//    	String resourcesPath = getResourcesPath();
//		Bot bot = new Bot("super", resourcesPath);
//		Chat chatSession = new Chat(bot);
//        String request = textLine;
//        if (MagicBooleans.trace_mode)
//            System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
//        String response = chatSession.multisentenceRespond(request);
//        return response;   
//    }
//    
//}








