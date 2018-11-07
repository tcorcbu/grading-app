import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class SelectClass {
		
		private String[] classList = {"CS 591 Fall 2018","CS 591 Sprint 2017","CS 112 Fall 2018","CS 112 Spring 2018"};
		
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
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel classLabel = new JLabel("Select a Class");
		JComboBox<String> classCombo = new JComboBox<String>(classList);
		
		
		JButton jbtLoad = new JButton("Load Class");
		jbtLoad.setAlignmentX(panel.CENTER_ALIGNMENT);		
		
		JButton jbtNew = new JButton( "New Class" );
		jbtNew.setAlignmentX(panel.CENTER_ALIGNMENT);

		JButton newFromSelected = new JButton( "New From Selected" );
		newFromSelected.setAlignmentX(panel.CENTER_ALIGNMENT);		
		
		JPanel newButtonsPanel = new JPanel();
		newButtonsPanel.add(jbtNew);
		newButtonsPanel.add(newFromSelected);
		
		
		JPanel comboPanel = new JPanel();
		comboPanel.add(classLabel);
		comboPanel.add(classCombo);
		
		
		panel.add(comboPanel);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(jbtLoad);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(newButtonsPanel);
		panel.add(Box.createHorizontalStrut(10));
		
		mainframe.add(panel);
		mainframe.validate();
		mainframe.repaint();
		
		ActionListener loadListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainframe.remove(panel);
				int width = 750;
				int height = 500;
			    
				// Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) mainframe.getLocation().x - ((width - mainframe.getWidth()) / 2);
				int y = (int) mainframe.getLocation().y - ((500 - mainframe.getHeight()) / 2);
				
				mainframe.setLocation(x, y);
				mainframe.setSize( width, 500 );
				
				Data data = new Data((String)classCombo.getSelectedItem());
			    mainframe.setTitle(data.getLoadedClass());
			    MainWindow m = new MainWindow(mainframe,data);

			    }
			};
		jbtLoad.addActionListener( loadListener );
				
		ActionListener newListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   String NewClassName = JOptionPane.showInputDialog("Please input the new class name ");
				   classCombo.addItem(NewClassName);
				   classCombo.setSelectedItem(NewClassName);
				   // Write new class name to the database.
				   				   }
				};
		jbtNew.addActionListener( newListener );
		
		ActionListener newFromSelectedListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(mainframe.getLocation().x);
				   				   }
				};
		newFromSelected.addActionListener( newFromSelectedListener );
		
	}
	
	
} 
