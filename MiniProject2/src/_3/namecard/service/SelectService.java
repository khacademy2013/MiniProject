package _3.namecard.service;


import java.sql.Connection;
import java.util.ArrayList;

import _0.namecard.vo.NameCardVO;
import _1.namecard.db.DBUtil;
import _2.namecard.dao.NameCardDAO;

public class SelectService {
	// 싱글톤 패턴으로 만듬
	private static SelectService instance = new SelectService();
	private SelectService() {
		// 생성자 사용 못하게 만듬
	}
	public static SelectService getInstance(){
		return instance;
	}
	////////Index로 1개 조회////////////////////////////
	public NameCardVO select(int idx){
		Connection conn = DBUtil.getConnection();
		NameCardVO nameCard = NameCardDAO.getInstance().selectByIdx(conn, idx);
		return nameCard;
	}
	////////이름으로 1개 조회////////////////////////////
	public NameCardVO select(String name){
		Connection conn = DBUtil.getConnection();
		NameCardVO nameCard = NameCardDAO.getInstance().selectByName(conn, name);
		return nameCard;
	}
	////////그룹으로 1개 조회////////////////////////////
	public  ArrayList<NameCardVO> selectGroup(int group){
		Connection conn = DBUtil.getConnection();
		 ArrayList<NameCardVO> list = NameCardDAO.getInstance().selectByGroup(conn, group);
		return list;
	}
	////////전체 조회////////////////////////////
	public ArrayList<NameCardVO> select(){
		Connection conn = DBUtil.getConnection();
		ArrayList<NameCardVO> list = NameCardDAO.getInstance().selectByList(conn);
		return list;
	}
	////////마지막 인덱스 조회////////////////////////////
	public int lastIndexSelect(){
		Connection conn = DBUtil.getConnection();
		int lastIndex = NameCardDAO.getInstance().lastIndex(conn);		
		return lastIndex;
	}
}
