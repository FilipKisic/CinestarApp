package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author filip
 */
public class ActorTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", "FirstName", "LastName"};
    private List<Actor> actors;

    public ActorTableModel(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public int getRowCount() {
        return actors.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return actors.get(rowIndex).getId();
            case 1:
                return actors.get(rowIndex).getFirstName();
            case 2:
                return actors.get(rowIndex).getLastName();
        }
        throw new RuntimeException("Column does not exist.");
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
        fireTableDataChanged();
    }
}
