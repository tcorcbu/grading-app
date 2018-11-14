package gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;


public class NewStudentDialog extends JDialog{
	private Student newStudent;
	private ArrayList<Student> newStudents = new ArrayList<Student>();
				
	public NewStudentDialog(final Data data) {
		
		this.setTitle("New Student");
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize( 400, 250 );
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
			
		
		// START Setup Layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(4,2));
		
		JPanel fnameInputPanel = new JPanel();
		JPanel lnameInputPanel = new JPanel();
		JPanel sidInputPanel = new JPanel();
		JPanel yearInputPanel = new JPanel();
		
		JPanel addPanel = new JPanel();
		JPanel botPanel = new JPanel();
		
		
		// END setup layout
		Object[] yearOptions = data.getStudentTypes().toArray();
		
		final JTextField fnameTextField = new JTextField(10);
		final JTextField lnameTextField = new JTextField(10);
		final JTextField sidTextField = new JTextField(10);
		final JComboBox<Object> yearCombo = new JComboBox<Object>(yearOptions);
		
		fnameInputPanel.add(fnameTextField);
		lnameInputPanel.add(lnameTextField);
		sidInputPanel.add(sidTextField);
		yearInputPanel.add(yearCombo);
		
		gridPanel.add(new JLabel("First Name"));
		gridPanel.add(fnameInputPanel);
		gridPanel.add(new JLabel("Last Name"));
		gridPanel.add(lnameInputPanel);
		gridPanel.add(new JLabel("School ID"));
		gridPanel.add(sidInputPanel);
		gridPanel.add(new JLabel("Year"));
		gridPanel.add(yearInputPanel);
		
		JButton addButton = new JButton("Add");
		JButton addAndClose = new JButton("Add and Close");
		JButton closeButton = new JButton("Close");
		addPanel.add(addButton);
		addPanel.add(addAndClose);
		botPanel.add(addPanel);
		botPanel.add(closeButton);
		
		mainPanel.add(gridPanel);
		mainPanel.add(botPanel);
		this.add(mainPanel);
		
		ActionListener addButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String firstName = fnameTextField.getText();
				String lastName = lnameTextField.getText();
				String schoolID = sidTextField.getText();
				String year = (String)yearCombo.getSelectedItem();
				
				// Check student ID against the database and error if there is a conflict
				newStudent = new Student(firstName,lastName,schoolID,year);
				data.addStudent(newStudent);
				newStudents.add(newStudent);
				
				fnameTextField.setText("");
				lnameTextField.setText("");
				sidTextField.setText("");
			}
		};
		addButton.addActionListener(addButtonListener);
		
		ActionListener addAndCloseButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String firstName = fnameTextField.getText();
				String lastName = lnameTextField.getText();
				String schoolID = sidTextField.getText();
				String year = (String)yearCombo.getSelectedItem();
				
				// Check student ID against the database and error if there is a conflict
				newStudent = new Student(firstName,lastName,schoolID,year);
				data.addStudent(newStudent);
				newStudents.add(newStudent);
				setVisible(false);
			}
		};
		addAndClose.addActionListener(addAndCloseButtonListener);
		
		ActionListener CloseButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
		closeButton.addActionListener(CloseButtonListener);
		
	}
	
	public void showDialog() {
		this.setVisible( true );
	}
	
	public ArrayList<Student> getStudents() {
		return newStudents;
	}
	
}