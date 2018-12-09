package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.text.*;

import db.GradeService;
import db.StudentService;
import db.GradableService;

public class GradableProfile {
		
	public static void drawGradableProfile(final JFrame mainframe,final Data data,final Gradable g) {
		mainframe.setTitle("Gradable Profile");
		MainWindow.drawMenuBar(mainframe,data);
			
		JPanel classSummaryPanel = new JPanel();
		classSummaryPanel.setLayout(new GridLayout(1,1));
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,6));
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		
		JPanel backButtonPanel = new JPanel();
		
		JButton classSummary = new JButton("View Class Summary");
		
		infoPanel.add(new JLabel("Title: "));
		infoPanel.add(new JLabel("Category: "));
		infoPanel.add(new JLabel("Undergrad Weight:"));
		infoPanel.add(new JLabel("Graduate Weight:"));
		infoPanel.add(new JLabel("Total Points: "));
		infoPanel.add(new JLabel("Gradable Weight: "));
		infoPanel.add(new JLabel(g.getName()));
		infoPanel.add(new JLabel(g.getType().getType()));
		infoPanel.add(new JLabel(String.valueOf(g.getType().getWeight("Undergraduate"))+"%"));
		infoPanel.add(new JLabel(String.valueOf(g.getType().getWeight("Graduate"))+"%"));
		
		NumberFormat pointsFormat;
		pointsFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField gradablePoints = new JFormattedTextField(pointsFormat);
		gradablePoints.setValue(g.getPoints());
		gradablePoints.setColumns(8);	
		infoPanel.add(gradablePoints);
		
		NumberFormat weightFormat;
		weightFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField gradableWeight = new JFormattedTextField(weightFormat);
		gradableWeight.setValue(g.getIntraCategoryWeight());
		gradableWeight.setColumns(8);
		infoPanel.add(gradableWeight);

		DefaultTableModel studentModel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == 0){
					return false;
				}
				else {
					return true;
				}
			}
		};
		
		final JTable studentTable = new JTable(studentModel);

		studentModel.addColumn("Student"); 
		studentModel.addColumn("Points Lost"); 
		studentModel.addColumn("Notes"); 

		studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		for (int i=0; i<2; i++) {
			TableColumn column = studentTable.getColumnModel().getColumn(i);
			switch (i) {
				case 0:
					column.setMaxWidth(300);
					column.setPreferredWidth(150);
					break;
				case 1:
					column.setMaxWidth(200);
					column.setPreferredWidth(80);
					break;
				default:
					break;
			}
		}
		
		for (int i=0; i<data.nStudents(); i++){
			Student stmp = data.getStudent(i);
			Grade gtmp = stmp.getGrade(g.getName());
			studentModel.addRow(new Object[]{stmp,gtmp.getPointsLost(),gtmp.getNote()});
		}

		final JScrollPane studentTablePane = new JScrollPane(studentTable);
		
		JLabel gradeInfo = new JLabel("  Gradable Average: "+String.valueOf(data.getGradableAverage(g))+"%");		
		
		JButton backButton = new JButton("Home");

		// START Layout 
		
		backButtonPanel.add(backButton);
		
		botPanel.add(gradeInfo,BorderLayout.WEST);
		botPanel.add(backButtonPanel,BorderLayout.EAST);

		classSummaryPanel.add(classSummary);
		
		mainPanel.add(classSummaryPanel);
		mainPanel.add(infoPanel);
		mainPanel.add(studentTablePane);
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
		
		gradablePoints.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			   update();
			}
			public void removeUpdate(DocumentEvent e) {
				update();
			}
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void update() {
				int p = ((Number)gradablePoints.getValue()).intValue();
				g.setPoints(p);
				data.addSaveCommand(GradableService.updatePoints(g,p));
			}
		});
			
		gradableWeight.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				update();
			}
			public void removeUpdate(DocumentEvent e) {
				update();
			}
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void update() {
				int w = ((Number)gradableWeight.getValue()).intValue();
				g.setIntraCategoryWeight(w);
				data.addSaveCommand(GradableService.updateWeight(g,w));
			}
		});

		studentModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				int row = studentTable.getSelectedRow();
				int column = studentTable.getSelectedColumn();
				
				Student s = data.getStudent(row);
				Grade grade = s.getGrade(g.getName());
				switch(column) {
				case 1:
					Integer tablePoints = Integer.parseInt(studentTable.getValueAt(row, column).toString());
					grade.setPointsLost(tablePoints);
					data.addSaveCommand(GradeService.updatePointsLost(grade, s.getSchoolID(),tablePoints));
					break;
				case 2:
					String tableNote = studentTable.getValueAt(row,column).toString();
					grade.setNote(tableNote);
					data.addSaveCommand(GradeService.updateComment(grade,s.getSchoolID(),tableNote));
					break;
				}	
		  }
		});
		
		studentTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = studentTable.getSelectedRow();
				int column = studentTable.getSelectedColumn();
				
				if (e.getClickCount() == 2 && column == 0) {
					Student student = (Student)studentTable.getValueAt(row,column);
					mainframe.remove(mainPanel);
					StudentProfile.drawStudentProfile(mainframe,data,student);
				}
			}
		});
		// END Action Listeners
	}
	
} 
