package processing.threading;

import processing.utility.*;
import processing.boardobject.BoardObject;
import processing.ParameterizedOperationsUtil;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the rotate operation
 *
 * @author Shruti Umat
 */

public class Rotate implements Runnable {
    private final BoardObject object;
    private final UserId userId;
    private final Angle angleOfRotation;

    public Rotate (BoardObject object, UserId userId, Angle angleOfRotation) {
        this.object = object;
        this.userId = userId;
        this.angleOfRotation = angleOfRotation;
    }

    public void run() {
        BoardObject rotateOperationObject =
                ParameterizedOperationsUtil.rotation(
                        object,
                        userId,
                        angleOfRotation);

        /*
         * Sending the rotate operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(rotateOperationObject);

    }
}
