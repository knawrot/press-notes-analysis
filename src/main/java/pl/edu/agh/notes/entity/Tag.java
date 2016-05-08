package pl.edu.agh.notes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public Tag(String name, Integer frequency) {
        this.frequency = frequency;
        this.name = name;
    }

    public Tag() {
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
