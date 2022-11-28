package game;
// 전우진 클라이언트 게임 화면
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Game extends JFrame implements ActionListener, MouseListener, KeyListener, WindowListener {

	private String ID, name;
	private Font font, btnFont, IDFont;
	private JTextField tfChat;
	private JButton btnChat;
	private String id, comment;
	private JTextArea ta;
	private Socket chat;
	private PrintStream out;
	private Socket socket;
	private BufferedReader in;
	private Color blue, skyBlue, sky;
	
	public Game(String title, String ID) {
		this.ID = ID;
		setTitle(title);
		setSize(350, 500);
		setLayout(new BorderLayout());
		setLocation(500, 150);
		setResizable(false); // 화면 크기 조절 불가능
		addWindowListener(this);
		
		IDFont = new Font("넥슨 풋볼고딕 B", Font.PLAIN, 16);		
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
		// 게임(클라이언트) 화면 채팅 표시 패널
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		
		// 게임(클라이언트) 화면 텍스트아레아 출력 + 스크롤팬
		ta = new JTextArea(7, 20);
		ta.setLineWrap(true);
		ta.setEditable(false);
		ta.setFont(new Font("Koverwatch", Font.PLAIN, 18));
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
		tfChat.setFont(IDFont);
		tfChat.setBackground(sky);
		tfChat.addActionListener(this);
		tfChat.addMouseListener(this);
		tfChat.addKeyListener(this);
		panelSouth.add(tfChat);
		
		// 게임(클라이언트) 화면 전송 버튼 출력
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

				if(obj == tfChat || obj == btnChat) {
					String outMsg = tfChat.getText();  						// 클라이언트가 입력한 문자열 담는 변수
						ClientThread clientThread = new ClientThread(socket, outMsg); 		// 출력 스트림
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
	
	public void setSocket() {
		
		try {			
			
			socket = new Socket("127.0.0.1", 9999);	  // 소켓 연결
			
			ta.append("서버 연결 완료!\n");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
			
			while(true) {			
				String inMessage = in.readLine(); // 서버에서 받아온 메시지 저장하는 변수
				
				ta.append(inMessage + "\n");
				
				if(inMessage.equals("lose")) {
					ta.append("클라이언트 패배! \n");
					ta.append("연결 종료 \n");
					JOptionPane.showMessageDialog(this, "패배ㅠㅠㅠ", "결과", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					Server server = new Server("서버화면", ID, name);
					break;
					
				}
				
				if(inMessage.equals("win")) {
					ta.append("클라이언트 승리! \n" );
					ta.append("연결 종료 \n");
					JOptionPane.showMessageDialog(this, "승리!!!", "결과", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					Server server = new Server("서버화면", ID, name);
					break;
				}
				if(inMessage.equals("exit")) {
					this.dispose();
					Server server = new Server("서버화면", ID, name);
					break;
				}
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
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);			
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
