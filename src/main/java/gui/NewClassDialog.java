package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import db.ClassService;
import db.Globals;
import objects.Data;


public class NewClassDialog extends JDialog{
		
		private String[] classList;
		private boolean res;

		private void intitalClassList(){
			java.util.List<String>classNames = ClassService.getAllClassNames();
			classNames.add(0,"Empty Class");
			classList = new String[classNames.size()];
			int count = 0;
			for (String str : classNames) {
				classList[count++] = str;
			}
		}

		public NewClassDialog(final Data data) {
			res = false;
			intitalClassList();
			this.setTitle("Create Class");
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
			final JTextField classNameTextField = new JTextField(10);
			JLabel useTemplateLabel = new JLabel("Use Template");
			final JComboBox<String> useTemplateCombo = new JComboBox<String>(classList);
			JButton createButton = new JButton("Create");
			
			classNamePanel.add(classNameLabel);
			classNamePanel.add(classNameTextField);
			
			useTemplatePanel.add(useTemplateLabel);
			useTemplatePanel.add(useTemplateCombo);
			
			mainPanel.add(classNamePanel);
			mainPanel.add(useTemplatePanel);
			mainPanel.add(createButton);
			
			this.add(mainPanel);
			
			ActionListener createListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (((String)useTemplateCombo.getSelectedItem()).equals("Empty Class")) {
						ClassService.insertClass(classNameTextField.getText());
						data.setLoadedClass(classNameTextField.getText());
					// close box
					} else {
						System.out.println((String)useTemplateCombo.getSelectedItem());
						// instantiate new Data using other class
						// copy assignments over
						Data copyClass = new Data((String)useTemplateCombo.getSelectedItem());
						ClassService.insertClass(classNameTextField.getText());
						data.setLoadedClass(classNameTextField.getText());
						data.clone(copyClass);
					}			
					res = true;
					setVisible(false);
				}
			};
			
			createButton.addActionListener(createListener);
		}
		
		public void showDialog() {
			this.setVisible( true );
		}
		
		public boolean returned() {
			return res;
		}
	
}