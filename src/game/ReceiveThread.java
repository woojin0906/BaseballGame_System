package game;

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
	Socket chat;
	Random r = new Random();
	String ID;
	int countwin;
	int tier;
	
	public ReceiveThread (Socket socket, Socket chat, String ID) {
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
		
			for(int i=0; i<randomNum.length; i++) {//i부터 시작해서 ran의 길이까지 i의값을 증가시켰다.
				randomNum[i]=r.nextInt(9)+1;//ran배열의 i번째에  랜덤 수를 넣었다.
				for(int j=0; j<i; j++) {//여기서부터는 중복검사 소스이다.
					if(randomNum[i]==randomNum[j]) {//중복검사소스이다.
						i--;//같을경우 다시 i를 입력하게끔 할려고 돌아갔다.
						break;//위에 for문으로 가야되니 다시 돌아갔다.
					}
				}
			}
			
			serverNum=randomNum[0]+" "+randomNum[1]+" "+randomNum[2];//이걸 해준 이유는 그냥 표시하기 위해서이다. 아래에다 그냥 변수안만들고 해도 상관은 없다.
			System.out.println("서버 숫자 ->" + serverNum);
			
			/*JTextArea ta = chat.getTa();
			ta.append("서버 숫자 ->" + serverNum + "\n\n");*/
			
			sendAll("야구 게임이 시작됩니다.");

				int count=0;//이걸 준 이유는 총 10번 왔다갔다할경우 서버가 이기게끔 하기 위해서이다.
				while (true) {//반복시킨 이유는 반복할려고
					int strike=0;
					int ball=0;
					
					sendAll("----------------------------");
					sendAll("세 수를 입력하세요(ex: 1 2 3)");
					
					String inputMsg;
					try {
						inputMsg = in.readLine();
							/*ta.append("클라이언트가 입력한 수 -> " + inputMsg + "\n");
							ta.append("-----------------------------> ");*/
							System.out.print("클라이언트가 입력한 수 -> " + inputMsg + "\n");
							System.out.println("-----------------------------> ");
						int[] msg = new int[3]; 
						
						if(inputMsg.length()==3) {
	                        // 입력받은 문자열을 정수로 변환하여 배열에 담는다.
	                        msg[0] = inputMsg.charAt(0)-'0';
	                        msg[1] = inputMsg.charAt(1)-'0';
	                        msg[2] = inputMsg.charAt(2)-'0';
	                  } else {
	                	 	sendAll("3자리를 입력해주세요.(공백제외)");
	                        continue;       // 3자리가 아니면 다시 입력을 받는다.
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
				//	ta.append(resultMsg + "\n\n");
					System.out.println(resultMsg);
					sendAll(resultMsg);

					count++;
					if(count>=9) {
						System.out.println("서버승리");
						//ta.append("서버승리" + "\n");
						sendAll("lose");
						
						break;
					}
					else if(strike==3) {
						System.out.println("서버패배");
					//	ta.append("서버패배" + "\n");
						sendAll("win");
						
						System.out.println(ID);
						DBRank db = new DBRank(ID);
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

