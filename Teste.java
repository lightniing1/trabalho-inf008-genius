package genius;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Teste extends JFrame {
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  public Teste() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws HeadlessException {
    Teste teste1 = new Teste();
    teste1.setVisible(true);
  }
  private void jbInit() throws Exception {
    this.setSize(800,600);
    this.getContentPane().setLayout(null);
    jButton1.setBounds(new Rectangle(98, 247, 197, 31));
    jButton1.setText("jButton1");
    jButton1.addActionListener(new Teste_jButton1_actionAdapter(this));
    jButton2.setBackground(Color.orange);
    jButton2.setBounds(new Rectangle(71, 100, 73, 42));
    jButton2.setText("jButton2");
    jButton2.setVisible(false);
    jButton3.setVisible(true);
    jButton3.setBackground(Color.blue);
    jButton3.setBounds(new Rectangle(68, 99, 81, 42));
    jButton3.setText("jButton3");
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton3, null);
  }

  void jButton1_actionPerformed(ActionEvent e) {
      try {
       MudaCor n =new MudaCor(jButton2,jButton3,this);
      }
      catch (Exception ex) {
        ex.printStackTrace();
    }
  }
}

class Teste_jButton1_actionAdapter implements java.awt.event.ActionListener {
  Teste adaptee;

  Teste_jButton1_actionAdapter(Teste adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}