package _4.namecard.main;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import _0.namecard.vo.NameCardVO;
import _3.namecard.service.SelectService;

public class InfoNameCard extends JFrame implements ActionListener{
	//JLabel company;	// 회사 Label
	//JLabel name;	// 이름 Label

	JLabel addr_1,addr_2;	// 주소 Label
	JLabel tel_1,tel_2;		// 번호 Label
	JLabel emai_1,emai_2;	// 이메일 Label
	JLabel group_1,group_2;	// 그룹 Label
	String[] group_str = {"가족","친구","직장","동호회","거래처","기타"};
	
	JButton btn1;		// 확인버튼

	JPanel main_center;
	
	JPanel top;
	JPanel mid;
	JPanel bot;
	
	JTable table;		// Main에서 전달된 table을 저장할 변수
	
	NameCardVO nameCard;
	
	public InfoNameCard() {
		super("명함 정보 보기");
	}
	public InfoNameCard(NameCardVO name) {
		this(name.getIdx());
	}
	
	public InfoNameCard(int index) {
		super("명함 정보 보기");
		setSize(450, 250);
		setLocation(272,475);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		main_center = new JPanel();
		main_center.setLayout(new GridLayout(3,1));

		nameCard = SelectService.getInstance().select(index);
		// ----  Top -----------
		
		top =new JPanel(){
			public void paint(Graphics g) {
				super.paint(g);
				/*
				g.drawString("아리랑", 50, 30);
				
				Font ft1 = new Font("바탕체", Font.BOLD, 15);
				g.setFont(ft1);
				g.drawString("아리랑", 50, 60);
				*/
				Font ft2 = new Font("궁서체", Font.ITALIC, 25);
				g.setFont(ft2);
				g.drawString(nameCard.getCompany(), 50, 40);
			}
		};
		mid =new JPanel(){
			public void paint(Graphics g) {
				super.paint(g);
				Font ft2 = new Font("궁서체", Font.ITALIC, 25);
				g.setFont(ft2);
				g.drawString(nameCard.getName(), 300, 40);
			}
		};
		
		// ----Bottom-----------
		JPanel addrPane = new JPanel();
		addr_1 = new JLabel("주      소   ",SwingConstants.CENTER);
		addr_2 = new JLabel(nameCard.getAddress(),SwingConstants.LEFT);
		addrPane.setLayout(new GridLayout(1,2));
		addrPane.add(addr_1);
		addrPane.add(addr_2);
		
		JPanel telPane = new JPanel();
		tel_1 = new JLabel("전화번호  ",SwingConstants.CENTER);
		tel_2 = new JLabel(nameCard.getTel(),SwingConstants.LEFT);
		telPane.setLayout(new GridLayout(1,2));
		telPane.add(tel_1);
		telPane.add(tel_2);
		
		JPanel emailPane = new JPanel();
		emai_1 = new JLabel("이  메   일   ",SwingConstants.CENTER);
		emai_2 = new JLabel(nameCard.getEmail(),SwingConstants.LEFT);
		emailPane.setLayout(new GridLayout(1,2));
		emailPane.add(emai_1);
		emailPane.add(emai_2);
		
		JPanel groupPane = new JPanel();
		group_1 = new JLabel("그      룹   ",SwingConstants.CENTER);
		group_2 = new JLabel(group_str[nameCard.getGroup()],SwingConstants.LEFT);
		groupPane.setLayout(new GridLayout(1,2));
		groupPane.add(group_1);
		groupPane.add(group_2);
		
		bot =new JPanel();
		bot.setLayout(new GridLayout(4,1));
		bot.add(addrPane);
		bot.add(telPane);
		bot.add(emailPane);
		bot.add(groupPane);
		
		main_center.add(top);
		main_center.add(mid);
		main_center.add(bot);
		
		add(main_center,BorderLayout.CENTER);
		
		// ---버튼--------------
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		btn1 = new JButton("확인");
		btn1.addActionListener(this);
		bottom.add(btn1);

		add(bottom, BorderLayout.SOUTH);
		setVisible(true);
	}
	@Override
	// 버튼 눌렀을때 하는 행동들
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn.getText().equals("확인")) {
			dispose();
		}
	}
	public static void main(String[] args) {
		new InfoNameCard();
	}
}
