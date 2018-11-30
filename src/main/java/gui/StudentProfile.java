package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;

import db.GradeService;
import db.StudentService;

public class StudentProfile {
		
	public StudentProfile(final JFrame mainframe,Data data, final Student s) {
		drawStudentProfile(mainframe,data,s);
	}
	
	private void drawStudentProfile(final JFrame mainframe,final Data data,Student s) {
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,4));
		
		infoPanel.add(new JLabel("First Name: "));
		infoPanel.add(new JLabel("Last Name: "));
		infoPanel.add(new JLabel("School ID: "));
		infoPanel.add(new JLabel("Year: "));
		infoPanel.add(new JLabel(s.getFirstName()));
		infoPanel.add(new JLabel(s.getLastName()));
		infoPanel.add(new JLabel(s.getSchoolID()));
		infoPanel.add(new JLabel(s.getYear()));
		
		// START Gradable Table
		
		DefaultTableModel gradableModel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == 0){
				return false;
				} 
				else {
					return true;
					}
			}
		};
		
		// indexTableModel gradableModel = new indexTableModel(); 
		final JTable gradableTable = new JTable(gradableModel);

		gradableModel.addColumn("Gradable"); 
		gradableModel.addColumn("Points Lost"); 
		gradableModel.addColumn("Weight");
		gradableModel.addColumn("Notes");
		
		gradableTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i=0; i<4; i++) {
			TableColumn column = gradableTable.getColumnModel().getColumn(i);
			switch (i) {
				case 0:
					column.setMaxWidth(300);
					column.setPreferredWidth(150);
					column.setMinWidth(75);
					break;
				case 1:
					column.setMaxWidth(200);
					column.setPreferredWidth(80);
					column.setMinWidth(50);
					break;
				case 2:
					column.setMaxWidth(200);
					column.setPreferredWidth(50);
					column.setMinWidth(50);
					break;
				case 3:
					column.setPreferredWidth(450);
					column.setMinWidth(125);
					break;
				default:
					break;
			}
		}
		
		for (int i=0; i<data.nGradables(); i++){
			Gradable g = s.getGradable(i);
			gradableModel.addRow(new Object[]{g.getName(),g.getPointsLost(),g.getStudentWeight(),g.getNote()});
		}

		JScrollPane gradableTablePane = new JScrollPane(gradableTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// END Gradable Table
		
		JButton backButton = new JButton("Back");
		
		
		// START Layout 
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// JPanel gradePanel = new JPanel();
		// gradePanel.setLayout(new GridLayout(0,2));
		JLabel gradeInfo = new JLabel("Current Grade: A");
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		// backButton.setAlignmentX(botPanel.RIGHT_ALIGNMENT);
		// gradeInfo.setAlignmentX(botPanel.LEFT_ALIGNMENT);
		botPanel.add(gradeInfo,BorderLayout.WEST);
		botPanel.add(backButton,BorderLayout.EAST);
		
		
		// mainPanel.add(upperPanel);
		mainPanel.add(infoPanel);
		mainPanel.add(Box.createHorizontalStrut(50));
		mainPanel.add(gradableTablePane);
		mainPanel.add(botPanel);
		
		mainframe.add(mainPanel);
		mainframe.validate();
		mainframe.repaint();
		
		
		// END Layout
		
		// START Action Listeners
			ActionListener backListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   mainframe.remove(mainPanel);
				   mainframe.setTitle(data.getLoadedClass());
				   MainWindow m = new MainWindow(mainframe,data);
				   }
				};

		backButton.addActionListener(backListener);
		
		
		MouseAdapter TableHeaderMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point clickPoint = e.getPoint();
				int column = gradableTable.columnAtPoint(clickPoint);
			}
		};
		
		JTableHeader gradableHeader = gradableTable.getTableHeader();
		gradableHeader.addMouseListener(TableHeaderMouseListener);
		
		gradableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				int row = gradableTable.getSelectedRow();
				int column = gradableTable.getSelectedColumn();
				String gradableName = gradableTable.getValueAt(row,0).toString();
				
				Gradable g = s.getGradable(gradableName);
				switch(column) {
				case 1:
					Integer tablePoints = Integer.parseInt(gradableTable.getValueAt(row, column).toString());
					g.setPointsLost(tablePoints);
					GradeService.updatePointsLost(g.getID(), StudentService.getId(s),tablePoints);
					break;
				case 2:
					Integer tableWeight = Integer.parseInt(gradableTable.getValueAt(row, column).toString());
					g.setStudentWeight(tableWeight);
					GradeService.updateStudentWeight(g.getID(),StudentService.getId(s),tableWeight);
					break;
				case 3:
					String tableNote = gradableTable.getValueAt(row,column).toString();
					g.setNote(tableNote);
					GradeService.updateComment(g.getID(),StudentService.getId(s),tableNote);
					break;
				}	
		  }
		});
		
		// END Action Listeners
		
	}
	
} 
