package processing.threading;

import processing.utility.*;
import processing.SelectDelete;
import processing.boardobject.BoardObject;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the delete operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
 */

public class Delete implements Runnable {
    private final BoardObject object;
    private final UserId userId;

    /**
     * Initializes board object and user id needed by delete as arguments
     *
     * @param object object to be deleted
     * @param userId user id of the user deleting the object
     */
    public Delete (BoardObject object, UserId userId) {
        this.object = object;
        this.userId = userId;
    }

    /**
     * Deletes the object which was initialized
     * Sends it to the Server Communicator
     */
    public void run() {
        BoardObject deleteOperationObject = SelectDelete.delete(this.object, this.userId);

        /*
         * Sending the delete operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(deleteOperationObject);
    }
}
