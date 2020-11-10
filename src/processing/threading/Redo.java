package processing.threading;

import processing.UndoRedo;
import processing.boardobject.BoardObject;
import processing.server.board.IServerCommunication;
import processing.server.board.ServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the redo operation
 *
 * @author Shruti Umat
 */

public class Redo implements Runnable {

    /**
     * Performs the redo operation on the client and sends the object
     * with the appropriate operation and parameter, if applicable,
     * which when performed on other clients,
     * results in redo operation done by this client
     */
    public void run() {
        BoardObject objectToBeSent = UndoRedo.redo();

        /*
         * Sending the redo operation object to the board server
         */
        if (objectToBeSent != null) {
            IServerCommunication communicator = new ServerCommunication();
            communicator.sendObject(objectToBeSent);
        }
    }
}
