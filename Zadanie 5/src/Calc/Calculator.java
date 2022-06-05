package Calc;

import javax.swing.*;

public class Calculator {
    JPanel panel1;
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    JButton dot, bcsk, clear, sign;
    JButton res;
    JButton add;
    JButton sub;
    JButton mul;
    JButton div;
    JLabel scr;
    JButton[] cs = new JButton[] {b9, b8, b7, b6, b5, b4, b3, b2, b1, b0, dot, bcsk, clear, sign};
    JButton[] op = new JButton[] {add, sub, mul, div, res};
    String cnt = "0";
    float ac = 0;
    String opSelected = "";
    boolean inEnd = false;

    Calculator() {
        Concatenate cenv = new Concatenate(this);
        for (JButton el : cs) {
            el.addActionListener(cenv);
        }
        Operation oenv = new Operation(this);
        for (JButton el : op) {
            el.addActionListener(oenv);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kalkulator");
        frame.setContentPane(new Calculator().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
