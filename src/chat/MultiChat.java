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
	import java.io.PrintWriter;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.List;
	import java.util.Random;
	import java.util.Scanner;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;

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

	public class MultiChat extends JFrame implements ActionListener, MouseListener, KeyListener  {
		
		private String ID, name;
		private Font font, btnFont;
		private JTextField tfChat;
		private JButton btnChat;
		private String id, comment;
		private JTextArea ta;
		private MultiChat multiChat;
		private BufferedWriter out;
		private Socket socket;
		private BufferedReader in;
		static int UserNum = 0; 
		private Color blue, skyBlue, sky;
		
		public static void main(String[] args) {
			MultiChat chat = new MultiChat(null, null, null);
			chat.start();
		}
		
		public MultiChat(String title, String ID, String name) {
			this.ID=ID;
			this.name=name;
			System.out.println(ID);
			System.out.println(name);
			
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

		public void start() {
			BufferedReader in = null;
			BufferedWriter out = null;
			ServerSocket listener = null;
			Socket socket = null;
			Scanner scanner = new Scanner(System.in);
			
			try {
		
				listener = new ServerSocket(9999);
			
				while(true) {
					System.out.println("연결을 기다리는 중..");

					socket = listener.accept();
					System.out.println("연결 성공!");
					
					ReceiveThread receiveThread = new ReceiveThread(socket, name);
					receiveThread.start();
					UserNum++;
					
				}
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					//scanner.close();
					socket.close();
					//listener.close();
			} catch (IOException e) {
				System.out.println("클라이언트와 채팅 중 오류 발생");
				}
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			// 2022-10-26 허유진 채팅 db 연결
			if(obj == btnChat) {
				
				try {
					String outMsg = tfChat.getText();
					out.write("[서버]" + outMsg + "\n");
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
		
		class ReceiveThread extends Thread {
			static List<BufferedWriter> list = Collections.synchronizedList(new ArrayList<BufferedWriter>());
			
			Socket socket = null;
			BufferedReader in = null;
			BufferedWriter out = null;

			private String name;
			
			public ReceiveThread (Socket socket, String name) {
				this.name=name;
				this.socket = socket;
				try {
					out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					list.add(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			public void run() {
			name = "";
				try {
					name = in.readLine();
				
						ta.append("[" + name + UserNum + "새연결생성]" + "\n");
						System.out.println("[" + name + UserNum + " 새연결생성]");
						
						ta.append("[" + name + UserNum + "]님이 들어오셨습니다." + "\n");
						System.out.println("[" + name + UserNum + "]님이 들어오셨습니다.");
						sendAll("[" + name + UserNum + "]님이 들어오셨습니다.");
						
						while(in != null) {
							String inputMsg = in.readLine();
							if("quit".equals(inputMsg)) break;
							System.out.println("[" + name + "] " + inputMsg);
							sendAll("[" + name + "] : "+ inputMsg);
						}
						
					
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				ta.append("[" + name + UserNum + "]님이 나가셨습니다." + "\n");
				System.out.println("[" + name + UserNum + "]님이 나가셨습니다");
				sendAll("[" + name + UserNum + "]님이 나가셨습니다");
				
				list.remove(out);
				try {
					socket.close();
					ta.append("[" + name + UserNum + " 연결종료] \n");
					System.out.println("[" + name + UserNum + " 연결종료]");
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
			}
			private void sendAll (String s) {
				for(BufferedWriter out : list) {
					try {
						out.write(s + "\n");
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		}

		
	}

