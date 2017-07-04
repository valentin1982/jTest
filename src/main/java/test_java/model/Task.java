package test_java.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue
    private Long id;
    private String taskName;
    private String explanationsText;
    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Ansver> ansvers;
    @ManyToOne
    private Razdel razdel;

    public Task() {
    }

    public Task(String taskName, String explanationsText, List<Ansver> ansvers, Razdel razdel) {
        this.taskName = taskName;
        this.explanationsText = explanationsText;
        this.ansvers = ansvers;
        this.razdel = razdel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getExplanationsText() {
        return explanationsText;
    }

    public void setExplanationsText(String explanationsText) {
        this.explanationsText = explanationsText;
    }

    public List<Ansver> getAnsvers() {
        return ansvers;
    }

    public void setAnsvers(List<Ansver> ansvers) {
        this.ansvers = ansvers;
    }

    public Razdel getRazdel() {
        return razdel;
    }

    public void setRazdel(Razdel razdel) {
        this.razdel = razdel;
    }

    @Override
    public String toString() {

        String res = "" + this.taskName + '\'' + "" + "\n";
        for (Ansver task : this.ansvers) {
            task.setText(task.getText().replaceAll("\\*", " "));
            res = res + task + "\n";
        }
        return res;
    }

    public int compareTo(Task o) {
        return taskName.compareTo(o.taskName);
    }
}
