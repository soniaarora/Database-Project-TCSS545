
package college.dbProject.student;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


/**
 * A custom table model for the Faculty Course Query.
 * 
 
 *
 */
@SuppressWarnings("serial")
public class MyTableModel extends AbstractTableModel implements TableModelListener {
	
	public static String [][] tableData = new String [40][7];
	public static String [] columnNames = {"StudentID", "LastName", "FirstName", "CourseID", "Grade"};
	public static int updatedRow;
	
	public MyTableModel() {
		addTableModelListener(this);
	}
	
	public String getColumnNames (int col) {
		return columnNames[col].toString();
	}
	
	
	/**
	 * @param row a row in the table
	 * @param col a column in the table
	 * @return true if it is the 5th column
	 */
	@Override
	public boolean isCellEditable (int row, int col) {
		if (col == 6) {
			return true;
		} else {
			return false;
		}	
	}
	
	/**
	 * 
	 * @param value
	 * @param row
	 * @param col
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		tableData [row][col] = (String) value;
		fireTableCellUpdated(row, col);
		
	}
	
	public int getUpdatedRow() {
		return updatedRow;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;	
	}
	
	@Override
	public int getRowCount() {
		return tableData.length;
	}
	
	@Override
	public String getValueAt (int row, int col) {
		return tableData[row][col];
	}
	

	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		updatedRow = row;
		//FacultyPage.updateCart(ItemChangeListener.getValue(), row);
	}

}
