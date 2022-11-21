package game;
// 게임 대기창 디자인 구현
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import db.DBRank;
import user.Mypage;

	public class Server extends JFrame implements ActionListener{
		
		private Color SeverbtnColor;
		private Font mainFont, startFont;
		private JPanel leftpanel;
		private JButton serverroom1, logout_btn, Rank_btn, Mypage_btn, Notice_btn;
		private String ID,name;
		private JLabel UserName;
		private Color blue, skyBlue;
		private Game game;
		
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
			mainFont = new Font("Koverwatch", Font.PLAIN, 30);
			startFont = new Font("Koverwatch", Font.PLAIN, 70);

			blue = new Color(26, 67, 141);
			skyBlue= new Color(218, 227, 243);
			
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
			UserName.setBounds(10, 20, 500, 30);
			UserName.setForeground(Color.white);
			UserName.setFont(mainFont);

			leftpanel.add(UserName);
			
			//2022-10-23 랭킹 이미지 버튼
			ImageIcon Server_RankBtn_img = new ImageIcon("images/rank.png");
			Rank_btn = new JButton(Server_RankBtn_img);
			Rank_btn.setBackground(Color.white);
			Rank_btn.setOpaque(false);
			Rank_btn.setBorderPainted(false);
			Rank_btn.setBounds(560, 400, 50, 50);
			Rank_btn.addActionListener(this);
			leftpanel.add(Rank_btn);

			//2022-10-23 마이페이지 이미지 버튼
			ImageIcon Server_MypageBtn_img = new ImageIcon("images/info.png");
			Mypage_btn = new JButton(Server_MypageBtn_img);
			Mypage_btn.setBackground(Color.white);
			Mypage_btn.setOpaque(false);
			Mypage_btn.setBorderPainted(false);
			Mypage_btn.setBounds(620, 400, 50, 50);
			Mypage_btn.addActionListener(this);
			leftpanel.add(Mypage_btn);
			
			//2022-10-23 로그아웃 이미지 버튼
			ImageIcon Server_logoutBtn_img = new ImageIcon("images/exit.png");
			logout_btn = new JButton(Server_logoutBtn_img);
			logout_btn.setBackground(Color.white);
			logout_btn.setOpaque(false);
			logout_btn.setBorderPainted(false);
			logout_btn.setBounds(630, 10, 50, 50);
			logout_btn.addActionListener(this);
			leftpanel.add(logout_btn);
			
			//2022-10-23 공지 이미지 버튼
			ImageIcon Server_NoticeBtn_img = new ImageIcon("images/notice.png");
			Notice_btn = new JButton(Server_NoticeBtn_img);
			Notice_btn.setBackground(Color.white);
			Notice_btn.setBorderPainted(false);
			Notice_btn.setOpaque(false);
			Notice_btn.setBounds(25, 410, 40, 40);
			Notice_btn.addActionListener(this);
			leftpanel.add(Notice_btn);
			
			//2022-10-23 서버 1
			serverroom1 = new JButton("게임 시작");
			serverroom1.setBounds(215, 150, 250, 100);
			serverroom1.setBackground(SeverbtnColor);
			serverroom1.setFont(startFont);
			serverroom1.setBorderPainted(false);
			serverroom1.setOpaque(false);
			//serverroom1.setBackground(blue);
			serverroom1.setForeground(Color.WHITE);
			serverroom1.addActionListener(this);
			leftpanel.add(serverroom1);
			
			//2022-10-23 뒷 배경 이미지 
			ImageIcon Serverbackground_img = new ImageIcon("images/ServerBackground.jpg");
			JLabel lblserverbackground = new JLabel(Serverbackground_img);
			lblserverbackground.setBounds(-50, -30, 780, 500);
			leftpanel.add(lblserverbackground);
			
			add(leftpanel, BorderLayout.CENTER);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			// 2022-10-26 전우진 각 프레임 연결
			if(obj == serverroom1) {
				game = new Game("클라이언트 게임 시작 화면",ID);
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
				Mypage my = new Mypage("마이페이지", ID, name);
				my.setLocationRelativeTo(this);
				this.dispose();
			}
			else if(obj == Rank_btn) {
				Rank rk = new Rank("랭킹 화면", ID, name);
				rk.setLocationRelativeTo(this);
				this.dispose();
			}else if(obj == Notice_btn) {
				if(JOptionPane.showConfirmDialog(this, 
                        "고객센터로 전화하시겠습니까?",
                        "공지",
                        JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "고객센터 전화번호는 032-777-7777 입니다.", "고객센테 안내", JOptionPane.INFORMATION_MESSAGE);
                    }
			}
			else if(obj == Notice_btn) {
				if(JOptionPane.showConfirmDialog(this, 
                        "고객센터로 전화하시겠습니까?",
                        "공지",
                        JOptionPane.YES_NO_OPTION
                        ) == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "고객센터 전화번호는 032-777-7777 입니다.", "고객센테 안내", JOptionPane.INFORMATION_MESSAGE);
                    }
			}
		}
	}
	

