package test;
//2022-10-07 전우진 로그인 디자인 구현
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login2 extends JFrame implements ActionListener, MouseListener, KeyListener{
	
	private Font font, btnFont, loginFont;
	private JButton btnJoin, btnSearch, btnLogin, btnCall;
	private JTextField tfId;
	private JPasswordField pfPw;
	private dbInfo lgDB;
	private String result, ID;
	private Color blue, skyBlue;

	public static void main(String[] args) {
		
		Login2 login = new Login2();
	}


	public Login2() {
		setTitle("로그인 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 550);
		setLayout(new BorderLayout());
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 243);
		
		loginFont = new Font("Koverwatch", Font.BOLD, 70);
		btnFont = new Font("Koverwatch", Font.PLAIN, 16);
		font = new Font("Koverwatch", Font.PLAIN, 14);
		PanelLeft();
		
		
		setVisible(true);
	}
	
	private void PanelLeft() {
		JPanel panelLeft= new JPanel();
		panelLeft.setLayout(null);
		panelLeft.setPreferredSize(new Dimension(780, 550));
		
		// 로그인 화면 라벨(로그인) 출력
		JLabel lbl = new JLabel("로그인");
		lbl.setFont(loginFont);
		lbl.setForeground(Color.WHITE);
		lbl.setBounds(310, 80, 350, 150);
		panelLeft.add(lbl);
		
		
		// 로그인 화면 텍스트 필드(아이디) 출력
		tfId = new JTextField("아이디", 20);
		tfId.setFont(btnFont);
		tfId.setBounds(300, 215, 180, 22);
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setBackground(Color.WHITE);
		tfId.addActionListener(this);
		tfId.addMouseListener(this);
		tfId.addKeyListener(this);
		panelLeft.add(tfId);
		
		
		// 로그인 화면 텍스트 필드(비밀번호) 출력
		pfPw = new JPasswordField("비밀번호", 20);
		pfPw.setFont(btnFont);
		pfPw.setBounds(300, 265, 180, 22);
		pfPw.setBorder(BorderFactory.createEmptyBorder());
		pfPw.setBackground(Color.WHITE);
		pfPw.addActionListener(this);
		pfPw.addMouseListener(this);
		pfPw.addKeyListener(this);

		panelLeft.add(pfPw);
		
		// 로그인 화면 텍스트 필드 배경이미지 출력
		ImageIcon imgId = new ImageIcon("images/background_loginid.png");
		JLabel lblId = new JLabel(imgId);
		lblId.setBounds(290, 210, 200, 30);
		panelLeft.add(lblId);
				
		// 로그인 화면 텍스트 필드 배경이미지 출력
		JLabel lblPw = new JLabel(imgId);
		lblPw.setBounds(290, 260, 200, 30);
		panelLeft.add(lblPw);
		
		Color gold = new Color(223, 202, 89);
		
		// 로그인 화면 로그인 버튼 출력
		btnLogin = new JButton("로그인");
		btnLogin.setBounds(340, 330, 80, 35);
		btnLogin.setFont(btnFont);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(blue);
		btnLogin.addActionListener(this);
		panelLeft.add(btnLogin);
		
		
		// 로그인 화면 직원 호출 출력
		btnCall = new JButton("임시 서버 생성 버튼");// 원래는 고객센터였음 -> 서버 버튼 아예 없앨 수도 
		btnCall.setBounds(20, 360, 150, 30);
		btnCall.setFont(font);
		btnCall.setBackground(Color.WHITE);
		btnCall.addActionListener(this);
		panelLeft.add(btnCall);
		
		// 로그인 화면 회원 가입 출력
		btnJoin = new JButton("회원 가입");
		btnJoin.setBounds(20, 400, 80, 30);
		btnJoin.setForeground(Color.BLACK);
		btnJoin.setFont(font);
		btnJoin.setBackground(Color.WHITE);
		btnJoin.addActionListener(this);
		panelLeft.add(btnJoin);
		
		// 로그인 화면 비밀번호 찾기 출력
		btnSearch = new JButton("비밀번호 찾기");
		btnSearch.setBounds(20, 440, 110, 30);
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setFont(font);
		btnSearch.setBackground(Color.WHITE);
		btnSearch.addActionListener(this);
		panelLeft.add(btnSearch);

		
		// 로그인 화면 메인 이미지 출력
		ImageIcon loginImg = new ImageIcon("images/loginBackground.jpg");
		JLabel lblLogin = new JLabel(loginImg);
		lblLogin.setBounds(0, 0, 780, 550);
		panelLeft.add(lblLogin);
		
		
		add(panelLeft, BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// 2022.10.22 전우진 회원가입 연결
		if(obj == btnJoin) {
			SignUp sign = new SignUp("회원가입");
			sign.setLocationRelativeTo(this);
			this.dispose();
		}
		// 2022.10.22 전우진 비밀번호 재설정 연결
		else if(obj == btnSearch) {
			PwCheck pw = new PwCheck("비밀번호 재설정");
			pw.setLocationRelativeTo(this);
			this.dispose();
		}
		// 2022.10.26 전우진 로그인 db 연결
		else if(obj == btnLogin || obj == tfId || obj == pfPw) {
			try {
				char[] tempPw = pfPw.getPassword();
				result = "";
				
				for(char ch	: tempPw) {
					Character.toString(ch);
					result += ""+ch+"";
				}
				
				lgDB = new dbInfo();
				lgDB.loginSelect(this, tfId.getText(), result, null);

			} catch (Exception e2) {
				if(tfId.getText().equals("") || result.equals(""))
					JOptionPane.showMessageDialog(this, "아이디/비밀번호를 입력해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				else {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(this, "아이디/비밀번호를 확인해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(obj == btnCall) {
		//	JOptionPane.showMessageDialog(this, "고객센터 전화번호는 032-777-7777 입니다.", "고객센터 안내", JOptionPane.INFORMATION_MESSAGE);
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						Chat c = new Chat("서버", ID);
						c.start();
					}
				});
				thread.start();
				
				this.dispose();
		
		}
	} 


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		// 2022.10.23 전우진 tfId, pfPw 클릭 시 공백 표시
		if(obj == tfId) {
			tfId.setText("");
		}
		else if(obj == pfPw) {
			pfPw.setText("");
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
		// 2022-10-26 전우진 비밀번호 필드 탭 연결 및 초기화
		if(e.getKeyCode() == KeyEvent.VK_TAB) {
			pfPw.requestFocus();
			pfPw.setText("");
		}	
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
