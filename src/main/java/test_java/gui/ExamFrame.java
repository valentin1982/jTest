package test_java.gui;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import org.springframework.beans.factory.annotation.Autowired;
import test_java.model.Task;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ExamFrame extends AbstractTestFrame {

    private static Logger log = Logger.getLogger(String.valueOf(ExamFrame.class));
    private JProgressBar progress = getProgress();
    private ActionListener actionListener;
    private Timer timer;
    private static final int MAX_CORRECT_ANSWER_COUNT = 16;
    private static final int MAX_ILLEGAL_ANSWER_COUNT = 5;
    private boolean isCorrect;

    public void setDataByRazdel(String razdelName) {
        super.setDataByRazdel(razdelName);
    }

    @Autowired
    public ExamFrame() throws IOException {
        try {
            UIManager.setLookAndFeel(new BernsteinLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AbstractTestFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    protected void createFrame() {
        super.createFrame();
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((ExamFrame.this.progress.getValue() >= 100) && (MAX_ILLEGAL_ANSWER_COUNT <= ExamFrame.this.getIncorrect())) {
                    ExamFrame.this.showIncorrectExamFrame();
                    ExamFrame.this.timer.stop();
                    ExamFrame.this.dispose();
                } else if ((ExamFrame.this.progress.getValue() >= 100) && (MAX_CORRECT_ANSWER_COUNT <= ExamFrame.this.getCorrectAnswersCount())) {
                    ExamFrame.this.showCorrectExamFrame();
                    ExamFrame.this.timer.stop();
                    ExamFrame.this.dispose();
                } else if (ExamFrame.this.progress.getValue() < 100) {
                    ExamFrame.this.progress.setValue(ExamFrame.this.progress.getValue() + 1);
                } else {
                    ExamFrame.this.showIncorrectExamFrame();
                    ExamFrame.this.timer.stop();
                    ExamFrame.this.dispose();
                }
            }
        };
        timer = new Timer(12000, actionListener);
    }

    protected JPanel makeUpPanel() {
        JPanel upPanel = super.makeUpPanel();
        upPanel.add(getProgress());
        getProgress().setVisible(true);
        return upPanel;
    }

    private void filterToTwenty(List<Task> tasks) {
        Random rnd = new Random();
        while (tasks.size() > 21) {
            tasks.remove(rnd.nextInt(tasks.size()));
        }
    }

    private void timer() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void showFrame() {
        setCorrectAnswersCount(0);
        setIncorrect(0);
        setTaskNumber(1);
        getProgress().setValue(0);
        getProgress().setVisible(true);
        timer();
        filterToTwenty(getTasks());
        setVisible(true);
    }

    public void okButActionPerformed(ActionEvent evt) {
        isCorrect = getTestService().isAnswerCorrect(((Task) getRazdel().getTasks().get(getTaskNumber())).getAnsvers(), getAnswers());
        getAnswered().set(getTaskNumber(), Boolean.valueOf(isCorrect));
        if (check(isCorrect)) {
            return;
        }
        Date date = getTestService().setDateResult(new Date());
        log.info(String.valueOf(date));
        log.info(" Правильный ответ" + getCorrectAnswersCount());
        log.info(" Не правильный ответ" + getIncorrect());
        buttonForwardActionPerformed();
    }

    private boolean check(boolean correct) {
        if (correct) {
            setCorrectAnswersCount(getCorrectAnswersCount() + 1);
        } else {
            setIncorrect(getIncorrect() + 1);
        }
        if (getTaskNumber() == getTasks().size() - 1) {
            timer.stop();
            if (MAX_ILLEGAL_ANSWER_COUNT <= getIncorrect()) {
                showIncorrectExamFrame();
            } else if (MAX_CORRECT_ANSWER_COUNT <= getCorrectAnswersCount()) {
                showCorrectExamFrame();
            }
            timer.stop();
            dispose();
        }
        return false;
    }
}