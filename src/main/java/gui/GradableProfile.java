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
		
	public GradableProfile(JFrame mainframe,Data data, final Gradable g) {
		drawGradableProfile(mainframe,data,g);
	}
	
	private void drawGradableProfile(final JFrame mainframe,final Data data,final Gradable g) {
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,7));
		
		infoPanel.add(new JLabel("Title: "));
		infoPanel.add(new JLabel("Category: "));
		infoPanel.add(new JLabel("Undergrad Weight:"));
		infoPanel.add(new JLabel("Graduate Weight:"));
		infoPanel.add(new JLabel("Total Points: "));
		infoPanel.add(new JLabel("Gradable Weight: "));
		infoPanel.add(new JLabel("Average: "));
		infoPanel.add(new JLabel(g.getName()));
		infoPanel.add(new JLabel(g.getType().getType()));
		
		infoPanel.add(new JLabel(String.valueOf(g.getType().getWeight("Undergraduate"))+"%"));
		infoPanel.add(new JLabel(String.valueOf(g.getType().getWeight("Graduate"))+"%"));
		
		NumberFormat pointsFormat;
		pointsFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField gradablePoints = new JFormattedTextField(pointsFormat);
		gradablePoints.setValue(g.getPoints());
		gradablePoints.setColumns(10);	
		infoPanel.add(gradablePoints);
		
		NumberFormat weightFormat;
		weightFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField gradableWeight = new JFormattedTextField(weightFormat);
		gradableWeight.setValue(g.getIntraCategoryWeight());
		gradableWeight.setColumns(10);
		infoPanel.add(gradableWeight);
		
		infoPanel.add(new JLabel("NEED AVG"));

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
		
		// indexTableModel studentModel = new indexTableModel(); 
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
			Gradable gtmp = stmp.getGradable(g.getName());
			studentModel.addRow(new Object[]{stmp.getFirstName()+" "+stmp.getLastName(),gtmp.getPointsLost(),gtmp.getNote()});
		}

		final JScrollPane studentTablePane = new JScrollPane(studentTable);
		
		// END Gradable Table
		
		JButton backButton = new JButton("Back");
		// START Layout 				
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		botPanel.add(backButton,BorderLayout.EAST);

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(infoPanel);
		mainPanel.add(studentTablePane);
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
		
		
		ActionListener pointsListener = new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   int p = ((Number)gradablePoints.getValue()).intValue();
			   g.setPoints(p);
			   GradableService.updatePoints(g,p);
			   }
			};
		gradablePoints.addActionListener(pointsListener);
		
		ActionListener weightListener = new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   int w = ((Number)gradableWeight.getValue()).intValue();
			   g.setIntraCategoryWeight(w);
			   GradableService.updateWeight(g,w);
			   }
			};
		gradableWeight.addActionListener(weightListener);
		
		
		studentModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				int row = studentTable.getSelectedRow();
				int column = studentTable.getSelectedColumn();
				String studentName = studentTable.getValueAt(row,0).toString();
				
				Student s = data.getStudent(row);
				Gradable gradable = s.getGradable(g.getName());
				switch(column) {
				case 1:
					Integer tablePoints = Integer.parseInt(studentTable.getValueAt(row, column).toString());
					gradable.setPointsLost(tablePoints);
					GradeService.updatePointsLost(gradable.getID(), StudentService.getId(s),tablePoints);
					break;
				case 2:
					String tableNote = studentTable.getValueAt(row,column).toString();
					gradable.setNote(tableNote);
					GradeService.updateComment(gradable.getID(),StudentService.getId(s),tableNote);
					break;
				}	
		  }
		});
		
		
		// END Action Listeners
		
	}
	
} 
