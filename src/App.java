import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.naming.event.NamingExceptionEvent;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.ImageIO;

public class App extends JFrame {
    private JButton acceptButton, deleteButton, showResultsButton, showListButton;
    private JTextField textName, textQuantity, textComment;
    private JTextArea textAreaList;
    private JLabel nameLabel, quantityLabel, commentLabel, statusBar;
    private JPanel buttonPanel, fieldPanel, fieldPanel1, fieldPanel2, fieldPanel3;

    private static Map<String, HashMap<Double, String>> nameList = new HashMap<>();
    private String userName;
    private double userQuantity = 0;
    private String paymentComment;
    private String moneyCurrency = "PLN";
    private ArrayList<String> arrayTextNameList;
    private ArrayList<String> resultArrayList;
    private ArrayList<String> arrayTextDeleteList;

    public void Align() {
        setTitle("App");

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

        // build button Introduce
        JButton acceptButton = new JButton();
        DesignButton(acceptButton);
        acceptButton.setText("Introduce");

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

        // build button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 5));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.add(acceptButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(showResultsButton);
        buttonPanel.add(showListButton);

        // set statusBar
        statusBar = new JLabel("Introduce data Example-> Name: Daniel; Quantity: 60; Comment: drinks");
        statusBar.setForeground(Color.white);
        textAreaList = new JTextArea();

        // set layout for applet

        JFrame f = new JFrame("Small app");
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images\\img.jpg")))));
        } catch (IOException e) {
            System.out.println("Image doesn't exist");
        }
        f.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        f.add(fieldPanel);
        f.add(buttonPanel);
        f.add(statusBar);
        f.add(textAreaList);
        f.setResizable(true);
        f.setSize(450, 300);
        f.setVisible(true);

        // action of the button Introduce
        acceptButton.addActionListener(e -> {
            insertData();
            statusBar.setText(String.format("Last data introduced.-> (%s paid %s %s for %s.)", textName.getText(),
                    textQuantity.getText(), moneyCurrency, textComment.getText()));

            arrayTextNameList.add(String.format("%s paid %s %s for %s.", textName.getText(), textQuantity.getText(),
                    moneyCurrency, textComment.getText()));

            arrayTextDeleteList.add(String.format("%s", textName.getText()));
            arrayTextDeleteList.add(String.format("%s", textQuantity.getText()));
            arrayTextDeleteList.add(String.format("%s", textComment.getText()));

            System.out.println("Has presionado el boton Introducir.");
            System.out.println(arrayTextDeleteList);
            textName.setText("");
            textQuantity.setText("");
            textComment.setText("");

            StringBuilder chars = new StringBuilder();
            for (int i = 0; i < arrayTextNameList.size(); i++) {
                chars.append(arrayTextNameList.get(i));
                chars.append("\n");
            } // end of FOR
            textAreaList.setText(String.format("List of data introduced:" + "\n" + "%s", chars));
            textAreaList.setEditable(false);
        });

        // action of the button Show List
        showListButton.addActionListener(e -> {
            StringBuilder chars = new StringBuilder();
            for (int i = 0; i < arrayTextNameList.size(); i++) {
                chars.append(arrayTextNameList.get(i));
                chars.append("\n");
            } // end of FOR
            JOptionPane.showMessageDialog(null, "The list of introduced data is:\n" + chars, "Introduced data",
                    JOptionPane.PLAIN_MESSAGE);

        });
        // action of the button Delete
        deleteButton.addActionListener(e -> {
            StringBuilder chars = new StringBuilder();
            chars.append("Write the number of the line that you want to delete\n");
            for (int i = 0; i < arrayTextNameList.size(); i++) {
                chars.append(String.format("%s) ", i + 1));
                chars.append(arrayTextNameList.get(i));
                chars.append("\n");
            } // end of FOR

            JFrame frame = new JFrame("Input dialog example");
            String StringuserDeleteRowNumber = JOptionPane.showInputDialog(frame, chars);
            int userDeleteRowNumber = Integer.parseInt(StringuserDeleteRowNumber);
            System.out.println(userDeleteRowNumber);

            String NameToDelete = arrayTextDeleteList.get(userDeleteRowNumber * 3 - 3);
            String QuantityToDelete = arrayTextDeleteList.get(userDeleteRowNumber * 3 - 2);
            String CommentToDelete = arrayTextDeleteList.get(userDeleteRowNumber * 3 - 1);

            int countNumberOfKeysRepeted = 0;
            for (int i = 0; i < arrayTextNameList.size(); i++) {
                if (arrayTextNameList.get(i).contains(NameToDelete) == true) {
                    countNumberOfKeysRepeted++;
                } else
                    continue;
            }
            if (countNumberOfKeysRepeted == 1) {
                nameList.remove(NameToDelete);
            } else {
                nameList.get(NameToDelete).values().remove(CommentToDelete); // This just delete de values but leaves the key

            }
            System.out.printf("The counter is: %s", countNumberOfKeysRepeted);
            System.out.printf("Print of the nameList: %s", nameList);

            arrayTextNameList.remove(userDeleteRowNumber - 1);
            arrayTextDeleteList.remove(userDeleteRowNumber * 3 - 1);
            arrayTextDeleteList.remove(userDeleteRowNumber * 3 - 2);
            arrayTextDeleteList.remove(userDeleteRowNumber * 3 - 3);

            statusBar.setText(String.format("Last data removed.-> (%s paid %s %s for %s.)", NameToDelete,
                    QuantityToDelete, moneyCurrency, CommentToDelete));

            chars.setLength(0);
            for (int i = 0; i < arrayTextNameList.size(); i++) {
                chars.append(arrayTextNameList.get(i));
                chars.append("\n");
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

    } // end of method Align

    public void insertData() {
        // This is to delete it, I only put it to not enter the data
        // manually each time.
        /*
         * nameList.put("Vero", new HashMap<Double, String>());
         * nameList.get("Vero").put(86., "Otros"); nameList.get("Vero").put(90.,
         * "Bebidas");
         * 
         * nameList.put("Radek", new HashMap<Double, String>());
         * nameList.get("Radek").put(34., "Gas");
         * 
         * nameList.put("Asia", new HashMap<Double, String>());
         * nameList.get("Asia").put(0., "Fiesta");
         * 
         * nameList.put("Daniel", new HashMap<Double, String>());
         * nameList.get("Daniel").put(54., "Carrefour");
         * nameList.get("Daniel").put(78., "Ticket cinema");
         */
        // From here starts the GOOD program to enter the data manually

        String userName = textName.getText();
        System.out.printf("The name introduced is: %s%n", userName);
        double userQuantity = Double.parseDouble(textQuantity.getText());
        System.out.printf("The quantity introduced is: %s%n", userQuantity);
        String paymentComment = textComment.getText();
        System.out.printf("The payment comment introduced is: %s%n", paymentComment);

        if (nameList.containsKey(userName)) {
            nameList.get(userName).put(userQuantity, paymentComment);
            System.out.printf("The namelist is: %s%n", nameList);
        } else {
            nameList.put(userName, new HashMap<Double, String>());
            nameList.get(userName).put(userQuantity, paymentComment);
            System.out.printf("The namelist is: %s%n", nameList);
        }

    } // end of the method introduceData

    public void calculateMoney(Map<String, HashMap<Double, String>> nameList) {

        double totalpagado = 0;
        double pagarCadaUno = 0;
        double totalPagadoTest = 0;

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

                totalpagado += childPair.getKey(); // This is the total paid by every user
 
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

                    // This converts the line written before in an arraylist called resultArrayList 


                    String result1 = String.format(String.format("%s have to pay %s %s %s for ",
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

                        // This converts the line written before in an arraylist called resultArrayList 

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
} // end of class App
