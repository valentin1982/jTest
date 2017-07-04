package test_java.gui;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import test_java.model.Razdel;
import test_java.service.RazdelServise;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RazdelFrame extends JFrame {

    public static final String IMG_256_PNG = "/img/256.png";
    private static Logger log = Logger.getLogger(String.valueOf(RazdelFrame.class));
    private JButton btnExam = new JButton("Экзамен");
    private JButton btnTrain = new JButton("Тест");
    private RazdelServise razdelServise;
    private JTable razdelTable;
    private TableModel razdelModel;
    private AbstractTestFrame testFrame;
    private AbstractTestFrame examFrame;
    private String razdelName;
    public static final String ERROR_CONNECTION = "Извините в данное время сервер не доступен, попробуйте позже ";


    @Autowired
    public RazdelFrame(AbstractTestFrame testFrame, AbstractTestFrame examFrame) throws HeadlessException, IOException {

        try {
            UIManager.setLookAndFeel(new BernsteinLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
            log.error("UnsupportedLookAndFeelException" + ex);
        }
        this.testFrame = testFrame;
        this.examFrame = examFrame;
        testFrame.setIconImage(new ImageIcon(getClass().getResource(IMG_256_PNG)).getImage());
        examFrame.setIconImage(new ImageIcon(getClass().getResource(IMG_256_PNG)).getImage());
        setIconImage(new ImageIcon(getClass().getResource(IMG_256_PNG)).getImage());
        this.razdelModel = createTableModel();
        this.razdelTable = new JTable(this.razdelModel);
        JScrollPane scrollPane = new JScrollPane(this.razdelTable);
        add(scrollPane);
        setLocation(100, 100);
        setMinimumSize(new Dimension(600, 400));
        setResizable(false);
        JPanel panel = new JPanel();
        panel.add(this.btnExam);
        panel.add(this.btnTrain);
        add(panel, "South");
        this.btnExam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eve) {
                RazdelFrame.this.examFrame.setDataByRazdel(RazdelFrame.this.razdelName);
                RazdelFrame.this.examFrame.showFrame();
            }
        });
        this.btnTrain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eve) {
                RazdelFrame.this.testFrame.setDataByRazdel(RazdelFrame.this.razdelName);
                RazdelFrame.this.testFrame.showFrame();
            }
        });
        setDefaultCloseOperation(3);
        this.razdelTable.setSelectionMode(0);
        this.btnTrain.setEnabled(false);
        this.btnExam.setEnabled(false);
        this.razdelTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    RazdelFrame.this.btnTrain.setEnabled(true);
                    RazdelFrame.this.btnExam.setEnabled(true);
                    RazdelFrame.this.razdelName = ((String) RazdelFrame.this.razdelTable.getValueAt(RazdelFrame.this.razdelTable.getSelectedRow(), 0));
                }
            }
        });
    }

    public RazdelServise getRazdelServise() {
        return this.razdelServise;
    }

    public void setRazdelServise(RazdelServise razdelServise) {
        this.razdelServise = razdelServise;
    }

    private TableModel createTableModel() {
        this.razdelModel = new AbstractTableModel() {

            public int getRowCount() {
                try {

                    int rowCount = RazdelFrame.this.razdelServise.findAll().size();
                    return rowCount;
                } catch (NullPointerException e) {
                    log.error("TableModel createTableModel() " + e.getLocalizedMessage() );
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, ERROR_CONNECTION);
                    System.exit(0);
                }
                return 0;
            }

            public int getColumnCount() {
                return 1;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return ((Razdel) RazdelFrame.this.razdelServise.findAll().get(rowIndex)).getName();
            }

            public String getColumnName(int column) {
                String str = " Выбирите раздел";
                return str;
            }
        };
        return this.razdelModel;
    }

    public JButton getBtnExam() {
        return this.btnExam;
    }

    public void setBtnExam(JButton btnExam) {
        this.btnExam = btnExam;
    }

    public JButton getBtnTrain() {
        return this.btnTrain;
    }

    public void setBtnTrain(JButton btnTrain) {
        this.btnTrain = btnTrain;
    }


}

