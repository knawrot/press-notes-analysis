package pl.edu.agh.notes;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.Session;
import pl.edu.agh.notes.entity.RssNote;
import pl.edu.agh.notes.entity.Tag;

/**
 * Created by Michał Adamczyk.
 */
public class Main {

    static {
        DOMConfigurator.configure("log4j.xml");
    }
    
    
    public static void main(String[] args) throws IOException {
        System.out.println("Hibernate many to many (Annotation)");
        HibernateUtil hibernateUtil = new HibernateUtil();
        Session session = hibernateUtil.openSession();
        session.beginTransaction();

        RssNote rssNote = (RssNote) session.get(RssNote.class, 1);
        Tag tag = (Tag) session.get(Tag.class, 1);
        List<Tag> list = new LinkedList<>();
        list.add(tag);
        rssNote.setTags(list);

        session.save(rssNote);
        session.getTransaction().commit();
        System.out.println("Done");
        System.exit(0);

    }
    
}
