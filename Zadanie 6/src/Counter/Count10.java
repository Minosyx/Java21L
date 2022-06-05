package Counter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Count10 implements Runnable
{
    private JButton STARTButton;
    private JPanel panel1;
    private JButton STOPButton;
    private JLabel label1;
    private JButton RESETButton;

    private int min = 10;
    private int sec = 0;

    volatile Thread t;
    private volatile boolean threadSuspended = false;
    private volatile boolean threadStarted = false;

    public Count10() {
        t = new Thread(this);

        STARTButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                if (!threadStarted) {
                    threadStarted = true;
                    t.start();
                }
                threadSuspended = false;

            }
        });
        STOPButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                threadSuspended = true;
            }
        });
        RESETButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    public void reset(){
        min = 10;
        sec = 0;
        label1.setText("00:" + String.format("%02d", min) + ":" + String.format("%02d", sec));
        threadSuspended = true;
    }

    @Override
    public void run()
    {
        while ( true ) {
            if (sec > 0) {
                sec--;
            } else {
                sec = 59;
                min--;
            }
            if (min == -1) {
                reset();
            }
            if (threadSuspended) {
                synchronized (this) {
                    while (threadSuspended) {
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                }
            }
            label1.setText("00:" + String.format("%02d", min) + ":" + String.format("%02d", sec));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Odliczanie");
        frame.setContentPane(new Count10().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}