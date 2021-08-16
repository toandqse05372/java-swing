/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import event.ButtonManager;
import gui.Puzzle;
import java.awt.Dimension;
import java.awt.GridLayout;
import static java.lang.Thread.sleep;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Thuy Trieu
 */
public class Controller {

    private final Puzzle xepHinh;
    private ButtonManager buttons;
    private static final JLabel lblCount = new JLabel("Move Count: 0");
    private static final JLabel lblTime = new JLabel("Elapsed: 0");
    public static List<String> random = new ArrayList<>();
    public static countTime cTime = new countTime();
    public int count = 0;
    static int time = 0;
    public int panelSize = 0;
    int minLv;
    int length;

    // creat game screen when start
    public Controller(Puzzle xepHinh) {
        this.xepHinh = xepHinh;
        xepHinh.pnMove2.setLayout(new GridLayout(1, 1));
        xepHinh.pnTime.setLayout(new GridLayout(1, 1));
        xepHinh.pnMove2.add(lblCount);
        xepHinh.pnTime.add(lblTime);
    }

    //set text move count
    public void changeCount() {
        lblCount.setText("Count: " + count);
    }

    //insert hard options to combobox
    public void insert(int min, int max){
        minLv = min;
        for(int i = 0; i <= max - min; i++){ //loop to insert each option
           int level = i + min;
            xepHinh.sizeCbb.insertItemAt(level+" x "+level, i);
        } 
        xepHinh.sizeCbb.setSelectedIndex(0);
    }
    
    //creat new game
    public void creatNewGame() {   
        lblTime.setText("Elapsed: 0");
        if (cTime.isAlive()) { //check if time is be counting or not
            time = 0;      //restart time couting
            cTime.resume();
        }else {
            time = 0;
            cTime.start();
        }
        lblCount.setText("Move Count: 0");
        count = 0;      
        createNewBoard(xepHinh.sizeCbb.getSelectedItem().toString());
    }
    
    //resize game window
    public void resizeWindow(JFrame jFrame){
        jFrame.setBounds(jFrame.getX(),jFrame.getY(),panelSize+50,panelSize+200);
    }
    
    //creat new board game
    public void createNewBoard(String level) {
        length = Integer.parseInt(level.substring(0, level.indexOf(" ")));
        rdm(length);
        int getRandom = 0;
        buttons = new ButtonManager(length, this);
        xepHinh.panelPuzzle.removeAll();
        panelSize = length * 50 + 10;
        xepHinh.panelPuzzle.setPreferredSize(new Dimension(panelSize,panelSize));
        xepHinh.panelPuzzle.setLayout(new GridLayout(length, length));
        //loop to add button for each possition
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                xepHinh.panelPuzzle.add(buttons.matrixButton[i][j]);
                buttons.matrixButton[i][j].setText(random.get(getRandom));
                getRandom++;
            }
        }       
    }
    
    //mix up buttons
    void rdm(int length) {
        random.clear();
        for (int i = 1; i <= length*length; i++) {
            if(i == 0){
                random.add("");
            }else{
                random.add(i+"");
            }
        }
        Collections.shuffle(random);
    }
    
    public void confirmNewGame(){
        if(!cTime.isAlive()){
            creatNewGame();
        }else{
            cTime.suspend();
            enableButton(false);
            int response = JOptionPane.showConfirmDialog(null, "Do you want "
                + "to creat new game?", "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                creatNewGame();
            }else{
                cTime.resume();
                enableButton(true);
            }
        }
    }
    
    //disable buttons
    public void enableButton(boolean status){
        for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    buttons.matrixButton[i][j].setEnabled(status);
                }
            }
    }

    //count playing time
    public static class countTime extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    time++;
                    lblTime.setText("Elapsed: " + time);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Puzzle.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
