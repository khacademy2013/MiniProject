package _3.namecard.service;


import java.sql.Connection;

import _1.namecard.db.DBUtil;
import _2.namecard.dao.NameCardDAO;

public class CountService {
	// 싱글톤 패턴으로 만듬
	private static CountService instance = new CountService();
	private CountService() {
		// 생성자 사용 못하게 만듬
	}
	public static CountService getInstance(){
		return instance;
	}
	/////////////////////////////////////////////////////
	public int count(){
		Connection conn = DBUtil.getConnection();
		int result = NameCardDAO.getInstance().getCount(conn);
		return result;
	}
}
