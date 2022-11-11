package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener{
	
	private JTextField tfPort;
	private JButton btn;

	public Server() {
		setTitle("서버 생성 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLayout(new GridLayout(2, 1));
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		

		tfPort = new JTextField();
		tfPort.addActionListener(this);
		add(tfPort);
		
		btn = new JButton("서버 생성");
		btn.addActionListener(this);
		add(btn);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == tfPort || obj == btn) {
			int port = Integer.parseInt(tfPort.getText());
			Boolean chk = check(port);
			if(chk = true) {
				dispose();
				ServerUi serverUi = new ServerUi(port);
				serverUi.start();
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
}
