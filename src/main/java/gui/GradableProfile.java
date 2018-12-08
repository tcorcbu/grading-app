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
		
		// START Menu toolbar
			mainframe.setJMenuBar(null);
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.getAccessibleContext().setAccessibleDescription("File Menu");
			menuBar.add(menu);
			
			JMenuItem menuItem_save = new JMenuItem("Save Class");
			menu.add(menuItem_save);
			
			JMenuItem menuItem_load = new JMenuItem("Load Class");
			menu.add(menuItem_load);
			
			JMenuItem menuItem_new = new JMenuItem("New Class");
			menu.add(menuItem_new);
			
			JMenuItem menuItem_close = new JMenuItem("Close Class");
			menu.add(menuItem_close);
			
			JMenuItem menuItem_exit = new JMenuItem("Exit");
			menu.add(menuItem_exit);
			
			mainframe.setJMenuBar(menuBar);
		// END Menu Toolbar
			
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
		
		JPanel backButtonPanel = new JPanel();
		JButton backButton = new JButton("Back");
		backButtonPanel.add(backButton);
		
		// START Layout 				
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		botPanel.add(backButtonPanel,BorderLayout.EAST);

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
		
		
		// ActionListener pointsListener = new ActionListener(){
		   // public void actionPerformed(ActionEvent e){
			   // int p = ((Number)gradablePoints.getValue()).intValue();
			   // g.setPoints(p);
			   // data.addSaveCommand(GradableService.updatePoints(g,p));
			   // }
			// };
		// gradablePoints.addActionListener(pointsListener);
		
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
		
		// ActionListener weightListener = new ActionListener(){
		   // public void actionPerformed(ActionEvent e){
			   // int w = ((Number)gradableWeight.getValue()).intValue();
			   // g.setIntraCategoryWeight(w);
			   // data.addSaveCommand(GradableService.updateWeight(g,w));
			   // }
			// };
		// gradableWeight.addActionListener(weightListener);
			
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
				String studentName = studentTable.getValueAt(row,0).toString();
				
				Student s = data.getStudent(row);
				Gradable gradable = s.getGradable(g.getName());
				switch(column) {
				case 1:
					Integer tablePoints = Integer.parseInt(studentTable.getValueAt(row, column).toString());
					gradable.setPointsLost(tablePoints);
					data.addSaveCommand(GradeService.updatePointsLost(gradable, s.getSchoolID(),tablePoints));
					break;
				case 2:
					String tableNote = studentTable.getValueAt(row,column).toString();
					gradable.setNote(tableNote);
					data.addSaveCommand(GradeService.updateComment(gradable,s.getSchoolID(),tableNote));
					break;
				}	
		  }
		});
		
		
		// END Action Listeners
			
			ActionListener menuItem_saveListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					data.saveClass();
				}
			};
			menuItem_save.addActionListener(menuItem_saveListener);
			
			ActionListener menuItem_loadListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainframe.remove(mainPanel);
					
					// load select class panel
					SelectClass s = new SelectClass(mainframe);
				}
			};
			menuItem_load.addActionListener(menuItem_loadListener);
			
			ActionListener menuItem_newListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Data data = new Data();
					NewClassDialog ncd = new NewClassDialog(data);
					ncd.setModal(true);
					ncd.showDialog();
					
					mainframe.remove(mainPanel);
					
					int width = 750;
					int height = 500;

					int x = (int) mainframe.getLocation().x - ((width - mainframe.getWidth()) / 2);
					int y = (int) mainframe.getLocation().y - ((height - mainframe.getHeight()) / 2);

					mainframe.setLocation(x, y);
					mainframe.setSize( width, height );

					mainframe.setTitle(data.getLoadedClass());
					MainWindow m = new MainWindow(mainframe,data);
				}
			};
			menuItem_new.addActionListener(menuItem_newListener);
			
			ActionListener menuItem_closeListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Close Class");
					// add dialog
					// ask for class name
					// if the class name matches to the current class,
					// set the class status in the db to closed
					CloseClassDialog ccd = new CloseClassDialog(data);
					ccd.setModal(true);
					ccd.showDialog();
				}
			};
			menuItem_close.addActionListener(menuItem_closeListener);
			
			ActionListener exitListener = new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   System.exit(0);
					   }
					};
			menuItem_exit.addActionListener(exitListener);
		
	}
	
} 
