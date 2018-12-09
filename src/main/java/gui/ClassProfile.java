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
import java.text.*;

import db.CategoryService;

public class ClassProfile {

	public static void drawClassProfile(final JFrame mainframe,final Data data) {
		mainframe.setTitle(data.getLoadedClass() + " Profile");
		MainWindow.drawMenuBar(mainframe,data);
		
		// START Panel Setup
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel statsPanel = new JPanel();
		
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
		
		// END Panel Setup
		
		JLabel meanLabel = new JLabel("  Mean: "+String.valueOf(data.getClassMean())+"%  ");
		JLabel medianLabel = new JLabel("  Median: "+String.valueOf(data.getClassMedian())+"%  ");
		JLabel StandardDeviationLabel = new JLabel("  Standard Deviation: "+String.valueOf(data.getClassStandardDeviation())+"%  ");
		
		JLabel curveLabel = new JLabel("Curve");
		NumberFormat curveFormat;
		curveFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField curveField = new JFormattedTextField(curveFormat);
		curveField.setValue(data.getCurve());
		curveField.setColumns(5);
		
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
		Object[] gradeTableRow = new Object[data.getCategoryList().size()+2];
		
		for (int i=0; i<data.nStudents(); i++){
			Student stmp = data.getStudent(i);
			gradeTableRow[0] = stmp;
			gradeTableRow[1] = String.valueOf(stmp.getOverallPercent(data.getCategoryList())+data.getCurve()) + "%";
			for (int j=2; j<gradeTableRow.length; j++) {
				if(stmp.hasCategoryNote(data.CategoryList(j-2))) {
					gradeTableRow[j] = String.valueOf(stmp.getCategoryAverage(data.CategoryList(j-2).getType()))+"%*";
				}else{
					gradeTableRow[j] = String.valueOf(stmp.getCategoryAverage(data.CategoryList(j-2).getType()))+"%";
				}
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
		
		final JTable breakoutTable = new JTable(breakoutTableModel) {
			public String getToolTipText(MouseEvent e) {
				ToolTipManager.sharedInstance().setInitialDelay(0);
                String tip = null;
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());

				Student student = (Student)gradeTable.getValueAt(row,0);
				String gradableName = getColumnName(column);
				Grade gtmp = student.getGrade(gradableName);
				
				if(gtmp.getNote() != null && gtmp.getNote().length() > 0) {
					tip = student.getName() + ": " + gtmp.getNote();
				}
				
                return tip;
            }
		};
		breakoutTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane breakoutTablePane = new JScrollPane(breakoutTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		BoundedRangeModel gradeTablePaneModel = gradeTablePane.getVerticalScrollBar().getModel();
		breakoutTablePane.getVerticalScrollBar().setModel( gradeTablePaneModel );
		
		// END Breakout Table
		
		// START Category Table
		final DefaultTableModel categoryTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex < 2){
					return false;
				} else {
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

		final JTableHeader categoryTableHeader = categoryTable.getTableHeader();
		final TableColumnModel categoryTableColumnModel = categoryTableHeader.getColumnModel();
		
		JScrollPane categoryTablePane = new JScrollPane(categoryTable);
		Dimension d = categoryTable.getPreferredSize();
		categoryTablePane.setPreferredSize(new Dimension(d.width,categoryTable.getRowHeight()*4+3));

		// END Category Table
		
		// START buttons
		
		JButton addCategoryButton = new JButton("Add Category");
		JButton removeCategoryButton = new JButton("Remove Category");
		JButton backButton = new JButton("Home");
		
		//END buttons
		
		
		// START Layout
		
		topPanel.add(categoryTablePanel);
		
		midPanel.add(gradeTablePane);
		
		categoryTablePanel.add(categoryTablePane);
		
		addRemoveCategoryPanel.add(addCategoryButton);
		addRemoveCategoryPanel.add(removeCategoryButton);
		
		
		backButtonPanel.add(backButton);
		
		// categoryTablePanel.add(addRemoveCategoryPanel);
		
		curvePanel.add(curveLabel);
		curvePanel.add(curveField);
		
		statsPanel.add(meanLabel);
		statsPanel.add(medianLabel);
		statsPanel.add(StandardDeviationLabel);
		statsPanel.add(curvePanel);
		
		buttonPanel.add(addRemoveCategoryPanel,BorderLayout.WEST);
		buttonPanel.add(backButtonPanel,BorderLayout.EAST);
		
		mainPanel.add(statsPanel);
		mainPanel.add(topPanel);
		mainPanel.add(midPanel);
		mainPanel.add(buttonPanel);
		
		// END Layout 
		
		mainframe.add(mainPanel);
		mainframe.validate();
		mainframe.repaint();
		
		// START Action Listeners
		backButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
				mainframe.remove(mainPanel);
				MainWindow.drawMainWindow(mainframe,data);
			}
		});
		
		TableModelListener categoryTableListener = new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
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
					categoryTableModel.addTableModelListener(this);
				}
			}
		};
		categoryTableModel.addTableModelListener(categoryTableListener);
		
		addCategoryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NewCategoryDialog ncd = new NewCategoryDialog(data);
				ncd.setModal(true);
				ncd.showDialog();
				ArrayList<Category> addedCategories = ncd.getCategories();
				categoryTableModel.removeTableModelListener(categoryTableListener);
				for (int i=0;i<addedCategories.size(); i++) {
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

					categoryTableModel.setValueAt(String.valueOf(data.sumUndergradCategories()),0,1);
					categoryTableModel.setValueAt(String.valueOf(data.sumGradCategories()),1,1);
					
					gradeTableModel.addColumn(addedCategories.get(i).getType());
					for(int j=0; j<data.nStudents(); j++){
						gradeTableModel.setValueAt("0%",j,gradeTableModel.getColumnCount()-1);
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
				categoryTableModel.addTableModelListener(categoryTableListener);
				categoryTable.repaint();
			   }
			});
		
		removeCategoryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			    String gt = (String)categoryTable.getColumnName(categoryTable.getSelectedColumn());
				data.dropCategory(gt);
				gradeTable.removeColumn(gradeTable.getColumnModel().getColumn(categoryTable.getSelectedColumn()));
				categoryTable.removeColumn(categoryTable.getColumnModel().getColumn(categoryTable.getSelectedColumn()));
				
				categoryTableModel.removeTableModelListener(categoryTableListener);
				categoryTableModel.setValueAt(String.valueOf(data.sumUndergradCategories()),0,1);
				categoryTableModel.setValueAt(String.valueOf(data.sumGradCategories()),1,1);
				categoryTableModel.addTableModelListener(categoryTableListener);
			}
		});
		
		MouseAdapter gradeTableBreakoutListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int column = gradeTable.columnAtPoint(e.getPoint());
				if(column>1){
					TableColumnModel columnModel = gradeTable.getColumnModel();
					
					String category = gradeTable.getColumnName(column);
					int nColumns = gradeTable.getColumnCount();
					
					breakoutTableModel.setColumnCount(0);
					breakoutTableModel.setRowCount(0);
					
					int nInCategory = 0;
					ArrayList<Gradable> breakoutGradables = new ArrayList<Gradable>();
					for (int i=0; i< data.nGradables(); i++) {
						if (data.getGradable(i).isType(category)) {
							breakoutTableModel.addColumn(data.getGradable(i));
							breakoutGradables.add(data.getGradable(i));
							nInCategory += 1;
						}
					}
					
					for (int i=0; i<data.nStudents(); i++){
						Student stmp = data.getStudent(i);

						Object[] rowValues = new Object[nInCategory+1];
						for (int j=0; j<nInCategory; j++) {
							Grade gtmp = stmp.getGrade(breakoutGradables.get(j).getName());
							if(gtmp.hasNote()){
								rowValues[j] = String.valueOf((gtmp.getPoints() - gtmp.getPointsLost())*100/(gtmp.getPoints()))+"%*";
							} else {
								rowValues[j] = String.valueOf((gtmp.getPoints() - gtmp.getPointsLost())*100/(gtmp.getPoints()))+"%";
							}
							
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
		gradeHeader.addMouseListener(gradeTableBreakoutListener);
		gradeTable.addMouseListener(gradeTableBreakoutListener);
		
		JTableHeader breakoutHeader = breakoutTable.getTableHeader();
		breakoutHeader.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int column = breakoutTable.columnAtPoint(e.getPoint());
					String gradableName = breakoutTable.getColumnName(column);
					mainframe.remove(mainPanel);
					GradableProfile.drawGradableProfile(mainframe,data,data.getGradable(gradableName));
				}
			}
		});
		
		curveField.getDocument().addDocumentListener(new DocumentListener() {
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
				int c = ((Number)curveField.getValue()).intValue();
				data.setCurve(c);
				meanLabel.setText("  Mean: "+String.valueOf(data.getClassMean())+"%  ");
				medianLabel.setText("  Median: "+String.valueOf(data.getClassMedian())+"%  ");
				StandardDeviationLabel.setText("  Standard Deviation: "+String.valueOf(data.getClassStandardDeviation())+"%  ");
				
				for (int i=0; i<data.nStudents(); i++){
					Student stmp = data.getStudent(i);
					gradeTableModel.setValueAt(String.valueOf(stmp.getOverallPercent(data.getCategoryList())+data.getCurve()) + "%",i,1);
				}
				
			}
		});
		
		curveField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int c = ((Number)curveField.getValue()).intValue();
				data.setCurve(c);
				meanLabel.setText("  Mean: "+String.valueOf(data.getClassMean())+"%  ");
				medianLabel.setText("  Median: "+String.valueOf(data.getClassMedian())+"%  ");
				StandardDeviationLabel.setText("  Standard Deviation: "+String.valueOf(data.getClassStandardDeviation())+"%  ");
				
				for (int i=0; i<data.nStudents(); i++){
					Student stmp = data.getStudent(i);
					gradeTableModel.setValueAt(String.valueOf(stmp.getOverallPercent(data.getCategoryList())+data.getCurve()) + "%",i,1);
				}
			}
		});
		
		gradeTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = gradeTable.getSelectedRow();
				
				if (e.getClickCount() == 2) {
					Student student = (Student)gradeTable.getValueAt(row,0);
					mainframe.remove(mainPanel);
					StudentProfile.drawStudentProfile(mainframe,data,student);
				}
			}
		});
		// END Action Listeners 
	}
} 
