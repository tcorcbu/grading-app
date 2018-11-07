import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;

public class GradableProfile {
		
	public GradableProfile(JFrame mainframe,Data data, Gradable g) {
		drawGradableProfile(mainframe,data,g);
	}
	
	private void drawGradableProfile(JFrame mainframe,Data data,Gradable g) {
		System.out.println("GradableProfile to do list:");
		System.out.println("> fix layout of table (need scroll bars, perhaps span whole screen)");
		System.out.println("> Add histogram if we figure out how");
		System.out.println("> Add functionality to Total Points");
		System.out.println("> Add functionality to Weight");
		System.out.println("> Fix or remove the class profile link in the file menu");
		System.out.println("> Make the Students column immutable");
		System.out.println("> Add saving functionality to the other columns");
		System.out.println();
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(5,2));
		
		infoPanel.add(new JLabel("Title: "));
		infoPanel.add(new JLabel(g.getName()));
		infoPanel.add(new JLabel("Type: "));
		infoPanel.add(new JLabel(g.getType()));
		infoPanel.add(new JLabel("Total Points: "));
		JTextField gradablePoints = new JTextField();
		gradablePoints.setText(String.valueOf(g.getPoints()));
		infoPanel.add(gradablePoints);
		infoPanel.add(new JLabel("Weight: "));
		JTextField gradableWeight = new JTextField();
		gradableWeight.setText(String.valueOf(g.getWeight()));
		infoPanel.add(gradableWeight);
		infoPanel.add(new JLabel("Average: "));
		infoPanel.add(new JLabel("90"));

		
		// START Gradable Table
		
		// String[] studentColumnNames = {"Student","Points Lost","Notes"};
		
		// Object[][] studentData = {
			
        // {"Blackjack", 8,20},
        // {"TreintaEna", 10,20},
        // {"Midterm 1", 9,30},
        // {"Grading System", 5,30}
		// };
		
		// JTable studentTable = new JTable(studentData, studentColumnNames);
		// JScrollPane studentTablePane = new JScrollPane(studentTable);
		
		DefaultTableModel studentModel = new DefaultTableModel(); 
		JTable studentTable = new JTable(studentModel); 

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

		JScrollPane studentTablePane = new JScrollPane(studentTable);
		
		// END Gradable Table
		
		JButton backButton = new JButton("Back");
		
		// START Layout 
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2,0));
		leftPanel.add(new JLabel(""));
		leftPanel.add(infoPanel);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,2));

		topPanel.add(leftPanel);
		topPanel.add(studentTablePane);
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new GridLayout(0,5));
		botPanel.add(new JLabel(""));
		botPanel.add(new JLabel(""));
		botPanel.add(new JLabel(""));
		botPanel.add(new JLabel(""));
		botPanel.add(backButton);
		
		mainPanel.add(topPanel);
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
