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
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Game extends JFrame implements ActionListener, MouseListener, KeyListener {
	
	public static void main(String[] args) {
		Game g = new Game("클라이언트");
		g.setSocket();
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
	//private ReceiveThread receiveThread;
	public Game(String title) {

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


	@Override
	public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			// 2022-10-23 전우진 게임 화면 채팅창 화면 연결

				// 2022-10-26 허유진 채팅 db 연결
				if(obj == tfChat || obj == btnChat) {
//					DBChat dbchat = new DBChat(this);
//					dbchat.chating("Hi", 0, tfChat.getText());

					try {
						String outMsg = tfChat.getText();
						out.write(outMsg + "\n");
						out.flush();
						ta.append(outMsg);
						tfChat.setText("");
						tfChat.requestFocus();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

			} 
			
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
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
	
	public void setSocket() {
			
		
		try {			
			
			socket = new Socket("127.0.0.1", 9999);	
			
			ta.append("서버 연결 완료!\n");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String inMessage = in.readLine();
			ta.append(inMessage + "\n");
			
			while(true) {			
				//ta.append( inMessage + "\n");
				ta.append("-->\n");
				
				//String text = tfChat.getText();
				// 서버로 보냄 text를
				
				//ta.append(inMessage + "\n");
				
				String sel = in.readLine();
				
				if(sel.equals("win")) {
					ta.append("클라이언트가 졌다.\n");
					break;
				}
				
				if(sel.equals("lose")) {
					ta.append("클라이언트가 이겼다.\n" );
					break;
				}
			}
			ta.append("연결을 종료합니다.\n");
			
		} catch (IOException e) {
			System.out.println("서버 생성이 되지 않았습니다");
			//e.printStackTrace();
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

	public BufferedWriter getOut() {
		return out;
	}
	
}
