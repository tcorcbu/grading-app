package gui;

import db.ClassService;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;



public class CloseAppDialog extends JDialog{

		public CloseAppDialog(final Data data, final WindowEvent windowMain) {
			
			this.setTitle("Close Application");
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize( 275, 150 );
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
			this.setLocation(x, y);
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			
			JPanel classNamePanel = new JPanel();

			JLabel classNameLabel = new JLabel("Are you sure you want to close this application?");
			final JTextField classNameTextField = new JTextField(10);

			JButton closeButton = new JButton("Close App");

			JButton cancelButton = new JButton("Save and Close");
			
			classNamePanel.add(classNameLabel);
			
			mainPanel.add(classNamePanel);
			mainPanel.add(closeButton);
			mainPanel.add(cancelButton);
			
			this.add(mainPanel);
			// this.setVisible( true );
			
			ActionListener createListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){

					windowMain.getWindow().dispose();
					
					setVisible(false);

				}
			};
			
			closeButton.addActionListener(createListener);

			ActionListener cancelListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){

					data.saveClass();

					windowMain.getWindow().dispose();
					
					setVisible(false);

				}
			};
			
			cancelButton.addActionListener(cancelListener);
		}
		
		public void showDialog() {
			this.setVisible( true );
		}
	
		
	
}