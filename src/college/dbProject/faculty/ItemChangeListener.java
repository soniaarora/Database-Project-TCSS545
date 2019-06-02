package college.dbProject.faculty;
import college.dbProject.faculty.FacultyPage;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


/** The item change listener for the table.
 *  Detects when a combo box in the table has been selected.
 * 
 
 */
public class ItemChangeListener implements ItemListener {
	
	public static int value;
	
	public static int getValue() {
		return value;
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			Object item = event.getItem();
			value = Integer.parseInt((String) item);
			int row = FacultyPage.model.getUpdatedRow();
		}
	}
}
