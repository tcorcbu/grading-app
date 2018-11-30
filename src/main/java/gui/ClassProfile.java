package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.Arrays;
import java.util.ArrayList;

import db.GradableTypeService;
import db.CategoryService;

public class ClassProfile {
		
	public ClassProfile(final JFrame mainframe,final Data data) {

		mainframe.setTitle(data.getLoadedClass() + " Profile");
		
		// START Panel Setup
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0,2));
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(0,2));
		
		JPanel categoryTablePanel = new JPanel();
		categoryTablePanel.setLayout(new BoxLayout(categoryTablePanel, BoxLayout.Y_AXIS));
		
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
		
		// Add columns
		gradeTableModel.addColumn("Student"); 
		for(int i = 0; i<data.gradableTypes().size(); i++) {
			gradeTableModel.addColumn(data.gradableTypes(i).getType());
		}
		
		// Add rows
		String[] gradeTableRow = new String[data.gradableTypes().size()+1];
		
		for (int i=0; i<data.nStudents(); i++){
			Student stmp = data.getStudent(i);
			gradeTableRow[0] = stmp.getFirstName()+" "+stmp.getLastName();
			for (int j=1; j<gradeTableRow.length; j++) {
				gradeTableRow[j] = String.valueOf(stmp.getCategoryAverage(data.gradableTypes(j-1).getType()))+"%";
			}
			gradeTableModel.addRow(gradeTableRow);
		}

		JScrollPane gradeTablePane = new JScrollPane(gradeTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// END Grade Table
		
		// START Breakout Table
		
		final myTableModel breakoutTableModel = new myTableModel();
		final JTable breakoutTable = new JTable(breakoutTableModel);
		breakoutTable.setFocusable(false);
		
		
		JScrollPane breakoutTablePane = new JScrollPane(breakoutTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// END Breakout Table
		
		// START Category Table
		final DefaultTableModel categoryTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == 0){
				return false;
				} 
				else {
					return true;
					}
			}
		};
		
		categoryTableModel.addColumn("Category");
		categoryTableModel.addColumn("Graduate Weight (%)");
		categoryTableModel.addColumn("Undergrad Weight (%)");
		
		for (int i=0; i<data.gradableTypes().size(); i++) {
			categoryTableModel.addRow(new String[]{data.gradableTypes(i).getType(),
										String.valueOf(data.gradableTypes(i).getWeight("Graduate")),
										String.valueOf(data.gradableTypes(i).getWeight("Undergraduate"))});
		}
		
		
		final JTable categoryTable = new JTable(categoryTableModel);
		
		JScrollPane categoryTablePane = new JScrollPane(categoryTable);
		// END Category Table
		
		gradeBreakdownPanel.add(new JLabel("A"));
		gradeBreakdownPanel.add(new JLabel("10"));
		gradeBreakdownPanel.add(new JLabel("B"));
		gradeBreakdownPanel.add(new JLabel("9"));
		gradeBreakdownPanel.add(new JLabel("C"));
		gradeBreakdownPanel.add(new JLabel("8"));
		gradeBreakdownPanel.add(new JLabel("D"));
		gradeBreakdownPanel.add(new JLabel("6"));
		gradeBreakdownPanel.add(new JLabel("F"));
		gradeBreakdownPanel.add(new JLabel("1"));
		
		
		// START buttons
		JLabel curveLabel = new JLabel("Curve");
		JTextField curveField = new JTextField(10);
		// JButton curveApply = new JButton("Add Percent");
		JButton addCategoryButton = new JButton("Add Category");
		JButton removeCategoryButton = new JButton("Remove Category");
		JButton backButton = new JButton("Back");
		
		//END buttons
		
		
		// START Layout
		
		topPanel.add(gradeTablePane);
		topPanel.add(breakoutTablePane);

		categoryTablePanel.add(categoryTablePane);
		
		addRemoveCategoryPanel.add(addCategoryButton);
		addRemoveCategoryPanel.add(removeCategoryButton);
		
		categoryTablePanel.add(addRemoveCategoryPanel);
		
		middlePanel.add(categoryTablePanel);		
		middlePanel.add(gradeBreakdownOuterPanel);		
		
		curvePanel.add(curveLabel);
		curvePanel.add(curveField);
		
		buttonPanel.add(curvePanel,BorderLayout.WEST);
		buttonPanel.add(backButton,BorderLayout.EAST);
		
		mainPanel.add(topPanel);
		mainPanel.add(middlePanel);
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
		
		ActionListener addCategoryListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NewCategoryDialog ncd = new NewCategoryDialog(data);
				ncd.setModal(true);
				ncd.showDialog();
				ArrayList<GradableType> addedGradableTypes = ncd.getGradableTypes();
				for (int i=0;i<addedGradableTypes.size(); i++) {
					GradableTypeService.insertGradableType(addedGradableTypes.get(i),data.getClassId());
					categoryTableModel.addRow(new String[]{addedGradableTypes.get(i).getType(),
														String.valueOf(addedGradableTypes.get(i).getWeight("Graduate")),
														String.valueOf(addedGradableTypes.get(i).getWeight("Undergraduate"))});
				
				}
			   }
			};
		addCategoryButton.addActionListener(addCategoryListener);
		
		ActionListener removeCategoryListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
			    String gt = (String)categoryTable.getValueAt(categoryTable.getSelectedRow(),0);
				GradableTypeService.dropGradableType(gt,data.getClassId());
				data.removeGradableType(gt);
				categoryTableModel.removeRow(categoryTable.getSelectedRow());
			   }
			};
		removeCategoryButton.addActionListener(removeCategoryListener);
		
		MouseAdapter TableHeaderMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point clickPoint = e.getPoint();
				int column = gradeTable.columnAtPoint(clickPoint);
				TableColumnModel columnModel = gradeTable.getColumnModel();
				
				String category = gradeTable.getColumnName(column);
				int nColumns = gradeTable.getColumnCount();
				
				TableColumnModel breakoutModel = breakoutTable.getColumnModel();

				breakoutTableModel.setColumnCount(0);
				breakoutTableModel.setRowCount(0);
				
				breakoutTableModel.addColumn("Student");
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
					rowValues[0] = stmp.getName();
					for (int j=0; j<nInCategory; j++) {
						Gradable gtmp = stmp.getGradable(breakoutGradables.get(j).getName());
						rowValues[j+1] = String.valueOf((gtmp.getPoints() - gtmp.getPointsLost())*100/(gtmp.getPoints()))+"%";
					}
					
					breakoutTableModel.addRow(rowValues);
				}
			}
		};
		
		JTableHeader gradeHeader = gradeTable.getTableHeader();
		gradeHeader.addMouseListener(TableHeaderMouseListener);
		
		categoryTableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == 0) {
					int row = categoryTable.getSelectedRow();
					int column = categoryTable.getSelectedColumn();
					String categoryType = categoryTable.getValueAt(row,0).toString();
					Integer tableValue = Integer.parseInt(categoryTable.getValueAt(row, column).toString());
					GradableType gt = data.getGradableTypeByName(categoryType);
					if (column == 1) {
						gt.setGraduateWeight(tableValue);
						CategoryService.updateUgradWeight(gt,tableValue);
						
					} else {
						gt.setUndergradWeight(tableValue);
						CategoryService.updateGradWeight(gt,tableValue);
					}
				}
		  }
		});
		
		// END Action Listeners 
	}

} 
