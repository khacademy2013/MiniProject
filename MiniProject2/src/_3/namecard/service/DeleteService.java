

package _3.namecard.service;


import java.sql.Connection;

import _1.namecard.db.DBUtil;
import _2.namecard.dao.NameCardDAO;

public class DeleteService {
	// 싱글톤 패턴으로 만듬
	private static DeleteService instance = new DeleteService();
	private DeleteService() {
		// 생성자 사용 못하게 만듬
	}
	public static DeleteService getInstance(){
		return instance;
	}
	/////////////////////////////////////////////////////
	public int delete(int idx){
		Connection conn = DBUtil.getConnection();
		int result = NameCardDAO.getInstance().delete(conn, idx);
		return result;
	}
}
