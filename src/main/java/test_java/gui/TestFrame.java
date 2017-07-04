package test_java.gui;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import test_java.model.Task;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@Component
public class TestFrame extends AbstractTestFrame {

    private static Logger log = Logger.getLogger(String.valueOf(TestFrame.class));

    public void setDataByRazdel(String razdelName) {
        super.setDataByRazdel(razdelName);
    }

    @Autowired
    public TestFrame() throws IOException {
        try {
            UIManager.setLookAndFeel(new BernsteinLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(AbstractTestFrame.class.getName()).log( null, ex.getLocalizedMessage());
            log.info("TestFrame() " + ex.getLocalizedMessage());
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    protected void setFormByCurrentTask() {
        super.setFormByCurrentTask();
        String taskName = ((Task) getTasks().get(getTaskNumber())).getTaskName();
        getFieldIssue().setText(convertStringToHtml(taskName));
        getQuestion().setText(String.valueOf(getTaskNumber()));
    }

    protected void createFrame() {
        super.createFrame();
        getButtonForward().setText("---->");
        getButtonForward().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TestFrame.this.buttonForwardActionPerformed();
            }
        });
        getButtonBack().setText("<----");
        getButtonBack().setVisible(true);
        getButtonBack().setEnabled(false);
        getStatusIncorrectText().setVisible(false);
        getStatusIncorrectText().setPreferredSize(new Dimension(500, 60));
        getStatusCorrectText().setVisible(false);
        getButtonBack().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TestFrame.this.buttonBackActionPerformed(evt);
            }
        });
    }

    public void showFrame() {
        setTaskNumber(1);
        setCorrectAnswersCount(0);
        setIncorrect(0);
        getButtonBack().setVisible(true);
        getButtonForward().setVisible(true);
        getButtonBack().setEnabled(false);
        getButtonForward().setEnabled(true);
        getStatusIncorrectText().setVisible(false);
        getStatusCorrectText().setVisible(false);

        setVisible(true);
    }

    protected void buttonBackActionPerformed(ActionEvent evt) {
        if (getTaskNumber() == 1) {
            getButtonBack().setEnabled(false);
        } else {
            setTaskNumber(getTaskNumber() - 1);
        }
        getButtonForward().setEnabled(true);
        setFormByCurrentTask();
        getStatusCorrectText().setVisible(false);
        getStatusIncorrectText().setVisible(false);
    }

    public void okButActionPerformed(ActionEvent evt) {
        getStatusCorrectText().setVisible(false);
        getStatusIncorrectText().setVisible(false);
        boolean isCorrect = getTestService().isAnswerCorrect(((Task) getRazdel().getTasks().get(getTaskNumber())).getAnsvers(), getAnswers());
        getAnswered().set(getTaskNumber(), isCorrect);
        check(isCorrect);
    }

    private void check(boolean correct) {
        if (correct) {
            setCorrectAnswersCount(getCorrectAnswersCount() + 1);
            getStatusCorrectText().setVisible(true);
            getStatusIncorrectText().setVisible(false);
            log.info(" Правильный ответ " + getCorrectAnswersCount());
        } else if (!correct) {
            setIncorrect(getIncorrect() + 1);
            getStatusIncorrectText().setVisible(true);
            getStatusCorrectText().setVisible(false);
            log.info(" Не правильный ответ " + getIncorrect());
        }
        setCorrectAnswersCount(0);
        setIncorrect(0);
    }

    protected JPanel makeDownPanel() {
        JPanel downPanel = super.makeDownPanel();
        downPanel.add(getButtonBack());
        downPanel.add(getButtonForward());
        downPanel.add(getStatusCorrectText());
        downPanel.add(getStatusIncorrectText());
        return downPanel;
    }
}

