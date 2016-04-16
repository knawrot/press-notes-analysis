package pl.edu.agh.notes;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import pl.edu.agh.notes.entity.RssNote;

import java.io.*;
import java.util.*;

/**
 * Created by Michał Adamczyk.
 */
public class CSVImport {

    private static final String FEED = "feed";
    private static final String TIME = "time";
    private static final String TEXT_1 = "text1";
    private static final String TEXT_2 = "text2";
    private static final String TAG_COUNTRY = "TAG_country";

    public static void main(String[] args){
        HibernateUtil hibernateUtil = new HibernateUtil();
        int i = 1;

        List<RssNote> rssNotes = new LinkedList<RssNote>();
        List<HashMap<String, String>> x = new ArrayList<HashMap<String, String>>();
        File dir = new File("C:/Users/Krecik/Downloads/geomedia/geomedia/Geomedia_extract_AGENDA/Geomedia_extract_AGENDA");
        File csvFile[];
        List<File> fileList = new LinkedList<File>();
        for(File csvDir : dir.listFiles()){
            if(csvDir.isDirectory()){
                csvFile = csvDir.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        if("rss_unique_TAG_country_Ebola.csv".equals(name)){
                            return true;
                        }
                        return false;
                    }
                });
                fileList.addAll(Arrays.asList(csvFile));
            }
        }
        for (File file : fileList) {
            Session session = hibernateUtil.openSession();

            CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter('\t').withIgnoreEmptyLines(true);
            CSVParser parser;

            InputStreamReader stream = null;
            try {
                stream = new InputStreamReader(new FileInputStream(file), "UTF-8");
                parser = new CSVParser(stream, format);
                session.beginTransaction();
                for(CSVRecord record : parser){
                    RssNote rssNote = new RssNote();
                    rssNote.setFeed(record.get(FEED));
                    rssNote.setNewspaper(getParentDirName(file.getParent()));
                    rssNote.setTag_country(record.get(TAG_COUNTRY));
                    rssNote.setText1(record.get(TEXT_1));
                    rssNote.setText2(record.get(TEXT_2));
                    rssNote.setTime(record.get(TIME));
                    rssNote.setId(i++);

                    rssNotes.add(rssNote);
                     session.save(rssNote);
                }
                parser.close();
                session.getTransaction().commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
            	System.out.println("Exception in file: " + file.getAbsolutePath());
            }
            System.out.println("Przetworzono: " + i);
            hibernateUtil.closeCurrentSession();

        }
        System.out.println(rssNotes.size());
        System.exit(0);

    }

    private static String getParentDirName(String path){
        String[] splitPath = path.split("/");
        if(splitPath.length == 2){
            return splitPath[1];
        }
        return "";
    }
}

