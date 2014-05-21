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
import javax.swing.table.DefaultTableModel;

import _0.namecard.vo.NameCardVO;
import _3.namecard.service.InsertService;
import _3.namecard.service.SelectService;

//메인에 창 만드는 거에 대한 간략한 설명이 있으니 여기선 생략
public class AddNameCard extends JFrame implements ActionListener {
	JLabel name_jl1;	// 이름 Label
	JLabel addr_jl2;	// 주소 Label
	JLabel tel_jl3;		// 번호 Label
	JLabel comp_jl4;	// 회사 Label
	JLabel emai_jl5;	// 이메일 Label
	JLabel group;		// 그룹 Label
	JButton btn1;		// 확인버튼(DB와 JTable에 추가한다)
	JButton btn2;		// 취소버튼(창을 닫는다)

	JTextField name_tf1;	// 이름 입력
	JTextField addr_tf2;	// 주소 입력
	JTextField tel_tf3;		// 번호 입력
	JTextField comp_tf4;	// 회사 입력
	JTextField emai_tf5;	// 이메일 입력
	JComboBox<String> group_combo;	// 그룹 입력
	String[] group_str = {"가족","친구","직장","동호회","거래처","기타"};
	
	JTable table;		// Main에서 전달된 table을 저장할 변수
	
	public AddNameCard() {
		this("명함 추가");
	}

	public AddNameCard(JTable table) {
		this("명함 추가");
		this.table = table;
	}

	public AddNameCard(String title) {
		super(title);
		setSize(300, 400);
		setLocation(750,250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());

		// ----Center-----------
		JPanel center = new JPanel(new GridLayout(6,1));	// center 패널의 레이아웃은 5행1열의 GridLayout 
		
		JPanel namePane = new JPanel();
		name_jl1 = new JLabel("이      름   ");
		name_tf1 = new JTextField(25);

		JPanel addrPane = new JPanel();
		addr_jl2 = new JLabel("주      소   ");
		addr_tf2 = new JTextField(25);

		JPanel telPane = new JPanel();
		tel_jl3 = new JLabel("전화번호");
		tel_tf3 = new JTextField(25);
		
		JPanel companyPane = new JPanel();
		comp_jl4 = new JLabel("회      사   ");
		comp_tf4 = new JTextField(25);
		
		JPanel emaillPane = new JPanel();
		emai_jl5 = new JLabel("이 메 일   ");
		emai_tf5 = new JTextField(25);
		
		JPanel groupPane = new JPanel();
		group = new JLabel("그      룹   ");
		group_combo = new JComboBox<String>(group_str);

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
	// 버튼 눌렀을때 하는 행동들
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		switch (btn.getText()) {
		case "확인":
			if(name_tf1.getText().length()!=0 && addr_tf2.getText().length()!=0 
				&& tel_tf3.getText().length()!=0  && comp_tf4.getText().length()!=0  && emai_tf5.getText().length()!=0 ){
				//디비작업
				int index=SelectService.getInstance().lastIndexSelect();	// DB에 저장된 마지막 index값을 불러온다.
					// 추가할 명함, 즉 NameCardVO를 생성한다.
				NameCardVO card = new NameCardVO(	index+1,	// 불러온 마지막 index값 다음 인덱스 번호(DB에 저장할 땐 자동증가라 필요없지만, 테이블은 자동증가가 아니므로 필요하다)
													name_tf1.getText(), // 이름
													addr_tf2.getText(), // 주소
													tel_tf3.getText(),  // 전화번호
													comp_tf4.getText(),	// 회사
													emai_tf5.getText(), //이메일
													group_combo.getSelectedIndex()		// 그룹 추가										
													);// 이메일
				
				InsertService.getInstance().insert(card);	// 생성된 명함을 DB에 추가한다.
				
				//테이블 작업
				String arr[] = new String[7];
				arr[0] = card.getIdx()+"";
				arr[1] = card.getName();
				arr[2] = card.getAddress();
				arr[3] = card.getTel();
				arr[4] = card.getCompany();
				arr[5] = card.getEmail();
				arr[6] = group_str[card.getGroup()];
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(arr);	//테이블에 추가(1줄 추가됨)
				JOptionPane.showMessageDialog(getParent(), "저장되었습니다.");
			}else
				JOptionPane.showMessageDialog(getParent(), "비어있는 항목이 있습니다.");
//			dispose();
			break;
		case "취소":				// System.exit(0);은 전체 프로그램 모두를 종료하는 것이고,
			dispose();			// dispose()는 현재 창만 닫는다. 
			break;
		}
	}
}
