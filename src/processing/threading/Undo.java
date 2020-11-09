package processing.threading;

import processing.UndoRedo;
import processing.boardobject.BoardObject;
import processing.server.board.IServerCommunication;
import processing.server.board.ServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the undo operation
 *
 * @author Shruti Umat
 */

public class Undo implements Runnable {

    public void run() {
        BoardObject objectToBeSent = UndoRedo.undo();

        /*
         * Sending the undo operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(objectToBeSent);
    }
}
