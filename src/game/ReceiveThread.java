package game;
// 서버 게임 시작
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

public class ReceiveThread extends Thread {
	static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());
	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	TcpServer tcpServer;
	static int UserNum = 0;
	Random r = new Random();
	private JTextArea ta;
	
	public ReceiveThread (Socket socket, TcpServer tcpServer) {
		this.socket = socket;
		this.tcpServer = tcpServer;
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

		while(in != null) {
					String inputMsg;
					try {
						inputMsg = in.readLine();
						System.out.println(inputMsg);
						
						//System.out.println(UserNum);
						///if(count <= 1) {
							if(inputMsg.equals("1 1 1")) {
								UserNum++;
								//System.out.println(UserNum);
							}
							if(UserNum == 2) {
								sendAll("2 2 2");
								//System.out.println(UserNum);
								UserNum = 0;
								
								int[] ran = new int [3];
								String numb = null;
								
									for(int i=0; i<ran.length; i++) {//i부터 시작해서 ran의 길이까지 i의값을 증가시켰다.
										ran[i]=r.nextInt(10)+1;//ran배열의 i번째에  랜덤 수를 넣었다.
										for(int j=0; j<i; j++) {//여기서부터는 중복검사 소스이다.
											if(ran[i]==ran[j]) {//중복검사소스이다.
												i--;//같을경우 다시 i를 입력하게끔 할려고 돌아갔다.
												break;//위에 for문으로 가야되니 다시 돌아갔다.
											}
										}
									}
									
									numb=ran[0]+" "+ran[1]+" "+ran[2];//이걸 해준 이유는 그냥 표시하기 위해서이다. 아래에다 그냥 변수안만들고 해도 상관은 없다.
									
									int oout=0;//이걸 준 이유는 총 10번 왔다갔다할경우 서버가 이기게끔 하기 위해서이다.
									
										System.out.println("서버 숫자 ->" + numb);
										ta = tcpServer.getTa();
										ta.append("서버 숫자 ->" + numb + "\n");
										sendAll("야구 게임이 시작됩니다.");
									

										while (true) {//반복시킨 이유는 반복할려고
											int strike=0;
											int ball=0;

											sendAll("----------------------------");
												sendAll("세 수를 입력하세요(ex: 1 2 3)");
													
													//ta.append("클라이언트가 입력한 수 -> " + inputMsg + "\n");
													
													StringTokenizer st = new StringTokenizer(inputMsg, " ");
													
													int [] nnum=new int [3];
													for(int i=0; i<3;i++) {
														nnum[i]=Integer.parseInt(st.nextToken());//입력받은것
													}
													for(int i=0; i<3; i++) {
														for(int j=0; j<3; j++) {
															if(ran[i]==nnum[j]) {
																if(i==j) {
																	strike++;
																}
																else {
																	ball++;
																}
															}
														}
													}
													String str = strike + " 스트라이크 " + ball + " 볼 (" + oout + " )번째"; 
													//ta.append(str + "\n");
													sendAll(str);
											}
											
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					/*oout++; 
					
					if(oout>=10) {
						sendAll("win");
						ta.append("서버승리" + "\n");
						System.out.println("서버승리");
						break;
					}
					else if(strike==3) {
						System.out.println("서버패배");
						ta.append("서버패배" + "\n");
						sendAll("lose");
						break;
					}
					else {
						sendAll("");
					}*/
				}
	}
	
		private void sendAll (String outmsg) {
			for(PrintWriter out : list) {
				out.println(outmsg);
				//System.out.println(outmsg);
				out.flush();
			}
		}
}

