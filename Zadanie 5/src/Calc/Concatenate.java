package Calc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Concatenate implements ActionListener {
    private final Calculator c;

    Concatenate(Calculator c){
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        Thread t = new Thread(() -> {
//            synchronized (c) {
                String content = ((JButton) e.getSource()).getText();
                int dotLen = 0;
                if (c.cnt.contains("."))
                    dotLen = c.cnt.substring(c.cnt.indexOf(".")).length();
                if (c.inEnd) {
                    c.inEnd = false;
                    c.cnt = "0";
                }
                if (content.equals("+/-")) {
                    if (c.cnt.charAt(0) != '-' && c.cnt.charAt(0) != '0') {
                        c.cnt = "-" + c.cnt;
                    } else if (c.cnt.charAt(0) != '-') {
                        c.cnt = c.cnt.substring(1);
                    }
                } else if (content.equals("C")) {
                    c.cnt = "0";
                } else if (content.equals("<")) {
                    if (c.cnt.length() == 1 || (c.cnt.length() == 2 && c.cnt.charAt(0) == '-'))
                        c.cnt = "0";
                    else if (c.cnt.charAt(c.cnt.length() - 2) == '.')
                        c.cnt = c.cnt.substring(0, c.cnt.length() - 2);
                    else
                        c.cnt = c.cnt.substring(0, c.cnt.length() - 1);
                } else if (c.cnt.equals("0") && !content.equals("0") && !content.equals(".")) {
                    c.cnt = content;
                } else if (content.equals(".") && !c.cnt.contains(".") && dotLen < 7) {
                    c.cnt += content;
                } else if (!c.cnt.equals("0") && !content.equals(".") && dotLen < 7) {
                    c.cnt += content;
                }
                c.scr.setText(c.cnt);
            }
//        });
//        t.start();
//    }
}
