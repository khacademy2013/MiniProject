package _3.namecard.service;


import java.sql.Connection;

import _0.namecard.vo.NameCardVO;
import _1.namecard.db.DBUtil;
import _2.namecard.dao.NameCardDAO;

public class UpdateService {
	// 싱글톤 패턴으로 만듬
	private static UpdateService instance = new UpdateService();
	private UpdateService() {
		// 생성자 사용 못하게 만듬
	}
	public static UpdateService getInstance(){
		return instance;
	}
	/////////////////////////////////////////////////////
	public int update(NameCardVO nameCard){
		Connection conn = DBUtil.getConnection();
		int result = NameCardDAO.getInstance().update(conn, nameCard);
		return result;
	}
}
