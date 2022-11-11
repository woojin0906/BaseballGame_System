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

	
	public void start() {
		BufferedReader in = null;
		BufferedWriter out = null;
		ServerSocket listener = null;
		Socket socket = null;
		
		try {
				
			listener = new ServerSocket(9999);
		
			while(true) {
				System.out.println("연결을 기다리는 중..\n");
				ta.append("연결을 기다리는 중..\n");
				
				socket = listener.accept();
				System.out.println("연결 성공!\n");
				ta.append("연결 성공!\n");
				
				receiveThread = new ReceiveThread(socket, name);
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
	
	class ReceiveThread extends Thread {
		static List<BufferedWriter> list = Collections.synchronizedList(new ArrayList<BufferedWriter>());
		
		Socket socket = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		Random r = new Random();
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
			int[] ran = new int [3];
			String numb = null;
			
				for(int i=0; i<ran.length; i++) {//i부터 시작해서 ran의 길이까지 i의값을 증가시켰다.
					ran[i]=r.nextInt(10)+1;//ran배열의 i번째에  랜덤 수를 넣었다.
					for(int j=0; j<i; j++) {//여기서부터는 중복검사 소스이다.
						if(ran[i]==ran[j]) {//중복검사소스이다.
							i--;//같을경우 다시 i를 입력하게끔 할려고 돌아갔다.
							break;//위에 for문으로 가야되니 다시 돌아갔다.
						}
					}
				}
				
				numb=ran[0]+" "+ran[1]+" "+ran[2];//이걸 해준 이유는 그냥 표시하기 위해서이다. 아래에다 그냥 변수안만들고 해도 상관은 없다.
				System.out.println("서버 숫자 ->" + numb);
				ta.append("서버 숫자 ->" + numb + "\n");
				
			try {
					
					sendAll("야구 게임을 시작합니다.");

					int oout=0;//이걸 준 이유는 총 10번 왔다갔다할경우 서버가 이기게끔 하기 위해서이다.
					while (true) {//반복시킨 이유는 반복할려고
						int strike=0;
						int ball=0;
						
						sendAll("세 수를 입력하세요(ex: 1 2 3)");
						
						String inputMsg = in.readLine();
						ta.append("클라이언트가 입력한 수 -> " + inputMsg + "\n");
					
						StringTokenizer st = new StringTokenizer(inputMsg, " ");
						
						int [] nnum=new int [3];
						for(int i=0; i<3;i++) {
							nnum[i]=Integer.parseInt(st.nextToken());//입력받은것
						}

						for(int i=0; i<3; i++) {
							for(int j=0; j<3; j++) {
								if(ran[i]==nnum[j]) {
									if(i==j) {
										strike++;
									}
									else {
										ball++;
									}
								}
							}
						}
						
						String str = strike + " 스트라이크 " + ball + " 볼 (" + oout + " )번째"; 
						ta.append(str + "\n");
						sendAll(str);
						
						oout++;
						if(oout>=10) {
							sendAll("win");
							ta.append("서버승리" + "\n");
							//System.out.println("서버승리");
							break;
						}
						else if(strike==3) {
							//System.out.println("서버패배");
							ta.append("서버패배" + "\n");
							sendAll("lose");
							break;
						}
						else {
							sendAll("");
						}
						
					}			
			} catch (IOException e) {
				e.printStackTrace();
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

