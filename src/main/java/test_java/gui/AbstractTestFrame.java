package test_java.gui;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import org.apache.log4j.Logger;
import test_java.model.Ansver;
import test_java.model.Razdel;
import test_java.model.Result;
import test_java.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test_java.service.TestService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public abstract class AbstractTestFrame extends JDialog {

    public static final String IMG_256_PNG = "/img/256.png";
    private static Logger log = Logger.getLogger(AbstractTestFrame.class);
    private Razdel razdel;
    private TestService testService;
    private Task task;
    private Result result;
    private String razdelName;
    private List<Task> tasks;
    private int taskNumber;
    private List<Boolean> answered;
    private int correctAnswersCount;
    private int incorrect;
    private Timer timer;
    private ActionListener actionListener;
    private JProgressBar progress = new JProgressBar();
    private JPanel jpanel = new JPanel();
    private JButton buttonBack = new JButton();
    private JButton buttonForward = new JButton();
    private JScrollPane scrollPane = new JScrollPane();
    private JToggleButton[] answers = new JToggleButton[6];
    private JLabel question = new JLabel();
    private JLabel fieldIssue = new JLabel();
    private JLabel answerBox1 = new JLabel();
    private JLabel answerBox2 = new JLabel();
    private JLabel answerBox3 = new JLabel();
    private JLabel answerBox4 = new JLabel();
    private JLabel answerBox5 = new JLabel();
    private JButton okBut = new JButton();
    public static final int MAX_ANSWERS_COUNT = 6;
    private Font font = new Font("Verdana", 0, 15);
    private JLabel label = new JLabel();
    private JLabel statusCorrectText = new JLabel();
    private JLabel statusIncorrectText = new JLabel();
    private String explanationsText = null;
    private JTextField firstName = new JTextField(15);
    private JTextField lastName = new JTextField(15);
    private JCheckBox saveBox = new JCheckBox("Save details:", false);


    @Autowired
    public AbstractTestFrame() throws IOException {
        setModal(true);
        this.testService = new TestService();
        try {
            UIManager.setLookAndFeel(new BernsteinLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            log.error(ex.getLocalizedMessage());
        }
        JFrame.setDefaultLookAndFeelDecorated(true);

        this.timer = new Timer(12000, this.actionListener);
        this.okBut.setText("Ответить");
        this.okBut.setEnabled(false);
        createFrame();
        setMinimumSize(new Dimension(1100, 600));
        setResizable(false);

    }

    public void setDataByRazdel(String razdelName) {
        this.razdel = this.testService.getRazdelByName(razdelName);
        this.tasks = this.razdel.getTasks();
        this.taskNumber = 1;
        if (this.tasks.size() - 1 < this.taskNumber) {
            JOptionPane.showMessageDialog(this, " Извените раздел еше не заполнен ! ");

        }
        Collections.shuffle(this.tasks);
        this.answered = new ArrayList();
        for (int i = 0; i < this.tasks.size(); i++) {
            this.answered.add(Boolean.FALSE);
        }
        setFormByCurrentTask();
        getStatusIncorrectText().setVisible(false);
        getStatusCorrectText().setVisible(false);
        this.razdelName = razdelName;
    }

    protected void setFormByCurrentTask() {
        this.font = new Font(null, 2, 16);
        this.fieldIssue.setFont(this.font);
        this.fieldIssue.setForeground(Color.decode("#00008B"));
        this.question.setFont(this.font);
        this.font = new Font(null, 2, 14);

        String taskName = ((Task) this.tasks.get(this.taskNumber)).getTaskName();
        this.fieldIssue.setText(convertStringToHtml(taskName));
        this.explanationsText = ((Task) this.tasks.get(this.taskNumber)).getExplanationsText();
        this.statusIncorrectText.setText(convertStringToHtml(" " + this.explanationsText));
        this.question.setText(String.valueOf(this.taskNumber));
        for (int i = 0; i < this.answers.length; i++) {
            this.answers[i].setVisible(false);
            this.answers[i].setSelected(false);
        }
        for (int i = 0; (i < 6) && (i < ((Task) this.razdel.getTasks().get(this.taskNumber)).getAnsvers().size()); i++) {
            this.answers[i].setVisible(true);
            String text = ((Ansver) ((Task) this.razdel.getTasks().get(this.taskNumber)).getAnsvers().get(i)).getText();
            this.answers[i].setText(convertStringToHtml(text));
            this.answers[i].setFont(this.font);
        }
    }

    protected String convertStringToHtml(String string) {
        StringBuilder sb = new StringBuilder();
        String[] lines = string.split("\n");
        sb.append("<HTML>");
        for (String ln : lines) {
            sb.append(filterForHtml(ln));
            sb.append("<BR/>");
        }
        sb.append("</HTML>");
        return sb.toString();
    }

    private String filterForHtml(String line) {
        String str = line.replaceAll("<", "&lt;");
        return str;
    }


    public TestService getTestService() {
        return this.testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    protected void createFrame() {
        getStatusCorrectText().setText(convertStringToHtml("  Правильный ответ "));
        getStatusCorrectText().setFont(this.font);
        getStatusCorrectText().setForeground(Color.decode("#008000"));

        getStatusIncorrectText().setText(convertStringToHtml(" Не правильный ответ !!!"));
        getStatusIncorrectText().setFont(this.font);
        getStatusIncorrectText().setForeground(Color.decode("#FF0000"));

        getOkBut().setText("Ответить");
        getQuestion().setText("Вопрос");
        getQuestion().setEnabled(false);
        getFieldIssue().setText("<html>Поле <br/>вопроса<sup>2</sup></html>");

        getOkBut().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AbstractTestFrame.this.okButActionPerformed(evt);

                AbstractTestFrame.this.okBut.setEnabled(false);
            }
        });
        JPanel upPanel = makeUpPanel();
        JPanel cetrePanel = makeCenterPanel();
        JPanel downPanel = makeDownPanel();
        add(upPanel, "North");
        add(cetrePanel, "Center");
        add(downPanel, "South");
        pack();
    }

    protected JPanel makeCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 1));
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < AbstractTestFrame.this.answers.length; i++) {
                    if (AbstractTestFrame.this.answers[i].isSelected()) {
                        AbstractTestFrame.this.okBut.setEnabled(true);
                        return;
                    }
                }
                AbstractTestFrame.this.okBut.setEnabled(false);
                AbstractTestFrame.this.getStatusCorrectText().setVisible(false);
                AbstractTestFrame.this.getStatusIncorrectText().setVisible(false);
            }
        };

        for (int i = 0; i < this.answers.length; i++) {
            this.answers[i] = new JCheckBox();
            this.answers[i].setVisible(false);
            this.answers[i].addActionListener(listener);
            centerPanel.add(this.answers[i]);
        }
        this.okBut.setEnabled(false);

        return centerPanel;
    }

    protected JPanel makeDownPanel() {
        JPanel downPanel = new JPanel();
        downPanel.add(this.okBut);
        downPanel.add(this.label);
        downPanel.add(this.statusCorrectText);
        downPanel.add(this.statusIncorrectText);
        return downPanel;
    }

    protected JPanel makeUpPanel() {
        JPanel upPanel = new JPanel();
        upPanel.add(this.question);
        upPanel.add(this.fieldIssue);
        return upPanel;
    }

    public abstract void okButActionPerformed(ActionEvent paramActionEvent);

    protected void buttonForwardActionPerformed() {
        if (getTaskNumber() >= getTasks().size() - 1) {
            getButtonForward().setEnabled(false);
        } else {
            setTaskNumber(getTaskNumber() + 1);
        }
        getButtonBack().setEnabled(true);
        setFormByCurrentTask();
        getStatusCorrectText().setVisible(false);
        getStatusIncorrectText().setVisible(false);
    }

    protected void showIncorrectExamFrame() {
        this.font = new Font("Verdana", 2, 22);

        this.label = new JLabel();
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setIconImage(new ImageIcon(getClass().getResource(IMG_256_PNG)).getImage());
        dialog.getContentPane().add(this.label);
        dialog.setLocation(400, 300);
        dialog.setPreferredSize(new Dimension(350, 150));
        dialog.setResizable(false);
        this.label.setFont(this.font);
        this.label.setForeground(Color.decode("#FF0000"));
        this.label.setText(convertStringToHtml(" Экзамен не пройден !!! \n Правильных ответов : " + getCorrectAnswersCount() + "\n" + " Не правильных ответов : " + getIncorrect()));
        dialog.pack();
        dialog.setVisible(true);
    }

    protected void showCorrectExamFrame() {
        this.font = new Font("Verdana", 2, 22);

        JLabel label = new JLabel();
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setIconImage(new ImageIcon(getClass().getResource(IMG_256_PNG)).getImage());
        dialog.getContentPane().add(label);
        dialog.setLocation(400, 300);
        dialog.setPreferredSize(new Dimension(350, 150));
        dialog.setResizable(false);
        label.setFont(this.font);
        label.setForeground(Color.decode("#008000"));
        label.setText(convertStringToHtml(" Экзамен пройден !!! \n Правильных ответов : " + getCorrectAnswersCount() + "\n" + " Не правильных ответов : " + getIncorrect()));
        dialog.pack();
        dialog.setVisible(true);
    }

    protected void logFrame() {
        JDialog dialog = new JDialog();
        dialog.getContentPane().setLayout(new GridLayout(3, 2));
        dialog.setTitle("Login ");
        dialog.setModal(true);
        dialog.setIconImage(new ImageIcon(getClass().getResource(IMG_256_PNG)).getImage());
        dialog.setLocation(400, 300);
        dialog.setPreferredSize(new Dimension(350, 200));
        dialog.getContentPane().add(new JLabel("Имя "));
        dialog.getContentPane().add(this.firstName);
        dialog.getContentPane().add(new JLabel("Фамилия "));
        dialog.getContentPane().add(this.lastName);
        dialog.getContentPane().add(this.saveBox);
        JButton submit = new JButton();
        dialog.getContentPane().add(submit);
        submit.addActionListener(this.actionListener);
        pack();
        setVisible(true);
    }

    public JLabel getStatusCorrectText() {
        return this.statusCorrectText;
    }

    public void actionPerformed(ActionEvent e) {
    }

    public JButton getButtonBack() {
        return this.buttonBack;
    }

    public JButton getButtonForward() {
        return this.buttonForward;
    }

    public JLabel getQuestion() {
        return this.question;
    }

    public JLabel getFieldIssue() {
        return this.fieldIssue;
    }

    public JButton getOkBut() {
        return this.okBut;
    }

    public int getCorrectAnswersCount() {
        return this.correctAnswersCount;
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public int getIncorrect() {
        return this.incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public abstract void showFrame();

    public Razdel getRazdel() {
        return this.razdel;
    }

    public List<Boolean> getAnswered() {
        return this.answered;
    }

    public JToggleButton[] getAnswers() {
        return this.answers;
    }

    public JProgressBar getProgress() {
        return this.progress;
    }

    public JLabel getStatusIncorrectText() {
        return this.statusIncorrectText;
    }

}
