package Calc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class Operation implements ActionListener {
    private final Calculator c;

    Operation(Calculator c){
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        Thread t = new Thread(() -> {
//            synchronized (c) {
                String content = ((JButton) e.getSource()).getText();
                switch (c.opSelected) {
                    case "" -> {
                        c.ac = Float.parseFloat(c.cnt);
                        c.opSelected = content;
                        c.inEnd = true;
                    }
                    case "+" -> {
                        c.ac += Float.parseFloat(c.cnt);
                        c.opSelected = content;
                        c.inEnd = true;
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        nf.setGroupingUsed(false);
                        nf.setMaximumFractionDigits(6);
                        c.scr.setText(nf.format(c.ac));
                    }
                    case "-" -> {
                        c.ac -= Float.parseFloat(c.cnt);
                        c.opSelected = content;
                        c.inEnd = true;
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        nf.setGroupingUsed(false);
                        nf.setMaximumFractionDigits(6);
                        c.scr.setText(nf.format(c.ac));
                    }
                    case "*" -> {
                        c.ac *= Float.parseFloat(c.cnt);
                        c.opSelected = content;
                        c.inEnd = true;
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        nf.setGroupingUsed(false);
                        nf.setMaximumFractionDigits(6);
                        c.scr.setText(nf.format(c.ac));
                    }
                    case "/" -> {
                        c.ac /= Float.parseFloat(c.cnt);
                        c.opSelected = content;
                        c.inEnd = true;
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                        nf.setGroupingUsed(false);
                        nf.setMaximumFractionDigits(6);
                        c.scr.setText(nf.format(c.ac));
                    }
                }
                if (content.equals("=")) {
                    c.opSelected = "";
                    c.ac = 0;
                    c.cnt = "0";
                }
            }
//        });
//        t.start();
//    }
}
