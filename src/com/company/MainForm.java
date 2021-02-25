package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static com.company.MovingCircle.showMovingCircle;

public class MainForm {
    public JPanel createContentPane(int k) {
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);

        totalGUI.setBounds(1000, 200, 1000, 250);
        JTextField[] mt = new JTextField[k];
        JTextField[] rxt = new JTextField[k];
        JTextField[] ryt = new JTextField[k];
        JTextField[] vxt = new JTextField[k];
        JTextField[] vyt = new JTextField[k];

        JLabel[] m = new JLabel[k];
        JLabel[] rx = new JLabel[k];
        JLabel[] ry = new JLabel[k];
        JLabel[] vx = new JLabel[k];
        JLabel[] vy = new JLabel[k];
        JRadioButton[] r = new JRadioButton[k + 2];
        r[0] = new JRadioButton("Система центра масс");
        r[1] = new JRadioButton("ЛСО");
        for (int i = 0; i < k; i++) {
            m[i] = new JLabel("Введите Массу тела " + (i + 1) + "\n" + "\n в кг*10^30:");
            vx[i] = new JLabel("Введите скорость тела " + (i + 1) + "\n" + " по x в м/с:");
            vy[i] = new JLabel("Введите скорость тела " + (i + 1) + "\n" + " по y в м/с:");
            rx[i] = new JLabel("Введите координату тела " + (i + 1) + "\n" + " по x в а.е.:");
            ry[i] = new JLabel("Введите координату тела " + (i + 1) + "\n" + " по y в а.е.:");

            mt[i] = new JTextField(10);
            vxt[i] = new JTextField(10);
            vyt[i] = new JTextField(10);
            rxt[i] = new JTextField(10);
            ryt[i] = new JTextField(10);

            r[i + 2] = new JRadioButton("Система тела " + (i + 1));

        }
        for (int i = 0; i < k; i++) {
            m[i].setLocation(10, 10 + i * 90);
            m[i].setSize(230, 40);
            totalGUI.add(m[i]);
            mt[i].setLocation(10, 60 + i * 90);
            mt[i].setSize(230, 40);
            totalGUI.add(mt[i]);

            vx[i].setLocation(250, 10 + i * 90);
            vx[i].setSize(230, 40);
            totalGUI.add(vx[i]);
            vxt[i].setLocation(250, 60 + i * 90);
            vxt[i].setSize(230, 40);
            totalGUI.add(vxt[i]);

            vy[i].setLocation(490, 10 + i * 90);
            vy[i].setSize(230, 40);
            totalGUI.add(vy[i]);
            vyt[i].setLocation(490, 60 + i * 90);
            vyt[i].setSize(230, 40);
            totalGUI.add(vyt[i]);

            rx[i].setLocation(730, 10 + i * 90);
            rx[i].setSize(230, 40);
            totalGUI.add(rx[i]);
            rxt[i].setLocation(730, 60 + i * 90);
            rxt[i].setSize(230, 40);
            totalGUI.add(rxt[i]);

            ry[i].setLocation(970, 10 + i * 90);
            ry[i].setSize(230, 40);
            totalGUI.add(ry[i]);
            ryt[i].setLocation(970, 60 + i * 90);
            ryt[i].setSize(230, 40);
            totalGUI.add(ryt[i]);


        }
        ButtonGroup gr = new ButtonGroup();

        JButton button = new JButton("Test");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //totalGUI.setOpaque(true);
                double mz[] = new double[k];
                double vxz[] = new double[k];
                double vyz[] = new double[k];
                double rxz[] = new double[k];
                double ryz[] = new double[k];
                for (int i = 0; i < k; i++) {
                    mz[i] = Double.parseDouble(m[i].getText());
                    vxz[i] = Double.parseDouble(vx[i].getText());
                    vyz[i] = Double.parseDouble(vy[i].getText());
                    rxz[i] = Double.parseDouble(rx[i].getText());
                    ryz[i] = Double.parseDouble(ry[i].getText());

                }
                showMovingCircle(mz,vxz,vyz,rxz,ryz);
            }
        });
        button.setLocation(1210, 500);
        button.setSize(60, 40);
        totalGUI.add(button);


        for (int i = 0; i < k + 2; i++) {
            r[i].setLocation(1210, 10 + i * 45);
            r[i].setSize(230, 40);
            totalGUI.add(r[i]);
            gr.add(r[i]);
        }
        r[0].setSelected(true);


        return (totalGUI);


    }
}