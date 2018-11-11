import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class SelectClass {
		
		private String[] classList = {"Select a class","CS 591 Fall 2018","CS 591 Sprint 2017","CS 112 Fall 2018","CS 112 Spring 2018","New Class"};
		
		public SelectClass(JFrame mainframe) {
			drawSelectClass(mainframe);
		}
	
		
	private void drawSelectClass(JFrame mainframe) {
		System.out.println("SelectClass to do list:");
		System.out.println("> Need to load list of classes from database");
		System.out.println("> Need to write our own New Class window which allows loading from previous");
		System.out.println("    > Perhaps keep it in the same window and just change the 'Load Class' button");
		System.out.println();
		
		
		mainframe.setTitle("Select Class");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JLabel classLabel = new JLabel("Select a Class");
		JPanel classLabelPanel = new JPanel();
		classLabelPanel.add(classLabel);
		
		JComboBox<String> classCombo = new JComboBox<String>(classList);
		JPanel classComboPanel = new JPanel();
		classComboPanel.add(classCombo);
		
		// JButton jbtLoad = new JButton("Load Class");
		// jbtLoad.setAlignmentX(panel.CENTER_ALIGNMENT);		
		
		// JButton jbtNew = new JButton( "New Class" );
		// jbtNew.setAlignmentX(panel.CENTER_ALIGNMENT);

		// JButton newFromSelected = new JButton( "New From Selected" );
		// newFromSelected.setAlignmentX(panel.CENTER_ALIGNMENT);		
		
		// JPanel newButtonsPanel = new JPanel();
		// newButtonsPanel.add(jbtNew);
		// newButtonsPanel.add(newFromSelected);
		
		
		// JPanel comboPanel = new JPanel();
		// comboPanel.add(classLabel);
		// comboPanel.add(classCombo);
		
		
		mainPanel.add(classLabelPanel);
		mainPanel.add(Box.createHorizontalStrut(10));
		mainPanel.add(classComboPanel);
		// panel.add(Box.createHorizontalStrut(10));
		// panel.add(newButtonsPanel);
		// panel.add(Box.createHorizontalStrut(10));
		
		mainframe.add(mainPanel);
		mainframe.validate();
		mainframe.repaint();
		
		ActionListener loadListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String mySelection = (String)classCombo.getSelectedItem();
				if (mySelection.equals("Select a class")){
				} else {
					
					Data data;
					if (mySelection.equals("New Class")) {
						data = new Data();
						NewClassDialog ncd = new NewClassDialog(data);
						ncd.setModal(true);
						ncd.showDialog();
						
					} else {
						data = new Data(mySelection);
						}
					
					
					mainframe.remove(mainPanel);
					int width = 750;
					int height = 500;
					
					int x = (int) mainframe.getLocation().x - ((width - mainframe.getWidth()) / 2);
					int y = (int) mainframe.getLocation().y - ((500 - mainframe.getHeight()) / 2);
					
					mainframe.setLocation(x, y);
					mainframe.setSize( width, 500 );
					
					mainframe.setTitle(data.getLoadedClass());
					System.out.println(data.getGradables());
					MainWindow m = new MainWindow(mainframe,data);
				}
			    }
			};
		classCombo.addActionListener( loadListener );
		
				
		// ActionListener newListener = new ActionListener(){
			   // public void actionPerformed(ActionEvent e){
				   // String NewClassName = JOptionPane.showInputDialog("Please input the new class name ");
				   // classCombo.addItem(NewClassName);
				   // classCombo.setSelectedItem(NewClassName);
				   
				   				   // }
				// };
		// jbtNew.addActionListener( newListener );
		
		// ActionListener newFromSelectedListener = new ActionListener(){
			// public void actionPerformed(ActionEvent e){
				// System.out.println(mainframe.getLocation().x);
				   				   // }
				// };
		// newFromSelected.addActionListener( newFromSelectedListener );
		
	}
	
	
} 
