package gui;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class LogIn{
		
		private static String username = "";
		private static String password = "";
		
	public static void main(String[] args) {
		JFrame mainframe = new JFrame();
		drawLogIn(mainframe);
	}		
	
	public static void sizeLogIn(final JFrame mainframe) {
		mainframe.setSize( 275, 150 );
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - mainframe.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - mainframe.getHeight()) / 2);
		mainframe.setLocation(x, y);
	}
	
	private static void drawLogIn(final JFrame mainframe) {
		sizeLogIn(mainframe);
		mainframe.setTitle("Log In");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JLabel usernameLabel = new JLabel("Username");
		final JTextField usernameField = new JTextField(10);
		
		JLabel passwordLabel = new JLabel("Password");
		final JPasswordField passwordField = new JPasswordField(10);
		
		JButton jbtLogIn = new JButton("Log In");
		jbtLogIn.setAlignmentX(mainPanel.CENTER_ALIGNMENT);
		
		JButton jbtCreateAccount = new JButton( "Create Account" );
		jbtCreateAccount.setAlignmentX(mainPanel.CENTER_ALIGNMENT);
		
		JPanel unamePanel = new JPanel();
		unamePanel.add(usernameLabel);
		unamePanel.add(usernameField);
		
		JPanel pwordPanel = new JPanel();
		pwordPanel.add(passwordLabel);
		pwordPanel.add(passwordField);
		
		final JLabel wrongpwd = new JLabel();
		wrongpwd.setForeground (Color.red);
		wrongpwd.setAlignmentX(mainPanel.CENTER_ALIGNMENT);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( jbtLogIn );
		buttonPanel.add( jbtCreateAccount );		
		
		mainPanel.add(unamePanel);
		mainPanel.add(pwordPanel);
		mainPanel.add(wrongpwd);
		mainPanel.add(buttonPanel);
		
		mainframe.add(mainPanel);
		
		ActionListener loginListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (username.equals(usernameField.getText()) && password.equals(passwordField.getText())) {
					mainframe.remove(mainPanel);
					SelectClass.drawSelectClass(mainframe);
				}else {
					wrongpwd.setText("Incorrect Username or Password");
				}
			}
		};
		jbtLogIn.addActionListener( loginListener );
		
		ActionListener createAccountListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Create Account Pressed");
				System.out.println( usernameField.getText() );
				System.out.println( passwordField.getPassword() );
			}
		};
		jbtCreateAccount.addActionListener( createAccountListener );
			
		mainframe.setVisible( true );
	}
	

} 