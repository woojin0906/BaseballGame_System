package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ClientUi extends JFrame {
	
	private Socket socket;
	String ip;
	int port;
	private JButton btn;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	private Font btnFont;
	
	public ClientUi(String ip, int port) {
		this.ip=ip;
		this.port=port;
		
		setTitle("클라이언트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 550);
		setLayout(new BorderLayout());
		setLocation(500, 300);
		setResizable(false); // 화면 크기 조절 불가능
		
		btnFont = new Font("Koverwatch", Font.PLAIN, 70);

		JTextField tfDAGI = new JTextField("입장 대기 중...!");
		tfDAGI.setEnabled(false);
		tfDAGI.setFont(btnFont);
		tfDAGI.setHorizontalAlignment(JTextField.CENTER);
		add(tfDAGI, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void setSocket() {
		try {
			socket = new Socket(ip, port);
			System.out.println("서버 연결 성공!!");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(true) {
				String inMessage = in.readLine();
				System.out.println(inMessage);
				
				if(inMessage.equals("gameStart")) {
					ClientThread clientThread = new ClientThread();
				}
			}
		} catch (Exception e) {
			System.out.println("서버 생성이 되지 않았습니다."); // 옵션팬으로 바꿔서 대화창 띄우기
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				socket.close();	
				
			} catch (IOException e) {		
				e.printStackTrace();
			}
		}
	}
}
