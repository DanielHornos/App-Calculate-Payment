import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartNewOrOpenScreen extends JFrame{
//    private JFrame frame = new JFrame();
//    private JTextField filename = new JTextField(), dir = new JTextField();
    private JButton openButton, newButton;
    private String fileNameString, filePathString, fullFileStringPath;
    private JPanel panelButtons;
    private JFrame frame2;

    public void run(int width, int height) {

    	buildPanelWithButtons();
        setLayoutForFrame("App Calculate Money", width, height);
       
        // add action for the New button
        newButton.addActionListener(e -> {
        	newButtonAction();
        }); // end of the ActionListener for newButton

        // add action for the Open button
        openButton.addActionListener(e -> {
        	openButtonAction();
        }); // end of the ActionListener for OpenButton

    } // end of the method run

    private void designNewButton(JButton newButton) {
        ImageIcon iconNewButton = new ImageIcon((((new ImageIcon("images"+File.separator+"buttonnew.png").getImage().getScaledInstance(100,
                100, java.awt.Image.SCALE_SMOOTH)))));
        newButton.setIcon(iconNewButton);
        newButton.setForeground(Color.WHITE);
        newButton.setBorder(null);
        newButton.setBorderPainted(false);
        newButton.setContentAreaFilled(false);
        newButton.setFocusPainted(false);
        newButton.setOpaque(false);
        newButton.setHorizontalTextPosition(JButton.CENTER);
        newButton.setVerticalTextPosition(JButton.CENTER);
        newButton.setRolloverIcon(new ImageIcon((((new ImageIcon("images"+File.separator+"buttonnew (CLICKED).png").getImage()
                .getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH))))));
    } // end of the method DesignButtonNew

    private void designOpenButton(JButton openButton) {
        ImageIcon iconOpenButton = new ImageIcon((((new ImageIcon("images"+File.separator+"buttonload.png").getImage()
                .getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH)))));
        openButton.setIcon(iconOpenButton);
        openButton.setForeground(Color.WHITE);
        openButton.setBorder(null);
        openButton.setBorderPainted(false);
        openButton.setContentAreaFilled(false);
        openButton.setFocusPainted(false);
        openButton.setOpaque(false);
        openButton.setHorizontalTextPosition(JButton.CENTER);
        openButton.setVerticalTextPosition(JButton.CENTER);
        openButton.setRolloverIcon(new ImageIcon((((new ImageIcon("images"+File.separator+"buttonload (CLICKED).png").getImage()
               .getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH))))));
    } // end of the method DesignButtonLoad
    
    private void openButtonAction(){
        /*
         * AppWithTables appgui = new AppWithTables(); 
         * appgui.loadTableData(
         * "C:\\Users\\daniel.hornos\\Documents\\output.txt");
         */

        JFileChooser fileChooser = new JFileChooser();
        // Demonstrate "Open" dialog:
        int rVal = fileChooser.showOpenDialog(StartNewOrOpenScreen.this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            fileNameString = fileChooser.getSelectedFile().getName();
            filePathString = fileChooser.getCurrentDirectory().toString();
            
            System.out.printf("The selected file is: %s%n", fileNameString);
            System.out.printf("The selected path of the file is: %s%n", filePathString);

            fullFileStringPath = filePathString + File.separator + fileNameString;
            System.out.printf("The selected full file path is: %s%n", fullFileStringPath);

            frame2.dispose();
            MainAppScreen appgui = new MainAppScreen();
            appgui.loadTableData(fullFileStringPath);
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            System.out.println("You pressed cancel");
        }
    }

    private void newButtonAction(){
        frame2.dispose();
        MainAppScreen appgui = new MainAppScreen();
        appgui.newTableData();
    }

    private void buildPanelWithButtons(){
    	// build button New File
        newButton = new JButton();
        designNewButton(newButton);
        newButton.setText("New File");

        // build button Open File
        openButton = new JButton();
        designOpenButton(openButton);
        openButton.setText("Open File");

        // build panel with buttons
        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(2, 1, 10, 5));
        panelButtons.setBackground(new Color(0, 0, 0, 0));
        panelButtons.add(newButton);
        panelButtons.add(openButton);
    }
    
    private void setLayoutForFrame(String titleOfTheFrame, int width, int height){
    	 // set layout for JFrame
        frame2 = new JFrame(titleOfTheFrame);

        try {
            frame2.setContentPane(
                    new JLabel(new ImageIcon(ImageIO.read(new File("images"+File.separator+"BackgroundLoadScreen.jpg")))));
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
    }
} // End of class
