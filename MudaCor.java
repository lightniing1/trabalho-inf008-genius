package genius;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MudaCor extends Thread {

    JButton bt = null;
    JButton bt2 = null;
    JFrame frame = null;

    public MudaCor(JButton j, JButton y, JFrame x) {
        bt = j;
        bt2 = y;
        frame = x;
        start();
    }

    @Override
    public void run() {
        bt.setVisible(true);
        bt2.setVisible(false);
        frame.repaint();
        try {
            MudaCor.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        bt.setVisible(false);
        bt2.setVisible(true);
        frame.repaint();
    }
}