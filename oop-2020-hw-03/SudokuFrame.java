import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame{

 	private JTextArea input;
	 private JTextArea output;
	 private JCheckBox checkBox;
	
	public SudokuFrame() {
		super("Sudoku Solver");
		
		// YOUR CODE HERE
		JPanel panel = new JPanel();
		BorderLayout borderLayout = new BorderLayout(4, 4);
		panel.setLayout(borderLayout);

		input = new JTextArea(15, 20);
		Document inputDocument = input.getDocument();
		inputDocument.addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(checkBox.isSelected()){
					check();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(checkBox.isSelected()){
					check();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(checkBox.isSelected()){
					check();
				}
			}
		});

		output = new JTextArea(15, 20);
		input.setBorder(new TitledBorder("Puzzle"));
		output.setBorder(new TitledBorder("Solution"));

		panel.add(input);
		panel.add(output, BorderLayout.EAST);

		JPanel southPanel = getSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

		add(panel);
		// Could do this:
		setLocationRelativeTo(null);

		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

	private JPanel getSouthPanel(){
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});
		checkBox = new JCheckBox("Auto Check");
		checkBox.setSelected(true);

		southPanel.add(checkButton, BorderLayout.SOUTH);
		southPanel.add(checkBox, BorderLayout.SOUTH);

		return southPanel;
	}


	 private void check(){
		 try {
			 Sudoku sudoku = new Sudoku(input.getText());
			 int solutions = sudoku.solve();
			 output.setText(sudoku.getSolutionText() + "Solutions: " + solutions + "\ntime elapsed: " + sudoku.getElapsed());
		 } catch (Exception ex){
			 output.setText("Invalid input");
		 }
	 }
 }
