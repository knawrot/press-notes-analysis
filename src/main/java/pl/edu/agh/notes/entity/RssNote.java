package pl.edu.agh.notes.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Michał Adamczyk.
 */
@Entity
@Table(name = "notes.rss_notes")
public class RssNote {

    @Id
    @Column(name = "id")
    protected int id;
    private String feed;
    private String time;
    private String text1;
    private String text2;
    private String tag_country;
    private String newspaper;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tag_to_nodes", joinColumns = {
            @JoinColumn(name = "nodes_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",nullable = false, updatable = false)})
    private List<Tag> tags = new LinkedList<Tag>();

    public RssNote() {
    }

    public RssNote(String feed, String time, String text1, String text2, String tag_country, String newspaper, List<Tag> tags) {
        this.feed = feed;
        this.time = time;
        this.text1 = text1;
        this.text2 = text2;
        this.tag_country = tag_country;
        this.newspaper = newspaper;
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        if (!tags.contains(tag)){
            tags.add(tag);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getTag_country() {
        return tag_country;
    }

    public void setTag_country(String tag_country) {
        this.tag_country = tag_country;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }
}

