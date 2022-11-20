package db;
//전우진 MySQL 연동
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import game.Socket;
import game.Login;
import game.Server;
import user.IdCheck;
import user.IdOverlap;
import user.PwCheck;
import user.SignUp;

public class dbInfo {
	
	private java.sql.Statement statement;
	private Connection conn;
	private SignUp signUp;
	private ResultSet result;
	private String name, email, inputId, pw;

	// 2022-10-26 전우진 MySQL 연결
	public dbInfo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://114.71.137.174:61083/tcpserver", 
					"tcpserver", 
					"serverjin");
			
			statement = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			System.out.println("예외발생 : 해당 드라이버가 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("예외발생 : 접속 정보 확인이 필요합니다.");
			e.printStackTrace();
		}
	}
		// 2022-10-26 회원가입 db 연결
		// 회원가입 할 때 텍스트필드에서 값 긁어와서 DB에 던지기
		public void infoInsert(String id, String pw, String nick, String email, int count) {
			String sqlInsert = "insert into TCP_user (id, pw, email, name, countwin)"
					+ " values('" + id + "', '" + pw + "', '" + email + "', '" + nick + "','" + count + "')";
		
			try {
				statement.executeUpdate(sqlInsert);
			} catch (SQLException e) {
				System.out.println("Insert Error!");
				//e.printStackTrace();
			} finally {
				try {
					statement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	}
		
		// 2022-10-26 아이디 중복 db 연결
		//아이디 중복 체크
		public void checkID(IdOverlap idOverlap, String idInput, JTextField tf) {
			try {
				result = statement.executeQuery("select id from TCP_user where id = '" + idInput + "'");
				
				if(result.next()) {
					JOptionPane.showMessageDialog(idOverlap, "이미 사용중인 아이디 입니다.");
					tf.setText("");
				}else {
					JOptionPane.showMessageDialog(idOverlap, "사용 가능한 아이디 입니다.");
					SignUp signUp = new SignUp("회원가입");
					signUp.setLocationRelativeTo(idOverlap);
					JTextField jfid = signUp.getId();
					idOverlap.dispose();
					
					jfid.setText(idInput);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 2022-10-26 패스워드 초기화 db 연결
		//패스워드 0000으로 초기화 -> 아이디와 이메일이 회원가입한 값과 동일한 경우에만
		public void resetPW(PwCheck pc, String id, String inputEmail) {
			try {
				result = statement.executeQuery("select email from TCP_user where id = '" + id + "'" );
			
				if(result.next()) {
					email = result.getString("email");
				}
				
				if(inputEmail.equals(email)) {
					String sqlUpdate = "update TCP_user set pw = '0000' where id = '" + id + "'";
					statement.executeUpdate(sqlUpdate);
					JOptionPane.showMessageDialog(pc, "이메일이 일치합니다. 비밀번호는 0000으로 초기화 됩니다.", "비밀번호 변경", JOptionPane.INFORMATION_MESSAGE);
					Login lg = new Login();
					lg.setLocationRelativeTo(pc);
					pc.dispose();
				}
				else {
					JOptionPane.showMessageDialog(pc, "아이디 또는 이메일이 일치하지 않습니다. ", "오류", JOptionPane.ERROR_MESSAGE);
					if(JOptionPane.showConfirmDialog(pc, 
							"고객센터로 전화하시겠습니까?",
							"힌트 오류",
							JOptionPane.YES_NO_OPTION
							) == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(pc, "고객센터 전화번호는 032-777-7777 입니다.", "고객센테 안내", JOptionPane.INFORMATION_MESSAGE);
						} 
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					statement.close();
					result.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
				// 2022-10-26 아이디 찾기 db 연결
				public void resetID(IdCheck ic, String inputName, String inputEmail) {
					
					try {
						result = statement.executeQuery("select id from TCP_user where name = '" + inputName + "'" );
					
						if(result.next()) {
							inputId = result.getString("id");
						} 
						result = statement.executeQuery("select email from TCP_user where name = '" + inputName + "'" );
						
						if(result.next()) {
							email = result.getString("email");
						}

							if(inputEmail.equals(email)) {
								JOptionPane.showMessageDialog(ic, "아이디는 " + inputId + "입니다." , "아이디 찾기", JOptionPane.INFORMATION_MESSAGE);
								Login lg = new Login();
								lg.setLocationRelativeTo(ic);
								ic.dispose();
							}
						else {
							JOptionPane.showMessageDialog(ic, "이메일이 일치하지 않습니다. ", "오류", JOptionPane.ERROR_MESSAGE);
							if(JOptionPane.showConfirmDialog(ic, 
									"고객센터로 전화하시겠습니까?",
									"이메일 오류",
									JOptionPane.YES_NO_OPTION
									) == JOptionPane.YES_OPTION) {
								JOptionPane.showMessageDialog(ic, "고객센터 전화번호는 032-777-7777 입니다.", "고객센테 안내", JOptionPane.INFORMATION_MESSAGE);
								} 
							} 
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							statement.close();
							result.close();
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				//로그인 검증, id값 돌려쓰기 위해서 String으로 id 리턴
				public void loginSelect(Login frame, String inputId, String inputPw, String name) {
					String id = inputId;
					String nickName = name;
					
					try {
						result = statement.executeQuery("select name from TCP_user where id = '" + inputId + "'" );
						
						if(result.next()) {
							nickName = result.getString("name");
						}
						result = statement.executeQuery("select pw from TCP_user where id = '" + inputId + "'" );
						if(result.next()) {
							pw = result.getString("pw");
							
							//입력한 비밀번호와 DB에 저장된 비밀번호가 일치하는지 검증
							if(pw.equals(inputPw)) {
								Server server = new Server("대기창", id, nickName);
								Thread thread = new Thread(new Runnable() {

									@Override
									public void run() {
										Socket c = new Socket(id);
										c.start();
									}
								});
								thread.start();
								frame.dispose();
								
							} else {
								JOptionPane.showMessageDialog(frame, "입력 정보를 다시 확인해주세요.", "정보 오류", JOptionPane.ERROR_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(frame, "입력 정보를 다시 확인해주세요.", "정보 오류", JOptionPane.ERROR_MESSAGE);
						}
						
					} catch (SQLException e) {
						System.out.println("select Query Error!");
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(frame, "입력 정보를 다시 확인해주세요.", "정보 오류", JOptionPane.ERROR_MESSAGE);
					}
					
					finally {
						try {
							result.close();
							statement.close();
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				//전달받은 ID에 해당하는 정보를 가져옴
				public void pullInfo(String id, JTextField name, JTextField email, JPasswordField pw, JPasswordField pwCh) {
					try {
							result = statement.executeQuery("select name from TCP_user where id = '" + id + "'");
							if(result.next()) {
								name.setText(result.getString("name"));
							}
							result = statement.executeQuery("select email from TCP_user where id = '" + id + "'");
							if(result.next()) {
								email.setText(result.getString("email"));
							}
							result = statement.executeQuery("select pw from TCP_user where id = '" + id + "'");
							if(result.next()) {
								pw.setText(result.getString("pw"));
								pwCh.setText(result.getString("pw"));
							}
								
					} catch (SQLException e) {
						System.out.println("pull Informaiton Error!");
						e.printStackTrace();
					} finally {
						try {
							statement.close();
							result.close();
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				//회원정보 업데이트
				public void memberInfo(String id, JPasswordField pw) {
					char[] temp = pw.getPassword();
					String result = "";
					
					for(char ch	: temp) {
						Character.toString(ch);
						result += ""+ch+"";
					}
					String sqlUpdate = "update TCP_user set pw = '" + result + "' where id = '" + id + "'";
					try {
						statement.executeUpdate(sqlUpdate);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							statement.close();
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
				}
				
}
