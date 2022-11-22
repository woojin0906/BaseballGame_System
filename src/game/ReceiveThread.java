package game;
// 소켓 연결 후 서버 게임
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.DBRank;

public class ReceiveThread extends Thread {
	static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());
	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	Chat chat;
	Random r = new Random();
	String ID;
	int countwin;
	int tier;
	
	public ReceiveThread (Socket socket, Chat chat, String ID) {
		this.socket = socket;
		this.chat = chat;
		this.ID = ID;
		
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			list.add(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		int[] randomNum = new int [3];
		String serverNum = null;
		
			for(int i=0; i<randomNum.length; i++) {			// i부터 시작 -> randomNum의 길이까지 i의 값을 1씩 증가
				randomNum[i]=r.nextInt(9) +1;				// randomNum 배열의 i번째에 랜덤 수 삽입
				for(int j=0; j<i; j++) {					// 중복검사 소스
					if(randomNum[i]==randomNum[j]) {		
						i--;								// 랜덤함수가 같은 경우 되돌아가기
						break;								
					}
				}
			}
			
			serverNum=randomNum[0]+" "+randomNum[1]+" "+randomNum[2];	// 서버에 랜덤함수 표시
			System.out.println("서버 숫자 ->" + serverNum);
			
			sendAll("야구 게임이 시작됩니다!!");

				int count=1;		// 10번 카운트 주기 위해
				while (true) {		
					int strike=0;
					int ball=0;
					
					sendAll("----------------------------");
					sendAll("세자리 수를 입력하세요(ex: 1 2 3)");
					
					String inputMsg;
					try {
						inputMsg = in.readLine();
						
							System.out.print("클라이언트가 입력한 수 -> " + inputMsg + "\n");
							System.out.println("-----------------------------> ");
							
						int[] msg = new int[3]; 
						
						if(inputMsg.length()==3) {
	                        		// 입력받은 문자열 -> 정수 변환 -> 배열에 담기
	                        msg[0] = inputMsg.charAt(0)-'0';    // 입력받은 첫 번째 문자열
	                        msg[1] = inputMsg.charAt(1)-'0';	// 입력받은 두 번째 문자열
	                        msg[2] = inputMsg.charAt(2)-'0';	// 입력받은 세 번째 문자열
	                  } else {
	                	 	sendAll("3자리를 입력해주세요.(공백제외)");
	                        continue;       	// 공백 없이 3자리가 아닌 경우 메시지 보내기
	                  }
						
						for(int i=0; i<3; i++) {
							for(int j=0; j<3; j++) {
								if(randomNum[i]==msg[j]) {
									if(i==j) {
										strike++;
										System.out.println(msg[i]);
									}
									else {
										ball++;
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					String resultMsg = strike + " 스트라이크 " + ball + " 볼 (" + count + " )번째"; 

					System.out.println(resultMsg);
					sendAll(resultMsg);

					count++;
					if(count>=11) {
						System.out.println("서버승리");

						sendAll("lose");  		// 서버 승리 시 클라이언트가 패배했다는 문자 전달
						
						break;
					}
					else if(strike==3) {
						System.out.println("서버패배");
						sendAll("win");			// 서버 패배 시 클라이언트가 승리했다는 문자 전달
						
						System.out.println(ID);
						
						DBRank db = new DBRank(ID);  	// 클라이언트가 승리한 경우 사용자에게 점수 부여
						db.UpdateCountTier(ID);
						
						System.out.println("해당아이디 업데이트 완료");
						break;
					}
					else {
						sendAll("");
					}
				}
	}
	
		private void sendAll (String outmsg) {
			for(PrintWriter out : list) {
				out.println(outmsg);
				out.flush();
				
			}
		}
}

