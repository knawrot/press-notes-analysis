package pl.edu.agh.notes;

import org.hibernate.Session;
import pl.edu.agh.notes.entity.Tag;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Michał Adamczyk.
 */


public class ImportTags {

    private static final String TAGS_DIRECTORY = "statistics/";
    public static final String SPLIT_FILE_PREFIX = "results";

    public static void main(String[] args) throws IOException {
        final Map<String, Integer> tagMap = new HashMap<String, Integer>();
        Files.list(Paths.get(TAGS_DIRECTORY))
                .filter(new Predicate<Path>() {

                    public boolean test(Path path) {
                        return path.getFileName().toString()
                                .startsWith(SPLIT_FILE_PREFIX);
                    }
                })
                .forEach(new Consumer<Path>() {
                    public void accept(Path path) {
                        try {
                            String fileName = path.getFileName().toString();
                            String country = fileName.substring(6);
                            InputStreamReader stream = null;
                            stream = new InputStreamReader(new FileInputStream(path.toFile()), "UTF-8");
                            BufferedReader br = new BufferedReader(stream);
                            String newLine;
                            while((newLine = br.readLine()) != null){
                                String[] lineSplit = newLine != null ? newLine.split(" ") : null;
                                if(lineSplit != null){
                                    if(!tagMap.containsKey(lineSplit[0])){
                                        tagMap.put(lineSplit[0], Integer.valueOf(lineSplit[1]));
                                    }else{
                                        Integer oldValue = tagMap.get(lineSplit[0]);
                                        tagMap.replace(lineSplit[0], oldValue, oldValue + Integer.valueOf(lineSplit[1]));
                                    }
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        List<Tag> tagList = new LinkedList<Tag>();
        int i = 1;
        tagMap.forEach((key, value) -> tagList.add(new Tag(key, value)));
        HibernateUtil hibernateUtil = new HibernateUtil();
        Session session = hibernateUtil.openSession();
        session.beginTransaction();

        for (Tag tag: tagList) {
            tag.setId(i++);
            session.save(tag);
        }
        session.getTransaction().commit();

        System.out.println(tagList.size());
        System.exit(0);
    }
}
