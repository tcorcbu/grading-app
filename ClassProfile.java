import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ClassProfile {
		
		private String[] classList = {"CS 591 Fall 2018","CS 591 Sprint 2017","CS 112 Fall 2018","CS 112 Spring 2018"};
		private String username = "";
		private String password = "";
		
		public ClassProfile(JFrame mainframe, Data data) {
			drawClassProfile(mainframe,data);
		}
		
	private void drawClassProfile(JFrame mainframe,Data data) {
		System.out.println("ClassProfile to do list:");
		System.out.println("> Fix layout");
		System.out.println("> Add functinality to addPercent");
		System.out.println("> Add in a histogram if we're feeling good");
		System.out.println();
		
		
		
		mainframe.setTitle(mainframe.getTitle() + " Profile");

		// START Menu toolbar
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.getAccessibleContext().setAccessibleDescription("File Menu");
			menuBar.add(menu);
			
			JMenuItem menuItem_save = new JMenuItem("Save Class");
			menu.add(menuItem_save);
			
			JMenuItem menuItem_load = new JMenuItem("Load Class");
			menu.add(menuItem_load);
			
			JMenuItem menuItem_exit = new JMenuItem("Exit");
			menu.add(menuItem_exit);
			

			mainframe.setJMenuBar(menuBar);
			// END Menu Toolbar
		
		// START Grade Table
		String[] gradeColumnNames = {"Grade","Number of Students"};
		
		Object[][] gradeData = {
        {"A", 8},
        {"B", 10},
        {"C", 9},
        {"D", 5},
        {"F", 1},
		{"Total",33}
		};
		
		final JTable gradeTable = new JTable(gradeData, gradeColumnNames);
		// gradeTable.setModel(new DefaultTableModel() {

			// @Override
			// public boolean isCellEditable(int row, int column) {
			   // return false;
			// }
		// });
		
		gradeTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
        // gradeTable.setFillsViewportHeight(true);
		JScrollPane gradeTablePane = new JScrollPane(gradeTable);
		// END Grade Table
		
		// START Category Table
		String[] categoryColumnNames = {"Category","Weight"};
		
		Object[][] categoryData = {
        {"Tests", 30},
        {"Homework", 35},
        {"Projects", 30},
        {"Participation", 5},
		{"Total", 100}
		};
		
		final JTable categoryTable = new JTable(categoryData, categoryColumnNames);
		
		categoryTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
        // gradeTable.setFillsViewportHeight(true);
		JScrollPane categoryTablePane = new JScrollPane(categoryTable);
		// END Grade Table
		
		// START buttons
		// JLabel curveLabel = new JLabel("Add Percent");
		JTextField curveField = new JTextField(10);
		JButton curveApply = new JButton("Add Percent");
		JButton backButton = new JButton("Back");
		// backButton.setMaximumSize(new Dimension(1, 1));
		
		//END buttons
		
		
		// START Layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		tablePanel.add(gradeTablePane);
		tablePanel.add(categoryTablePane);
		
		JPanel curvePanel = new JPanel();
		curvePanel.setLayout(new GridLayout(2,1,5,5));
		// curvePanel.add(curveLabel);
		curvePanel.add(curveField);
		curvePanel.add(curveApply);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2,5,5));
		buttonPanel.add(curvePanel);
		buttonPanel.add(backButton);
		
		mainPanel.add(tablePanel);
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
		// END Action Listeners 
	}
	
} 
