package db;
// 랭킹 DB
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;

import game.Rank;

public class DBRank {
	
	private Connection conn =null;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ResultSet result;
	private String username,ID;
	private String SQLUpdate;
	private String SQLSELECT_win;
	private String SQLSELECT_tier;
	private int winScore;
	private int tiersum;
	
	//2022-10-27 허유진 랭킹DB구현
	public DBRank(String ID){
		this.ID = ID;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://114.71.137.174:61083/tcpserver",
					"tcpserver", 
					"serverjin");
		
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			System.out.println("예외발생 : 해당 드라이버가 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("예외발생 : 접속 정보 확인이 필요합니다.");
			e.printStackTrace();
		}
	}
	
	//랭킹 보여주기
	public void showRank(Rank rank, int count) {
		rank.getModel().setNumRows(0);
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery("select * from TCP_user order by countwin desc");
			int num = 0;
		
			while(result.next()) {
				String[] imsi = {result.getString("name"),result.getString("tier"),result.getString("countwin")};
				rank.getModel().addRow(imsi);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//Server 이름 정보 가져오는 디비
	public void UserName(String id, JLabel name) {
		try {
			result = stmt.executeQuery("select name from TCP_user where id ='" + id + "'");
			
			if(result.next()) {
				name.setText(result.getString("name")+"님"); 
			}
		} catch (SQLException e) {
			System.out.println("이름값을 받아오지 못합니다.");
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void UpdateCountTier(String ID) {
			try {
				result = stmt.executeQuery("SELECT countwin FROM TCP_user WHERE ID='"+ID+"'");
				if(result.next()) {
					winScore = result.getInt(1);
				}
				result = stmt.executeQuery("SELECT tier FROM TCP_user WHERE ID='"+ID+"'");
				if(result.next()) {
					tiersum = result.getInt(1);
				}
				
				 winScore += 1;
		         tiersum  += 30;

		         SQLUpdate = "UPDATE TCP_user SET countwin='"+(winScore)+"',tier='"+(tiersum)+"'"
		         		+ " WHERE id = '"+ID+"'";
		         
		        stmt.executeUpdate(SQLUpdate);
	
			} catch (SQLException e) {
				System.out.println("승리 후 값 업데이트 안됨");
				e.printStackTrace();
			}finally {
				try {
					result.close();
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
}	

	

