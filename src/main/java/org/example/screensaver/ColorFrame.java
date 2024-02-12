package org.example.screensaver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Scope("singleton")
@Component
public abstract class ColorFrame extends JFrame {
    @Autowired
    private Color color;//ApplicationContext context;

    public ColorFrame() throws HeadlessException {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace(){
        Random random = new Random();
        setLocation(random.nextInt(1200),random.nextInt(600));
        getContentPane().setBackground(getColor());//context.getBean(Color.class));
        repaint();
    }

    protected abstract Color getColor();
}
