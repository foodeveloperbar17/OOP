import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MetropolisViewer extends JFrame {
    private JTextField metropolisesTextField;
    private JTextField continentTextField;
    private JTextField populationTextField;

    private JButton addButton;
    private JButton searchButton;

    private JTable metropolisesTable;
    private MetropolisesTableModel tableModel;

    private MetropolisesDatabase database = new MetropolisesDatabase();

    private JComboBox matchComboBox;
    private JComboBox populationComboBox;


    private static final String FRAME_TITLE = "Metropolis Viewer";
    private static final String POPULATION_LARGER = "Population Larger Than";
    private static final String POPULATION_SMALLER = "Population Less Than";
    private static final String TEXTS_EXACT_MATCH = "Exact Match";
    private static final String TEXTS_PARTIAL_MATCH = "Partial Match";
    private static final int COLUMN_COUNT = 3;

    public MetropolisViewer(int width, int height) {
        super(FRAME_TITLE);

        setLayout(new BorderLayout(10, 10));

        addNorthPanel();
        addEastPanel();
        addCenterPanel();

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MetropolisViewer(800, 600);
    }

    private void addNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        add(northPanel, BorderLayout.NORTH);

        northPanel.add(new JLabel("Metropolises: "));
        metropolisesTextField = new JTextField(10);
        northPanel.add(metropolisesTextField);

        northPanel.add(new JLabel("Continent: "));
        continentTextField = new JTextField(10);
        northPanel.add(continentTextField);

        northPanel.add(new JLabel("Population: "));
        populationTextField = new JTextField(10);
        northPanel.add(populationTextField);

    }

    private void addEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        add(eastPanel, BorderLayout.EAST);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] curr = new String[COLUMN_COUNT];
                curr[0] = metropolisesTextField.getText();
                curr[1] = continentTextField.getText();
                curr[2] = populationTextField.getText();
                database.addMetropolis(curr);
                List<String[]> oneRowList = new ArrayList<>();
                oneRowList.add(curr);
                tableModel.updateTable(oneRowList);
            }
        });
        eastPanel.add(addButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.updateTable(database.getMetropolises(getWhereClause()));
            }
        });
        eastPanel.add(searchButton);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
        searchPanel.setBorder(new TitledBorder("Search Options"));
        populationComboBox = new JComboBox();
        populationComboBox.addItem(POPULATION_LARGER);
        populationComboBox.addItem(POPULATION_SMALLER);
        searchPanel.add(populationComboBox);

        matchComboBox = new JComboBox();
        matchComboBox.addItem(TEXTS_EXACT_MATCH);
        matchComboBox.addItem(TEXTS_PARTIAL_MATCH);
        searchPanel.add(matchComboBox);

        eastPanel.add(searchPanel);
    }

    private void addCenterPanel() {
        metropolisesTable = new JTable(4, 3);
        tableModel = new MetropolisesTableModel();
        metropolisesTable.setModel(tableModel);
        add(metropolisesTable);
    }

    private String getWhereClause() {
        String result = "where true";
        if (!populationTextField.getText().equals("")) {
            String populationSelection = (String) populationComboBox.getSelectedItem();
            if(populationSelection.equals(POPULATION_LARGER)){
                result += " and population > " + populationTextField.getText();
            } else{
                result += " and population < " + populationTextField.getText();
            }
        }
        String matchSelection = (String) matchComboBox.getSelectedItem();
        if(!metropolisesTextField.getText().equals("")){
            if(matchSelection.equals(TEXTS_EXACT_MATCH)){
                result += " and metropolis like '" + metropolisesTextField.getText() + "'";
            } else{
                result += " and metropolis like '%" + metropolisesTextField.getText() + "%'";
            }
        }
        if(!continentTextField.getText().equals("")){
            if(matchSelection.equals(TEXTS_EXACT_MATCH)){
                result += " and continent like '" + continentTextField.getText() + "'";
            } else{
                result += " and continent like '%" + continentTextField.getText() + "%'";
            }
        }
        System.out.println(result);
        return result;
    }
}
