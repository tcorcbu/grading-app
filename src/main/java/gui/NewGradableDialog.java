package gui;

import db.GradableService;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.text.*;


public class NewGradableDialog extends JDialog{
	private Gradable newGradable;
	private ArrayList<Gradable> newGradables = new ArrayList<Gradable>();
				
	public NewGradableDialog(final Data data) {
		
		this.setTitle("New Gradable");
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
		
		JPanel nameInputPanel = new JPanel();
		JPanel pointsInputPanel = new JPanel();
		JPanel weightInputPanel = new JPanel();
		JPanel categoryInputPanel = new JPanel();
		
		JPanel addPanel = new JPanel();
		JPanel botPanel = new JPanel();
		
		
		// END setup layout
		final ArrayList<GradableType> gradableTypes = data.getGradableTypes();
		gradableTypes.add(new GradableType("New Category",0,0));
		Object[] categoryOptions = gradableTypes.toArray();
		
		final JTextField nameTextField = new JTextField(10);
		NumberFormat pointsFormat;
		pointsFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField pointsTextField = new JFormattedTextField(pointsFormat);
		pointsTextField.setValue(new Integer(100));
		pointsTextField.setColumns(10);
		// JTextField pointsTextField = new JTextField(10);
		NumberFormat weightFormat;
		weightFormat = NumberFormat.getNumberInstance();
		final JFormattedTextField weightTextField = new JFormattedTextField(weightFormat);
		weightTextField.setValue(new Integer(100));
		weightTextField.setColumns(10);
		weightTextField.setValue(new Integer(100));
		// JTextField weightTextField = new JTextField(10);
		
		final JComboBox<Object> categoryCombo = new JComboBox<Object>(categoryOptions);
		
		nameInputPanel.add(nameTextField);
		pointsInputPanel.add(pointsTextField);
		weightInputPanel.add(weightTextField);
		categoryInputPanel.add(categoryCombo);
		
		gridPanel.add(new JLabel("Gradable Name"));
		gridPanel.add(nameInputPanel);
		gridPanel.add(new JLabel("Total Points"));
		gridPanel.add(pointsInputPanel);
		gridPanel.add(new JLabel("Relative Weight"));
		gridPanel.add(weightInputPanel);
		gridPanel.add(new JLabel("Category"));
		gridPanel.add(categoryInputPanel);
		
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
				Integer points = ((Number)pointsTextField.getValue()).intValue();
				Integer weight = ((Number)weightTextField.getValue()).intValue();
				GradableType category = (GradableType)categoryCombo.getSelectedItem();
				
				// Check student ID against the database and error if there is a conflict
				newGradable = new Gradable(name,points,category,weight);
				GradableService.insert(newGradable, data.getClassId());
				data.refreshGradables();
				newGradables.add(newGradable);
				
				nameTextField.setText("");
				pointsTextField.setText("");
				weightTextField.setText("");
			}
		};
		addButton.addActionListener(addButtonListener);
		
		ActionListener addAndCloseButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				Integer points = ((Number)pointsTextField.getValue()).intValue();
				Integer weight = ((Number)weightTextField.getValue()).intValue();
				GradableType category = (GradableType)categoryCombo.getSelectedItem();
				
				// Check student ID against the database and error if there is a conflict
				newGradable = new Gradable(name,points,category,weight);
				GradableService.insert(newGradable,data.getClassId());
				data.getCategories();
				data.refreshGradables();
				newGradables.add(newGradable);
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
		
		
		
		ActionListener categoryListener = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    GradableType mySelection = (GradableType)categoryCombo.getSelectedItem();
                    if (mySelection.getType().equals("New Category")){
						NewCategoryDialog ncd = new NewCategoryDialog(data);
						ncd.setModal(true);
						ncd.showDialog();
						ArrayList<GradableType> addedGradableTypes = data.getGradableTypes();
						categoryCombo.removeAllItems();
						for (int i=0;i<addedGradableTypes.size(); i++) {
						categoryCombo.addItem(addedGradableTypes.get(i));
						categoryCombo.setSelectedItem(addedGradableTypes.get(i));
						}
						categoryCombo.addItem(new GradableType("New Category",0,0));
                    }
                    }
                };
            categoryCombo.addActionListener( categoryListener );
	}
	
	public void showDialog() {
		this.setVisible( true );
	}
	
	public ArrayList<Gradable> getGradables() {
		return newGradables;
	}
	
}