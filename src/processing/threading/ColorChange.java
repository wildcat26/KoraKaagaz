package processing.threading;

import processing.utility.*;
import processing.boardobject.BoardObject;
import processing.ParameterizedOperationsUtil;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the color change operation
 *
 * @author Shruti Umat
 */

public class ColorChange implements Runnable {
    private final BoardObject object;
    private final UserId userId;
    private final Intensity intensity;

    public ColorChange (BoardObject object, UserId userId, Intensity intensity) {
        this.object = object;
        this.userId = userId;
        this.intensity = intensity;
    }

    /**
     * Performs color change operation on the object which was initialized
     * Sends it to the Server Communicator
     */
    public void run() {
        BoardObject colorChangeOperationObject =
                ParameterizedOperationsUtil.colorChange(
                        object,
                        userId,
                        intensity);

        /*
         * Sending the color change operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(colorChangeOperationObject);
    }
}
