package user;
// 허유진 마이페이지
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.dbInfo;
import game.Server;

public class Mypage extends JFrame implements ActionListener, MouseListener, KeyListener, WindowListener{
	
	private Font mainFont, subFont, IDFont;
	private String ID, name;
	private dbInfo db;
	private JTextField Mypage_textfield_id, Mypage_textfield_Nickname, Mypage_textfield_Email;
	private JPasswordField Mypage_pwcheck, Mypage_pw;
	private JButton Mypage_btn, exit_btn;
	private Color blue, skyBlue;

	public Mypage(String title, String ID, String name) {
		this.ID = ID;
		this.name = name;
		setTitle(title);
		setLocation(300, 300);
		setSize(530, 430);
		setLayout(new BorderLayout());
		setResizable(false); // 화면 크기 조절 불가능
		addWindowListener(this);
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 238);
		
		IDFont = new Font("넥슨 풋볼고딕 B", Font.PLAIN, 16);
		mainFont = new Font("Koverwatch", Font.PLAIN, 20);
		subFont = new Font("Koverwatch", Font.PLAIN, 15);
		SignUpPannel();
		
		// 2022-10-31 전우진 마이페이지 db 연결
		db = new dbInfo();
		db.pullInfo(ID, Mypage_textfield_Nickname, Mypage_textfield_Email, Mypage_pw, Mypage_pwcheck);
		setVisible(true);
		
		}
	
	private void SignUpPannel() {
		JPanel Mypage_Pannel = new JPanel();
		Mypage_Pannel.setLayout(null);
		Mypage_Pannel.setPreferredSize(new Dimension(520, 430));
		Mypage_Pannel.setBackground(skyBlue);
		
		//2022-10-23 신규유저 라벨
		JLabel Mypage_title = new JLabel("자기 정보 수정하기");
		Mypage_title.setBounds(45, 25, 200, 30);
		Mypage_title.setForeground(Color.BLACK);
		Mypage_title.setFont(new Font("Koverwatch", Font.BOLD, 30));
		Mypage_Pannel.add(Mypage_title);
		
		//2022-10-23 아이디 라벨
		JLabel Mypage_ID = new JLabel("아이디");
		Mypage_ID.setForeground(Color.DARK_GRAY);
		Mypage_ID.setBounds(90, 80, 100, 30);
		Mypage_ID.setFont(mainFont);
		Mypage_Pannel.add(Mypage_ID);
		
		//2022-10-23 비밀번호 라벨
		JLabel Mypage_PW = new JLabel("비밀번호");
		Mypage_PW.setForeground(Color.DARK_GRAY);
		Mypage_PW.setBounds(90, 130, 100, 30);
		Mypage_PW.setFont(mainFont);
		Mypage_Pannel.add(Mypage_PW);
		
		//2022-10-23 비밀번호 확인 라벨
		JLabel Mypage_PW_Check = new JLabel("비밀번호 확인");
		Mypage_PW_Check.setForeground(Color.DARK_GRAY);
		Mypage_PW_Check.setBounds(90, 180, 100, 30);
		Mypage_PW_Check.setFont(mainFont);
		Mypage_Pannel.add(Mypage_PW_Check);
		
		//2022-10-23 닉네임 라벨
		JLabel Mypage_Nickname = new JLabel("닉네임");
		Mypage_Nickname.setForeground(Color.DARK_GRAY);
		Mypage_Nickname.setBounds(90, 230, 100, 30);
		Mypage_Nickname.setFont(mainFont);
		Mypage_Pannel.add(Mypage_Nickname);
		
		//2022-10-23 이메일 라벨
		JLabel Mypage_Email = new JLabel("이메일");
		Mypage_Email.setForeground(Color.DARK_GRAY);
		Mypage_Email.setBounds(90, 280, 100, 30);
		Mypage_Email.setFont(mainFont);
		Mypage_Pannel.add(Mypage_Email);
		
		//2022-10-23 취소 버튼 
		exit_btn = new JButton("취소");
		exit_btn.setBounds(450, 10, 65, 30);
		exit_btn.setFont(new Font("Koverwatch", Font.BOLD, 20));
		exit_btn.setForeground(Color.DARK_GRAY);
		exit_btn.setBackground(blue);
		exit_btn.setBorderPainted(false);
		exit_btn.setOpaque(false);
		exit_btn.addActionListener(this);
		Mypage_Pannel.add(exit_btn);
				
		//2022-10-23 등록 버튼 
		Mypage_btn = new JButton("수정하기");
		Mypage_btn.setBounds(90, 335, 320, 35);
		Mypage_btn.setFont(new Font("Koverwatch", Font.BOLD, 15));
		Mypage_btn.setBackground(blue);
		Mypage_btn.setForeground(Color.WHITE);
		Mypage_btn.addActionListener(this);
		Mypage_Pannel.add(Mypage_btn);
		
		//2022-10-24 아이디 텍스트
		Mypage_textfield_id =new JTextField(ID);
		Mypage_textfield_id.setBounds(210, 80, 200, 30);
		Mypage_textfield_id.setFont(IDFont);
		Mypage_textfield_id.setEditable(false);
		Mypage_Pannel.add(Mypage_textfield_id);
		
		//2022-10-24 패스워드 텍스트
		Mypage_pw = new JPasswordField();
		Mypage_pw.setBounds(210, 130, 200, 30);
		Mypage_pw.addActionListener(this);
		Mypage_pw.addMouseListener(this);
		Mypage_pw.addKeyListener(this);
		Mypage_pw.setFocusTraversalKeysEnabled(false);
		Mypage_Pannel.add(Mypage_pw);
		
		//2022-10-24 패스워드 확인 텍스트
		Mypage_pwcheck = new JPasswordField();
		Mypage_pwcheck.setBounds(210, 180, 200, 30);
		Mypage_pwcheck.addActionListener(this);
		Mypage_pwcheck.addMouseListener(this);
		Mypage_pwcheck.addKeyListener(this);
		Mypage_pwcheck.setFocusTraversalKeysEnabled(false);
		Mypage_Pannel.add(Mypage_pwcheck);
		
		//2022-10-24 닉네임 텍스트
		Mypage_textfield_Nickname =new JTextField();
		Mypage_textfield_Nickname.setBounds(210, 230, 200, 30);
		Mypage_textfield_Nickname.setFont(IDFont);
		Mypage_textfield_Nickname.setEditable(false);
		Mypage_Pannel.add(Mypage_textfield_Nickname);
		
		//2022-10-24 이메일 텍스트
		Mypage_textfield_Email =new JTextField();
		Mypage_textfield_Email.setBounds(210, 280, 200, 30);
		Mypage_textfield_Email.setFont(IDFont);
		Mypage_textfield_Email.setEditable(false);
		Mypage_Pannel.add(Mypage_textfield_Email);
		
		add(Mypage_Pannel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == Mypage_btn || obj == Mypage_pw || obj == Mypage_pwcheck) {
			char[] temp = Mypage_pw.getPassword();
			String resultPw = "";
			String resultPwCh = "";
			
			for(char ch	: temp) {
				Character.toString(ch);
				resultPw += ""+ch+"";
			}
			
			temp = Mypage_pwcheck.getPassword();
			
			for(char ch	: temp) {
				Character.toString(ch);
				resultPwCh += ""+ch+"";
			}

			if(resultPw.equals(resultPwCh)) {
				if(resultPw.length() <= 18 && resultPw.length() >= 8 && resultPwCh.length() <= 18 && resultPwCh.length() >= 8) {
				  db.memberInfo(ID, Mypage_pw);
				  this.dispose();
				  JOptionPane.showMessageDialog(this, "변경되었습니다.",
							"변경 안내", JOptionPane.INFORMATION_MESSAGE);
			  Server s = new Server("대기화면", ID, name);
				} else {
					JOptionPane.showMessageDialog(this, "비밀번호는 8자리 이상 18자리 이하로 작성해주세요.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다.",
						"경고", JOptionPane.WARNING_MESSAGE);
			}
		} else if(obj == exit_btn) {
			this.dispose();
			Server s = new Server("서버화면", ID, name);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == Mypage_pw) {
			Mypage_pw.setText("");
		}
		else if(obj == Mypage_pwcheck) {
			Mypage_pwcheck.setText("");
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
		if (e.getKeyCode()==KeyEvent.VK_TAB&&e.getSource() == Mypage_pw) {
			Mypage_pwcheck.requestFocus();
			Mypage_pwcheck.setText("");
		}else if (e.getKeyCode()==KeyEvent.VK_TAB&&e.getSource() == Mypage_pwcheck) {
			Mypage_pw.requestFocus();
			Mypage_pw.setText("");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
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