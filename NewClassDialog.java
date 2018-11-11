import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;



public class NewClassDialog extends JDialog{
		
		private String[] classList = {"Empty Class","CS 591 Fall 2018","CS 591 Sprint 2017","CS 112 Fall 2018","CS 112 Spring 2018"};
		
		public NewClassDialog(Data data) {
			
			this.setTitle("Create Class");
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize( 275, 150 );
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
			this.setLocation(x, y);
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			
			JPanel classNamePanel = new JPanel();
			JPanel useTemplatePanel = new JPanel();

			JLabel classNameLabel = new JLabel("Class Name");
			JTextField classNameTextField = new JTextField(10);
			JLabel useTemplateLabel = new JLabel("Use Template");
			JComboBox<String> useTemplateCombo = new JComboBox<String>(classList);
			JButton createButton = new JButton("Create");
			
			classNamePanel.add(classNameLabel);
			classNamePanel.add(classNameTextField);
			
			useTemplatePanel.add(useTemplateLabel);
			useTemplatePanel.add(useTemplateCombo);
			
			mainPanel.add(classNamePanel);
			mainPanel.add(useTemplatePanel);
			mainPanel.add(createButton);
			
			this.add(mainPanel);
			// this.setVisible( true );
			
			ActionListener createListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (((String)useTemplateCombo.getSelectedItem()).equals("Empty Class")) {
					data.setLoadedClass(classNameTextField.getText());
					// close box
					} else {
						System.out.println((String)useTemplateCombo.getSelectedItem());
						// instantiate new Data using other class
						// copy assignments over
						Data copyClass = new Data((String)useTemplateCombo.getSelectedItem());
						data.setLoadedClass((String)classNameTextField.getText());
						data.clone(copyClass);
					}
					
					setVisible(false);
				}
			};
			
			createButton.addActionListener(createListener);
		}
		
		public void showDialog() {
			this.setVisible( true );
		}
	
		
	
}