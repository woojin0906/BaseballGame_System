package game;
/* 
 디자인, 설계 : 허유진
 클래스 : 랭킹
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import db.DBRank;

public class Rank extends JFrame implements MouseListener, ActionListener, WindowListener {

	private String[] title = {"닉네임", "누적 점수", "이긴횟수"};
	private String[][] datas = new String[0][3];
	private DefaultTableModel model = new DefaultTableModel(datas,title);
	private JTable table = new JTable(model);
	private JScrollPane ScrollPane;
	private int count;
	private Color blue, skyBlue;
	private JButton exit_btn;
	private static String ID, NICK;
	
	public DefaultTableModel getModel() {
		return model;
	}
	
	public Rank(String title, String ID, String NICK) {
		this.ID = ID;
		this.NICK = NICK;
		setTitle(title);
		setLocation(300, 300);
		setSize(414, 600);
		setLayout(new BorderLayout());
		setResizable(false); // 화면 크기 조절 불가능
		addWindowListener(this);
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 238);
		
		JPanel UpPanel = new JPanel();
		UpPanel.setLayout(null);
		UpPanel.setPreferredSize(new Dimension(300, 80));
		UpPanel.setBackground(skyBlue);
		
		//2022-10-23 취소 버튼 
		exit_btn = new JButton("취소");
		exit_btn.setFont(new Font("Koverwatch", Font.BOLD, 20));
		exit_btn.setForeground(Color.WHITE);
		exit_btn.setBackground(blue);
		exit_btn.setBorderPainted(false);
		exit_btn.setOpaque(false);
		exit_btn.setBounds(0, 10, 65, 30);
		exit_btn.addActionListener(this);
		UpPanel.add(exit_btn);
				
		// 랭킹 라벨 출력
		JLabel lbltitle = new JLabel("랭킹");
		lbltitle.setForeground(blue);
		lbltitle.setFont(new Font("Koverwatch", Font.BOLD, 32));
		lbltitle.setBounds(170, 25, 65, 30);
		lbltitle.setBorder(BorderFactory.createEmptyBorder(20 , 5, 20 , 0));
		UpPanel.add(lbltitle);
		add(UpPanel, BorderLayout.NORTH);
		
		RankTable();
		DBRank dbrank = new DBRank(ID);
		dbrank.showRank(this, count);
		
		setVisible(true);

	}
	
	//오른쪽 랭킹 테이블에 대한 코드
	private void RankTable() {
		JPanel RankTable_Panel = new JPanel();
		RankTable_Panel.setLayout(null);
		RankTable_Panel.setPreferredSize(new Dimension(200, 600));
		RankTable_Panel.setBackground(skyBlue);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getTableHeader().setBackground(new Color(90, 90, 90));
		table.getTableHeader().setFont(new Font("Koverwatch", Font.PLAIN, 16));
		table.getTableHeader().setForeground(Color.white);
		table.setBackground(Color.white);
		
		//DafaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한), 2022-10-28
		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
				
		//DfaultTableCellHeaderRender의 정렬을 가운데 정렬로 지정, 2022-10-28
		tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
		//정렬할 테이블의 ColumnModel 을 가져옴, 2022-10-28
		TableColumnModel tableColumnModel = table.getColumnModel();
				
		//반복문을 이용하여 테이블 가운데 정렬로 지정, 2022-10-28
		for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
			tableColumnModel.getColumn(i).setCellRenderer(tableCellRenderer);
		}
		
		table.addMouseListener(this);
		
		//글자폰트 조절, 2022-10-28
		table.setFont(new Font("Koverwatch", Font.PLAIN, 16) );
				
		//테이블 행간 조절,2022-10-28
		table.setRowHeight(25);
		
		ScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollPane.setBounds(27, 0, 350, 465);
		
		RankTable_Panel.add(ScrollPane);
		add(RankTable_Panel, BorderLayout.CENTER);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		JTable jtable =(JTable) e.getSource();
		int row = jtable.getSelectedRow();
		int col = jtable.getSelectedColumn();
		
		System.out.println(model.getValueAt(row, col));
		
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
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == exit_btn) {
			this.dispose();
			Server s = new Server("서버화면", ID, NICK);
		}
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
