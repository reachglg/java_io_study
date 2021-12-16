package com.glg.study;

import com.glg.study.gui.ClientUI;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * @author glg
 * @version 1.0
 * @date 2021/12/16 14:00
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new ClientUI();
    }
}
