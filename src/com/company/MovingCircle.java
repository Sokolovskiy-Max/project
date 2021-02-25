package com.company;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class MovingCircle extends JComponent implements ActionListener {
    int kol = 4;
    int k = 0;
    int del[] = new int[kol];
    private double f = 0;
    private double scale;
    private Color color;
    private Timer timer;
    double m[] = new double[]{11 * Math.pow(10, 29), 11 * Math.pow(10, 29), 11 * Math.pow(10, 29), 11 * Math.pow(10, 29)};
    double vx[] = new double[]{9700, 10000, -10000, -10000};
    double vy[] = new double[]{-10000, 10000, 10000, -10000};
    double rx[] = new double[]{-1.5 * 1.5 * Math.pow(10, 11), 1.5 * 1.5 * Math.pow(10, 11), 1.5 * 1.5 * Math.pow(10, 11), -1.5 * 1.5 * Math.pow(10, 11)};
    double ry[] = new double[]{-1.5 * 1.5 * Math.pow(10, 11), -1.5 * 1.5 * Math.pow(10, 11), 1.5 * 1.5 * Math.pow(10, 11), 1.5 * 1.5 * Math.pow(10, 11)};
    Vect v[];
    Vect r[];
    Vect a[];
    Vect Vcm, rcm;

    public MovingCircle(Color color, int delay) {
        a = new Vect[kol];
        v = new Vect[kol];
        r = new Vect[kol];
        for (int i = 0; i < kol; i++) {
            v[i] = new Vect();
            a[i] = new Vect();
            v[i].x = vx[i];
            v[i].y = vy[i];
            a[i].x = 0;
            a[i].y = 0;
            del[i] = -1;
        }
        for (int i = 0; i < kol; i++) {
            r[i] = new Vect();
            r[i].x = rx[i];
            r[i].y = ry[i];
        }
        Vcm = new Vect();
        double M = 0;
        for (int i = 0; i < kol; i++) {
            Vcm = Vcm.VSum(v[i].VSp(m[i]));
            M = M + m[i];
        }
        Vcm = Vcm.VSp(1 / M);
        scale = 1.0;
        timer = new Timer(delay, this);
        this.color = color;
        setPreferredSize(new Dimension(1000, 1000));
        rcm = new Vect();
        rcm.x = 500 * (1.5 * Math.pow(10, 9));
        rcm.y = 500 * (1.5 * Math.pow(10, 9));
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }


    public static Vect a(Vect[] r, Vect[] v, double[] m, int kol, int n) {
        Vect a[] = new Vect[kol];
        Vect ao = new Vect();
        ao.x = 0;
        ao.y = 0;
        for (int i = 0; i < kol; i++) {
            a[i] = new Vect();
            if (i != n) {
                a[i] = (r[n].VMi(r[i])).VSp((m[i] * (-6.67 * Math.pow(10, -11)) / Math.pow((r[n].VMi(r[i])).Le(), 3)));
            } else {
                a[i].x = 0;
                a[i].y = 0;
            }
            ao = ao.VSum(a[i]);
        }
        return (ao);
    }


    public static Vect v(Vect v, Vect a, double ti) {
        Vect dv = a.VSp(ti);
        Vect v1 = v.VSum(dv);
        return v1;
    }

    public static Vect r(Vect r, Vect v, double ti, Vect Vcm) {
        Vect dr = (v).VSp(ti);
        Vect r1 = r.VSum(dr);
        return r1;
    }

    public static Vect rcm(Vect rcm, Vect Vcm, double ti) {
        Vect drcm = Vcm.VSp(ti);
        Vect r = rcm.VSum(drcm);
        return r;
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    @Override

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        int width = 1000;
        int height = 1000;
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.scale(scale, scale);

        for (int i = 0; i < kol; i++)
            a[i] = a(r, v, m, kol, i);
        for (int i = 0; i < kol; i++)
            v[i] = v(v[i], a[i], 200000);
        for (int i = 0; i < kol; i++)
            r[i] = r(r[i], v[i], 200000, Vcm);

        rcm = rcm(rcm, Vcm, 200000);
        for (int i = 0; i < kol; i++) {

            for (int j = i + 1; j < kol; j++) {
                if ((i != del[0]) && (i != del[1]) && (i != del[2]) && (i != del[3]) && (j != del[0]) && (j != del[1]) && (j != del[2]) && (j != del[3])) {
                    if (Math.abs((r[i].VMi(r[j])).Le()) < 2 * Math.pow(10, 10)) {
                        v[i] = ((v[i].VSp(m[i])).VSum(v[j].VSp(m[j]))).VSp(1 / (m[i] + m[j]));
                        m[i] = m[j] + m[i];
                        m[j] = 0;
                        r[j].y = 0;
                        r[j].x = 5000;
                        v[j].y = 0;
                        v[j].x = 0;
                        del[k] = j;
                        k++;
                    }
                }
            }
        }


        for (int i = 0; i < kol; i++) {
            if ((i != del[0]) && (i != del[1]) && (i != del[2]) && (i != del[3])) {
                g2d.setColor(new Color(100 + i * 35, 100 - i * 20, 100 + i * 30));
                Ellipse2D el = new Ellipse2D.Double((r[i].x - rcm.x) / (1.5 * Math.pow(10, 9)) + 1000, (r[i].y - rcm.y) / (1.5 * Math.pow(10, 9)) + 1000, 20, 20);
                g2d.fill(el);


            }
        }


    }

    public static void showMovingCircle(double[] mz,double[] vxz,double []vyz[]) {

        JFrame frame = new JFrame("Moving Circle");
        JPanel panel = new JPanel();
        final MovingCircle MovingCircleB = new MovingCircle(Color.BLUE, 2);
        panel.add(MovingCircleB);
        frame.getContentPane().add(panel);
        final JButton bst = new JButton("Start");

        final JTextField tfm1 = new JTextField(10);
        final JTextField tfm2 = new JTextField(10);
        final JTextField tfm3 = new JTextField(10);
        final JTextField tfa1 = new JTextField(10);
        final JTextField tfa2 = new JTextField(10);
        final JTextField tfa3 = new JTextField(10);
        final JTextField tfe1 = new JTextField(10);
        ;
        final JTextField tfe2 = new JTextField(10);
        final JTextField tfe3 = new JTextField(10);

        bst.addActionListener(new ActionListener() {
            private boolean pulsing = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pulsing) {
                    pulsing = false;
                    MovingCircleB.stop();
                    bst.setText("Start");

                } else {
                    pulsing = true;
                    MovingCircleB.start();
                    bst.setText("Stop");
                }

            }
        });
        panel.add(bst);
        panel.add(tfm1);
        panel.add(tfm2);
        panel.add(tfm3);
        panel.add(tfa1);
        panel.add(tfa2);
        panel.add(tfa3);
        panel.add(tfe1);
        panel.add(tfe2);
        panel.add(tfe3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 1100);
        frame.setVisible(true);
    }
}