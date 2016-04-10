package pl.edu.agh.notes;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Michał Adamczyk.
 */
public class StatsCSV {

    private static final String FEED = "feed";
    private static final String TIME = "time";
    private static final String TEXT_1 = "text1";
    private static final String TEXT_2 = "text2";
    private static final String TAG_COUNTRY = "TAG_country";

    public static void main(String[] args) throws IOException {


        List<HashMap<String, String>> x = new ArrayList<HashMap<String, String>>();
        InputStream fis = new FileInputStream("Tagi_rosja.csv");
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        String line;
        HashMap<String, Integer> tagMap = new HashMap<String, Integer>();

        while ((line = br.readLine()) != null) {
            String splitedString[] = line.split(",");
            List<String> splitedList = Arrays.asList(splitedString);

            for(int j = 0; j < splitedList.size(); j+= 2){
                String name = splitedList.get(j);
                if(j== 80){
                    j=81;
                }
                String count = splitedList.get(j+1);
                if(name.length() != 0 && count.length() != 0){
                    if(tagMap.get(name) == null){
                        tagMap.put(name, Integer.valueOf(count));
                    }else{
                        Integer value = tagMap.get(name);
                        value += Integer.valueOf(count);
                        tagMap.replace(name, value);
                    }

                }
            }

            System.out.println(line);
        }


        Map sortedMap = sortMapByValue(tagMap);


        System.out.println(sortedMap);
        File file = new File("res");
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.write(sortedMap.toString().getBytes());
        s.flush();
        s.close();
        System.exit(0);

    }

    public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){

        TreeMap<String, Integer> result = new TreeMap<String, Integer>(new ValueComparator(map));
        result.putAll(map);
        return result;
    }

    static class ValueComparator implements Comparator<String>{

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        public ValueComparator(HashMap<String, Integer> map){
            this.map.putAll(map);
        }


        public int compare(String s1, String s2) {
            if(map.get(s1) >= map.get(s2)){
                return -1;
            }else{
                return 1;
            }
        }
    }
}

