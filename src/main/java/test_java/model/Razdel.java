package test_java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Razdel implements Comparable<Razdel> {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private String name;
    @OneToMany
    private List<Task> tasks = new ArrayList();

    public Razdel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int compareTo(Razdel o) {
        return name.compareTo(o.name);
    }
}
