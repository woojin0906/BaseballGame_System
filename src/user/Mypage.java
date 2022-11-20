package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class Mypage extends JFrame implements ActionListener{
	
	private Font mainFont, subFont;
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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 300);
		//setSize(380, 500);
		setSize(530, 430);
		setLayout(new BorderLayout());
		setResizable(false); // 화면 크기 조절 불가능
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 238);
		
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
		Mypage_title.setForeground(Color.white);
		Mypage_title.setFont(new Font("Koverwatch", Font.BOLD, 30));
		Mypage_Pannel.add(Mypage_title);
		
		//2022-10-23 아이디 라벨
		JLabel Mypage_ID = new JLabel("아이디");
		Mypage_ID.setForeground(Color.white);
		Mypage_ID.setBounds(90, 80, 100, 30);
		Mypage_ID.setFont(mainFont);
		Mypage_Pannel.add(Mypage_ID);
		
		//2022-10-23 비밀번호 라벨
		JLabel Mypage_PW = new JLabel("비밀번호");
		Mypage_PW.setForeground(Color.white);
		Mypage_PW.setBounds(90, 130, 100, 30);
		Mypage_PW.setFont(mainFont);
		Mypage_Pannel.add(Mypage_PW);
		
		//2022-10-23 비밀번호 확인 라벨
		JLabel Mypage_PW_Check = new JLabel("비밀번호 확인");
		Mypage_PW_Check.setForeground(Color.white);
		Mypage_PW_Check.setBounds(90, 180, 100, 30);
		Mypage_PW_Check.setFont(mainFont);
		Mypage_Pannel.add(Mypage_PW_Check);
		
		//2022-10-23 닉네임 라벨
		JLabel Mypage_Nickname = new JLabel("닉네임");
		Mypage_Nickname.setForeground(Color.white);
		Mypage_Nickname.setBounds(90, 230, 100, 30);
		Mypage_Nickname.setFont(mainFont);
		Mypage_Pannel.add(Mypage_Nickname);
		
		//2022-10-23 이메일 라벨
		JLabel Mypage_Email = new JLabel("이메일");
		Mypage_Email.setForeground(Color.white);
		Mypage_Email.setBounds(90, 280, 100, 30);
		Mypage_Email.setFont(mainFont);
		Mypage_Pannel.add(Mypage_Email);
		
		//2022-10-23 취소 버튼 
		exit_btn = new JButton("취소");
		exit_btn.setBounds(450, 10, 65, 30);
		exit_btn.setFont(new Font("Koverwatch", Font.BOLD, 20));
		exit_btn.setForeground(Color.WHITE);
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
		Mypage_textfield_id.setFont(subFont);
		Mypage_textfield_id.setEditable(false);
		Mypage_Pannel.add(Mypage_textfield_id);
		
		//2022-10-24 패스워드 텍스트
		Mypage_pw = new JPasswordField();
		Mypage_pw.setBounds(210, 130, 200, 30);
		Mypage_Pannel.add(Mypage_pw);
		
		//2022-10-24 패스워드 확인 텍스트
		Mypage_pwcheck = new JPasswordField();
		Mypage_pwcheck.setBounds(210, 180, 200, 30);
		Mypage_Pannel.add(Mypage_pwcheck);
		
		//2022-10-24 닉네임 텍스트
		Mypage_textfield_Nickname =new JTextField();
		Mypage_textfield_Nickname.setBounds(210, 230, 200, 30);
		Mypage_textfield_Nickname.setFont(subFont);
		Mypage_Pannel.add(Mypage_textfield_Nickname);
		
		//2022-10-24 이메일 텍스트
		Mypage_textfield_Email =new JTextField();
		Mypage_textfield_Email.setBounds(210, 280, 200, 30);
		Mypage_textfield_Email.setFont(subFont);
		Mypage_textfield_Email.setEditable(false);
		Mypage_Pannel.add(Mypage_textfield_Email);
		
		
//		//2022-10-23 뒷 배경 이미지 
//		ImageIcon Mypageimg = new ImageIcon("images/signUpImg.jpg");
//		JLabel lblMypage = new JLabel(Mypageimg);
//		lblMypage.setBounds(0, -20, 600, 500);
//		Mypage_Pannel.add(lblMypage);
		add(Mypage_Pannel);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == Mypage_btn) {
			char[] temp = Mypage_pw.getPassword();
			String result = "";
			String result2 = "";
			
			for(char ch	: temp) {
				Character.toString(ch);
				result += ""+ch+"";
			}
			
			temp = Mypage_pwcheck.getPassword();
			
			for(char ch	: temp) {
				Character.toString(ch);
				result2 += ""+ch+"";
			}
			

			if(result.equals(result2)) {
			  db.memberInfo(ID, Mypage_pw);
			} else {
				JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다.",
						"경고", JOptionPane.WARNING_MESSAGE);
			}
		} else if(obj == exit_btn) {
			this.dispose();
			Server s = new Server("서버화면", ID, name);
		}
	}

}