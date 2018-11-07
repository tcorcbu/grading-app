import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;

public class StudentProfile {
		
		public StudentProfile(JFrame mainframe,Data data, Student s) {
			drawStudentProfile(mainframe,data,s);
		}
	
	private void drawStudentProfile(JFrame mainframe,Data data,Student s) {
		System.out.println("StudentProfile to do list:");
		System.out.println("> fix layout of table (need scroll bars, perhaps span whole screen)");
		System.out.println("> Consider making the fields mutable");
		System.out.println("> Fix or remove the class profile link in the file menu");
		System.out.println("> Make the Gradable column immutable");
		System.out.println("> Add saving functionality to the other columns");
		System.out.println();
		
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
		
		DefaultTableModel gradableModel = new DefaultTableModel(); 
		JTable gradableTable = new JTable(gradableModel); 

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
					break;
				case 1:
					column.setMaxWidth(200);
					column.setPreferredWidth(80);
					break;
				case 2:
					column.setMaxWidth(200);
					column.setPreferredWidth(50);
					break;
				default:
					break;
			}
		}

		for (int i=0; i<data.nGradables(); i++){
			Gradable g = s.getGradable(i);
			gradableModel.addRow(new Object[]{g.getName(),g.getPointsLost(),g.getWeight(),g.getNote()});
		}

		JScrollPane gradableTablePane = new JScrollPane(gradableTable);
		// END Gradable Table
		
		JButton backButton = new JButton("Back");
		
		
		// START Layout 
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		mainPanel.add(infoPanel);
		mainPanel.add(Box.createHorizontalStrut(50));
		mainPanel.add(gradableTablePane);

		JPanel gradePanel = new JPanel();
		gradePanel.setLayout(new GridLayout(0,2));
		gradePanel.add(new JLabel("Current Grade: "));
		gradePanel.add(new JLabel("A"));
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.LINE_AXIS));
		backButton.setAlignmentX(botPanel.RIGHT_ALIGNMENT);
		gradePanel.setAlignmentX(botPanel.LEFT_ALIGNMENT);
		// botPanel.setLayout(new GridLayout(0,2));
		botPanel.add(gradePanel);
		botPanel.add(backButton);
		
		// mainPanel.add(upperPanel);
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
		// END Action Listeners
		
	}
	
} 