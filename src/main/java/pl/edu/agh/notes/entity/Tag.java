package pl.edu.agh.notes.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<RssNote> nodes = new HashSet<RssNote>(0);

    public Tag(String name, Integer frequency) {
        this.frequency = frequency;
        this.name = name;
    }

    public Tag(int frequency, String name, Set<RssNote> nodes) {
        this.frequency = frequency;
        this.name = name;
        this.nodes = nodes;
    }

    public Tag() {
    }

    public Set<RssNote> getNodes() {
        return nodes;
    }

    public void setNodes(Set<RssNote> nodes) {
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
