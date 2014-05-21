package _4.namecard.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import _0.namecard.vo.NameCardVO;
import _3.namecard.service.SelectService;
import _3.namecard.service.UpdateService;

public class UpdateNameCard extends JFrame implements ActionListener{
	JLabel idx_Label;
	JLabel name_jl1, addr_jl2,tel_jl3, comp_jl4,emai_jl5;
	JLabel group;		// 그룹 Label
	
	JTextField idx_text,name_tf1,addr_tf2,tel_tf3,comp_tf4,emai_tf5;
	
	JButton btn1, btn2;
	JTable table;
	int index;
	int selectRow;
	JComboBox<String> group_combo;	// 그룹 입력
	String[] group_str = {"가족","친구","직장","동호회","거래처","기타"};
	
	NameCardVO nameCard;
	
	public UpdateNameCard() {
		// TODO Auto-generated constructor stub
	}
	public UpdateNameCard(JTable tb,int id, int sR) {
		super("명함 수정");
		this.table = tb;
		this.index = id;
		this.selectRow = sR;
		
		setSize(400, 400);
		setLocation(750,250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());

		// ----Center-----------
		JPanel center = new JPanel(new GridLayout(7,1));// center 패널의 레이아웃은 7행1열의 GridLayout
		nameCard = SelectService.getInstance().select(index);
		
		JPanel indexPane = new JPanel();
		idx_Label = new JLabel("인  덱  스   ");
		idx_text = new JTextField(nameCard.getIdx()+"", 25);
		idx_text.setEditable(false);

		JPanel namePane = new JPanel();
		name_jl1 = new JLabel("이      름   ");
		name_tf1 = new JTextField(nameCard.getName(), 25);
		name_tf1.setEditable(false);

		JPanel addrPane = new JPanel();
		addr_jl2 = new JLabel("주      소   ");
		addr_tf2 = new JTextField(nameCard.getAddress(),25);

		JPanel telPane = new JPanel();
		tel_jl3 = new JLabel("전화번호");
		tel_tf3 = new JTextField(nameCard.getTel(),25);
		
		JPanel companyPane = new JPanel();
		comp_jl4 = new JLabel("회      사   ");
		comp_tf4 = new JTextField(nameCard.getCompany(),25);
		
		JPanel emaillPane = new JPanel();
		emai_jl5 = new JLabel("이  메  일   ");
		emai_tf5 = new JTextField(nameCard.getEmail(),25);
		
		JPanel groupPane = new JPanel();
		group = new JLabel("그      룹   ");
		group_combo = new JComboBox<String>(group_str);

		indexPane.add(idx_Label);
		indexPane.add(idx_text);
		namePane.add(name_jl1);//
		namePane.add(name_tf1);
		addrPane.add(addr_jl2);//
		addrPane.add(addr_tf2);
		telPane.add(tel_jl3);//
		telPane.add(tel_tf3);
		companyPane.add(comp_jl4);//
		companyPane.add(comp_tf4);
		emaillPane.add(emai_jl5);//
		emaillPane.add(emai_tf5);
		groupPane.add(group);
		groupPane.add(group_combo);
		center.add(indexPane);
		center.add(namePane);
		center.add(addrPane);
		center.add(telPane);
		center.add(companyPane);
		center.add(emaillPane);
		center.add(groupPane);
		add(center, BorderLayout.CENTER);

		// ---버튼--------------
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		btn1 = new JButton("확인");
		btn1.addActionListener(this);
		btn2 = new JButton("취소");
		btn2.addActionListener(this);
		bottom.add(btn1);
		bottom.add(btn2);

		add(bottom, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		switch (btn.getText()) {
		case "확인":
			// DB 수정
			nameCard.setAddress(addr_tf2.getText());
			nameCard.setTel(tel_tf3.getText());
			nameCard.setCompany(comp_tf4.getText());
			nameCard.setEmail(emai_tf5.getText());
			nameCard.setGroup(group_combo.getSelectedIndex());
			UpdateService.getInstance().update(nameCard);
			
			//테이블 수정
			table.setValueAt(addr_tf2.getText(), selectRow, 2);
			table.setValueAt(tel_tf3.getText(), selectRow, 3);
			table.setValueAt(comp_tf4.getText(), selectRow, 4);
			table.setValueAt(emai_tf5.getText(), selectRow, 5);
			table.setValueAt(group_str[group_combo.getSelectedIndex()], selectRow, 6);
			JOptionPane.showMessageDialog(getParent(), "수정완료");	// 수정 완료 메시지를 띄워주고
			dispose();	//창을 닫아준다.
			break;
		case "취소":
			dispose();
			break;
				
		}
	}
}
