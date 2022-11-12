package game;
// 클라이언용 IP, PORT번호 입력 1번
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener, MouseListener {
	
	private JTextField tfId;
	private JTextField tfIp;
	private JTextField tfPort;
	private JButton btn;
	private Font font, btnFont;
	private Color blue, skyBlue;
	private Socket socket;
	
	public Client() {
		setTitle("서버 입장 화면");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLayout(new BorderLayout());
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		
		font = new Font("Koverwatch", Font.PLAIN, 16);
		btnFont = new Font("Koverwatch", Font.PLAIN, 18);

		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 243);
		
		setCenter();
		
		setVisible(true);
	}

	private void setCenter() {
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setPreferredSize(new Dimension(300, 300));
		panelCenter.setBackground(skyBlue);
		
		tfId = new JTextField("아이디");
		tfId.addActionListener(this);
		tfId.addMouseListener(this);
		tfId.setBounds(45, 30, 200, 40);
		tfId.setHorizontalAlignment(JTextField.CENTER);
		tfId.setFont(font);
		panelCenter.add(tfId);
		
		tfIp = new JTextField("192.168.0.10"); // IP주소
		tfIp.addActionListener(this);
		tfIp.addMouseListener(this);
		tfIp.setBounds(45, 80, 200, 40);
		tfIp.setHorizontalAlignment(JTextField.CENTER);
		tfIp.setFont(font);
		panelCenter.add(tfIp);
		
		tfPort = new JTextField("3333");  // 포트번호
		tfPort.addActionListener(this);
		tfPort.addMouseListener(this);
		tfPort.setBounds(45, 130, 200, 40);
		tfPort.setHorizontalAlignment(JTextField.CENTER);
		tfPort.setFont(font);
		panelCenter.add(tfPort);
		
		btn = new JButton("서버 입장");
		btn.addActionListener(this);
		btn.addMouseListener(this);
		btn.setBounds(80, 190, 120, 50);
		btn.setBackground(blue);
		btn.setForeground(Color.WHITE);
		btn.setFont(btnFont);
		panelCenter.add(btn);		
		
		add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == tfId || obj == tfIp || obj == tfPort || obj == btn) {
			if(tfId.getText().equals("") || tfIp.getText().equals("") || tfPort.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "정확한 포트를 입력해주세요.");
			}
			String ip = tfIp.getText();
			int port = Integer.parseInt(tfPort.getText());
			Boolean chk = check(port);
			if(chk = true) {
				try {
					socket = new Socket(ip, port);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ClientUi clientUi = new ClientUi(socket);
//						TcpClient tcpClient = new TcpClient("클라이언트", ip, port);
//						tcpClient.setSocket();
					}
				});
				thread.start();
				
						
				
				this.dispose();
			}
		}
	}
	
	boolean check(int port) {
        try {
            int i = port;
            if (1 > i | i > 65535) {
                JOptionPane.showMessageDialog(this, "정확한 포트를 입력해주세요.");
                return false;
            }
            return true;
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(this, "정확한 포트를 입력해주세요");
            return false;
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == tfPort) {
			tfPort.setText("");
		} else if(obj == tfId) {
			tfId.setText("");
		} else if(obj == tfIp) {
			tfIp.setText("");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
}
