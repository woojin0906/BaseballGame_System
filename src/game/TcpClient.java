package game;
// 클라이언트 채팅창
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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import test.Chat;
import test.ClientThread;
import test.Game;

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.Socket;

import java.util.Scanner;

public class TcpClient extends JFrame implements ActionListener, MouseListener, KeyListener {

//	public static void main(String[] args) {
//		TcpClient tcpClient = new TcpClient("클라이언트");
//		tcpClient.setSocket();
//	}
//	
	private String ID, name;
	private Font font, btnFont;
	private JTextField tfChat;
	private JButton btnChat;
	private String id, comment;
	private JTextArea ta;
	private Chat chat;
	private PrintStream out;
	private Socket socket;
	private BufferedReader in;
	String ip;
	int port;
	
	public TcpClient(String title, Socket socket) {
		this.socket=socket;
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 500);
		setLayout(new BorderLayout());
		setLocation(500, 150);
		setResizable(false); // 화면 크기 조절 불가능
		
		btnFont = new Font("Koverwatch", Font.PLAIN, 16);
		font = new Font("Koverwatch", Font.PLAIN, 14);
		
		setCenter();
		setSouth();
		
		inThread in = new inThread(socket, this);
		in.run();
		
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
		ta.setFont(btnFont);
		ta.setBackground(Color.WHITE);
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
		tfChat.setFont(btnFont);
	//	tfChat.setBorder(BorderFactory.createEmptyBorder());
		tfChat.setBackground(Color.LIGHT_GRAY);
		tfChat.addActionListener(this);
		tfChat.addMouseListener(this);
		tfChat.addKeyListener(this);
		panelSouth.add(tfChat);
		
		// 채팅창 전송 버튼 출력
		btnChat = new JButton("전송");
		btnChat.setFont(btnFont);
		btnChat.setForeground(Color.WHITE);
		btnChat.setBackground(Color.DARK_GRAY);
		btnChat.setBounds(260, 10, 60, 30);
		btnChat.addActionListener(this);
		panelSouth.add(btnChat);
		
		add(panelSouth, BorderLayout.SOUTH);
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
//	public void setSocket() {
//		try {
//			socket = new Socket(ip, port);
//			System.out.println("서버 연결 성공!!");
//			
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			
//			while(true) {
//				String inMessage = in.readLine();
//				System.out.println(inMessage);
//				
//				if(inMessage.equals("gameStart")) {
//					tcpClient = new TcpClient("클라이언트", ip, port);
//					break;
//				}
//				
//				if(inMessage.equals("win")) {
//					ta = tcpClient.getTa();
//					ta.append("클라이언트가 졌다.\n");
//					break;
//				}
//				
//				if(inMessage.equals("lose")) {
//					JTextArea ta = tcpClient.getTa();
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
//	public void setSocket() {
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

	public JTextArea getTa() {
		return ta;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

				if(obj == tfChat || obj == btnChat) {
					String outMsg = tfChat.getText();
					
					ClientThread clientThread = new ClientThread(socket, outMsg);
					clientThread.start();
					ta.append("-->" + outMsg + "\n");
					tfChat.setText("");
					tfChat.requestFocus();
			} 
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println(tfChat.getText());
		}		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == tfChat) {
			tfChat.setText("");
		}			
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
