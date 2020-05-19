import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MetropolisesTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"Metropolis", "Continent", "Population"};
    private static final int COLUMN_COUNT = 3;
    private List<String[]> lists;

    public MetropolisesTableModel(List<String[]> lists){
        this.lists = lists;
    }

    public MetropolisesTableModel(){
    }

    @Override
    public int getRowCount() {
        if (lists == null){
            return 0;
        }
        return lists.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return lists.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void updateTable(List<String[]> lists){
        this.lists = lists;
        fireTableDataChanged();
    }
}
