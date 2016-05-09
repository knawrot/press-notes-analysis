package pl.edu.agh.notes.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michał Adamczyk.
 */
@Entity
@Table(name = "notes.tag")
public class Tag {

    @Id
    @Column(name = "id")
    protected int id;
    private int frequency;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<RssNote> nodes = new LinkedList<>();

    public Tag(String name, Integer frequency) {
        this.frequency = frequency;
        this.name = name;
    }

    public Tag(int frequency, String name, List<RssNote> nodes) {
        this.frequency = frequency;
        this.name = name;
        this.nodes = nodes;
    }

    public Tag() {
    }

    public List<RssNote> getNodes() {
        return nodes;
    }

    public void setNodes(List<RssNote> nodes) {
        this.nodes = nodes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
