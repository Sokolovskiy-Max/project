package com.company;

import javax.swing.JFrame;

public class Form {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame=new JFrame("Ввод 1");
        int k=6;

        MainForm demo=new MainForm();

        frame.setContentPane(demo.createContentPane(k));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int w;
        if(k==1) w =(3)*45+50;
        else
            w= (k)*90+50;

        frame.setSize( 1450,w);
        frame.setVisible(true);
    }
}