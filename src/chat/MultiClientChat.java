package chat;

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Container;
	import java.awt.Dimension;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.awt.Panel;
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
	import java.io.PrintStream;
	import java.net.Socket;
	import java.util.Scanner;

	import javax.swing.BorderFactory;
	import javax.swing.ButtonGroup;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JRadioButton;
	import javax.swing.JScrollPane;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;

	public class MultiClientChat extends JFrame implements ActionListener, MouseListener, KeyListener {
		
		private Font font, btnFont;
		private JTextField tfChat;
		private JButton btnChat;
		private String id, comment;
		private JTextArea ta;
		private MultiChat multiChat;
		private BufferedWriter out;
		private Socket socket;
		private BufferedReader in;
		private Color blue, skyBlue, sky;
		
		public static void main(String[] args) {
			MultiClientChat chat = new MultiClientChat("ChatClient");			
			chat.setSocket();
		}

		public MultiClientChat(String title) { //, Chat chat
			//this.chat = chat;
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(350, 500);
			setLayout(new BorderLayout());
			setLocation(500, 150);
			setResizable(false); // 화면 크기 조절 불가능
			
			btnFont = new Font("Koverwatch", Font.PLAIN, 16);
			font = new Font("Koverwatch", Font.PLAIN, 14);
			
			blue = new Color(26, 67, 141);
			skyBlue= new Color(218, 227, 238);
			sky= new Color(246, 246, 246);
			
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
			panelSouth.setBackground(skyBlue);
			
			// 채팅창 입력창 텍스트 필드 출력
			tfChat = new JTextField("텍스트를 입력하세요.");
			tfChat.setBounds(10, 7, 235, 35);
			tfChat.setFont(btnFont);
			tfChat.setBackground(Color.WHITE);
			tfChat.addActionListener(this);
			tfChat.addMouseListener(this);
			tfChat.addKeyListener(this);
			panelSouth.add(tfChat);
			
			// 채팅창 전송 버튼 출력
			btnChat = new JButton("전송");
			btnChat.setFont(btnFont);
			btnChat.setForeground(Color.WHITE);
			btnChat.setBackground(blue);
			btnChat.setBounds(260, 10, 60, 30);
			btnChat.addActionListener(this);
			panelSouth.add(btnChat);
			
			add(panelSouth, BorderLayout.SOUTH);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			// 2022-10-26 허유진 채팅 db 연결
			if(obj == tfChat || obj == btnChat) {
			
				try {
					String outMsg = tfChat.getText();
					out.write(outMsg + "\n");
					out.flush();

					tfChat.setText("");
					tfChat.requestFocus();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
				System.out.println(tfChat.getText());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public JTextArea getTa() {
			return ta;
		}
		
		public void setSocket() {
			try {			
				socket = new Socket("10.200.42.36", 9999);	
				ta.append("서버 연결 완료!\n");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				while(true) {			
					String inMessage = in.readLine();
					if(inMessage.equalsIgnoreCase("quit")) {
						ta.append("[클라이언트] : " + inMessage + "\n");	
						this.dispose();
						break;
					}
					ta.append( inMessage + "\n");	
				}
				
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

		public JTextField getTfChat() {
			return tfChat;
		}
}
