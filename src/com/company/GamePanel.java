package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;

    static final int DELAY = 105;

    final int[] X = new int[GAME_UNIT]; // X coordinates for the SNAKE
    final int[] Y = new int[GAME_UNIT];// Y coordiantes for the SNAKE

    int bodyParts = 5;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        play();
    }

    public void snakeMovement(){
        for(int i = bodyParts;i>0;i--){
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch (direction) {
            case 'U' -> Y[0] -= UNIT_SIZE;
            case 'D' -> Y[0] += UNIT_SIZE;
            case 'L' -> X[0] -= UNIT_SIZE;
            case 'R' -> X[0] += UNIT_SIZE;
        }
    }

    public void play(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paint(Graphics graphics){
        super.paintComponent(graphics);
        surrounding(graphics);
        //newApple();
    }

    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public  void gameOver(Graphics graphics){
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("INK",Font.BOLD,75));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER!",(SCREEN_WIDTH - fontMetrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);


        graphics.setColor(Color.RED);
        graphics.setFont(new Font("INK",Font.BOLD,40));
        FontMetrics fontMetrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: "+appleEaten,(SCREEN_WIDTH - fontMetrics1.stringWidth("SCORE: "+appleEaten))/2,SCREEN_HEIGHT/3);

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("INK",Font.BOLD,35));
        FontMetrics fontMetrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Enter to restart", (int) ((SCREEN_WIDTH - fontMetrics1.stringWidth("Enter to restart"))/1.7), (int) (SCREEN_HEIGHT/1.6));
}

    public void checkCollisions(){
        for(int i =bodyParts;i>0;i--){ // head collides with body?
            if(X[0] == X[i] && Y[0]==Y[i])
                running = false;
        }
        if(X[0] < 0)
            running = false;
        if(X[0] > SCREEN_WIDTH)
            running = false;
        if(Y[0] < 0)
            running = false;
        if(Y[0] > SCREEN_HEIGHT)
            running = false;
        if(!running)
            timer.stop();
    }
    public void checkapple(){
        if(X[0] == appleX && Y[0] == appleY){
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }

    public void surrounding(Graphics graphics) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            graphics.setColor(Color.RED);
            graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    graphics.setColor(Color.BLUE);
                    //graphics.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); //RBG LIGHTING
                    graphics.fill3DRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE, true);

                } else {
                    graphics.setColor(Color.WHITE);
                    //graphics.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));// RBG LIGHTING
                    graphics.fill3DRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE, true);
                }
            }
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("INK",Font.BOLD,40));
            FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
            graphics.drawString("SCORE: "+appleEaten,(SCREEN_WIDTH - fontMetrics.stringWidth("SCORE: "+appleEaten))/2,graphics.getFont().getSize());
        }

        else{
            gameOver(graphics);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            snakeMovement();
            checkapple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if(direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT :
                    if(direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP :
                    if(direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN :
                    if(direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }
}
