
/*
# Project Calculate Payment
This application helps you to keep track of the expenses and calculate the debts. 
It comes in handy when you're planning a group trip or a party and don't want to have it 
ruined by constant discussions over "who paid" and "who should pay".

# Instructions
When the program starts you can either start a new file with for the expenses list or load one.

Write the expenses one by one (Name, Quantity and Comment) and press "Add" button to add it to the expenses list.

You can keep track of the expenses introduced by pressing "Show List" button. 
If you need to make some changes in the expenses introduced you can press "Delete Data" button, 
and select the expenses that you would like to delete from the showed list.

You can also save the expenses list.

When all the expenses are introduced and you want to know the results, just press "Show Results" button.

# Comments
(Last Update 09-05-2017) Created by Daniel Hornos Valiente.
This is not a finished application, it is 100% functional, but it will have some changes.
The application includes some console print messages that are not necessary for the software to execute and that 
they will be remove in the final version of the software.
*/

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class DemoApp {

	public static void main(String[] args) {
		DemoApp testApp = new DemoApp();
		testApp.startLoadingScreen();
		testApp.startNewOrOpenScreen();
	}

	private void startLoadingScreen(){
		try {
			JWindow window = new JWindow();
			window.getContentPane()
					.add(new JLabel("", new ImageIcon("images"+File.separator+"loadingScreen.gif"), SwingConstants.CENTER));
			window.setBounds(450, 300, 540, 304);
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			Thread.sleep(4500);
			window.dispose();
		} catch (InterruptedException e) {
			System.err.println("There was an error.");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void startNewOrOpenScreen(){
		StartNewOrOpenScreen fileChooser = new StartNewOrOpenScreen();
		fileChooser.run(550, 350);
	}
}