package gui;

import db.CategoryService;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.text.*;


public class NewCategoryDialog extends JDialog{
	private Category newCategory;
	private ArrayList<Category> newCategories = new ArrayList<Category>();
	
	public NewCategoryDialog(final Data data) {
		
		this.setTitle("New Category");

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
		
		JPanel nameInputPanel = new JPanel();
		JPanel gWeightInputPanel = new JPanel();
		JPanel uWeightInputPanel = new JPanel();
		
		JPanel addPanel = new JPanel();
		JPanel botPanel = new JPanel();
		
		
		// END setup layout
		
		final JTextField nameTextField = new JTextField(10);
		NumberFormat gWeightFormat;
		gWeightFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField gWeightTextField = new JFormattedTextField(gWeightFormat);
		// gWeightTextField.setValue(new Integer(0));
		gWeightTextField.setColumns(10);

		NumberFormat uWeightFormat;
		uWeightFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField uWeightTextField = new JFormattedTextField(uWeightFormat);
		// uWeightTextField.setValue(new Integer(0));
		uWeightTextField.setColumns(10);
				
		nameInputPanel.add(nameTextField);
		gWeightInputPanel.add(gWeightTextField);
		uWeightInputPanel.add(uWeightTextField);
		
		gridPanel.add(new JLabel("Category Name"));
		gridPanel.add(nameInputPanel);
		gridPanel.add(new JLabel("Graduate Weight"));
		gridPanel.add(gWeightInputPanel);
		gridPanel.add(new JLabel("Undergraduate Weight"));
		gridPanel.add(uWeightInputPanel);
		
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
				String name = nameTextField.getText();
				Integer gWeight = ((Number)gWeightTextField.getValue()).intValue();
				Integer uWeight = ((Number)uWeightTextField.getValue()).intValue();
				
				// Check student ID against the database and error if there is a conflict
				newCategory = new Category(name,gWeight,uWeight);
				data.addSaveCommand(CategoryService.insert(newCategory));
				newCategories.add(newCategory);
				data.addCategory(newCategory);
				nameTextField.setText("");
				// gWeightTextField.setText("");
				// uWeightTextField.setText("");
			}
		};
		addButton.addActionListener(addButtonListener);
		
		ActionListener addAndCloseButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				Integer gWeight = ((Number)gWeightTextField.getValue()).intValue();
				Integer uWeight = ((Number)uWeightTextField.getValue()).intValue();
				// Check student ID against the database and error if there is a conflict
				newCategory = new Category(name,gWeight,uWeight);
				data.addSaveCommand(CategoryService.insert(newCategory));
				// data.getCategories();
				data.addCategory(newCategory);
				newCategories.add(newCategory);
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
	
	public ArrayList<Category> getCategories() {
		return newCategories;
	}
	
}