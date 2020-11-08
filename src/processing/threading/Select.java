package processing.threading;

import java.util.ArrayList;
import processing.utility.*;
import processing.SelectDelete;

/**
 * Wrapper class implementing Runnable interface for threading of the select operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
 */

public class Select implements Runnable {
    private ArrayList<Position> inputPositions, selectedObjectPositions;

    /**
     * Returns all the positions of the object that got selected from
     * the top of input positions
     *
     * @return positions of the object that got selected
     */
    public ArrayList<Position> getSelectedObjectPositions() {
        return selectedObjectPositions;
    }

    /**
     * Initialize input positions needed by select as argument
     *
     * @param inputPositions positions clicked by the user
     */
    public Select (ArrayList<Position> inputPositions) {
        this.inputPositions = inputPositions;
    }

    /**
     * Compute the selected object and its positions from the input positions
     */
    public void run() {
        this.selectedObjectPositions = SelectDelete.select(inputPositions);
    }
}
