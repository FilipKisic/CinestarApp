package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author filip
 */
public class MovieTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", "Title", "Publish Date", "Duration", "Genre"};
    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getPublishDate();
            case 3:
                return movies.get(rowIndex).getDuration();
            case 4:
                return movies.get(rowIndex).getGenre();
        }
        throw new RuntimeException("Column does not exist.");
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return Integer.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }
}
