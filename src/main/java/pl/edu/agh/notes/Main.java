package pl.edu.agh.notes;

import java.io.IOException;

import java.util.*;

import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.Query;
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
        HibernateUtil hibernateUtil = new HibernateUtil();
        Session session = hibernateUtil.openSession();

        Query query = session.createQuery("from Tag");
        List<Tag> tagList = query.list();
        int i = 0;
        for(Tag tag1 : tagList){
            i++;
            System.out.println(i + "/" + tagList.size() + " : " + tag1.getName());
            session.beginTransaction();
            String tagName = tag1.getName();
            if(tagName.contains("\'")){
                tagName = tagName.split("\'")[0];
            }
            Query tagQuery = session.createQuery("from RssNote where text2 like \'% " + tagName + " %\'");
            List<RssNote> notesTaged = tagQuery.list();
            for(RssNote rssNote : notesTaged){
                rssNote.getTags().add(tag1);
                session.save(rssNote);
            }
            session.flush();
            session.getTransaction().commit();

        }

        System.out.println("Done");
        System.exit(0);

    }
    
}
