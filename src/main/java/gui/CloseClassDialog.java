package gui;

import db.ClassService;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;



public class CloseClassDialog extends JDialog{

		public CloseClassDialog(final Data data) {
			
			this.setTitle("Close Class");
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize( 275, 150 );
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
			this.setLocation(x, y);
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			
			JPanel classNamePanel = new JPanel();

			JLabel classNameLabel = new JLabel("Are you sure you want to close this class?");
			final JTextField classNameTextField = new JTextField(10);

			JButton createButton = new JButton("Close Class");
			
			classNamePanel.add(classNameLabel);
			
			mainPanel.add(classNamePanel);
			mainPanel.add(createButton);
			
			this.add(mainPanel);
			// this.setVisible( true );
			
			ActionListener createListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){

					// close class in db
					data.closeClass();
					
					setVisible(false);

				}
			};
			
			createButton.addActionListener(createListener);
		}
		
		public void showDialog() {
			this.setVisible( true );
		}
	
		
	
}