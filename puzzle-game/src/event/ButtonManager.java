/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import bo.PuzzleButton;
import control.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Thuy Trieu
 */
public class ButtonManager implements ActionListener {

    public PuzzleButton[][] matrixButton;
    int lengthGame = 0;
    private final Controller controller;

    public ButtonManager(int length, Controller controller) {
        this.controller = controller;
        matrixButton = new PuzzleButton[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                matrixButton[i][j] = new PuzzleButton(i, j);
                matrixButton[i][j].addActionListener((ActionListener) this);
            }
        }
        lengthGame = length;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        swapButton(name);
        if (end()) {
            controller.cTime.suspend();
            controller.enableButton(false);
            JOptionPane.showMessageDialog(null, "You Win", "Congratulation", 1);
        }
    }
    
    //swap number button and empty button
    public void swapButton(String name) {
        int x = 0, y = 0;
        int x2 = 0, y2 = 0;
        if(name.equals("0")){
            return;
        }
        //loop to get number button place
        for (int i = 0; i < lengthGame; i++) {
            for (int j = 0; j < lengthGame; j++) {
                if (matrixButton[i][j].getText().equals(name)) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        //loop to get empty button
        for (int i = 0; i < lengthGame; i++) {
            for (int j = 0; j < lengthGame; j++) {
                if (matrixButton[i][j].getText().equals("")) {
                    x2 = i;
                    y2 = j;
                    break;
                }
            }
        }
        //check if empty button and number button can swap
        if (check(x, y, x2, y2)) {
            String temp = matrixButton[x][y].getText();
            matrixButton[x][y].setText(matrixButton[x2][y2].getText());
            matrixButton[x2][y2].setText(temp);
            controller.count++;
            controller.changeCount();
        }
    }
    
    //check 2 buttons are adjecent or not
    boolean check(int x, int y, int x2, int y2) {
        if    (x == x2 && y == y2 - 1) {
            return true;
        }
        if    (x == x2 && y == y2 + 1) {
            return true;
        }
        if    (x == x2 + 1 && y == y2) {
            return true;
        }
        return x == x2 - 1 && y == y2;
    }
    
    //check if user win the game
    boolean end() {
        //loop for check buttons are in right place
        for (int i = 0; i < lengthGame * lengthGame - 1; i++) {
            if (!matrixButton[i / lengthGame][i % lengthGame].getText().
                    equals((i + 1) + "")) {
                return false;
            }
        }
        return true;
    }
}
