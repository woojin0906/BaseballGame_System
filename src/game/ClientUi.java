package game;
// 클라이언트 2번
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientUi extends JFrame {
	
	private Socket socket;
	private String ip;
	private int port;
	private JButton btn;
	private BufferedReader in = null;
	private PrintStream out = null;
	private Font btnFont;
	private Color blue;
	private TcpClient tcpClient;
	private JTextArea ta;
	public String inMessage;
	
	public ClientUi(Socket socket) {
		this.socket=socket;
		
		setTitle("클라이언트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 550);
		setLayout(new BorderLayout());
		setLocation(500, 300);
		setResizable(false); // 화면 크기 조절 불가능
		
		btnFont = new Font("Koverwatch", Font.PLAIN, 70);

		blue = new Color(26, 67, 141);
		
		
		
		JTextField tfDAGI = new JTextField("입장 대기 중...!");
		tfDAGI.setEnabled(false);
		tfDAGI.setFont(btnFont);
		//tfDAGI.setForeground(blue); 색깔 안바뀜
		tfDAGI.setHorizontalAlignment(JTextField.CENTER);
		add(tfDAGI, BorderLayout.CENTER);
		
		setVisible(true);
		
		ClientThread clientThread = new ClientThread(socket, "1 1 1");
		clientThread.run();
		
		inThread inThread = new inThread(socket, this);
		inThread.run();
		
		
		
		while(true) {
			try {
				if(inMessage.equals("2 2 2")) {
				//	System.out.println(inMessage);
					break;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
		this.dispose();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				TcpClient tcpClient = new TcpClient("채팅", socket);
			}
		});
		thread.start();
		
		//System.out.println(inMessage);
		//System.out.println(inMessage);
	}
	public void msg(String inMessage) {
		this.inMessage=inMessage;
	}
	
}
	
	
	
//	public void setSocket() {
//		try {
//			socket = new Socket(ip, port);
//			System.out.println("서버 연결 성공!!");
//			
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintStream(socket.getOutputStream());
//			
//			while(true) {
//				String inMessage = in.readLine();
//				ta.append(inMessage);
//				System.out.println(inMessage);
//				
//				if(inMessage.equals("gameStart")) {
//					tcpClient = new TcpClient("클라이언트", ip, port);
//					
//				}
//				
//				if(inMessage.equals("win")) {
//					ta.append("클라이언트가 졌다.\n");
//					break;
//				}
//				
//				if(inMessage.equals("lose")) {
//					ta.append("클라이언트가 이겼다.\n");
//					break;
//				}
//			}
//			ta.append("연결을 종료합니다.\n");
//		} catch (Exception e) {
//			System.out.println("서버 생성이 되지 않았습니다."); // 옵션팬으로 바꿔서 대화창 띄우기
//			e.printStackTrace();
//		} finally {
//			try {
//				out.close();
//				in.close();
//				socket.close();	
//				
//			} catch (IOException e) {		
//				e.printStackTrace();
//			}
//		}
//	}
	
//public void setSocket() {
//		
//		try {			
//			
//			socket = new Socket(ip, port);	
//			
//			ta.append("서버 연결 완료!\n");
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintStream(socket.getOutputStream());
//			
//			while(true) {			
//				String inMessage = in.readLine();
//				
//				ta.append(inMessage + "\n");
//				
//				
//				if(inMessage.equals("win")) {
//					ta.append("클라이언트가 졌다.\n");
//					break;
//				}
//				
//				if(inMessage.equals("lose")) {
//					ta.append("클라이언트가 이겼다.\n" );
//					break;
//				}
//			}
//			ta.append("연결을 종료합니다.\n");
//		} catch (IOException e) {
//			System.out.println("서버 생성이 되지 않았습니다");
//			//e.printStackTrace();
//		} finally {
//			try {
//				out.close();
//				in.close();
//				socket.close();	
//				
//			} catch (IOException e) {		
//				e.printStackTrace();
//			}
//		}
//	}

