package urlcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 *
 * @author ankur
 * This class implements the url counter grouped by the dates.
 * n is the number of lines of records in log
 * Time Complexity - O(n)
 * Space Complexity - O(n)
 */
public class URLCounter {
    
    private static TreeMap<String,HashMap<String,Integer>> dateMap = new TreeMap<>();
    
    //converts the UNIX time to date string of format MM/DD/YYYY
    private static String epochToDateConverter(Long timestamp){
        Date epochDate = new Date(timestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dateString = sdf.format(epochDate);
        return dateString;
    }
    
    //adds an entry to the treemap with key date and value as a hashmap of a count
    private static void addEntry(String[] entry){
        String dateString = epochToDateConverter(Long.parseLong(entry[0]));
        HashMap<String,Integer> urlCountMap = dateMap.get(dateString);
        if(urlCountMap == null){
            urlCountMap = new HashMap<String,Integer>();
            urlCountMap.put(entry[1],1); 
        }
        else{
            urlCountMap.put(entry[1],(urlCountMap.getOrDefault(entry[1],0)+1));
        }
        dateMap.put(dateString,urlCountMap);
    }
    
    //displays the url counts grouped with date to sysout
    private static void displayResult(){
        for(Map.Entry<String,HashMap<String,Integer>> entry : dateMap.entrySet()){
            System.out.println(entry.getKey() + " GMT");
            HashMap<String,Integer> urlCountMap = entry.getValue();
            List<Map.Entry<String,Integer>> aList = new LinkedList<Map.Entry<String,Integer>>(urlCountMap.entrySet());
            Collections.sort(aList, new Comparator<Map.Entry<String,Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> value1,Map.Entry<String, Integer> value2) {
                    return -(value1.getValue().compareTo(value2.getValue()));
                }
            });
            for(Map.Entry<String,Integer> urlEntry : aList){
                System.out.println(urlEntry.getKey() + " " + urlEntry.getValue());
            }
        }
    }
    
    
    public static void main(String[] args) {
        try{
            BufferedReader in = new BufferedReader(new FileReader(args[0]));
            String s;
            while((s = in.readLine()) != null){
                String[] entry = s.split("\\|");
                addEntry(entry);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        displayResult();
    }
    
}
