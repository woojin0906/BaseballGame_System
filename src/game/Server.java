package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener, MouseListener{
	
	private JTextField tfPort;
	private JButton btn;
	private Font font;
	private Color blue;
	private Color skyBlue;

	public Server() {
		setTitle("서버 생성 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocation(500, 200);
		setResizable(false); // 화면 크기 조절 불가능
		
		font = new Font("Koverwatch", Font.PLAIN, 16);
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
		
		tfPort = new JTextField("포트번호 입력");
		tfPort.addActionListener(this);
		tfPort.addMouseListener(this);
		tfPort.setBounds(40, 30, 200, 40);
		tfPort.setHorizontalAlignment(JTextField.CENTER);
		tfPort.setFont(font);
		panelCenter.add(tfPort);
		
		btn = new JButton("서버 생성");
		btn.addActionListener(this);
		btn.setBounds(90, 90, 100, 50);
		btn.setBackground(blue);
		btn.setForeground(Color.WHITE);
		btn.setFont(font);
		panelCenter.add(btn);
		
		add(panelCenter, BorderLayout.CENTER);
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
