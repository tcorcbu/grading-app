package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.tree.DefaultMutableTreeNode.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.text.*;

import db.GradableService;
import db.GradeService;

public class MainWindow {
		
		public MainWindow(final JFrame mainframe,final Data data) {
					
			// START Menu toolbar
			final JMenuBar menuBar = new JMenuBar();
			final JMenu menu = new JMenu("File");
			mainframe.setJMenuBar(null);
			menu.getAccessibleContext().setAccessibleDescription("File Menu");
			menuBar.add(menu);
			
			JMenuItem menuItem_save = new JMenuItem("Save Class");
			menu.add(menuItem_save);
			
			JMenuItem menuItem_load = new JMenuItem("Load Class");
			menu.add(menuItem_load);
			
			JMenuItem menuItem_new = new JMenuItem("New Class");
			menu.add(menuItem_new);
			
			JMenuItem menuItem_close = new JMenuItem("Close Class");
			menu.add(menuItem_close);
			
			JMenuItem menuItem_exit = new JMenuItem("Exit");
			menu.add(menuItem_exit);
			
			mainframe.setJMenuBar(menuBar);
			// END Menu Toolbar
			
			// START Layout
			final JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

			final JPanel treePanel = new JPanel();
			treePanel.setLayout(new GridLayout(1,0));
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(2,2));
			
			// END Layout
			
			// START Gradables tree
			int mysumU = 0;
			int mysumG = 0;
			// for(int i=0; i<data.getCategoryList().size(); i++) {
				// mysumU += data.CategoryList(i).getWeight("Undergraduate");
				// mysumG += data.CategoryList(i).getWeight("Graduate");
			// }
			
			String GradablesTreeHeader;
			if(data.sumUndergradCategories()!=data.sumGradCategories()){
				GradablesTreeHeader = "Gradables ("+String.valueOf(data.sumUndergradCategories())+"%, "+String.valueOf(data.sumGradCategories())+"%)";
			}else{
				GradablesTreeHeader = "Gradables ("+String.valueOf(data.sumUndergradCategories())+"%)";
			}
			DefaultMutableTreeNode topGradables = new DefaultMutableTreeNode(GradablesTreeHeader);
					
			ArrayList<DefaultMutableTreeNode> gradableCategories = new ArrayList<DefaultMutableTreeNode>();
			for (int i=0; i<data.getCategoryList().size(); i++) {
				gradableCategories.add(new DefaultMutableTreeNode(data.CategoryList(i)));
				
				for (int j=0;j<data.nGradables();j++) {
					if (data.getGradable(j).isType(data.CategoryList(i).getType())) {
						gradableCategories.get(i).add(new DefaultMutableTreeNode(data.getGradable(j)));
					}
				}
				
			}
			
			for (int i=0; i<gradableCategories.size(); i++) {
				topGradables.add(gradableCategories.get(i));
			}

			final JTree gradablesTree = new JTree(topGradables);
			gradablesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			final DefaultTreeModel gradablesModel = (DefaultTreeModel) gradablesTree.getModel();
			final DefaultMutableTreeNode gradablesRoot = (DefaultMutableTreeNode)gradablesModel.getRoot();
			final JScrollPane gradableView = new JScrollPane(gradablesTree);
			
			for(int i=0;i<gradablesTree.getRowCount();i++){
				gradablesTree.expandRow(i);
			}
			
			// END Gradables tree 
			
			// START Students tree
			
			DefaultMutableTreeNode topStudents = new DefaultMutableTreeNode("Students");
					
			ArrayList<DefaultMutableTreeNode> studentCategories = new ArrayList<DefaultMutableTreeNode>();
			for (int i=0; i<data.studentTypes().size(); i++) {
				studentCategories.add(new DefaultMutableTreeNode(data.studentTypes().get(i)));
			}
			
			for (int i=0;i<data.nStudents();i++) {
				
				for (int j=0; j<studentCategories.size(); j++) {
					
					if (data.getStudent(i).is(studentCategories.get(j).toString())) {
						studentCategories.get(j).add(new DefaultMutableTreeNode(data.getStudent(i)));
					}
				}
			}
			
			for (int i=0; i<studentCategories.size(); i++) {
				topStudents.add(studentCategories.get(i));
			}
			
			final JTree studentsTree = new JTree(topStudents);
			studentsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			final DefaultTreeModel studentsModel = (DefaultTreeModel) studentsTree.getModel();
			final DefaultMutableTreeNode studentsRoot = (DefaultMutableTreeNode)studentsModel.getRoot();
				
			final JScrollPane studentView = new JScrollPane(studentsTree);
			
			for(int i=0;i<studentsTree.getRowCount();i++){
				studentsTree.expandRow(i);
			}
			
			// END Students tree
			
			// START Buttons
			
			JButton addGradable = new JButton("Add Gradable");
			JButton addStudent = new JButton("Add Student");
			JButton dropGradable = new JButton("Drop Gradable");
			JButton dropStudent = new JButton("Drop Student");
			
			JButton classSummary = new JButton("View Class Summary");
			JPanel classSummaryPanel = new JPanel();
			classSummaryPanel.setLayout(new GridLayout(1,1));
			classSummaryPanel.add(classSummary);
			
			// END Buttons
			
			// START layout
			
			treePanel.add(gradableView);
			treePanel.add(studentView);
			
			
			buttonPanel.add(addGradable);
			buttonPanel.add(addStudent);
			buttonPanel.add(dropGradable);
			buttonPanel.add(dropStudent);			
			
			mainPanel.add(classSummaryPanel);
			mainPanel.add(treePanel);
			mainPanel.add(buttonPanel);
			
			mainframe.add(mainPanel);
			mainframe.validate();
			mainframe.repaint();
			// END layout
			
			// START Action Listeners		
			
			ActionListener ClassProfileListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					   mainframe.remove(mainPanel);
					   
					   ClassProfile c = new ClassProfile(mainframe, data);
					   }
					};
			classSummary.addActionListener(ClassProfileListener);
			
			studentsTree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)studentsTree.getLastSelectedPathComponent();
						
						if (node.getUserObject() instanceof Student) {
							mainframe.remove(mainPanel);
							
							mainframe.setTitle("Student Profile");
							StudentProfile sp = new StudentProfile(mainframe,data,(Student)node.getUserObject());
						}
					}
				}
			});
			
			gradablesTree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)gradablesTree.getLastSelectedPathComponent();
						
						if (node.getUserObject() instanceof Gradable) {
							mainframe.remove(mainPanel);
							
							mainframe.setTitle("Gradable Profile");
							GradableProfile sp = new GradableProfile(mainframe,data,(Gradable)node.getUserObject());
						}
					}
				}
			});
			
			ActionListener AddStudentListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					treePanel.remove(0);
					
					// START setup layout
					JPanel newStudentPanel = new JPanel();		
					newStudentPanel.setLayout(new BoxLayout(newStudentPanel, BoxLayout.Y_AXIS));
					
					JPanel gridPanel = new JPanel();
					gridPanel.setLayout(new GridLayout(4,2));
					
					JPanel fnameInputPanel = new JPanel();
					JPanel lnameInputPanel = new JPanel();
					JPanel sidInputPanel = new JPanel();
					JPanel yearInputPanel = new JPanel();
					
					JPanel addPanel = new JPanel();
					JPanel botPanel = new JPanel();
					// END setup layout
					
					Object[] yearOptions = data.studentTypes().toArray();
					
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
					
					newStudentPanel.add(gridPanel);
					newStudentPanel.add(botPanel);
					
					treePanel.add(newStudentPanel,0,0);
					mainframe.revalidate();
					mainframe.repaint();
					
					for(int i=0;i<studentsTree.getRowCount();i++){
						studentsTree.expandRow(i);
					}	
				
					ActionListener addStudentButtonListener = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							String firstName = fnameTextField.getText();
							String lastName = lnameTextField.getText();
							String schoolID = sidTextField.getText();
							String year = (String)yearCombo.getSelectedItem();
							
							// Check student ID against the database and error if there is a conflict
							Student newStudent = new Student(firstName,lastName,schoolID,year);
							
							data.addStudent(newStudent);
							
							String sYear = newStudent.getYear();
							for (int i=0; i<studentsRoot.getChildCount();i++) {
								if (studentsModel.getChild(studentsRoot,i).toString().equals(sYear)) {
									DefaultMutableTreeNode yearBranch = (DefaultMutableTreeNode) studentsModel.getChild(studentsRoot,i);
									studentsModel.insertNodeInto(new DefaultMutableTreeNode(newStudent), yearBranch, yearBranch.getChildCount());
								}
							}
							
							fnameTextField.setText("");
							lnameTextField.setText("");
							sidTextField.setText("");
							
							for(int i=0;i<studentsTree.getRowCount();i++){
								studentsTree.expandRow(i);
							}
						}
					};
					addButton.addActionListener(addStudentButtonListener);
					
					ActionListener addStudentAndCloseButtonListener = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							String firstName = fnameTextField.getText();
							String lastName = lnameTextField.getText();
							String schoolID = sidTextField.getText();
							String year = (String)yearCombo.getSelectedItem();
							
							// Check student ID against the database and error if there is a conflict
							Student newStudent = new Student(firstName,lastName,schoolID,year);
							
							data.addStudent(newStudent);
												
							String sYear = newStudent.getYear();
							for (int i=0; i<studentsRoot.getChildCount();i++) {
								if (studentsModel.getChild(studentsRoot,i).toString().equals(sYear)) {
									DefaultMutableTreeNode yearBranch = (DefaultMutableTreeNode) studentsModel.getChild(studentsRoot,i);
									studentsModel.insertNodeInto(new DefaultMutableTreeNode(newStudent), yearBranch, yearBranch.getChildCount());
								}
							}
								
							treePanel.remove(0);
							treePanel.add(gradableView,0,0);
							mainframe.revalidate();
							mainframe.repaint();
							
							for(int i=0;i<studentsTree.getRowCount();i++){
								studentsTree.expandRow(i);
							}
						}
					};
					addAndClose.addActionListener(addStudentAndCloseButtonListener);
					
					ActionListener CloseAddStudentButtonListener = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							treePanel.remove(0);
							treePanel.add(gradableView,0,0);
							mainframe.revalidate();
							mainframe.repaint();
							
						}
					};
					closeButton.addActionListener(CloseAddStudentButtonListener);
				
				
				
				
				}
			};
			addStudent.addActionListener(AddStudentListener);
			
			ActionListener AddGradableListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					treePanel.remove(0);
					treePanel.remove(0);
					// START Setup Layout
					JPanel newGradablePanel = new JPanel();
					newGradablePanel.setLayout(new BoxLayout(newGradablePanel, BoxLayout.Y_AXIS));
					
					JPanel gridPanel = new JPanel();
					gridPanel.setLayout(new GridLayout(4,2));
					
					JPanel nameInputPanel = new JPanel();
					JPanel pointsInputPanel = new JPanel();
					JPanel weightInputPanel = new JPanel();
					JPanel categoryInputPanel = new JPanel();
					
					JPanel addPanel = new JPanel();
					JPanel botPanel = new JPanel();
					// END setup layout
		
					ArrayList<Category> gradableTypes = data.copyCategories();
					gradableTypes.add(new Category("New Category",0,0));
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
					
					DefaultMutableTreeNode node = (DefaultMutableTreeNode)gradablesTree.getLastSelectedPathComponent();
					if (node.getUserObject() instanceof Category) {
						Category selectedCategory = (Category)node.getUserObject();
						// System.out.println(selectedCategory);
						categoryCombo.setSelectedItem(selectedCategory);
					} else{
						if (node.getUserObject() instanceof Gradable) {
							Gradable selectedGradable = (Gradable)node.getUserObject();
							Category selectedCategory = selectedGradable.getType();
							// System.out.println(selectedCategory);
							categoryCombo.setSelectedItem(selectedCategory);
						}
					}
					
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
					
					newGradablePanel.add(gridPanel);
					newGradablePanel.add(botPanel);
					
					treePanel.add(newGradablePanel,0,0);
					treePanel.add(gradableView,0,1);
					mainframe.revalidate();
					mainframe.repaint();
					
					for(int i=0;i<gradablesTree.getRowCount();i++){
						gradablesTree.expandRow(i);
					}
					
					ActionListener addGradableButtonListener = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							String name = nameTextField.getText();
							Integer points = ((Number)pointsTextField.getValue()).intValue();
							Integer weight = ((Number)weightTextField.getValue()).intValue();
							Category category = (Category)categoryCombo.getSelectedItem();
							
							Gradable newGradable = new Gradable(name,points,category,weight);
							data.addGradable(newGradable);
							data.addSaveCommand(GradableService.insert(newGradable));
								
							for(int i=0; i<data.nStudents(); i++){
								Gradable gtemp = newGradable.copy();
								gtemp.setPointsLost(newGradable.getPoints());
								gtemp.setStudentWeight(100);
								data.getStudent(i).addGradable(gtemp);
								data.addSaveCommand(GradeService.insert(gtemp,data.getStudent(i)));
							}
									
								String gCategory = newGradable.getType().toString();
								boolean added = false;
								for (int i=0; i<gradablesRoot.getChildCount();i++) {
									if (gradablesModel.getChild(gradablesRoot,i).toString().equals(gCategory)) {
										DefaultMutableTreeNode categoryBranch = (DefaultMutableTreeNode) gradablesModel.getChild(gradablesRoot,i);
										gradablesModel.insertNodeInto(new DefaultMutableTreeNode(newGradable), categoryBranch, categoryBranch.getChildCount());
										added = true;
									}
								}
								
								if (!added){
									int count = gradablesRoot.getChildCount();
									gradablesModel.insertNodeInto(new DefaultMutableTreeNode(newGradable.getType()),gradablesRoot,count);
									DefaultMutableTreeNode categoryBranch = (DefaultMutableTreeNode) gradablesModel.getChild(gradablesRoot,count);
									gradablesModel.insertNodeInto(new DefaultMutableTreeNode(newGradable), categoryBranch, categoryBranch.getChildCount());
								}

							nameTextField.setText("");
							
							for(int i=0;i<gradablesTree.getRowCount();i++){
								gradablesTree.expandRow(i);
							}
							// pointsTextField.setText("");
							// weightTextField.setText("");
						}
					};
					addButton.addActionListener(addGradableButtonListener);
		
					ActionListener addAndGradableCloseButtonListener = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							String name = nameTextField.getText();
							Integer points = ((Number)pointsTextField.getValue()).intValue();
							Integer weight = ((Number)weightTextField.getValue()).intValue();
							Category category = (Category)categoryCombo.getSelectedItem();
							
							Gradable newGradable = new Gradable(name,points,category,weight);
							data.addGradable(newGradable);
							data.addSaveCommand(GradableService.insert(newGradable));
								
							for(int i=0; i<data.nStudents(); i++){
								Gradable gtemp = newGradable.copy();
								gtemp.setPointsLost(newGradable.getPoints());
								gtemp.setStudentWeight(100);
								data.getStudent(i).addGradable(gtemp);
								data.addSaveCommand(GradeService.insert(gtemp,data.getStudent(i)));
							}
									
							String gCategory = newGradable.getType().toString();
							boolean added = false;
							for (int i=0; i<gradablesRoot.getChildCount();i++) {
								if (gradablesModel.getChild(gradablesRoot,i).toString().equals(gCategory)) {
									DefaultMutableTreeNode categoryBranch = (DefaultMutableTreeNode) gradablesModel.getChild(gradablesRoot,i);
									gradablesModel.insertNodeInto(new DefaultMutableTreeNode(newGradable), categoryBranch, categoryBranch.getChildCount());
									added = true;
								}
							}
							
							if (!added){
								int count = gradablesRoot.getChildCount();
								gradablesModel.insertNodeInto(new DefaultMutableTreeNode(newGradable.getType()),gradablesRoot,count);
								DefaultMutableTreeNode categoryBranch = (DefaultMutableTreeNode) gradablesModel.getChild(gradablesRoot,count);
								gradablesModel.insertNodeInto(new DefaultMutableTreeNode(newGradable), categoryBranch, categoryBranch.getChildCount());
							}
									
							treePanel.remove(0);
							treePanel.remove(0);
							treePanel.add(gradableView,0,0);
							treePanel.add(studentView,0,1);
							mainframe.revalidate();
							mainframe.repaint();
							
							for(int i=0;i<gradablesTree.getRowCount();i++){
								gradablesTree.expandRow(i);
							}
						}
					};
					addAndClose.addActionListener(addAndGradableCloseButtonListener);
					
					ActionListener CloseAddGradableButtonListener = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							treePanel.remove(0);
							treePanel.remove(0);
							treePanel.add(gradableView,0,0);
							treePanel.add(studentView,0,1);
							mainframe.revalidate();
							mainframe.repaint();
						}
					};
					closeButton.addActionListener(CloseAddGradableButtonListener);
		
					ActionListener categoryListener = new ActionListener(){
							public void actionPerformed(ActionEvent e){
								Category mySelection = (Category)categoryCombo.getSelectedItem();
								if (mySelection.getType().equals("New Category")){
									NewCategoryDialog ncd = new NewCategoryDialog(data);
									ncd.setModal(true);
									ncd.showDialog();
									ArrayList<Category> addedCategories = ncd.getCategories();
									for (int i=0;i<addedCategories.size(); i++) {
									categoryCombo.addItem(addedCategories.get(i));
									categoryCombo.setSelectedItem(addedCategories.get(i));
									}
								}
								}
							};
					categoryCombo.addActionListener( categoryListener );
				}
			};
			addGradable.addActionListener(AddGradableListener);
			
			ActionListener DropStudentListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)studentsTree.getLastSelectedPathComponent();				
				if (node.getUserObject() instanceof Student) {
					data.dropStudent((Student)node.getUserObject());
					DefaultTreeModel studentsModel = (DefaultTreeModel) studentsTree.getModel();
					studentsModel.removeNodeFromParent(node);
					
					// int sid = (Student)node.getUserObject().getID();
					
				}
				}
			};
			dropStudent.addActionListener(DropStudentListener);
			
			ActionListener DropGradableListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)gradablesTree.getLastSelectedPathComponent();
				
				if (node.getUserObject() instanceof Gradable) {
					data.dropGradable((Gradable)node.getUserObject());
					for (int i = 0; i < data.nStudents(); i++) {
						data.getStudent(i).dropGradable((Gradable)node.getUserObject());
					}
					
					DefaultTreeModel gradablesModel = (DefaultTreeModel) gradablesTree.getModel();
					gradablesModel.removeNodeFromParent(node);
					data.addSaveCommand(GradeService.drop((Gradable)node.getUserObject()));
					data.addSaveCommand(GradableService.drop((Gradable)node.getUserObject()));
				}
				}
			};
			dropGradable.addActionListener(DropGradableListener);
			
			
			ActionListener menuItem_saveListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					data.saveClass();
				}
			};
			menuItem_save.addActionListener(menuItem_saveListener);
			
			ActionListener menuItem_loadListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainframe.remove(mainPanel);
					
					// load select class panel
					SelectClass s = new SelectClass(mainframe);
				}
			};
			menuItem_load.addActionListener(menuItem_loadListener);
			
			ActionListener menuItem_newListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Data data = new Data();
					NewClassDialog ncd = new NewClassDialog(data);
					ncd.setModal(true);
					ncd.showDialog();
					
					mainframe.remove(mainPanel);
					
					int width = 750;
					int height = 500;

					int x = (int) mainframe.getLocation().x - ((width - mainframe.getWidth()) / 2);
					int y = (int) mainframe.getLocation().y - ((height - mainframe.getHeight()) / 2);

					mainframe.setLocation(x, y);
					mainframe.setSize( width, height );

					mainframe.setTitle(data.getLoadedClass());
					MainWindow m = new MainWindow(mainframe,data);
				}
			};
			menuItem_new.addActionListener(menuItem_newListener);
			
			ActionListener menuItem_closeListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Close Class");
					// add dialog
					// ask for class name
					// if the class name matches to the current class,
					// set the class status in the db to closed
					CloseClassDialog ccd = new CloseClassDialog(data);
					ccd.setModal(true);
					ccd.showDialog();
				}
			};
			menuItem_close.addActionListener(menuItem_closeListener);
			
			ActionListener exitListener = new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   System.exit(0);
					   }
					};
			menuItem_exit.addActionListener(exitListener);
			// END Action Listeners 
		}
	
} 
