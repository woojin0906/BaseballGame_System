package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class inThread extends Thread {
	
	Socket socket;
	private BufferedReader in;
	ClientUi clientUi;
	TcpClient tcpClient;
	int num;
	
	public inThread(Socket socket, ClientUi clientUi) {
		this.socket=socket;
		this.clientUi=clientUi;
		num=1;
	}
	
	public inThread(Socket socket, TcpClient tcpClient) {
		this.socket=socket;
		this.tcpClient=tcpClient;
		num=2;
	}
	@Override
	public void run() {
		try {
			//while(true) {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(num==1) {
				String inMessage = in.readLine();
				clientUi.msg(inMessage);
			}
			if(num==2) {
				
					String inMessage = in.readLine();
					JTextArea ta = tcpClient.getTa();
					ta.append(inMessage);
				
				
			}
			
			//System.out.println(inMessage);
			
//			if(inMessage.equals("win")) {
//				ta = tcpClient.getTa();
//				ta.append("클라이언트가 졌다.\n");
//				break;
//			}
//			
//			if(inMessage.equals("lose")) {
//				JTextArea ta = tcpClient.getTa();
//				ta.append("클라이언트가 이겼다.\n");
//				break;
//			}
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
