package test_java.gui;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

@Component
class LoadWindow extends JFrame {

    private static Logger log = Logger.getLogger(String.valueOf(LoadWindow.class));
    private JProgressBar progressBar;
    private JLabel text;
    private JFrame loading;

    @Autowired
    public LoadWindow() throws IOException {
        try {
            UIManager.setLookAndFeel(new BernsteinLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AbstractTestFrame.class.getName());
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        dataBase();
    }

    public void loadWindow() throws IOException {
        this.loading = new JFrame("Загрузка");
        this.loading.setIconImage(new ImageIcon(getClass().getResource("/img/256.png")).getImage());

        this.loading.setUndecorated(true);
        this.loading.setResizable(false);
        this.loading.setLocation(350, 250);
        this.loading.setSize(500, 200);
        this.loading.setDefaultCloseOperation(0);
        this.loading.setLayout(null);
        this.loading.setAlwaysOnTop(true);

        this.progressBar = new JProgressBar(5, 100);
        this.progressBar.setSize(450, 35);
        this.progressBar.setStringPainted(true);
        this.progressBar.setLocation(25, 60);

        this.text = new JLabel();
        this.text.setSize(250, 20);
        this.text.setLocation(25, 20);
        this.text.setText("Загрузка...");

        this.loading.add(this.text);
        this.loading.add(this.progressBar);
        this.loading.setVisible(true);
    }

    public void dataBase() throws IOException {
        loadWindow();

        this.progressBar.setValue(10);
        this.text.setText("Загрузка списков..");
        load();
        this.progressBar.setValue(20);
        this.text.setText("Загрузка разделов..");
        load();
        this.progressBar.setValue(30);
        this.text.setText("Загрузка вопросов..");
        load();
        this.progressBar.setValue(40);
        this.text.setText("Установка соединения..");
        load();
        this.progressBar.setValue(50);
        this.text.setText("Получение данных..");
        load();
        this.progressBar.setValue(60);
        this.text.setText("Получение данных..");
        load();
        this.progressBar.setValue(70);
        this.text.setText("Обработка данных..");
        load();
        this.progressBar.setValue(80);
        this.text.setText("Отрисовка графических компонентов..");
        load();
        this.progressBar.setValue(90);
        this.text.setText("Отрисовка графических компонентов..");
        load();
        this.progressBar.setValue(100);
        this.loading.dispose();
    }

    protected void load() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

