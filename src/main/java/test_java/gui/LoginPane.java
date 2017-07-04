package test_java.gui;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import org.springframework.beans.factory.annotation.Autowired;
import test_java.model.Result;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

@Component
class LoginPane extends JDialog implements ActionListener {



    private static Logger log = Logger.getLogger(String.valueOf(LoginPane.class));
    private Result result;
    private String firstName = this.result.getFirstName();
    private String lastName = this.result.getLastName();
    private boolean itsFirst = true;
    private boolean itsKeep = false;
    private JTextField itsFirstNameField = new JTextField(15);
    private JPasswordField itsLastNameField = new JPasswordField(15);
    private JCheckBox itsKeepBox = new JCheckBox("Save details:", false);
    private boolean itsInit = false;

    @Autowired
    public LoginPane()
    {
        try
        {
            UIManager.setLookAndFeel(new BernsteinLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(LoginPane.class.getName());
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Login");
        setModal(true);
        getContentPane().setLayout(new GridLayout(3, 2));
        getContentPane().add(new JLabel(" Имя "));
        getContentPane().add(this.itsFirstNameField);
        getContentPane().add(new JLabel(" Фамилия "));
        getContentPane().add(this.itsLastNameField);
        getContentPane().add(this.itsKeepBox);
        JButton submit = new JButton("done");
        getContentPane().add(submit);
        submit.addActionListener(this);
        pack();
    }

    public String[] getLogin()
    {
        if ((!this.itsKeep) && (!this.itsFirst)) {
            return null;
        }
        if (!this.itsInit) {
            return null;
        }
        this.itsFirst = false;
        String[] res = new String[2];
        res[0] = this.firstName;
        res[1] = this.lastName;
        if (!this.itsKeep)
        {
            this.firstName = this.result.getFirstName();
            this.lastName = this.result.getLastName();
        }
        return res;
    }

    public void actionPerformed(ActionEvent e)
    {
        this.firstName = this.itsFirstNameField.getText();
        this.lastName = new String(this.itsLastNameField.getPassword());
        this.itsKeep = this.itsKeepBox.isSelected();
        this.itsInit = true;
        setVisible(false);
    }
}

