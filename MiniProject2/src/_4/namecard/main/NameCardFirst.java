package _4.namecard.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class NameCardFirst extends JFrame implements ActionListener {
	JLabel setup;
	JButton btn1, btn2;
	ProgressBarTest pro;
	
	public NameCardFirst() {
		this("명함관리 설치 관리자");
	}
	
	public NameCardFirst(String title) {
		super(title);
		setSize(300,300);
		setLocation(550,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
				
		setup = new JLabel("명함관리 프로그램을 설치하시겠습니까?");
		panel.add(setup);
		add(panel, BorderLayout.CENTER);
		
		JPanel botton = new JPanel();
		botton.setLayout(new FlowLayout());
		btn1 = new JButton("Ok");
		btn1.addActionListener(this);
		btn2 = new JButton("cancel");
		btn2.addActionListener(this);
		botton.add(btn1);
		botton.add(btn2);
		
		add(botton, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		switch(btn.getText()){
		case "Ok":
			new ProgressBarTest();
			dispose();
			break;
		case "cancel": 
			JOptionPane.showMessageDialog(getParent(), "OK 눌러~!!");
			break;														
			}
		
	}
	
	public static void main(String[] args) {
		new NameCardFirst();
	}

}

