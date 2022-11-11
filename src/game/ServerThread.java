package game;

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
	int count = 0;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threadList) {
		this.socket = socket;
		this.threadList=threadList;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String inmsg = null;
			
			while(true) {

					if(threadList.size() == 2) {
						inmsg = "gameStart";
						sendToAllClients(inmsg);
						System.out.println(inmsg);
						break;
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