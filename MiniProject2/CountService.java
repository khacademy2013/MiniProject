package me.namecard.service;

import java.sql.Connection;

import me.namecard.dao.NameCardDAO;
import me.namecard.db.DBUtil;
import me.namecard.vo.NameCard;

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
		DBUtil.serverStart();
		Connection conn = DBUtil.getConnection();
		int result = NameCardDAO.getInstance().getCount(conn);
		DBUtil.serverShutdown();
		return result;
	}
	
}
