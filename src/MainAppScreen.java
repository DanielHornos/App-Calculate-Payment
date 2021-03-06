import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class MainAppScreen extends JFrame {

	private JButton addButton, deleteButton, showResultsButton, showListButton, saveButton, saveAsButton;
	private JTextField textName, textQuantity, textComment;
	private JTextArea textAreaList;
	private JLabel nameLabel, quantityLabel, commentLabel, statusBar;
	private JPanel buttonPanel, fieldPanel, fieldPanel1, fieldPanel2, fieldPanel3;
	private static Map<String, HashMap<Double, String>> nameList = new HashMap<>();
	private String moneyCurrency = "PLN";
	private String fileNameString, filePathString, fullFileStringPath;
	private ArrayList<String> resultArrayList;
	private JTable table;
	private DefaultTableModel model;
	private Scanner input;

	public void newTableData() {
		// Create table
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Name");
		model.addColumn("Quantity");
		model.addColumn("Comment");

		alignFrameComponents();
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
			input = new Scanner(Paths.get(filePath));
			while (input.hasNext()) {
				model.addRow(new Object[] { input.nextLine(), input.nextLine(), input.nextLine() });
			}
			System.out.printf("This line is just a test to see if it loaded correctly the file, number of rows: %s",
					model.getRowCount());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		alignFrameComponents();
	} // end of the method loadTableData

	private void alignFrameComponents() {

		buildTextfieldsPanel();

		buildButtonPanel();

		buildStatusBar();

		setLayoutForFrame("App Calculate Money", 550, 350);

		setActionOfButtons();
	}

	private void buildTextfieldsPanel() {

		// build field panel (name)
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
	}

	private void buildButtonPanel() {
		// build button Add
		addButton = new JButton();
		designStandardButton(addButton);
		addButton.setText("Add");

		// build button Delete
		deleteButton = new JButton();
		designStandardButton(deleteButton);
		deleteButton.setText("Delete Data");

		// build button Show Results
		showResultsButton = new JButton();
		designStandardButton(showResultsButton);
		showResultsButton.setText("Show Results");

		// build button Show list
		showListButton = new JButton();
		designStandardButton(showListButton);
		showListButton.setText("Show List");

		// build button Save
		saveButton = new JButton();
		designStandardButton(saveButton);
		saveButton.setText("Save");

		// build button Save As
		saveAsButton = new JButton();
		designStandardButton(saveAsButton);
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
	}

	private void buildStatusBar() {
		// set statusBar
		statusBar = new JLabel();
		statusBar.setText(
				"<html>"
				+ "<body>" 
						+ "<font color=black>How to introduce data?"
						+ "<br>(Example)-> Name: Daniel; Quantity: 63.7; Comment: tickets</font>" 
						+ "<br><font color=blue>"
						+ "<br>Instructions:"
						+ "<br>Add = Add the data written in the white fields."
						+ "<br>Delete Data = Open dialog to choose the data to delete."
						+ "<br>Show Result = Display the result."
						+ "<br>Show List = Display the data introduced until now."	
						+ "<br>Save = Save the data introduced into a file."
						+ "<br>Save as = Save the data introduced into a different file.</font>"				
				+ "</body>"
				+ "</html>");
		statusBar.setForeground(Color.white);
		textAreaList = new JTextArea();
		textAreaList.setEditable(false);
	}

	private void setLayoutForFrame(String titleOfTheFrame, int width, int height) {
		// set layout for JFrame
		JFrame f = new JFrame(titleOfTheFrame);
		try {
			f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images"+File.separator+"img.jpg")))));
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
		f.setSize(width, height);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private void setActionOfButtons() {
		// action of the button Add
		addButton.addActionListener(e -> {
			addButtonAction();
		});

		// action of the button Show List
		showListButton.addActionListener(e -> {
			showListButtonAction();
		});

		// action of the button Delete
		deleteButton.addActionListener(e -> {
			deleteButtonAction();
		});

		// action of the button Show results
		showResultsButton.addActionListener(e -> {
			showResultButtonAction();
		});

		// action of the button Save
		saveButton.addActionListener(e -> {
			writeToFile(fullFileStringPath);
		});

		// action of the button Save As
		saveAsButton.addActionListener(e -> {
			saveAsButtonAction();
		});
	}

	private void addButtonAction() {
		try {
			insertData();
			statusBar.setText(String.format("Last data introduced.-> (%s paid %s %s for %s.)", textName.getText(),
					textQuantity.getText(), moneyCurrency, textComment.getText()));

			System.out.println("Add button was pressed.");
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

			JOptionPane.showMessageDialog(panel,
					"The quantity introduced is not correct, please try again. (Example: 50.6)", "Incorrect quantity",
					JOptionPane.WARNING_MESSAGE);
			System.err.print("The quantity introduced is not correct, please try again.");
		}
	}

	private void showListButtonAction() {
		StringBuilder chars = new StringBuilder();
		for (int r = 0; r < model.getRowCount(); r++) {
			chars.append(model.getValueAt(r, 0));
			chars.append(" paid ");
			chars.append(model.getValueAt(r, 1));
			chars.append(" PLN for ");
			chars.append(model.getValueAt(r, 2));
			chars.append(".\n");
		}
		JOptionPane.showMessageDialog(null, "The list of introduced data is:\n" + chars, "Introduced data",
				JOptionPane.PLAIN_MESSAGE);

	}

	private void deleteButtonAction() {
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
		}
		try{
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
		}

		textAreaList.setText(String.format("List of data introduced:" + "\n" + "%s", chars));
		}catch (NumberFormatException e){
			System.err.println("It wasn't introduced any number");		
		}catch (ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(null, "The number introduced is not correct.\nPlease try again.", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		
		}

	private void showResultButtonAction() {
		calculateMoney(nameList);
		StringBuilder chars = new StringBuilder();
		for (int i = 0; i < resultArrayList.size(); i++) {
			chars.append(resultArrayList.get(i));
			chars.append(".");
			chars.append("\n");
		}
		JOptionPane.showMessageDialog(null, "The result is:\n" + chars, "Result", JOptionPane.PLAIN_MESSAGE);

	}

	private void saveAsButtonAction() {
		JFileChooser c = new JFileChooser();
		// Demonstrate "Save" dialog:
		int rVal = c.showSaveDialog(MainAppScreen.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			fileNameString = c.getSelectedFile().getName();
			filePathString = c.getCurrentDirectory().toString();
			System.out.printf("The selected file is: %s%n", fileNameString);
			System.out.printf("The selected path of the file is: %s%n", filePathString);

			fullFileStringPath = filePathString + File.separator + fileNameString;
			System.out.printf("The selected full file path is: %s%n", fullFileStringPath);

			writeToFile(fullFileStringPath);
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			System.out.println("You pressed cancel");
		}
	}

	private void insertData() {
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

	private void calculateMoney(Map<String, HashMap<Double, String>> nameList) {

		double totalPaidAmount = 0;
		double amountPayByEachOne = 0;
		double totalPaidTest = 0;

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

		// This is used to insert the values in the hashMap

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

				totalPaidAmount += childPair.getKey(); // This is the total paid
														// amount by every user

			} // end child parent
		} // end parent while

		amountPayByEachOne = totalPaidAmount / nameList.size();

		for (String key : nameList.keySet()) {

			double sumKeys = 0.0;
			for (double f : nameList.get(key).keySet()) {
				sumKeys += f;
			} // end of inner FOR

			eachOnePaidAmount.add(sumKeys);
			names.add(key);
			mapPayOrGetPaid.put(key, amountPayByEachOne - sumKeys);
			amountPayOrGetPaid.add(amountPayByEachOne - sumKeys);
			totalPaidTest += sumKeys;

		} // end of outer for
		System.out.printf("Total paid (including everybody): %s%n", totalPaidTest);
		amountPayByEachOne = totalPaidTest / nameList.size();
		System.out.printf("Amount that has to be paid (by each one): %s%n", amountPayByEachOne);

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

		nameList.clear();
	} // end of the method calculateMoney

	private void designStandardButton(JButton Button) {
		ImageIcon iconButton = new ImageIcon((((new ImageIcon("images"+File.separator+"OrangeSquareButton-100px.png").getImage()
				.getScaledInstance(100, 26, java.awt.Image.SCALE_SMOOTH)))));
		Button.setIcon(iconButton);
		Button.setBorder(null);
		Button.setBorderPainted(false);
		Button.setContentAreaFilled(false);
		Button.setFocusPainted(false);
		Button.setOpaque(false);
		Button.setHorizontalTextPosition(JButton.CENTER);
		Button.setVerticalTextPosition(JButton.CENTER);
		Button.setRolloverIcon(new ImageIcon("images"+File.separator+"OrangeSquareButton-100px(CLICK).png"));
	} // end of the method DesignButton

	private void writeToFile(String filePath) {
		System.out.printf("This line print the number of rows of model:%s%n", model.getRowCount());
		System.out.printf("This line print the fullFileStringPath: %s%n", fullFileStringPath);
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
			System.err.println(
					"There is no File selected, so user has to choose one or create new one. Error: " + e.getMessage());
			// action from Save As button
			JFileChooser c = new JFileChooser();
			// Demonstrate "Save" dialog:
			int rVal = c.showSaveDialog(MainAppScreen.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				fileNameString = c.getSelectedFile().getName();
				filePathString = c.getCurrentDirectory().toString();
				System.out.printf("The selected file is: %s%n", fileNameString);
				System.out.printf("The selected path of the file is: %s%n", filePathString);

				fullFileStringPath = filePathString + File.separator + fileNameString;
				System.out.printf("The selected full file path is: %s%n", fullFileStringPath);

				writeToFile(fullFileStringPath);
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("You pressed cancel");
			}
		}
	} // end of method writeToFile

} // end of class App
