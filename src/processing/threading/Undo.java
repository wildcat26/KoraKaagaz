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

    /**
     * Performs the undo operation on the client and sends the object
     * with the appropriate operation and parameter, if applicable,
     * which when performed on other clients,
     * results in undo operation done by this client
     */
    public void run() {
        BoardObject objectToBeSent = UndoRedo.undo();

        /*
         * Sending the undo operation object to the board server
         */
        if (objectToBeSent != null) {
            IServerCommunication communicator = new ServerCommunication();
            communicator.sendObject(objectToBeSent);
        }
    }
}
