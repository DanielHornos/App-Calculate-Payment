import java.awt.*;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import javax.naming.event.NamingExceptionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.sun.glass.events.MouseEvent;

import java.io.*;
import java.nio.file.Paths;

import javax.imageio.*;
import javax.imageio.ImageIO;

public class AppWithTables extends JFrame {
    private JButton acceptButton, deleteButton, showResultsButton, showListButton, saveButton, saveAsButton;
    private JTextField textName, textQuantity, textComment;
    private JTextArea textAreaList;
    private JLabel nameLabel, quantityLabel, commentLabel, statusBar;
    private JPanel buttonPanel, fieldPanel, fieldPanel1, fieldPanel2, fieldPanel3;

    private static Map<String, HashMap<Double, String>> nameList = new HashMap<>();
    private String userName;
    private double userQuantity = 0;
    private String paymentComment;
    private String moneyCurrency = "PLN";
    private String fileName, filePath;
    private String fileNameString, filePathString, fullFileStringPath;
    private JTextField filename = new JTextField(), dir = new JTextField();
    private ArrayList<String> arrayTextNameList;
    private ArrayList<String> resultArrayList;
    private ArrayList<String> arrayTextDeleteList;

    private JTable table;
    private DefaultTableModel model;
    private static Scanner input;

    public void Align() {

        // build field panel (name)
        arrayTextNameList = new ArrayList<>();
        arrayTextDeleteList = new ArrayList<>();
        nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.white);
        textName = new JTextField("", 10);
        fieldPanel1 = new JPanel();
        fieldPanel1.setLayout(new FlowLayout());
        fieldPanel1.setBackground(new Color(0, 0, 0, 0));
        fieldPanel1.add(nameLabel);
        fieldPanel1.add(textName);

        // build field panel2 (quantity)
        quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setForeground(Color.white);
        textQuantity = new JTextField("", 10);
        fieldPanel2 = new JPanel();
        fieldPanel2.setLayout(new FlowLayout());
        fieldPanel2.setBackground(new Color(0, 0, 0, 0));
        fieldPanel2.add(quantityLabel);
        fieldPanel2.add(textQuantity);

        // build field panel3 (comment)
        commentLabel = new JLabel("Comment: ");
        commentLabel.setForeground(Color.white);
        textComment = new JTextField("", 10);
        fieldPanel3 = new JPanel();
        fieldPanel3.setLayout(new FlowLayout());
        fieldPanel3.setBackground(new Color(0, 0, 0, 0));
        fieldPanel3.add(commentLabel);
        fieldPanel3.add(textComment);

        // build field panels (include panel1, panel2 and panel3)
        fieldPanel = new JPanel();
        fieldPanel.setLayout(new BorderLayout());
        fieldPanel.setBackground(new Color(0, 0, 0, 0));
        fieldPanel.add(fieldPanel1, BorderLayout.NORTH);
        fieldPanel.add(fieldPanel2, BorderLayout.CENTER);
        fieldPanel.add(fieldPanel3, BorderLayout.SOUTH);

        // build button Add
        JButton addButton = new JButton();
        DesignButton(addButton);
        addButton.setText("Add");

        // build button Delete
        JButton deleteButton = new JButton();
        DesignButton(deleteButton);
        deleteButton.setText("Delete Data");

        // build button Show Results
        JButton showResultsButton = new JButton();
        DesignButton(showResultsButton);
        showResultsButton.setText("Show Results");

        // build button Show list
        JButton showListButton = new JButton();
        DesignButton(showListButton);
        showListButton.setText("Show List");

        // build button Save
        JButton saveButton = new JButton();
        DesignButton(saveButton);
        saveButton.setText("Save");

        // build button Save As
        JButton saveAsButton = new JButton();
        DesignButton(saveAsButton);
        saveAsButton.setText("Save As");

        // build button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 5));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(showResultsButton);
        buttonPanel.add(showListButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);

        // set statusBar
        statusBar = new JLabel("Introduce data Example-> Name: Daniel; Quantity: 60; Comment: drinks");
        statusBar.setForeground(Color.white);
        textAreaList = new JTextArea();
        textAreaList.setEditable(false);

        // set layout for JFrame
        JFrame f = new JFrame("App Calculate Money");
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images\\img.jpg")))));
        } catch (IOException e) {
            System.out.println("Image doesn't exist");
        }
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        f.add(fieldPanel);
        f.add(buttonPanel);
        f.add(statusBar);
        f.add(textAreaList);
        f.setResizable(true);
        f.setSize(450, 300);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        // action of the button Add
        addButton.addActionListener(e -> {
            try {
                insertData();
                statusBar.setText(String.format("Last data introduced.-> (%s paid %s %s for %s.)", textName.getText(),
                        textQuantity.getText(), moneyCurrency, textComment.getText()));

                System.out.println("Has presionado el boton Introducir.");
                textName.setText("");
                textQuantity.setText("");
                textComment.setText("");

                StringBuilder chars = new StringBuilder();
                for (int r = 0; r < model.getRowCount(); r++) {
                    chars.append(model.getValueAt(r, 0));
                    chars.append(" paid ");
                    chars.append(model.getValueAt(r, 1));
                    chars.append(" PLN for ");
                    chars.append(model.getValueAt(r, 2));
                    chars.append(".\n");
                } // end of FOR
                textAreaList.setText(String.format("List of data introduced:" + "\n" + "%s", chars));

            } // end of try
            catch (NumberFormatException ex) {
                final JPanel panel = new JPanel();

                JOptionPane.showMessageDialog(panel, "The quantity introduced is not correct, please try again.",
                        "Incorrect quantity", JOptionPane.WARNING_MESSAGE);
                System.err.print("The quantity introduced is not correct, please try again.");
            }
        });

        // action of the button Show List
        showListButton.addActionListener(e -> {
            StringBuilder chars = new StringBuilder();
            for (int r = 0; r < model.getRowCount(); r++) {
                chars.append(model.getValueAt(r, 0));
                chars.append(" paid ");
                chars.append(model.getValueAt(r, 1));
                chars.append(" PLN for ");
                chars.append(model.getValueAt(r, 2));
                chars.append(".\n");
            } // end of FOR
            JOptionPane.showMessageDialog(null, "The list of introduced data is:\n" + chars, "Introduced data",
                    JOptionPane.PLAIN_MESSAGE);
        });

        // action of the button Delete
        deleteButton.addActionListener(e -> {
            StringBuilder chars = new StringBuilder();
            chars.append("Write the number of the line that you want to delete\n");
            for (int r = 0; r < model.getRowCount(); r++) {
                chars.append(r + 1);
                chars.append(") ");
                chars.append(model.getValueAt(r, 0));
                chars.append(" has to pay ");
                chars.append(model.getValueAt(r, 1));
                chars.append(" PLN for ");
                chars.append(model.getValueAt(r, 2));
                chars.append(".\n");
            } // end of FOR

            JFrame frame = new JFrame("Input dialog example");
            String StringuserDeleteRowNumber = JOptionPane.showInputDialog(frame, chars);
            int userDeleteRowNumber = Integer.parseInt(StringuserDeleteRowNumber);
            System.out.println(userDeleteRowNumber);

            statusBar.setText(String.format("Last data removed.-> (%s paid %s %s for %s.)",
                    model.getValueAt(userDeleteRowNumber - 1, 0), model.getValueAt(userDeleteRowNumber - 1, 1),
                    moneyCurrency, model.getValueAt(userDeleteRowNumber - 1, 2)));

            model.removeRow(userDeleteRowNumber - 1);

            chars.setLength(0);
            for (int r = 0; r < model.getRowCount(); r++) {

                chars.append(model.getValueAt(r, 0));
                chars.append(" has to pay ");
                chars.append(model.getValueAt(r, 1));
                chars.append(" PLN for ");
                chars.append(model.getValueAt(r, 2));
                chars.append(".\n");
            } // end of FOR

            textAreaList.setText(String.format("List of data introduced:" + "\n" + "%s", chars));

        });

        // action of the button Show results
        showResultsButton.addActionListener(e -> {
            calculateMoney(nameList);
            StringBuilder chars = new StringBuilder();
            for (int i = 0; i < resultArrayList.size(); i++) {
                chars.append(resultArrayList.get(i));
                chars.append(".");
                chars.append("\n");

            }
            JOptionPane.showMessageDialog(null, "The result is:\n" + chars, "Result", JOptionPane.PLAIN_MESSAGE);
        });

        // action of the button Save
        saveButton.addActionListener(e -> {
            writeToFile(fullFileStringPath);
        });

        // action of the button Save As
        saveAsButton.addActionListener(e -> {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(AppWithTables.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
                fileNameString = filename.getText();
                filePathString = dir.getText();
                System.out.printf("The selected file is: %s%n", fileNameString);
                System.out.printf("The selected path of the file is: %s%n", filePathString);
                System.out.println("Test para ver si imprime");

                fullFileStringPath = filePathString + File.separator + fileNameString;
                System.out.printf("The selected full file path is: %s%n", fullFileStringPath);

                writeToFile(fullFileStringPath);
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        });
    } // end of method Align

    public void insertData() {
        /*
         * model.addRow(new Object[] { "Antonio", "50", "drinks" });
         * model.addRow(new Object[] { "Asia", "20", "food" }); model.addRow(new
         * Object[] { "Vero", "95", "party" }); model.addRow(new Object[] {
         * "Asia", "70", "tickets cinema" });
         */

        String userName = textName.getText();
        System.out.printf("The name introduced is: %s%n", userName);
        double userQuantity = Double.parseDouble(textQuantity.getText());
        System.out.printf("The quantity introduced is: %s%n", userQuantity);
        String paymentComment = textComment.getText();
        System.out.printf("The payment comment introduced is: %s%n", paymentComment);

        model.addRow(new Object[] { userName, userQuantity, paymentComment });

    } // end of the method introduceData

    public void calculateMoney(Map<String, HashMap<Double, String>> nameList) {

        double totalpagado = 0;
        double pagarCadaUno = 0;
        double totalPagadoTest = 0;

        for (int r = 0; r < model.getRowCount(); r++) {

            String userName = model.getValueAt(r, 0).toString();
            Double userQuantity = Double.parseDouble(model.getValueAt(r, 1).toString());
            String paymentComment = model.getValueAt(r, 2).toString();

            if (nameList.containsKey(userName)) {
                nameList.get(userName).put(userQuantity, paymentComment);
                System.out.printf("The namelist is: %s%n", nameList);
            } else {
                nameList.put(userName, new HashMap<Double, String>());
                nameList.get(userName).put(userQuantity, paymentComment);
                System.out.printf("The namelist is: %s%n", nameList);
            }
        } // end of FOR

        // esto se usa en App para insertar los valores en el hashMap

        ArrayList<Double> eachOnePaidAmount = new ArrayList<Double>(nameList.size());
        ArrayList<String> names = new ArrayList<>(nameList.size());
        Map<String, Double> mapPayOrGetPaid = new LinkedHashMap<>();
        ArrayList<Double> amountPayOrGetPaid = new ArrayList<>(nameList.size());

        Iterator<Entry<String, HashMap<Double, String>>> parent = nameList.entrySet().iterator();
        while (parent.hasNext()) {
            Entry<String, HashMap<Double, String>> parentPair = parent.next();

            Iterator<Entry<Double, String>> child = (parentPair.getValue()).entrySet().iterator();
            while (child.hasNext()) {
                Entry<Double, String> childPair = child.next();

                totalpagado += childPair.getKey(); // This is the total paid by
                                                   // every user

            } // end child parent
        } // end parent while

        pagarCadaUno = totalpagado / nameList.size();

        for (String key : nameList.keySet()) {

            double sumKeys = 0.0;
            for (double f : nameList.get(key).keySet()) {
                sumKeys += f;
            } // end of inner FOR

            eachOnePaidAmount.add(sumKeys);
            names.add(key);
            mapPayOrGetPaid.put(key, pagarCadaUno - sumKeys);
            amountPayOrGetPaid.add(pagarCadaUno - sumKeys);
            totalPagadoTest += sumKeys;

        } // end of outer for
        System.out.printf("Total paid (including everybody): %s%n", totalPagadoTest);
        pagarCadaUno = totalPagadoTest / nameList.size();
        System.out.printf("Amount that has to be paid (by each one): %s%n", pagarCadaUno);

        resultArrayList = new ArrayList<>();
        ArrayList<Integer> hasToPay = new ArrayList<>();
        ArrayList<Integer> hasToGetPay = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            if (amountPayOrGetPaid.get(i) > 0) {
                hasToPay.add(i);
            } // end of IF
            else
                hasToGetPay.add(i);
        } // end of FOR

        // This is used to show just 2 decimals
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (int i = 0; i < hasToPay.size(); i++) {

            for (int x = 0; x < hasToGetPay.size(); x++) {
                if (amountPayOrGetPaid.get(hasToPay.get(i)) != 0
                        && amountPayOrGetPaid.get(hasToPay.get(i)) >= Math
                                .abs(amountPayOrGetPaid.get(hasToGetPay.get(x)))
                        && amountPayOrGetPaid.get(hasToGetPay.get(x)) != 0.0) {

                    System.out.printf("%s has to pay %s %s %s of %s%n", names.get(hasToPay.get(i)),
                            names.get(hasToGetPay.get(x)), Math.abs(amountPayOrGetPaid.get(hasToGetPay.get(x))),
                            moneyCurrency, nameList.get(names.get(hasToGetPay.get(x))).values());

                    // This converts the line written before in an arraylist
                    // called resultArrayList

                    String result1 = String.format(String.format("%s has to pay %s %s %s for ",
                            names.get(hasToPay.get(i)), names.get(hasToGetPay.get(x)),
                            df.format(Math.abs(amountPayOrGetPaid.get(hasToGetPay.get(x)))), moneyCurrency));
                    result1 = result1.replace("[", "").replace("]", "");
                    String result2 = String.format("%s", nameList.get(names.get(hasToGetPay.get(x))).values());
                    result2 = result2.replace(",", " &").replace("[", "").replace("]", "");
                    String result = result1 + result2;
                    resultArrayList.add(result);

                    amountPayOrGetPaid.set(hasToPay.get(i),
                            amountPayOrGetPaid.get(hasToPay.get(i)) + amountPayOrGetPaid.get(hasToGetPay.get(x)));

                    amountPayOrGetPaid.set(hasToGetPay.get(x), 0.0);
                } // end of IF
                else {
                    if (amountPayOrGetPaid.get(hasToGetPay.get(x)) != 0.0
                            && amountPayOrGetPaid.get(hasToPay.get(i)) != 0) {

                        System.out.printf("%s has to pay %s %s %s for %s%n", names.get(hasToPay.get(i)),
                                names.get(hasToGetPay.get(x)), Math.abs(amountPayOrGetPaid.get(hasToPay.get(i))),
                                moneyCurrency, nameList.get(names.get(hasToGetPay.get(x))).values());

                        // This converts the line written before in an arraylist
                        // called resultArrayList

                        String result1 = String.format("%s has to pay %s %s %s for ", names.get(hasToPay.get(i)),
                                names.get(hasToGetPay.get(x)),
                                df.format(Math.abs(amountPayOrGetPaid.get(hasToPay.get(i)))), moneyCurrency);
                        result1 = result1.replace("[", "").replace("]", "");
                        String result2 = String.format("%s", nameList.get(names.get(hasToGetPay.get(x))).values());
                        result2 = result2.replace(",", " &").replace("[", "").replace("]", "");
                        String result = result1 + result2;
                        resultArrayList.add(result);

                        amountPayOrGetPaid.set(hasToGetPay.get(x),
                                amountPayOrGetPaid.get(hasToGetPay.get(x)) + amountPayOrGetPaid.get(hasToPay.get(i)));

                        amountPayOrGetPaid.set(hasToPay.get(i), 0.0);

                    } // end of IF
                    else
                        continue;
                }
            } // end of inner FOR
        } // end of outer FOR

    } // end of the method calculateMoney

    public void DesignButton(JButton Button) {
        ImageIcon iconButton = new ImageIcon((((new ImageIcon("images\\OrangeSquareButton-100px.png").getImage()
                .getScaledInstance(100, 26, java.awt.Image.SCALE_SMOOTH)))));
        Button.setIcon(iconButton);
        Button.setBorder(null);
        Button.setBorderPainted(false);
        Button.setContentAreaFilled(false);
        Button.setFocusPainted(false);
        Button.setOpaque(false);
        Button.setHorizontalTextPosition(JButton.CENTER);
        Button.setVerticalTextPosition(JButton.CENTER);
        Button.setRolloverIcon(new ImageIcon("images\\OrangeSquareButton-100px(CLICK).png"));
    } // end of the method DesignButton

    public void setFileName(String filename) {
        System.out.printf("Esto es para ver si imprime el filename desde APP: %s%n", filename);
        this.fileName = filename;
        System.out.printf("Esto es para ver si imprime el fileName desde APP: %s%n", fileName);
        // getFileName();
        // System.out.printf("Esto es para ver si imprime el getFileName() desde
        // APP: %s%n", getFileName());
    }

    public void newTableData() {
        // Create table
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Comment");

        Align();
    }

    public void loadTableData(String filePath) {

        this.fullFileStringPath = filePath;

        // Create table
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Comment");

        try {
            // FileReader freader = new FileReader("out.txt");
            input = new Scanner(Paths.get(filePath));

            while (input.hasNext()) {
                model.addRow(new Object[] { input.nextLine(), input.nextLine(), input.nextLine() });
            }
            System.out.printf("Esto es solo una prueba para ver si se ha pasado la lista oout.txt: %s",
                    model.getRowCount());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        // writeToFile("out.txt");
        Align();
    } // end of the method loadTableData

    public void writeToFile(String filePath) {
        System.out.printf("Esto imprime el numero de filas de model:%s%n", model.getRowCount());
        System.out.printf("Este es el fullFileStringPath impreso: %s%n", fullFileStringPath);
        try {
            FileWriter fstream = new FileWriter(filePath);
            BufferedWriter out = new BufferedWriter(fstream);
            for (int row = 0; row < model.getRowCount(); row++) {
                out.write(model.getValueAt(row, 0).toString() + System.getProperty("line.separator"));
                out.write(model.getValueAt(row, 1).toString() + System.getProperty("line.separator"));
                out.write(model.getValueAt(row, 2).toString() + System.getProperty("line.separator"));
            }
            statusBar.setText("Data list was saved into the location: " + System.lineSeparator() + fullFileStringPath);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // action from Save As button
            JFileChooser c = new JFileChooser();
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(AppWithTables.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
                fileNameString = filename.getText();
                filePathString = dir.getText();
                System.out.printf("The selected file is: %s%n", fileNameString);
                System.out.printf("The selected path of the file is: %s%n", filePathString);
                System.out.println("Test para ver si imprime");

                fullFileStringPath = filePathString + File.separator + fileNameString;
                System.out.printf("The selected full file path is: %s%n", fullFileStringPath);

                writeToFile(fullFileStringPath); // esta no se si es necesaria
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        }
    } // end of method writeToFile

} // end of class App
