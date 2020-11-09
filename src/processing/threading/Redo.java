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

    public void run() {
        BoardObject objectToBeSent = UndoRedo.redo();

        /*
         * Sending the redo operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(objectToBeSent);
    }
}
