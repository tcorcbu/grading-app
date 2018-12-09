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
		
	public static void drawStudentProfile(final JFrame mainframe,final Data data,final Student s) {
		mainframe.setTitle("Student Profile");
		MainWindow.drawMenuBar(mainframe,data);
			
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel classSummaryPanel = new JPanel();
		classSummaryPanel.setLayout(new GridLayout(1,1));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,4));

		JPanel backButtonPanel = new JPanel();
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		
		JButton classSummary = new JButton("View Class Summary");
		
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
		
		gradableTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		for (int i=0; i<3; i++) {
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
				default:
					break;
			}
		}
		
		for (int i=0; i<data.nGradables(); i++){
			Gradable g = s.getGradable(i);
			gradableModel.addRow(new Object[]{g,g.getPointsLost(),g.getStudentWeight(),g.getNote()});
		}

		JScrollPane gradableTablePane = new JScrollPane(gradableTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JLabel gradeInfo = new JLabel("  Current Grade: A");
		JButton backButton = new JButton("Home");
		
		// START Layout 
		
		backButtonPanel.add(backButton);

		botPanel.add(gradeInfo,BorderLayout.WEST);
		botPanel.add(backButtonPanel,BorderLayout.EAST);

		classSummaryPanel.add(classSummary);
		
		mainPanel.add(classSummaryPanel);
		mainPanel.add(infoPanel);
		// mainPanel.add(Box.createHorizontalStrut(50));
		mainPanel.add(gradableTablePane);
		mainPanel.add(botPanel);
		
		mainframe.add(mainPanel);
		mainframe.validate();
		mainframe.repaint();

		// END Layout
		
		// START Action Listeners
		
		classSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.remove(mainPanel);
				ClassProfile.drawClassProfile(mainframe, data);
			}
		});
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainframe.remove(mainPanel);
				MainWindow.drawMainWindow(mainframe,data);
			}
		});
		
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
					data.addSaveCommand(GradeService.updatePointsLost(g, s.getSchoolID(),tablePoints));
					break;
				case 2:
					Integer tableWeight = Integer.parseInt(gradableTable.getValueAt(row, column).toString());
					g.setStudentWeight(tableWeight);
					data.addSaveCommand(GradeService.updateStudentWeight(g,s.getSchoolID(),tableWeight));
					break;
				case 3:
					String tableNote = gradableTable.getValueAt(row,column).toString();
					g.setNote(tableNote);
					data.addSaveCommand(GradeService.updateComment(g,s.getSchoolID(),tableNote));
					break;
				}	
			}
		});
		
		gradableTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = gradableTable.getSelectedRow();
				int column = gradableTable.getSelectedColumn();
				
				if (e.getClickCount() == 2 && column == 0) {
					Gradable gradable = (Gradable)gradableTable.getValueAt(row,column);
					mainframe.remove(mainPanel);
					GradableProfile.drawGradableProfile(mainframe,data,data.getGradable(gradable.getName()));
				}
			}
		});
		// END Action Listeners
	}
	
} 
