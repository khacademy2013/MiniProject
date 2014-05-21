package _4.namecard.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarTest extends JFrame implements Runnable{
	JProgressBar p;
    JLabel status;
    public ProgressBarTest() {
    	super("프로그램 설치중");
    	JPanel panel = new JPanel();
		setLocation(550,400);
    	panel.setLayout(new BorderLayout());
        p = new JProgressBar();
        p.setMinimum(0);
        p.setMaximum(100);
        p.setValue(0);
        status = new JLabel("");
        panel.add(p,"Center");
        panel.add(status, "South");
        
        add(panel);
        pack();
        setVisible(true);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
		thread.start();
    }
    public Dimension getPreferredSize() {
            return new Dimension(300, 80);
    }
	@Override
	public void run() {
		try{
            for(int i=0;i<=100;i++) {
                p.setValue(i);
                Thread.sleep(25);
                status.setText(i+"% 진행중...");
                if(i==100){
        			JOptionPane.showMessageDialog(getParent(), "설치 완료");        			
        			new NameCardMain();
        			dispose();
                }
            }
        }
        catch (InterruptedException e) {}
	}
}

