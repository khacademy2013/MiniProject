package _2.namecard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import _0.namecard.vo.NameCardVO;
import _1.namecard.db.DBUtil;

public class NameCardDAO {
	// 싱글톤 패턴으로 만듬
	private static NameCardDAO instance = new NameCardDAO();
	private NameCardDAO() {
		// 생성자 사용 못하게 만듬
	}
	public static NameCardDAO getInstance(){
		return instance;
	}
	/////////////////////////////////////////////////////
	// SQL 명령어 1개당 1개의 메소드를 만든다.
	
	// 테이블 생성 메소드
	/*public int creatTable(Connection conn){
		int result = -1;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "create table IF NOT EXISTS namecard("
					     + "idx int IDENTITY,"
					     + "name varchar(30),"
					     + "address varchar(50),"
					     + "tel varchar(30),"
					     + "email varchar(30)";
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(stmt);
		}
		return result;
	}*/
	
	// 전체개수를 구해오는 메소드
	public int getCount(Connection conn){
		int result = 0;	// 전체 데이터개수
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select count(*) from namecard";
			rs = stmt.executeQuery(sql);
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(stmt);
		}
		return result;
	}
	
	// 1 개의 레코드를 추가하는 메소드
	public int insert(Connection conn, NameCardVO nameCard){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into namecard (name,address,tel,company, email,groups) values (?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,nameCard.getName());
			pstmt.setString(2,nameCard.getAddress());
			pstmt.setString(3,nameCard.getTel());
			pstmt.setString(4, nameCard.getCompany());
			pstmt.setString(5, nameCard.getEmail());
			pstmt.setInt(6, nameCard.getGroup());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println(" 나 에러임?");
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return result;		// 0이 넘어가면 에러가 난것이고 1개의 레코드가 성공했으면 1이 넘어간다.
	}
	
	// 1 개의 레코드를 Index로 검색해서 가져오는 메소드
	public NameCardVO selectByIdx(Connection conn, int idx){
		NameCardVO nameCard = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from namecard where idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()){
				nameCard = new NameCardVO();
				nameCard.setIdx(rs.getInt("idx"));
				nameCard.setName(rs.getString("name"));
				nameCard.setAddress(rs.getString("address"));
				nameCard.setTel(rs.getString("tel"));
				nameCard.setCompany(rs.getString("company"));
				nameCard.setEmail(rs.getString("email"));
				nameCard.setGroup(rs.getInt("groups"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return nameCard;	// 동작이 안했다면 null값으로 넘어갈것이고 성공했다면 데이터가 채워져서 넘어간다.
	}
	
	// 1 개의 레코드를 이름으로 검색해서 가져오는 메소드. 리스트로 리턴
	public NameCardVO selectByName(Connection conn, String name){
		NameCardVO nameCard = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from namecard where name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				nameCard = new NameCardVO();
				nameCard.setIdx(rs.getInt("idx"));
				nameCard.setName(rs.getString("name"));
				nameCard.setAddress(rs.getString("address"));
				nameCard.setTel(rs.getString("tel"));
				nameCard.setCompany(rs.getString("company"));
				nameCard.setEmail(rs.getString("email"));
				nameCard.setGroup(rs.getInt("groups"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return nameCard;	// 동작이 안했다면 null값으로 넘어갈것이고 성공했다면 데이터가 채워져서 넘어간다.
	}
	
	// 1 개의 레코드를 그룹으로 검색해서 가져오는 메소드
	public ArrayList<NameCardVO> selectByGroup(Connection conn, int group){
		ArrayList<NameCardVO> list =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from namecard where groups=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, group);
			rs = pstmt.executeQuery();
			if(rs.next()){
				list = new ArrayList<NameCardVO>();
				do{
					NameCardVO nameCard = new NameCardVO();
					nameCard.setIdx(rs.getInt("idx"));
					nameCard.setName(rs.getString("name"));
					nameCard.setAddress(rs.getString("address"));
					nameCard.setTel(rs.getString("tel"));
					nameCard.setCompany(rs.getString("company"));
					nameCard.setEmail(rs.getString("email"));
					nameCard.setGroup(rs.getInt("groups"));
					list.add(nameCard);
				}while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return list;	// 동작이 안했다면 null값으로 넘어갈것이고 성공했다면 데이터가 채워져서 넘어간다.
	}
		
	// 모든 레코드를 가져오는 메소드(나중에 페이징 작업을 해주어야 한다)
	public ArrayList<NameCardVO> selectByList(Connection conn){
		ArrayList<NameCardVO> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from namecard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){	// 존재하냐 존재하지 않냐에 대한 if문
				list = new ArrayList<NameCardVO>();
				do{
					NameCardVO nameCard = new NameCardVO();
					nameCard.setIdx(rs.getInt("idx"));
					nameCard.setName(rs.getString("name"));
					nameCard.setAddress(rs.getString("address"));
					nameCard.setTel(rs.getString("tel"));
					nameCard.setCompany(rs.getString("company"));
					nameCard.setEmail(rs.getString("email"));
					nameCard.setGroup(rs.getInt("groups"));
					list.add(nameCard);
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return list;
	}
	
	// 1 개의 레코드를 삭제 메소드(idx로 지우기)
	public int delete(Connection conn, int idx){
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String sql = "delete from namecard where idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return result;
	}
	
	// 1 개의 레코드를 수정 메소드
	public int update(Connection conn, NameCardVO nameCard){
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String sql = "update namecard set address=?, tel=? , company=?, email=? , groups=? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nameCard.getAddress());
			pstmt.setString(2, nameCard.getTel());
			pstmt.setString(3, nameCard.getCompany());
			pstmt.setString(4, nameCard.getEmail());
			pstmt.setInt(5, nameCard.getGroup());
			pstmt.setInt(6, nameCard.getIdx());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return result;
	}	
	
	// 마지막 인덱스값 가져오기
	public int lastIndex(Connection conn){
		NameCardVO nameCard = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lastIndex=0;
		try {
			String sql = "select * from namecard order by idx desc limit 1";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				lastIndex=rs.getInt("idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt);
		}
		return lastIndex;	
	}
	
}
