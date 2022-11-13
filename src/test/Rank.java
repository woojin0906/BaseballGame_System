package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
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

public class Rank extends JFrame implements MouseListener{

	private String[] title = {"닉네임", "누적 점수", "이긴횟수"};
	private String[][] datas = new String[0][3];
	private DefaultTableModel model = new DefaultTableModel(datas,title);
	private JTable table = new JTable(model);
	private JScrollPane ScrollPane;
	private int count;
	private Color blue, skyBlue;
	private static String ID;
	
	public DefaultTableModel getModel() {
		return model;
	}
	
	public Rank(String title, String ID) {
		this.ID = ID;
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 300);
		setSize(414, 600);
		setLayout(new BorderLayout());
		setResizable(false); // 화면 크기 조절 불가능
		
		blue = new Color(26, 67, 141);
		skyBlue= new Color(218, 227, 243);
		
		JPanel UpPanel = new JPanel();
		UpPanel.setBackground(skyBlue);
		JLabel lbltitle = new JLabel("랭킹");
		lbltitle.setForeground(blue);
		lbltitle.setFont(new Font("Koverwatch", Font.BOLD, 30));
		lbltitle.setBorder(BorderFactory.createEmptyBorder(20 , 0, 20 , 0));
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
		table.getTableHeader().setFont(new Font("Koverwatch", Font.PLAIN, 14));
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
		table.setFont(new Font("Koverwatch", Font.PLAIN, 14) );
				
		//테이블 행간 조절,2022-10-28
		table.setRowHeight(25);
		
		ScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollPane.setBounds(27, 0, 350, 465);
		
		RankTable_Panel.add(ScrollPane);
		add(RankTable_Panel, BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		Rank sv = new Rank("랭킹",ID);
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

}
