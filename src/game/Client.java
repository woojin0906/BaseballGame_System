package game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
	private JTextField tfId;
	private JTextField tfIp;
	private JTextField tfPort;
	private JButton btn;

	public Client() {
		setTitle("서버 입장 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLayout(new GridLayout(4, 1));
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		

		tfId = new JTextField();
		tfId.addActionListener(this);
		add(tfId);
		
		tfIp = new JTextField();
		tfIp.addActionListener(this);
		add(tfIp);
		
		tfPort = new JTextField();
		tfPort.addActionListener(this);
		add(tfPort);
		
		btn = new JButton("서버 입장");
		btn.addActionListener(this);
		add(btn);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == tfIp || obj == tfPort || obj == btn) {
			String ip = tfIp.getText();
			int port = Integer.parseInt(tfPort.getText());
			Boolean chk = check(port);
			if(chk = true) {
				dispose();
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ClientUi clientUi = new ClientUi(ip, port);
						clientUi.setSocket();						
					}
				});
				thread.start();
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
