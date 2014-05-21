package _4.namecard.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import _0.namecard.vo.NameCardVO;
import _3.namecard.service.SelectService;

public class ComboBoxEvent implements ActionListener{
	JTable table;
	DefaultTableModel model;
	private String[] group_str = {"가족","친구","직장","동호회","거래처","기타","전체보기"};
	
	public ComboBoxEvent(JTable table,DefaultTableModel model) {
		this.table = table;
		this.model = model;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// 모든 테이블 내용을 초기화한다.
		model.setRowCount(0);
		JComboBox combo = (JComboBox)e.getSource();
		ArrayList<NameCardVO> list =null;
		if(combo.getSelectedIndex()==6)
			list =SelectService.getInstance().select();	// 가져온 형태를  list에 저장한다;
		else{
			list =SelectService.getInstance().selectGroup(combo.getSelectedIndex());	// 가져온 형태를  nameCard에 저장한다
		}
			
		
		if(list!=null){	// 명함관리를 처음 시작시 DB에 데이터가 없으므로 null값이 올수 있다.
			for (NameCardVO v : list) {													
				String str[]={ v.getIdx()+"",v.getName(),v.getAddress()
							  ,v.getTel(),v.getCompany(),v.getEmail(),group_str[v.getGroup()]};	// 항목에 맞춰서 (index, name, address, tel, company, email)
				model.fireTableDataChanged();
				model.addRow(str);										// 한줄씩 추가한다.
			}
		}
	}
}
