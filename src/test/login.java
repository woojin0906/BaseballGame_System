package test;
// 메인
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import game.ClientUi;

public class login extends JFrame implements ActionListener {
	private JButton btnServer, btnClient;
	private Font btnFont;
	
	public static void main(String[] args) {
		new login();
		Long pid = ProcessHandle.current().pid();
		System.out.println(pid);
	}
	
	public login() {
		setTitle("시작 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 550);
		setLayout(new BorderLayout());
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setPreferredSize(new DimensionUIResource(780,550));
		
		btnFont = new Font("Koverwatch", Font.PLAIN, 18);
		
		//panelCenter.setBackground(n);
		btnServer = new JButton("임시 서버 버튼"); // 일대일로 가면 없앨 거임
		btnServer.setBounds(570, 130, 150, 80);
		btnServer.setBackground(new Color(26, 67, 141));
		btnServer.addActionListener(this);
		btnServer.setFont(btnFont);
		btnServer.setForeground(Color.white);
		panelCenter.add(btnServer);

		btnClient = new JButton("서버 입장하기");
		btnClient.addActionListener(this);
		btnClient.setBounds(570, 300, 150, 80);
		btnClient.setBackground(new Color(26, 67, 141));
		btnClient.setFont(btnFont);
		btnClient.setForeground(Color.white);
		panelCenter.add(btnClient);
		
		ImageIcon Serverbackground_img = new ImageIcon("images/loginBackground.jpg");
		JLabel lblserverbackground = new JLabel(Serverbackground_img);
		lblserverbackground.setBounds(0, -10, 780, 550);
		panelCenter.add(lblserverbackground);
		add(panelCenter,BorderLayout.CENTER);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnServer) {  // 서버 화면 숨겨야함 -> sysout으로 변경하기? 이건 고민해봐야해서 그냥 놔두기
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Chat c = new Chat("서버");
					c.start();
				}
			});
			thread.start();
			
			this.dispose();
		} 
		else if(obj == btnClient) {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Game g = new Game("게임화면");
					g.setSocket();
				}
			});
			thread.start();
			
			this.dispose();
		}
	}
}