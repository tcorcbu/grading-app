import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class LogIn{
		
		private String username = "";
		private String password = "";
		
	public static void main(String[] args) {
		LogIn li = new LogIn(new JFrame());
	}
		
		public LogIn(JFrame mainframe) {
			drawLogIn(mainframe);
		}
		
	
	private void drawLogIn(JFrame mainframe) {
		System.out.println("LogIn to do list:");
		System.out.println("> Change password getter to getPassword");
		System.out.println("> Figure out how to set up a user profile");
		System.out.println("> Figure out how to do authentication");
		System.out.println();
		
		mainframe.setTitle("Log In");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setSize( 275, 150 );
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
				   // System.out.println("Log In Pressed");
				   // System.out.println( usernameField.getText() );
				   // System.out.println( passwordField.getPassword() );
				   
				   if (username.equals(usernameField.getText()) && password.equals(passwordField.getText())) {
					    mainframe.remove(panel);
						SelectClass s = new SelectClass(mainframe);
				   }else {
					   wrongpwd.setText("Incorrect Username or Password");
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
	

} 