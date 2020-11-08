package processing.threading;

import processing.UndoRedo;

/**
 * Wrapper class implementing Runnable interface for threading of the redo operation
 *
 * @author Shruti Umat
 */

public class Redo implements Runnable {

    public void run() {
        UndoRedo.redo();
        // TODO send to the network
    }
}
