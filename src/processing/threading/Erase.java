package processing.threading;

import java.util.ArrayList;
import processing.utility.*;
import processing.CurveBuilder;
import processing.boardobject.BoardObject;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;
import processing.boardobject.IBoardObjectOperation;

/**
 * Wrapper class implementing Runnable interface for threading of the erase operation
 *
 * @author Shruti Umat
 */

public class Erase implements Runnable {
    private final ArrayList<Position> position;
    private final IBoardObjectOperation newBoardOp;
    private final ObjectId newObjectId;
    private final Timestamp newTimestamp;
    private final UserId newUserId;
    private final Boolean reset;

    public Erase (
            ArrayList<Position> position,
            IBoardObjectOperation newBoardOp,
            ObjectId newObjectId,
            Timestamp newTimestamp,
            UserId newUserId,
            Boolean reset
    ) {
        this.position = position;
        this.newBoardOp = newBoardOp;
        this.newObjectId = newObjectId;
        this.newTimestamp = newTimestamp;
        this.newUserId = newUserId;
        this.reset = reset;
    }

    public void run() {
        BoardObject eraseObject =
                CurveBuilder.eraseCurve(
                        position,
                        newBoardOp,
                        newObjectId,
                        newTimestamp,
                        newUserId,
                        reset
                );

        /*
         * Sending the erase operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(eraseObject);
    }
}
