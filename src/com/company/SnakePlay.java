package com.company;

import javax.swing.*;

public class SnakePlay extends JFrame {
    SnakePlay(){
        add(new GamePanel());
        setTitle("SNAKE ");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
