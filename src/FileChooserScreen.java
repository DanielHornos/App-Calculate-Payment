import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.*;
import javax.imageio.*;
import javax.imageio.ImageIO;

public class FileChooserScreen extends JFrame {
    // private JFrame frame = new JFrame();
    private JTextField filename = new JTextField(), dir = new JTextField();
    private JButton openButton, saveButton, newButton;
    private String fileNameString, filePathString, fullFileStringPath;

    public void run(JFrame frame, int width, int height) {

        // build button New File
        JButton newButton = new JButton();
        DesignButtonNew(newButton);
        newButton.setText("New File");

        // build button Open File
        JButton openButton = new JButton();
        DesignButtonLoad(openButton);
        openButton.setText("Open File");

        // build panel with buttons
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(2, 1, 10, 5));
        panelButtons.setBackground(new Color(0, 0, 0, 0));
        panelButtons.add(newButton);
        panelButtons.add(openButton);

        // set layout for JFrame
        JFrame frame2 = new JFrame("App Calculate Money");

        try {
            frame2.setContentPane(
                    new JLabel(new ImageIcon(ImageIO.read(new File("images\\BackgroundLoadScreen.jpg")))));
        } catch (IOException e) {
            System.out.println("Image doesn't exist");
        }

        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        frame2.add(panelButtons);
        frame2.setResizable(true);
        frame2.setSize(width, height);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);

        // add action for de New button
        newButton.addActionListener(e -> {
            frame2.dispose();
            AppWithTables appgui = new AppWithTables();
            appgui.newTableData();

        }); // end of the ActionListener for newButton

        // add action for de Open button
        openButton.addActionListener(e -> {

            /*
             * AppWithTables appgui = new AppWithTables(); 
             * appgui.loadTableData(
             * "C:\\Users\\daniel.hornos\\Documents\\output.txt");
             */

            JFileChooser fileChooser = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = fileChooser.showOpenDialog(FileChooserScreen.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                fileNameString = fileChooser.getSelectedFile().getName();
                filePathString = fileChooser.getCurrentDirectory().toString();
                
                System.out.printf("The selected file is: %s%n", fileNameString);
                System.out.printf("The selected path of the file is: %s%n", filePathString);

                fullFileStringPath = filePathString + File.separator + fileNameString;
                System.out.printf("The selected full file path is: %s%n", fullFileStringPath);

                frame2.dispose();
                AppWithTables appgui = new AppWithTables();
                appgui.loadTableData(fullFileStringPath);
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                System.out.println("You pressed cancel");
            }

        }); // end of the ActionListener for OpenButton

    } // end of the method run

    public void DesignButtonNew(JButton Button) {
        ImageIcon iconButton = new ImageIcon((((new ImageIcon("images\\buttonnew.png").getImage().getScaledInstance(100,
                100, java.awt.Image.SCALE_SMOOTH)))));
        Button.setIcon(iconButton);
        Button.setForeground(Color.WHITE);
        Button.setBorder(null);
        Button.setBorderPainted(false);
        Button.setContentAreaFilled(false);
        Button.setFocusPainted(false);
        Button.setOpaque(false);
        Button.setHorizontalTextPosition(JButton.CENTER);
        Button.setVerticalTextPosition(JButton.CENTER);
        Button.setRolloverIcon(new ImageIcon((((new ImageIcon("images\\buttonnew (CLICKED).png").getImage()
                .getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH))))));
    } // end of the method DesignButtonNew

    public void DesignButtonLoad(JButton Button) {
        ImageIcon iconButton = new ImageIcon((((new ImageIcon("images\\buttonload.png").getImage()
                .getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH)))));
        Button.setIcon(iconButton);
        Button.setForeground(Color.WHITE);
        Button.setBorder(null);
        Button.setBorderPainted(false);
        Button.setContentAreaFilled(false);
        Button.setFocusPainted(false);
        Button.setOpaque(false);
        Button.setHorizontalTextPosition(JButton.CENTER);
        Button.setVerticalTextPosition(JButton.CENTER);
        Button.setRolloverIcon(new ImageIcon((((new ImageIcon("images\\buttonload (CLICKED).png").getImage()
                .getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH))))));
    } // end of the method DesignButtonLoad
} // End of class
