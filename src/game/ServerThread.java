package game;
// 서버 인원 수 제한
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

class ServerThread extends Thread {
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	TcpServer tcpServer;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threadList, TcpServer tcpServer) {
		this.socket = socket;
		this.threadList=threadList;
		this.tcpServer=tcpServer;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String inmsg = null;
			
			while(true) {
					System.out.println(threadList.size());
					if(threadList.size() == 2) {
						inmsg = "gameStart";
						sendToAllClients(inmsg);
						ReceiveThread receiveThread = new ReceiveThread(socket, tcpServer);
						
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}          

	private void sendToAllClients(String outmsg) {
		for (ServerThread st : threadList) {
			try {
				st.out.write(outmsg+"\n");
				st.out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}