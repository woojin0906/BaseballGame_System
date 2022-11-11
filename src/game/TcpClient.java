package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.Socket;

import java.util.Scanner;



public class TcpClient extends JFrame implements ActionListener {

	Scanner sc = new Scanner(System.in);
	private BufferedReader in = null;
	private BufferedWriter out = null;
	private JTextField tfChat;
	private JButton btnChat;
	private JTextArea ta;
	
	public TcpClient() {
		setTitle("야구게임 클라이언트 테스트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 550);
		setLayout(new BorderLayout());
		setLocation(500, 300);
		setResizable(false); // 화면 크기 조절 불가능
		
		setCenter();
		setSouth();
		
		setVisible(true);
	}
	
	private void setCenter() {
		// 채팅창 채팅 표시 패널
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		
		// 채팅창 텍스트아레아 출력 + 스크롤팬
		ta = new JTextArea(7, 20);
		ta.setLineWrap(true);
		ta.setEditable(false);
		ta.setBackground(Color.GRAY);
		JScrollPane sp = new JScrollPane(ta, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelCenter.add(sp);
		
		add(panelCenter, BorderLayout.CENTER);
		
	}

	private void setSouth() {
		// 채팅창 하단 패널
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(null);
		panelSouth.setPreferredSize(new Dimension(400, 50));
		panelSouth.setBackground(Color.GRAY);
		
		// 채팅창 입력창 텍스트 필드 출력
		tfChat = new JTextField("텍스트를 입력하세요.");
		tfChat.setBounds(10, 7, 235, 35);
		//tfChat.setFont(btnFont);
	//	tfChat.setBorder(BorderFactory.createEmptyBorder());
		tfChat.setBackground(Color.LIGHT_GRAY);
		tfChat.addActionListener(this);
		panelSouth.add(tfChat);
		
		// 채팅창 전송 버튼 출력
		btnChat = new JButton("전송");
		//btnChat.setFont(btnFont);
		btnChat.setForeground(Color.WHITE);
		btnChat.setBackground(Color.DARK_GRAY);
		btnChat.setBounds(260, 10, 60, 30);
		btnChat.addActionListener(this);
		panelSouth.add(btnChat);
		
		add(panelSouth, BorderLayout.SOUTH);
	}
	
	public void start() {

		try {

			System.out.print("서버 IP입력 : ");
			// String serverIP = sc.next();

			String serverIp = "localhost";

			System.out.println("서버에 연결중입니다....." + serverIp);
			ta.append("서버에 연결중입니다....." + serverIp + "\n");
			// 소켓생성

			Socket so = new Socket(serverIp, 7777);

			System.out.println("클라이언트 소켓을 생성 했습니다.");
			ta.append("클라이언트 소켓을 생성 했습니다." + "\n");
			// 서버에서 메세지 받아야 한다.

			InputStream in = so.getInputStream();

			DataInputStream dis = new DataInputStream(in);

			OutputStream out = so.getOutputStream();// 만들어진 소켓의 아웃풋스트림넣겠다.

			DataOutputStream dos = new DataOutputStream(out);

			System.out.println(dis.readUTF());
			ta.append(dis.readUTF() + "\n");
			//System.out.println("종규받은것 " + dis.readUTF());


			while (true) {

				//ystem.out.println(dis.readUTF());
				ta.append(dis.readUTF() + "\n");

				//System.out.print("내가 입력한거 : ");

				//System.out.print("-->");
				ta.append("-->" + "\n");
				
				String text = sc.nextLine();

				dos.writeUTF(text);//어떤건지 보내는것


				//System.out.println(dis.readUTF());
				ta.append(dis.readUTF() + "\n");
				
				String sel =dis.readUTF();//스트라이크 볼 받는것


				if(sel.equals("win")) {
					//System.out.println("클라이언트가 졌다.");
					ta.append("클라이언트가 졌다." + "\n");
					break;

				}

				if(sel.equals("lose")) {

					//System.out.println("클라이언트가 이겼다.");
					ta.append("클라이언트가 이겼다." + "\n");
					break;
				}
			}

			//System.out.println("연결종료합니다.");
			ta.append("연결종료합니다." + "\n");
			dis.close();
			dos.close();
			so.close();


		} catch (Exception e) {

		}

	}
	public static void main(String[] args) {
		TcpClient tc = new TcpClient();
		tc.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnChat || obj == tfChat) {			
			try {
				String outMsg = tfChat.getText();
				out.write(outMsg + "\n");
				out.flush();
				
//				ta.append("[클라이언트] " + outMsg + "\n");
				tfChat.setText("");
				tfChat.requestFocus();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
