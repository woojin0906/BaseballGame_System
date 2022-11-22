package user;
// 2022.10.26 전우진 아이디 중복 디자인 구현
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.dbInfo;

public class IdOverlap extends JFrame implements ActionListener, MouseListener, WindowListener{
	private Font font, btnFont, IDFont;
	private JButton btnCancel, btnCheck;
	private JTextField tfId;
	private String id;
	private dbInfo db;
	private SignUp sg;
	private Color blue, skyBlue;
	
	public IdOverlap(String title, SignUp signUp) {
		this.sg = signUp;
		setTitle(title);
		setSize(342, 150);
		setLayout(new BorderLayout());
		setLocation(500, 500);
		setResizable(false); // 화면 크기 조절 불가능
		addWindowListener(this);
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 238);
		
		IDFont = new Font("넥슨 풋볼고딕 B", Font.PLAIN, 16);
		btnFont = new Font("Koverwatch", Font.PLAIN, 16);
		font = new Font("Koverwatch", Font.PLAIN, 14);
		
		setBack();
		
		setVisible(true);
	}
	
	private void setBack() {
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setBackground(skyBlue);
		
		// 아이디 중복 화면 취소 버튼 출력
		btnCancel = new JButton("취소");
		btnCancel.setFont(font);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.setBounds(0, 5, 40, 30);
		btnCancel.addActionListener(this);
        panelCenter.add(btnCancel);
        
        // 아이디 중복 확인 버튼 출력
        btnCheck = new JButton("확인");
		btnCheck.setBounds(218, 55, 70, 30);
		btnCheck.setBackground(blue);
		btnCheck.setForeground(Color.WHITE);
		btnCheck.setFont(btnFont);
		btnCheck.addActionListener(this);
		panelCenter.add(btnCheck);
		
        // 아이디 텍스트 필드(아이디) 출력
		tfId = new JTextField("아이디");
		tfId.setFont(IDFont);
		tfId.setBounds(38, 55, 160, 30);
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setFocusTraversalKeysEnabled(false);
		tfId.addActionListener(this);
		tfId.addMouseListener(this);
		panelCenter.add(tfId);
        
        add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// 2022.10.26 전우진 기능 구현
		if(obj == btnCancel) {
			this.dispose();
			SignUp signUp = new SignUp("회원가입");
			signUp.setLocationRelativeTo(this);
		} else if(obj == btnCheck || obj == tfId) {
			id = tfId.getText();
			if(id.equals("") || id.equals("아이디")) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
			} else {
				if(id.length() >= 4 && id.length() <= 10) {
					// 전우진 아이디 중복 DB연결
					db = new dbInfo();
					db.checkID(this, id, tfId);
				} else {
					JOptionPane.showMessageDialog(this, "아이디는 4자리 이상 10자리 이하로 작성해주세요.");
				} 
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
		if(obj == tfId) {
			tfId.setText("");
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
