package test;

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

public class Chat extends JFrame implements ActionListener, MouseListener, KeyListener  {
	
	public static void main(String[] args) {
		Chat c = new Chat("서버");
		c.start();
	}
	
	private String ID, name;
	private Font font, btnFont;
	private JTextField tfChat;
	private JButton btnChat;
	private String id, comment;
	private JTextArea ta;
	private Chat chat;
	private BufferedWriter out;
	private Socket socket;
	private BufferedReader in;
	private ReceiveThread receiveThread;
	
	public Chat(String title) {

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

	public JTextArea getTa() {
		return ta;
	}
	
	public void start() {
		BufferedReader in = null;
		BufferedWriter out = null;
		ServerSocket listener = null;
		Socket socket = null;
		
		try {
				
			listener = new ServerSocket(9999);
			ta.append("연결을 기다리는 중..\n");
			System.out.println("연결을 기다리는 중..\n");
			
			while(true) {
				socket = listener.accept();
				
				System.out.println("연결 성공!\n");
				ta.append("연결 성공!\n");
				
				receiveThread = new ReceiveThread(socket, this);
				receiveThread.start();
				}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				//scanner.close();
				socket.close();
				//listener.close();
		} catch (IOException e) {
			System.out.println("클라이언트와 채팅 중 오류 발생\n");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == btnChat) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 2022-10-27 전우진 채팅 초기화
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// 2022-10-27 전우진 엔터키 누르면 채팅 전송
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//			DBChat dbchat = new DBChat(this);
//			dbchat.chating("Hi", 0, tfChat.getText());
			System.out.println(tfChat.getText());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

