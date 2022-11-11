package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class login extends JFrame implements ActionListener {
	private JButton btnServer, btnClient;
	
	public static void main(String[] args) {
		new login();
	}
	
	public login() {
		setTitle("시작 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 550);
		setLayout(new BorderLayout());
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		
		btnServer = new JButton("서버 생성하기");
		btnServer.addActionListener(this);
		add(btnServer, BorderLayout.WEST);
		
		btnClient = new JButton("서버 입장하기");
		btnClient.addActionListener(this);
		add(btnClient, BorderLayout.EAST);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnServer) {
			Server server = new Server();
		} else if(obj == btnClient) {
			Client client = new Client();
		}
	}
}
