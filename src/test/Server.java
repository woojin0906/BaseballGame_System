package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


	public class Server extends JFrame implements ActionListener{
		
		private Color SeverbtnColor;
		private Font mainFont;
		private JPanel leftpanel;
		private JButton serverroom1, logout_btn, Rank_btn, Mypage_btn;
		private String ID,name;
		private JLabel UserName;
		
		
		public Server(String title, String ID, String name) {
			this.ID=ID;
			this.name = name;
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocation(300, 300);
			setSize(700, 500);
			setLayout(new BorderLayout());
			setResizable(false); // 화면 크기 조절 불가능
			
			SeverbtnColor = new Color(109, 109, 109);
			mainFont = new Font("Koverwatch", Font.PLAIN, 20);

			LeftPanel();
			DBRank db = new DBRank(ID); //랭킹 디비 붙이기
			db.UserName(ID ,UserName);

			setVisible(true);
	
		}
		private void LeftPanel() {
			leftpanel = new JPanel();
			leftpanel.setLayout(null);

			//유저 이름
			UserName = new JLabel(name);
			UserName = new JLabel();
			UserName.setBounds(600, 25, 500, 20);
			UserName.setForeground(Color.white);
			UserName.setFont(mainFont);

			leftpanel.add(UserName);
			
			//2022-10-23 서버 1
			serverroom1 = new JButton("서버 1");
			serverroom1.setBounds(70, 80, 150, 130);
			serverroom1.setBackground(SeverbtnColor);
			serverroom1.setFont(mainFont);
			serverroom1.addActionListener(this);
			leftpanel.add(serverroom1);
			
			//2022-10-23 서버 2
			JButton serverroom2 = new JButton("서버 2");
			serverroom2.setBounds(270, 80, 150, 130);
			serverroom2.setBackground(SeverbtnColor);
			serverroom2.setFont(mainFont);
			leftpanel.add(serverroom2);
			
			//2022-10-23 서버 3
			JButton serverroom3 = new JButton("서버 3");
			serverroom3.setBounds(470, 80, 150, 130);
			serverroom3.setBackground(SeverbtnColor);
			serverroom3.setFont(mainFont);
			leftpanel.add(serverroom3);
			
			//2022-10-23 서버 4
			JButton serverroom4 = new JButton("서버 4");
			serverroom4.setBounds(70, 250, 150, 130);
			serverroom4.setBackground(SeverbtnColor);
			serverroom4.setFont(mainFont);
			leftpanel.add(serverroom4);
			
			//2022-10-23 서버 5
			JButton serverroom5 = new JButton("서버 5");
			serverroom5.setBounds(270, 250, 150, 130);
			serverroom5.setBackground(SeverbtnColor);
			serverroom5.setFont(mainFont);
			leftpanel.add(serverroom5); 
			
			//2022-10-23 서버 6
			JButton serverroom6 = new JButton("서버 6");
			serverroom6.setBounds(470, 250, 150, 130);
			serverroom6.setBackground(SeverbtnColor);
			serverroom6.setFont(mainFont);
			leftpanel.add(serverroom6);
			
			Button_IMG();
			
			//2022-10-23 뒷 배경 이미지 
			ImageIcon Serverbackground_img = new ImageIcon("images/ServerBackgroundImg.jpg");
			JLabel lblserverbackground = new JLabel(Serverbackground_img);
			lblserverbackground.setBounds(-7, -5, 780, 500);
			leftpanel.add(lblserverbackground);
			
			
			add(leftpanel, BorderLayout.CENTER);
		}
		
		
		private void Button_IMG() {
			//2022-10-23 로그아웃 이미지 버튼
			ImageIcon Server_logoutBtn_img = new ImageIcon("images/Server_Logout1.png");
			logout_btn = new JButton(Server_logoutBtn_img);
			logout_btn.setBounds(630, 410, 40, 40);
			logout_btn.addActionListener(this);
			leftpanel.add(logout_btn);
			
			//2022-10-23 랭킹 이미지 버튼
			ImageIcon Server_RankBtn_img = new ImageIcon("images/Rank1.png");
			Rank_btn = new JButton(Server_RankBtn_img);
			Rank_btn.setBackground(Color.white);
			Rank_btn.setBounds(570, 410, 40, 40);
			Rank_btn.addActionListener(this);
			leftpanel.add(Rank_btn);
			
			//2022-10-23 마이페이지 이미지 버튼
			ImageIcon Server_MypageBtn_img = new ImageIcon("images/mypage.png");
			Mypage_btn = new JButton(Server_MypageBtn_img);
			Mypage_btn.setBackground(Color.white);
			Mypage_btn.setBounds(510, 410, 40, 40);
			Mypage_btn.addActionListener(this);
			leftpanel.add(Mypage_btn);
			
			//2022-10-23 공지 이미지 버튼
			ImageIcon Server_NoticeBtn_img = new ImageIcon("images/Notice.png");
			JButton Notice_btn = new JButton(Server_NoticeBtn_img);
			Notice_btn.setBackground(Color.white);
			Notice_btn.setBounds(25, 410, 40, 40);
			leftpanel.add(Notice_btn);
			
			//2022-10-23 정보 이미지 버튼
			ImageIcon Server_InfoBtn_img = new ImageIcon("images/imformation.jpg");
			JButton Info_btn = new JButton(Server_InfoBtn_img);
			Info_btn.setBounds(85, 410, 40, 40);
			leftpanel.add(Info_btn);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			// 2022-10-26 전우진 각 프레임 연결
			if(obj == serverroom1) {
				Game game = new Game("클라이언트 게임 시작 화면",ID);
				System.out.println(ID);
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						game.setSocket();
						
					}
				});
				thread.start();
				game.setLocationRelativeTo(this);
					
			}
			else if(obj == logout_btn) {
				Login lg = new Login();
				lg.setLocationRelativeTo(this);
				this.dispose();
			}
			else if(obj == Mypage_btn) {
				Mypage my = new Mypage("마이페이지", ID);
				my.setLocationRelativeTo(this);
				this.dispose();
			}
			else if(obj == Rank_btn) {
				Rank rk = new Rank("랭킹 화면", ID);
				rk.setLocationRelativeTo(this);
				this.dispose();
			}
		}
	}
	

