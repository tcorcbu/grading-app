import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
		
		private String[] classList = {"CS 591 Fall 2018","CS 591 Sprint 2017","CS 112 Fall 2018","CS 112 Spring 2018"};
		private String username = "";
		private String password = "";
		private String loadedClass;
		
		public GUI() {
			drawLogIn(this);
		}
		
		public static void main (String[] args) {
		JFrame frame = new GUI();
	}
	
	private void drawLogIn(JFrame mainframe) {
		mainframe.setTitle("Log In");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setSize( 250, 150 );
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - mainframe.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - mainframe.getHeight()) / 2);
		mainframe.setLocation(x, y);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel usernameLabel = new JLabel("Username");
		JTextField usernameField = new JTextField(10);
		
		JLabel passwordLabel = new JLabel("Password");
		JPasswordField passwordField = new JPasswordField(10);
		
		JButton jbtLogIn = new JButton("Log In");
		jbtLogIn.setAlignmentX(panel.CENTER_ALIGNMENT);
		
		JButton jbtCreateAccount = new JButton( "Create Account" );
		jbtCreateAccount.setAlignmentX(panel.CENTER_ALIGNMENT);
		
		JPanel unamePanel = new JPanel();
		unamePanel.add(usernameLabel);
		unamePanel.add(usernameField);
		
		JPanel pwordPanel = new JPanel();
		pwordPanel.add(passwordLabel);
		pwordPanel.add(passwordField);
		
		JLabel wrongpwd = new JLabel();
		wrongpwd.setForeground (Color.red);
		wrongpwd.setAlignmentX(panel.CENTER_ALIGNMENT);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( jbtLogIn );
		buttonPanel.add( jbtCreateAccount );		
		
		panel.add(unamePanel);
		panel.add(pwordPanel);
		panel.add(wrongpwd);
		panel.add(buttonPanel);
		
		mainframe.add(panel);
		
		ActionListener loginListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   System.out.println("Log In Pressed");
				   System.out.println( usernameField.getText() );
				   System.out.println( passwordField.getPassword() );
				   
				   if (username.equals(usernameField.getText()) && password.equals(passwordField.getText())) {
					    mainframe.remove(panel);
						drawSelectClass(mainframe);
				   }else {
					   wrongpwd.setText("Incorrect Username or Password");
					   // JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
				   }
				   }
				};
				
		ActionListener createAccountListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   System.out.println("Create Account Pressed");
				   System.out.println( usernameField.getText() );
				   System.out.println( passwordField.getPassword() );
				   }
				};
		
		jbtLogIn.addActionListener( loginListener );
		jbtCreateAccount.addActionListener( createAccountListener );
		
		mainframe.setVisible( true );
	}
	
	private void drawSelectClass(JFrame mainframe) {
		mainframe.setTitle("Select Class");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel classLabel = new JLabel("Select a Class");
		JComboBox<String> classCombo = new JComboBox<String>(classList);
		
		
		JButton jbtLoad = new JButton("Load Class");
		jbtLoad.setAlignmentX(panel.CENTER_ALIGNMENT);		
		
		JButton jbtNew = new JButton( "New Class" );
		jbtNew.setAlignmentX(panel.CENTER_ALIGNMENT);	
		
		JPanel comboPanel = new JPanel();
		comboPanel.add(classLabel);
		comboPanel.add(classCombo);
		
		
		panel.add(comboPanel);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(jbtLoad);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(jbtNew);
		panel.add(Box.createHorizontalStrut(10));
		
		mainframe.add(panel);
		mainframe.validate();
		mainframe.repaint();
		
		ActionListener loadListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   mainframe.remove(panel);
				   loadedClass = (String)classCombo.getSelectedItem();
				   mainframe.setTitle(loadedClass);
				   drawMainWindow(mainframe);
				   System.out.println("Load Class Pressed");
				   System.out.println( classCombo.getSelectedItem() );
				   }
				};
				
		ActionListener newListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   String NewClassName = JOptionPane.showInputDialog("Please input the new class name ");
				   classCombo.addItem(NewClassName);
				   classCombo.setSelectedItem(NewClassName);
				   // Write new class name to the database.
				   				   }
				};
		
		jbtLoad.addActionListener( loadListener );
		jbtNew.addActionListener( newListener );
		
	}
	
	private void drawMainWindow(JFrame mainframe) {
		// mainframe.setTitle("Selected Class");
		mainframe.setSize( 500, 325 );
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - mainframe.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - mainframe.getHeight()) / 2);
		mainframe.setLocation(x, y);
				
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
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Gradables");
				
		DefaultMutableTreeNode tests = new DefaultMutableTreeNode("Tests");
		tests.add(new DefaultMutableTreeNode("Midterm 1"));
		top.add(tests);
		
		DefaultMutableTreeNode homeworks = new DefaultMutableTreeNode("Homework");
		homeworks.add(new DefaultMutableTreeNode("Blackjack"));
		homeworks.add(new DefaultMutableTreeNode("Treinta Ena"));
		top.add(homeworks);
		
		DefaultMutableTreeNode projects = new DefaultMutableTreeNode("Projects");
		projects.add(new DefaultMutableTreeNode("Grading System"));
		top.add(projects);

		JTree gradablesTree = new JTree(top);
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
				
		DefaultMutableTreeNode graduates = new DefaultMutableTreeNode("Graduates");
		graduates.add(new DefaultMutableTreeNode("Joe O'Donnell"));
		topStudents.add(graduates);
		
		DefaultMutableTreeNode undergrads = new DefaultMutableTreeNode("Undergraduates");
		undergrads.add(new DefaultMutableTreeNode("Tom Corcoran"));
		undergrads.add(new DefaultMutableTreeNode("Yize Liu"));
		topStudents.add(undergrads);

		JTree studentsTree = new JTree(topStudents);
        studentsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane studentView = new JScrollPane(studentsTree);
		
		for(int i=0;i<studentsTree.getRowCount();i++){
			studentsTree.expandRow(i);
		}
		
		studentsTree.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
					System.out.println((DefaultMutableTreeNode)studentsTree.getLastSelectedPathComponent());
					// mainframe.setTitle((DefaultMutableTreeNode)studentsTree.getLastSelectedPathComponent());
            }
        }
    });
		// END Students tree
		
		// START layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new GridLayout(0,2));
		treePanel.add(gradableView);
		treePanel.add(studentView);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,2));
		buttonPanel.add(new JButton("Add Gradable"));
		buttonPanel.add(new JButton("Add Student"));
		buttonPanel.add(new JButton("Drop Gradable"));
		buttonPanel.add(new JButton("Drop Student"));
		
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
				
		ActionListener ClassProfileListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				   System.out.println("Class Profile Selected");
				   mainframe.remove(mainPanel);
				   drawClassProfile(mainframe);
				   }
				};

		menuItem_exit.addActionListener(exitListener);
		menuItem_Class_Profile.addActionListener(ClassProfileListener);
		// END Action Listeners 
		
	}
	
	private void drawClassProfile(JFrame mainframe) {
		
		mainframe.setTitle(mainframe.getTitle() + " Profile");

		// START Grade Table
		String[] gradeColumnNames = {"Grade","Number of Students"};
		
		Object[][] gradeData = {
        {"A", 8},
        {"B", 10},
        {"C", 9},
        {"D", 5},
        {"F", 1},
		{"Total",33}
		};
		
		final JTable gradeTable = new JTable(gradeData, gradeColumnNames);
		// gradeTable.setModel(new DefaultTableModel() {

			// @Override
			// public boolean isCellEditable(int row, int column) {
			   // return false;
			// }
		// });
		
		gradeTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
        // gradeTable.setFillsViewportHeight(true);
		JScrollPane gradeTablePane = new JScrollPane(gradeTable);
		// END Grade Table
		
		// START Category Table
		String[] categoryColumnNames = {"Category","Weight"};
		
		Object[][] categoryData = {
        {"Tests", 30},
        {"Homework", 35},
        {"Projects", 30},
        {"Participation", 5},
		{"Total", 100}
		};
		
		final JTable categoryTable = new JTable(categoryData, categoryColumnNames);
		
		categoryTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
        // gradeTable.setFillsViewportHeight(true);
		JScrollPane categoryTablePane = new JScrollPane(categoryTable);
		// END Grade Table
		
		// START buttons
		// JLabel curveLabel = new JLabel("Add Percent");
		JTextField curveField = new JTextField(10);
		JButton curveApply = new JButton("Add Percent");
		JButton backButton = new JButton("Back");
		// backButton.setMaximumSize(new Dimension(1, 1));
		
		//END buttons
		
		
		// START Layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		tablePanel.add(gradeTablePane);
		tablePanel.add(categoryTablePane);
		
		JPanel curvePanel = new JPanel();
		curvePanel.setLayout(new GridLayout(2,1,5,5));
		// curvePanel.add(curveLabel);
		curvePanel.add(curveField);
		curvePanel.add(curveApply);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2,5,5));
		buttonPanel.add(curvePanel);
		buttonPanel.add(backButton);
		
		mainPanel.add(tablePanel);
		mainPanel.add(buttonPanel);
		// END Layout 
		mainframe.add(mainPanel);
		mainframe.validate();
		mainframe.repaint();
		
		// START Action Listeners
			ActionListener backListener = new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   mainframe.remove(mainPanel);
				   mainframe.setTitle(loadedClass);
				   drawMainWindow(mainframe);
				   }
				};

		backButton.addActionListener(backListener);
		// END Action Listeners 
	}
	
	private void drawStudentProfile(JFrame mainframe) {
		String firstName = "Joe";
		String lastName = "O'Donnell";
		String schoolID = "U08447737";
		String year = "Graduate";
		String Grade = "A";
		
		// START Gradable Table
		String[] gradableColumnNames = {"Gradable","Points Lost","Weight"};
		
		Object[][] gradableData = {
        {"Blackjack", 8,20},
        {"TreintaEna", 10,20},
        {"Midterm 1", 9,30},
        {"Grading System", 5,30}
		};
		
		JTable gradableTable = new JTable(gradableData, gradableColumnNames);
		JScrollPane gradableTablePane = new JScrollPane(gradableTable);
		// END Gradable Table
		
		// START Layout 
		
		
		
		// END Layout
		
	}
	
} 
