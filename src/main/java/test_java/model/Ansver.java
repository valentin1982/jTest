package test_java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ansver {

    @Id
    @GeneratedValue
    private long id;
    private String text;
    private boolean isCor;
    @ManyToOne
    private Task task;

    public Ansver() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCor() {
        return isCor;
    }

    public void setIsCor(boolean isCor) {
        this.isCor = isCor;
    }

    @Override
    public String toString() {
        return  id + text;
    }
}
