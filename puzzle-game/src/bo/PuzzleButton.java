/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import javax.swing.JButton;

/**
 *
 * @author Thuy Trieu
 */
public class PuzzleButton extends JButton {

    public int positionX, positionY, number;

    public PuzzleButton(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
