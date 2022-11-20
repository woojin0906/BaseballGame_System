package user;
// 2022.10.22 전우진 비밀번호 재설정 디자인 구현
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.dbInfo;
import game.Login;

public class PwCheck extends JFrame implements ActionListener, MouseListener, KeyListener{
	
	private Font font, btnFont;
	private JButton btnIdSearch, btnCheck, btnCancel;
	private JTextField tfEmail, tfId;
	private Color blue, skyBlue;
	
	public PwCheck(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(342, 202);
		setLayout(new BorderLayout());
		setLocation(500, 500);
		setResizable(false); // 화면 크기 조절 불가능
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 238);
		
		btnFont = new Font("Koverwatch", Font.PLAIN, 16);
		font = new Font("Koverwatch", Font.PLAIN, 14);
		
		setBack();
		
		setVisible(true);
	}
	
	private void setBack() {
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setBackground(skyBlue);
		
		// 비밀번호 재설정 취소 버튼 출력
		btnCancel = new JButton("취소");
		btnCancel.setFont(font);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.setBounds(0, 5, 40, 30);
		btnCancel.addActionListener(this);
        panelCenter.add(btnCancel);
        
        // 비밀번호 재설정 확인 버튼 출력
        btnCheck = new JButton("확인");
		btnCheck.setBounds(218, 55, 70, 70);
		btnCheck.setBackground(blue);
		btnCheck.setForeground(Color.WHITE);
		btnCheck.setFont(btnFont);
		btnCheck.addActionListener(this);
		panelCenter.add(btnCheck);
		
        // 비밀번호 재설정 텍스트 필드(아이디) 출력
		tfId = new JTextField("아이디");
		tfId.setFont(font);
		tfId.setBounds(38, 55, 160, 30);
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setFocusTraversalKeysEnabled(false);
		tfId.addActionListener(this);	
		tfId.addMouseListener(this);
		tfId.addKeyListener(this);
		panelCenter.add(tfId);
        
		// 비밀번호 재설정 텍스트 필드(이메일) 출력
		tfEmail = new JTextField("이메일");
		tfEmail.setFont(font);
		tfEmail.setBorder(BorderFactory.createEmptyBorder());
		tfEmail.setBounds(38, 98, 160, 28);
		tfEmail.addActionListener(this);	
		tfEmail.addMouseListener(this);
		panelCenter.add(tfEmail);
		
		// 비밀번호 재설정 -> 아이디 찾기 버튼 출력
		btnIdSearch = new JButton("아이디를 잃어버리셨나요?");
		btnIdSearch.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
		btnIdSearch.setContentAreaFilled(false);
		btnIdSearch.setBorderPainted(false);
		Color gray = new Color(100, 100, 100);
		btnIdSearch.setForeground(gray);
		btnIdSearch.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnIdSearch.setBounds(180, 130, 140, 30);
		btnIdSearch.addActionListener(this);
		panelCenter.add(btnIdSearch);
        
        add(panelCenter, BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			// 2022-10-26 전우진 기능 구현
			if(obj == btnCancel) {
				Login lg = new Login();
				lg.setLocationRelativeTo(this);
				this.dispose();
			} else if(obj == btnIdSearch) {
				IdCheck idc = new IdCheck("아이디 찾기");
				idc.setLocationRelativeTo(this);
				this.dispose();
			} else if(obj == btnCheck || obj == tfId || obj == tfEmail) {
				try {
					// 전우진 비밀번호 재설정 DB연결
					dbInfo temp = new dbInfo();
					temp.resetPW(this, tfId.getText(), tfEmail.getText());
				} catch (Exception e2) {
					e2.printStackTrace();
					}
				}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		// 2022-10-26 전우진 텍스트필드 클릭 시 초기화
		if(obj == tfId) {
			tfId.setText("");
		} else if(obj == tfEmail) {
			tfEmail.setText("");
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
		// 2022-10-26 전우진 이메일 필드 탭 연결 및 초기화
		if(e.getKeyCode() == KeyEvent.VK_TAB) {
				tfEmail.requestFocus();
				tfEmail.setText("");
		}		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
