package gui;

import db.ClassService;
import db.Globals;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class SelectClass {
		
		private String[] classList;
        private String[] oldClassList;

		public SelectClass(JFrame mainframe) {
			intitalClassList();
			drawSelectClass(mainframe);
		}

		private void intitalClassList(){
		    java.util.List<String>classNames = ClassService.getClassNames();
            java.util.List<String>oldClassNames = ClassService.getOldClassNames();
            classNames.add(0,"Select a class");
            classNames.add("New Class");
            oldClassNames.add(0,"Select an old class");

            classList = new String[classNames.size()];
            int count = 0;
            for (String str : classNames) {
                classList[count++] = str;
            }

            oldClassList = new String[oldClassNames.size()];
            int old_count = 0;
            for (String old_str : oldClassNames) {
                oldClassList[old_count++] = old_str;
            }
        }

		private void drawSelectClass(final JFrame mainframe) {

            mainframe.setTitle("Select Class");
			int width = 275;
			int height = 150;
			int x = (int) mainframe.getLocation().x - ((width - mainframe.getWidth()) / 2);
			int y = (int) mainframe.getLocation().y - ((height - mainframe.getHeight()) / 2);
			mainframe.setLocation(x, y);
			mainframe.setSize( width, height );

            final JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JLabel classLabel = new JLabel("Select a Class");
            JPanel classLabelPanel = new JPanel();
            classLabelPanel.add(classLabel);

            final JComboBox<String> classCombo = new JComboBox<String>(classList);
            JPanel classComboPanel = new JPanel();
            classComboPanel.add(classCombo);

            // old classes

            final JComboBox<String> oldClassCombo = new JComboBox<String>(oldClassList);
            JPanel oldClassComboPanel = new JPanel();
            oldClassComboPanel.add(oldClassCombo);

            // old classes

            mainPanel.add(classLabelPanel);
            mainPanel.add(Box.createHorizontalStrut(10));
            mainPanel.add(classComboPanel);
            mainPanel.add(Box.createHorizontalStrut(10));
            mainPanel.add(oldClassComboPanel);

            mainframe.add(mainPanel);
            mainframe.validate();
            mainframe.repaint();

            ActionListener loadListener = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String mySelection = (String)classCombo.getSelectedItem();
                    if (!mySelection.equals("Select a class")){
                    // } else {

                        Data data;
                        if (mySelection.equals("New Class")) {
                            data = new Data();
                            NewClassDialog ncd = new NewClassDialog(data);
                            ncd.setModal(true);
                            ncd.showDialog();

                        } else {
                            data = new Data(mySelection);
							
                        }


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
                    }
                };
            classCombo.addActionListener( loadListener );




            //----------------------

            ActionListener loadOldListener = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String mySelection = (String)oldClassCombo.getSelectedItem();
                    if (!mySelection.equals("Select an old class")){

                        Data data = new Data(mySelection);

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
                }
            };
            oldClassCombo.addActionListener( loadOldListener );
		
	    }
	
	
} 
