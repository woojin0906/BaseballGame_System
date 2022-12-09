package game;
/* 
 디자인, 설계 : 전우진, 허유진
 클래스 : 서버 소켓 + Thread 연결
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat {

	private String ID, name;
	private String id, comment;
	private Chat chat;
	private BufferedWriter out;
	private Socket socket;
	private BufferedReader in;
	private ReceiveThread receiveThread;
	
	public Chat(String ID) {
		this.ID =ID;
	}
	
	public void start() {
		BufferedReader in = null;
		BufferedWriter out = null;
		ServerSocket listener = null;
		Socket socket = null;
		
		try {
				
			listener = new ServerSocket(9999);
			System.out.println("연결을 기다리는 중..\n");
			
			while(true) {
				socket = listener.accept();  // 소켓 연결
				System.out.println("연결 성공!\n");
				
				receiveThread = new ReceiveThread(socket, this, ID);
				receiveThread.start();
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				socket.close();
		} catch (IOException e) {
			System.out.println("클라이언트와 채팅 중 오류 발생\n");
			}
		}
	}


}

