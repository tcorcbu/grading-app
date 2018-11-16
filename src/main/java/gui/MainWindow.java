package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.tree.DefaultMutableTreeNode.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MainWindow {
		
		public MainWindow(final JFrame mainframe,final Data data) {
			System.out.println("MainWindow to do list:");
			System.out.println("> Add popup for addGradable");
			System.out.println("> Add functionality to dropGradable");
			System.out.println("> Add popup for addStudent");
			System.out.println("> Add functionality to dropStudent");
			System.out.println("> Add functionality to Save Class");
			System.out.println("> Add functionality to Load Class");
			System.out.println();
					
			// START Menu toolbar
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.getAccessibleContext().setAccessibleDescription("File Menu");
			menuBar.add(menu);
			
			JMenuItem menuItem_save = new JMenuItem("Save Class");
			menu.add(menuItem_save);
			
			JMenuItem menuItem_load = new JMenuItem("Load Class");
			menu.add(menuItem_load);
			
			JMenuItem menuItem_Class_Profile = new JMenuItem("Class Profile");
			menu.add(menuItem_Class_Profile);
			
			JMenuItem menuItem_exit = new JMenuItem("Exit");
			menu.add(menuItem_exit);
			

			mainframe.setJMenuBar(menuBar);
			// END Menu Toolbar
			
			// START Gradables tree
			DefaultMutableTreeNode topGradables = new DefaultMutableTreeNode("Gradables");
					
			ArrayList<DefaultMutableTreeNode> gradableCategories = new ArrayList<DefaultMutableTreeNode>();
			for (int i=0; i<data.gradableTypes().size(); i++) {
				gradableCategories.add(new DefaultMutableTreeNode(data.gradableTypes().get(i).getType()));
			}
			
			for (int i=0;i<data.nGradables();i++) {
				
				for (int j=0; j<gradableCategories.size(); j++) {
					
					if (data.getGradable(i).isType(gradableCategories.get(j).toString())) {
						gradableCategories.get(j).add(new DefaultMutableTreeNode(data.getGradable(i)));
					}
				}
			}
			
			for (int i=0; i<gradableCategories.size(); i++) {
				topGradables.add(gradableCategories.get(i));
			}

			final JTree gradablesTree = new JTree(topGradables);
			gradablesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			JScrollPane gradableView = new JScrollPane(gradablesTree);
			
			for(int i=0;i<gradablesTree.getRowCount();i++){
				gradablesTree.expandRow(i);
			}
			
			gradablesTree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println((DefaultMutableTreeNode)gradablesTree.getLastSelectedPathComponent());
				}
			}
		});
		
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
			JScrollPane studentView = new JScrollPane(studentsTree);
			
			for(int i=0;i<studentsTree.getRowCount();i++){
				studentsTree.expandRow(i);
			}
			
			// END Students tree
			
			// START Buttons
			
			JButton addGradable = new JButton("Add Gradable");
			JButton addStudent = new JButton("Add Student");
			JButton dropGradable = new JButton("Drop Gradable");
			JButton dropStudent = new JButton("Drop Student");
			
			JButton classSummary = new JButton("Class Summary");
			JPanel classSummaryPanel = new JPanel();
			classSummaryPanel.setLayout(new GridLayout(1,1));
			classSummaryPanel.add(classSummary);
			
			// END Buttons
			
			// START layout
			final JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			
			JPanel treePanel = new JPanel();
			treePanel.setLayout(new GridLayout(0,2));
			treePanel.add(gradableView);
			treePanel.add(studentView);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(2,2));
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
			ActionListener exitListener = new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   System.exit(0);
					   }
					};
			menuItem_exit.addActionListener(exitListener);		
			
			ActionListener ClassProfileListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					   // System.out.println("Class Profile Selected");
					   mainframe.remove(mainPanel);
					   ClassProfile c = new ClassProfile(mainframe, data);
					   }
					};
			menuItem_Class_Profile.addActionListener(ClassProfileListener);
			
			studentsTree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)studentsTree.getLastSelectedPathComponent();
						
						// System.out.println(node.getUserObject().getClass());
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
						
						System.out.println(node.getUserObject().getClass());
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
					// System.out.println("Add Student");
					NewStudentDialog nsd = new NewStudentDialog(data);
					nsd.setModal(true);
					nsd.showDialog();
					ArrayList<Student> addedStudents = nsd.getStudents();
					
					DefaultTreeModel studentsModel = (DefaultTreeModel) studentsTree.getModel();
					DefaultMutableTreeNode studentsRoot = (DefaultMutableTreeNode)studentsModel.getRoot();
					
					Student addedStudent;
					
					for (int s=0; s<addedStudents.size(); s++) {
					
						addedStudent = addedStudents.get(s);
						String sYear = addedStudent.getYear();
						for (int i=0; i<studentsRoot.getChildCount();i++) {
							if (studentsModel.getChild(studentsRoot,i).toString().equals(sYear)) {
								DefaultMutableTreeNode yearBranch = (DefaultMutableTreeNode) studentsModel.getChild(studentsRoot,i);
								studentsModel.insertNodeInto(new DefaultMutableTreeNode(addedStudent), yearBranch, yearBranch.getChildCount());
							}
						}
					}
					
					for(int i=0;i<studentsTree.getRowCount();i++){
						studentsTree.expandRow(i);
					}	
				}
			};
			addStudent.addActionListener(AddStudentListener);
			
			ActionListener AddGradableListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Add Gradable");
					
					NewGradableDialog ngd = new NewGradableDialog(data);
					ngd.setModal(true);
					ngd.showDialog();
					ArrayList<Gradable> addedGradables = ngd.getGradables();
					
					DefaultTreeModel gradablesModel = (DefaultTreeModel) gradablesTree.getModel();
					DefaultMutableTreeNode gradablesRoot = (DefaultMutableTreeNode)gradablesModel.getRoot();
					
					Gradable addedGradable;
					
					for (int s=0; s<addedGradables.size(); s++) {
					
					addedGradable = addedGradables.get(s);
					String gCategory = addedGradable.getType().getType();
					boolean added = false;
						for (int i=0; i<gradablesRoot.getChildCount();i++) {
							if (gradablesModel.getChild(gradablesRoot,i).toString().equals(gCategory)) {
								DefaultMutableTreeNode categoryBranch = (DefaultMutableTreeNode) gradablesModel.getChild(gradablesRoot,i);
								gradablesModel.insertNodeInto(new DefaultMutableTreeNode(addedGradable), categoryBranch, categoryBranch.getChildCount());
								added = true;
							}
						}
						
						if (!added){
							int count = gradablesRoot.getChildCount();
							gradablesModel.insertNodeInto(new DefaultMutableTreeNode(addedGradable.getType()),gradablesRoot,count);
							DefaultMutableTreeNode categoryBranch = (DefaultMutableTreeNode) gradablesModel.getChild(gradablesRoot,count);
							gradablesModel.insertNodeInto(new DefaultMutableTreeNode(addedGradable), categoryBranch, categoryBranch.getChildCount());
						}
					}
					for(int i=0;i<gradablesTree.getRowCount();i++){
						gradablesTree.expandRow(i);
					}
				}
			};
			addGradable.addActionListener(AddGradableListener);
			
			ActionListener DropStudentListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				// System.out.println("Drop Student");
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)studentsTree.getLastSelectedPathComponent();
				// System.out.println((Student)node.getUserObject());
				
				if (node.getUserObject() instanceof Student) {
					data.dropStudent((Student)node.getUserObject());
					DefaultTreeModel studentsModel = (DefaultTreeModel) studentsTree.getModel();
					studentsModel.removeNodeFromParent(node);
				}
				}
			};
			dropStudent.addActionListener(DropStudentListener);
			
			ActionListener DropGradableListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)gradablesTree.getLastSelectedPathComponent();
				
				if (node.getUserObject() instanceof Gradable) {
					data.dropGradable((Gradable)node.getUserObject());
					// NEED TO DROP IT FROM EVERY STUDENT TOO
					for (int i = 0; i < data.nStudents(); i++) {
						data.getStudent(i).dropGradable((Gradable)node.getUserObject());
					}
					
					DefaultTreeModel gradablesModel = (DefaultTreeModel) gradablesTree.getModel();
					gradablesModel.removeNodeFromParent(node);
				}
				}
			};
			dropGradable.addActionListener(DropGradableListener);
			
			classSummary.addActionListener(ClassProfileListener);
			// END Action Listeners 
			
		}

		
	// private void drawMainWindow(JFrame mainframe,Data data) {

	
} 
