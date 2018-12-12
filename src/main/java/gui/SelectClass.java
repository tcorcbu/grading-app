package gui;

import db.ClassService;
import db.Globals;
import objects.Data;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class SelectClass {
		
		private static String[] classList;
        private static String[] oldClassList;

		public SelectClass(JFrame mainframe) {
			drawSelectClass(mainframe);
		}

		private static void intitalClassList(){
		    ArrayList<String>classNames = ClassService.getClassNames();
            ArrayList<String>oldClassNames = ClassService.getOldClassNames();
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

		public static void drawSelectClass(final JFrame mainframe) {
			mainframe.setJMenuBar(null);
            mainframe.setTitle("Select Class");

            final JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JLabel classLabel = new JLabel("Select a Class");
            JPanel classLabelPanel = new JPanel();
            classLabelPanel.add(classLabel);

			intitalClassList();
            final JComboBox<String> classCombo = new JComboBox<String>(classList);
            JPanel classComboPanel = new JPanel();
            classComboPanel.add(classCombo);

            // old classes

            final JComboBox<String> oldClassCombo = new JComboBox<String>(oldClassList);
            JPanel oldClassComboPanel = new JPanel();
            oldClassComboPanel.add(oldClassCombo);

            // old classes

            mainPanel.add(classLabelPanel);
            mainPanel.add(classComboPanel);
            mainPanel.add(oldClassComboPanel);

            mainframe.add(mainPanel);
            mainframe.validate();
            mainframe.repaint();

            ActionListener loadListener = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String mySelection = (String)classCombo.getSelectedItem();
                    if (!mySelection.equals("Select a class")){

                        Data data;
                        if (mySelection.equals("New Class")) {
                            data = new Data();
                            NewClassDialog ncd = new NewClassDialog(data);
                            ncd.setModal(true);
                            ncd.showDialog();

                        } else {
                            data = new Data(mySelection);
                        }
						
						mainframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						mainframe.addWindowListener(new WindowAdapter(){
							@Override
							public void windowClosing(WindowEvent e){
								if (data.needSave() && ClassService.classIsOpen(Globals.class_id())){
									Object[] options = {"Yes","No","Cancel"};
									int n = JOptionPane.showOptionDialog(mainframe,
										"Would you like to save before exiting?","Save",
										JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
										null,options,options[0]);
									
									switch(n) {
										case 0:
											data.saveClass();
											mainframe.dispose();
											System.exit(0);
										break;
										case 1:
											mainframe.dispose();
											System.exit(0);
										break;
									}
								} else {
									mainframe.dispose();
									System.exit(0);
								}
							}
						});
					
                        mainframe.remove(mainPanel);
						MainWindow.sizeMainWindow(mainframe);
						MainWindow.drawMainWindow(mainframe,data);
                    }
				}
			};
            classCombo.addActionListener( loadListener );

            ActionListener loadOldListener = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String mySelection = (String)oldClassCombo.getSelectedItem();
                    if (!mySelection.equals("Select an old class")){
                        Data data = new Data(mySelection);
						
						mainframe.addWindowListener(new WindowAdapter(){
							@Override
							public void windowClosing(WindowEvent e){
								mainframe.dispose();
								System.exit(0);
							}
						});
						
                        mainframe.remove(mainPanel);
						MainWindow.sizeMainWindow(mainframe);
                        MainWindow.drawMainWindow(mainframe,data);
                    }
                }
            };
            oldClassCombo.addActionListener( loadOldListener );
		
	    }
	
	
} 
