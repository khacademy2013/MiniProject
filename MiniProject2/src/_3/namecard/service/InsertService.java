package _3.namecard.service;


import java.sql.Connection;

import _0.namecard.vo.NameCardVO;
import _1.namecard.db.DBUtil;
import _2.namecard.dao.NameCardDAO;

public class InsertService {
	// 싱글톤 패턴으로 만듬
		private static InsertService instance = new InsertService();
		private InsertService() {
			// 생성자 사용 못하게 만듬
		}
		public static InsertService getInstance(){
			return instance;
		}
		/////////////////////////////////////////////////////
		public int insert(NameCardVO nameCard){
			Connection conn = DBUtil.getConnection();
			int result = NameCardDAO.getInstance().insert(conn, nameCard);
			return result;		//0이면 에러, 1이면 추가성공
		}
}
