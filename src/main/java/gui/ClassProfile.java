package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math.*;

import db.CategoryService;

public class ClassProfile {
		
	public ClassProfile(final JFrame mainframe,final Data data) {

		mainframe.setTitle(data.getLoadedClass() + " Profile");
		
		// START Panel Setup
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));
		
		JPanel categoryTablePanel = new JPanel();
		categoryTablePanel.setLayout(new BoxLayout(categoryTablePanel, BoxLayout.Y_AXIS));
		JPanel backButtonPanel = new JPanel();
		
		JPanel addRemoveCategoryPanel = new JPanel();
		
		JPanel curvePanel = new JPanel();
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		JPanel gradeBreakdownPanel = new JPanel();
		gradeBreakdownPanel.setLayout(new GridLayout(5,2));
		
		JPanel gradeBreakdownOuterPanel = new JPanel();
		gradeBreakdownOuterPanel.add(gradeBreakdownPanel);
		
		// END Panel Setup
		
		// START Grade Table
		class myTableModel extends DefaultTableModel {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		}
		
		myTableModel gradeTableModel = new myTableModel(); 
		final JTable gradeTable = new JTable(gradeTableModel);
		gradeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		// Add columns
		gradeTableModel.addColumn("Student"); 
		gradeTableModel.addColumn("Total");
		for(int i = 0; i<data.getCategoryList().size(); i++) {
			gradeTableModel.addColumn(data.CategoryList(i).getType());
		}
		
		// Add rows
		String[] gradeTableRow = new String[data.getCategoryList().size()+2];
		
		for (int i=0; i<data.nStudents(); i++){
			Student stmp = data.getStudent(i);
			gradeTableRow[0] = stmp.getFirstName()+" "+stmp.getLastName();
			gradeTableRow[1] = String.valueOf(stmp.getOverallPercent(data.getCategoryList())) + "%";
			for (int j=2; j<gradeTableRow.length; j++) {
				gradeTableRow[j] = String.valueOf(stmp.getCategoryAverage(data.CategoryList(j-2).getType()))+"%";
			}
			gradeTableModel.addRow(gradeTableRow);
		}
		
		TableColumnModel gradeColumnModel = gradeTable.getColumnModel();
		for (int i=0; i<gradeColumnModel.getColumnCount(); i++) {
			TableColumn column = gradeTable.getColumnModel().getColumn(i);
			if (i==0){
				column.setPreferredWidth(125);
			}else{
				column.setPreferredWidth(75);
			}
		}
		JScrollPane gradeTablePane = new JScrollPane(gradeTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// END Grade Table
		
		// START Breakout Table
		
		final myTableModel breakoutTableModel = new myTableModel();
		final JTable breakoutTable = new JTable(breakoutTableModel);
		breakoutTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane breakoutTablePane = new JScrollPane(breakoutTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		BoundedRangeModel gradeTablePaneModel = gradeTablePane.getVerticalScrollBar().getModel();
		breakoutTablePane.getVerticalScrollBar().setModel( gradeTablePaneModel );
		
		// Dimension d = breakoutTable.getPreferredSize();
		// breakoutTablePane.setPreferredSize(new Dimension(50*breakoutColumnModel.getColumnCount(),d.height));
		
		// END Breakout Table
		
		// START Category Table
		final DefaultTableModel categoryTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex < 2){
				return false;
				} 
				else {
					return true;
					}
			}
		};
		
		categoryTableModel.addColumn("Student Type"); 
		categoryTableModel.addColumn("Total");
		for(int i = 0; i<data.getCategoryList().size(); i++) {
			categoryTableModel.addColumn(data.CategoryList(i).getType());
		}
		
		
		
		String[] categoryTableLabels = {"Undergraduate","Graduate"};
		String[] categoryTotals = {String.valueOf(data.sumUndergradCategories()),String.valueOf(data.sumGradCategories())};
		String[] categoryTableRow = new String[data.getCategoryList().size()+2];
		
		for(int i=0; i<2; i++){
			categoryTableRow[0] = categoryTableLabels[i];
			categoryTableRow[1] = categoryTotals[i];
			for (int j=2; j<categoryTableRow.length; j++) {
				categoryTableRow[j] = String.valueOf(data.CategoryList(j-2).getWeight(categoryTableLabels[i]));
			}
			categoryTableModel.addRow(categoryTableRow);
		}
		
		final JTable categoryTable = new JTable(categoryTableModel);
		categoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		categoryTable.setColumnSelectionAllowed(true);
		
		TableColumnModel categoryColumnModel = categoryTable.getColumnModel();
		for (int i=0; i<categoryColumnModel.getColumnCount(); i++) {
			TableColumn column = categoryTable.getColumnModel().getColumn(i);
			if (i==0){
				column.setPreferredWidth(125);
			}else{
				column.setPreferredWidth(75);
			}
		}
		JTableHeader categoryTableHeader = categoryTable.getTableHeader();
		TableColumnModel categoryTableColumnModel = categoryTableHeader.getColumnModel();
		
		JScrollPane categoryTablePane = new JScrollPane(categoryTable);
		Dimension d = categoryTable.getPreferredSize();
		categoryTablePane.setPreferredSize(new Dimension(d.width,categoryTable.getRowHeight()*4));
		 
		
		// END Category Table
		
		// START buttons
		JLabel curveLabel = new JLabel("Curve");
		JTextField curveField = new JTextField(10);
		// JButton curveApply = new JButton("Add Percent");
		JButton addCategoryButton = new JButton("Add Category");
		JButton removeCategoryButton = new JButton("Remove Category");
		JButton backButton = new JButton("Back");
		
		//END buttons
		
		
		// START Layout
		
		midPanel.add(gradeTablePane);
		
		categoryTablePanel.add(categoryTablePane);
		
		addRemoveCategoryPanel.add(addCategoryButton);
		addRemoveCategoryPanel.add(removeCategoryButton);
		backButtonPanel.add(backButton);
		
		// categoryTablePanel.add(addRemoveCategoryPanel);
		
		topPanel.add(categoryTablePanel);		
		
		curvePanel.add(curveLabel);
		curvePanel.add(curveField);
		
		// buttonPanel.add(curvePanel,BorderLayout.WEST);
		buttonPanel.add(addRemoveCategoryPanel,BorderLayout.WEST);
		buttonPanel.add(backButtonPanel,BorderLayout.EAST);
		
		mainPanel.add(topPanel);
		mainPanel.add(midPanel);
		mainPanel.add(buttonPanel);
		
		// END Layout 
		
		mainframe.add(mainPanel);
		mainframe.validate();
		mainframe.repaint();
		
		// START Action Listeners
		ActionListener backListener = new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   mainframe.remove(mainPanel);
			   mainframe.setTitle(data.getLoadedClass());
			   MainWindow m = new MainWindow(mainframe,data);
			   }
			};
		backButton.addActionListener(backListener);
		
				TableModelListener categoryTableListener = new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				System.out.println(e.getType());
				if(e.getType() == 0) {

					int row = categoryTable.getSelectedRow();
					int column = categoryTable.getSelectedColumn();
					String categoryType = categoryTable.getColumnName(column);
					Integer tableValue = Integer.parseInt(categoryTable.getValueAt(row, column).toString());
					Category gt = data.getCategoryByName(categoryType);
					if (row == 1) {
						gt.setGraduateWeight(tableValue);
						data.addSaveCommand(CategoryService.updateGradWeight(gt, tableValue));
						
					} else {
						gt.setUndergradWeight(tableValue);
						data.addSaveCommand(CategoryService.updateUgradWeight(gt, tableValue));
					}
					categoryTableModel.removeTableModelListener(this);
					categoryTableModel.setValueAt(String.valueOf(data.sumUndergradCategories()),0,1);
					categoryTableModel.setValueAt(String.valueOf(data.sumGradCategories()),1,1);
					// categoryTableModel.fireTableDataChanged();
					categoryTableModel.addTableModelListener(this);
					
					// categoryTable.repaint();
				}
		  }
		};
		categoryTableModel.addTableModelListener(categoryTableListener);
		
		ActionListener addCategoryListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NewCategoryDialog ncd = new NewCategoryDialog(data);
				ncd.setModal(true);
				ncd.showDialog();
				ArrayList<Category> addedCategories = ncd.getCategories();
				for (int i=0;i<addedCategories.size(); i++) {
					// categoryTableModel.addRow(new String[]{addedCategories.get(i).getType(),
														// String.valueOf(addedCategories.get(i).getWeight("Graduate")),
														// String.valueOf(addedCategories.get(i).getWeight("Undergraduate"))});
				categoryTableModel.removeTableModelListener(categoryTableListener);
				categoryTableModel.addColumn(addedCategories.get(i).getType());
				categoryTableModel.setValueAt(String.valueOf(addedCategories.get(i).getWeight("Undergraduate")),0,categoryTableModel.getColumnCount()-1);
				categoryTableModel.setValueAt(String.valueOf(addedCategories.get(i).getWeight("Graduate")),1,categoryTableModel.getColumnCount()-1);
				for (int j=0; j<categoryColumnModel.getColumnCount(); j++) {
					TableColumn column = categoryTable.getColumnModel().getColumn(j);
					if (j==0){
						column.setPreferredWidth(125);
					}else{
						column.setPreferredWidth(75);
					}
				}
				categoryTableModel.addTableModelListener(categoryTableListener);
				
				gradeTableModel.addColumn(addedCategories.get(i).getType());
				for(int j=0; j<data.nStudents(); j++){
				gradeTableModel.setValueAt("0%",i,gradeTableModel.getColumnCount()-1);
				}
				
				for (int j=0; j<gradeColumnModel.getColumnCount(); j++) {
					TableColumn column = gradeTable.getColumnModel().getColumn(j);
					if (j==0){
						column.setPreferredWidth(125);
					}else{
						column.setPreferredWidth(75);
					}
				}
				
				}
				categoryTable.repaint();
			   }
			};
		addCategoryButton.addActionListener(addCategoryListener);
		
		ActionListener removeCategoryListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
			    String gt = (String)categoryTable.getColumnName(categoryTable.getSelectedColumn());
				data.addSaveCommand(CategoryService.drop(gt));
				data.removeCategory(gt);
				gradeTable.removeColumn(gradeTable.getColumnModel().getColumn(categoryTable.getSelectedColumn()));
				categoryTable.removeColumn(categoryTable.getColumnModel().getColumn(categoryTable.getSelectedColumn()));
				
			   }
			};
		removeCategoryButton.addActionListener(removeCategoryListener);
		
		MouseAdapter TableHeaderMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point clickPoint = e.getPoint();
				int column = gradeTable.columnAtPoint(clickPoint);
				if(column>1){
					TableColumnModel columnModel = gradeTable.getColumnModel();
					
					String category = gradeTable.getColumnName(column);
					int nColumns = gradeTable.getColumnCount();
					
					TableColumnModel breakoutModel = breakoutTable.getColumnModel();

					breakoutTableModel.setColumnCount(0);
					breakoutTableModel.setRowCount(0);
					
					// breakoutTableModel.addColumn("Student");
					int nInCategory = 0;
					ArrayList<Gradable> breakoutGradables = new ArrayList<Gradable>();
					for (int i=0; i< data.nGradables(); i++) {
						if (data.getGradable(i).isType(category)) {
							breakoutTableModel.addColumn(data.getGradable(i).getName());
							breakoutGradables.add(data.getGradable(i));
							nInCategory += 1;
							// breakoutTable.getColumnModel().getColumn(0).setMinWidth(500);
						}
					}
					
					for (int i=0; i<data.nStudents(); i++){
						Student stmp = data.getStudent(i);
						
						
						String[] rowValues = new String[nInCategory+1];
						// rowValues[0] = stmp.getName();
						for (int j=0; j<nInCategory; j++) {
							Gradable gtmp = stmp.getGradable(breakoutGradables.get(j).getName());
							rowValues[j] = String.valueOf((gtmp.getPoints() - gtmp.getPointsLost())*100/(gtmp.getPoints()))+"%";
						}
						
						breakoutTableModel.addRow(rowValues);
					}
					
					TableColumnModel breakoutColumnModel = breakoutTable.getColumnModel();
					for (int i=0; i<breakoutColumnModel.getColumnCount(); i++) {
						breakoutColumnModel.getColumn(i).setPreferredWidth(75);;
					}
					Dimension d = breakoutTable.getPreferredSize();
					breakoutTablePane.setPreferredSize(new Dimension(Math.min(60*(breakoutColumnModel.getColumnCount()),200),d.height));
					
					midPanel.add(breakoutTablePane);
		
				}else{
					midPanel.remove(breakoutTablePane);
				}
				mainframe.revalidate();
				mainframe.repaint();
			}
		};
		
		JTableHeader gradeHeader = gradeTable.getTableHeader();
		gradeHeader.addMouseListener(TableHeaderMouseListener);

		

		
		// END Action Listeners 
	}

} 
