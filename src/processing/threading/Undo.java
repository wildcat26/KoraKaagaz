package processing.threading;

import processing.UndoRedo;

/**
 * Wrapper class implementing Runnable interface for threading of the undo operation
 *
 * @author Shruti Umat
 */

public class Undo implements Runnable {

    public void run() {
        UndoRedo.undo();
    }
}
