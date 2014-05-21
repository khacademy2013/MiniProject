package _4.namecard.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import _0.namecard.vo.NameCardVO;
import _3.namecard.service.DeleteService;
import _3.namecard.service.SelectService;

public class NameCardMain extends JFrame implements ActionListener , Runnable{
	private JLabel title;				// BoderLayout.NORTH 영역에 새겨질 제목
	private JLabel group;				
	private JComboBox<String> group_combo;	// 그룹 콤보박스	
	private String[] group_str = {"가족","친구","직장","동호회","거래처","기타","전체보기"};	// 그룹 명칭
	private JLabel search;				// 검색 JLabel
	private JTextField search_text;		// 검색할 단어를 입력받는 곳
	private JButton add_card,update_card,del_card;	// 추가, 수정, 삭제 버튼
	private JButton exitBtn;			// 종료 버튼
	private JPanel center;				// BorderLayout의 CENTER 영역에 들어갈 판(?) - JTable을 위한 Panel
	private JTable table;				// 명함 목록을 보여줄 Table(표) 
	private DefaultTableModel model; 	// DefaultTableModel은 JTable의 기본 형태를 제공하는 클래스

	
	public NameCardMain() {				// 생성자 - 윈도우 창을 만들어주는 생성자
		super("명함관리 프로그램 v1.0");		// 윈도우 창 타이틀 이름을 설정.
		
		setSize(700,600);				// 창 크기 설정
		setLocation(250, 250);			// 창 위치 설정
			
		setLayout(new BorderLayout());		//이 창의 기본적인 Layout은 BorderLayout이다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// 윈도우 창을 종료함과 동시에 프로그램 종료
		//-------테이블 세팅--------------------------
		String colName[] = {"No","Name","Address","Tel","Company","Email","Group"};		// JTable에 들어갈 컬럼명		
		model = new DefaultTableModel(colName,0){			// colName을 헤더(컬럼명)로 가지고 있는 JTable의 기본적인 모델, 즉 틀을 설정한다.
	         public boolean isCellEditable(int i, int c){	// 더블클릭으로 인한 수정 여부 , return값이 false일시 수정하지 못한다.
	        	 return false;		
	         }
		};
		table = new JTable(model);		// 설정한 model을 table에 적용한다.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 JTable t = (JTable)e.getSource();
				 if(e.getClickCount()==2) {					// 테이블을 더블 클릭할 경우
					 int selectRow = t.getSelectedRow();	// 테이블에서 선택한 행번호를 가져온다.(0이 첫번째행), 만약 선택하지 않았다면 -1을 반환한다.
						if(selectRow==-1){													// row1값이 -1이라면
							JOptionPane.showMessageDialog(getParent(), "항목을 선택하세요");	// 메시지를 띄워주고
						}
						Object value1 = t.getValueAt(selectRow, 0);		// no값(=index값) 조회(선택된 행의 첫번째 열을 가져온다)
						new InfoNameCard(Integer.parseInt(value1.toString()));	// InfoNameCard로 index값(int형으로)을 넘겨준다.
				 }
			}
		});
		
		
		//--------상단---------------------------------
		JPanel top = new JPanel(new GridLayout(1,3));			// JPanel은 여러개의 컴포넌트(ex.JButton, JLabel등)을 하나로 묶어주는 역할을 한다.
		title = new JLabel("명함관리 프로그램 v1.0         ");				// JLabel은 그냥 글자나 이미지를 출력해주는 곳.
		
		group = new JLabel("그룹 : ",SwingConstants.RIGHT);		// 그룹 Label생성
		group_combo = new JComboBox<String>(group_str);			// group_str을 이름으로 갖는 콤보박스 생성
		group_combo.setSelectedIndex(6);						// 처음 선택된 콤보박스는 6번(전체보기)이다.
		group_combo.addActionListener(new ComboBoxEvent(table,model));	// table과 model을 ComboBoxEvent클래스에 전달,그리고 호출
		
		top.add(title);						 	// top 패널에 title를 추가한다.
		top.add(group);
		top.add(group_combo);					// top 패널에 group Label과 콤보박스를 추가한다.
						
		add(top,BorderLayout.NORTH);		// top 패널을 창에서 BorderLayout.NORTH에 추가한다. 
		
		//--------중단---------------------------------
		center = new JPanel();				// center 패널	( JPanel의 기본적인 레이아웃은 FlowLayout로 설정되어 있다.)
		center.add(new JScrollPane(table));		// 스크롤이 적용된 table을 center 패널에 추가한다.
		
		ArrayList<NameCardVO> list =null;
		list =SelectService.getInstance().select();	// 가져온 형태를  list에 저장한다;
		
		if(list!=null){	// 명함관리를 처음 시작시 DB에 데이터가 없으므로 null값이 올수 있다.
			for (NameCardVO v : list) {													
				String str[]={ v.getIdx()+"",v.getName(),v.getAddress()
							  ,v.getTel(),v.getCompany(),v.getEmail(),group_str[v.getGroup()]};	// 항목에 맞춰서 (index, name, address, tel, company, email)
				model.fireTableDataChanged();
				model.addRow(str);										// 한줄씩 추가한다.
			}
		}
		
		add(center,BorderLayout.CENTER);		// center 패널을 창에서 BorderLayout.CENTER에 추가한다. 
		
		//--------하단---------------------------------
		JPanel bottom = new JPanel();			// bottom 패널	( JPanel의 기본적인 레이아웃은 FlowLayout로 설정되어 있다.)
		
		search = new JLabel("검색",SwingConstants.CENTER);	
		search_text = new JTextField(10);					
		search_text.addKeyListener(new KeyAdapter() {	// 검색 Key이벤트
			@Override
			public void keyPressed(KeyEvent e) {
				int key_code = e.getKeyCode();			// Enter 버튼이 입력되었을 경우
				if(key_code==KeyEvent.VK_ENTER){
					NameCardVO nm = SelectService.getInstance().select(search_text.getText());	// 이름으로 DB 검색
					if(nm!=null){			// null값이 아닐경우  NameCardVO객체를 전달
						new InfoNameCard(nm);		
					}else					// 만약 null값일 경우 검색한 이름이 존재하지 않는 것이다.
						JOptionPane.showMessageDialog(getParent(), "검색하신 이름이 존재하지 않습니다.");
				}
			}
		});
		add_card = new JButton("추가");			// 추가 버튼 생성
		add_card.addActionListener(this);		// 추가 버튼에 액션리스너 연결
		update_card = new JButton("수정");		// 수정 버튼 생성
		update_card.addActionListener(this);	// 수정 버튼에 액션리스너 연결
		del_card = new JButton("삭제");			// 삭제 버튼 생성
		del_card.addActionListener(this);		// 삭제 버튼에 액션리스너 연결
		exitBtn = new JButton("나가기");			// 나가기 버튼 생성
		exitBtn.addActionListener(this);		// 나가기 버튼에 액션리스너 연결
		bottom.add(search);
		bottom.add(search_text);
		bottom.add(add_card);
		bottom.add(update_card);
		bottom.add(del_card);
		bottom.add(exitBtn);				// bottom 패널에 검색Label, 검색TextField, 추가, 수정, 삭제, 나가기 버튼 추가
		add(bottom,BorderLayout.SOUTH);		// bottom 패널을 창에서 BorderLayout.SOUTH에 추가한다. 
		
		pack();				// 창 사이즈를 내용에 맞게 줄여줌
		setVisible(true);	// 창을 보여준다(false로 설정시 창이 화면에 아예 보이지 않는다)	
		
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();	// 이벤트가 발생한 버튼을 가져온다.
		switch(btn.getText()){			// 이벤트가 발생한 버튼을 switch로 구분한다.(버튼의 text)
		case "추가":
			new AddNameCard(table);		// 명함을 추가하기 위한 클래스를 호출한다
			break;
		case "수정":
			int selectRow = table.getSelectedRow();	// 테이블에서 선택한 행번호를 가져온다.(0이 첫번째행), 만약 선택하지 않았다면 -1을 반환한다.
			if(selectRow==-1){													// row1값이 -1이라면
				JOptionPane.showMessageDialog(getParent(), "수정할 항목을 선택하세요");	// 메시지를 띄워주고
				break;															// 빠져나간다.
			}
			Object value1 = table.getValueAt(selectRow, 0);		// no값(=index값) 조회(선택된 행의 첫번째 열을 가져온다)
			new UpdateNameCard(table,Integer.parseInt(value1.toString()),selectRow);	// 명함을 수정하는 클래스를 호출하면서
																						// table, value1, selectRow 값을 
																						// 전달한다.
			break;
		case "삭제":
			int row2 = table.getSelectedRow();			// 테이블에서 선택한 행번호를 가져온다.
			if(row2==-1){
				JOptionPane.showMessageDialog(getParent(), "삭제할 항목을 선택하세요");	
				break;
			}else{
				int i=JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제 경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(i==0){
					Object value2 = table.getValueAt(row2, 0); 	// 선택한 행에서 첫번째 값, 즉 index값을 가져온다.
					// DB 삭제
					DeleteService.getInstance().delete(Integer.parseInt(value2.toString()));	// DB에서 index에 해당되는 레코드(데이터)를 삭제한다.
					// 테이블 삭제
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.removeRow(row2);		// table에서 row2에 해당하는 행을 지운다.
					
					break;
				}
				break;
			}
		case "나가기":	// 프로그램 종료 버튼
			JOptionPane.showMessageDialog(getParent(), "안녕히가세요");
			System.exit(0);			
			break;			
		}
	}

	// top 패널에 명함관리프로그램v1.0 텍스트를 스레드 처리하여 움직이도록 함.
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(100);		// 속도

				String str = title.getText();
				for(int i=0;i<str.length();i++){
					title.setText(str);
					char t = str.charAt(0);		
					str = str.substring(1);
					str += t;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
/*
	public static void main(String[] args) {
		NameCardMain nameCard = new NameCardMain();
	}*/
}
