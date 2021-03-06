package gui;

import db.CategoryService;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.text.*;
import objects.*;


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
		gWeightTextField.setColumns(10);

		NumberFormat uWeightFormat;
		uWeightFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField uWeightTextField = new JFormattedTextField(uWeightFormat);
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
		
		nameTextField.requestFocus();
		
		ActionListener addButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				Integer gWeight = ((Number)gWeightTextField.getValue()).intValue();
				Integer uWeight = ((Number)uWeightTextField.getValue()).intValue();

				if(name.replaceAll("\\s+","").equals("")){
					return;
				}	
				
				newCategory = new Category(name,gWeight,uWeight);
				newCategories.add(newCategory);
				data.addCategory(newCategory);
				nameTextField.setText("");
				nameTextField.requestFocus();
			}
		};
		addButton.addActionListener(addButtonListener);

		ActionListener CloseButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
		closeButton.addActionListener(CloseButtonListener);
		
		addAndClose.addActionListener(addButtonListener);
		addAndClose.addActionListener(CloseButtonListener);
	}
	
	public void showDialog() {
		this.setVisible( true );
	}
	
	public ArrayList<Category> getCategories() {
		return newCategories;
	}
	
}