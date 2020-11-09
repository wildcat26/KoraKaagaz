package processing.threading;

import java.util.ArrayList;
import processing.utility.*;
import processing.CurveBuilder;
import processing.boardobject.BoardObject;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;
import processing.boardobject.IBoardObjectOperation;

/**
 * Wrapper class implementing Runnable interface for threading of the draw curve operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
 */

public class DrawCurve implements Runnable {
    private final ArrayList<Pixel> pixels;
    private final IBoardObjectOperation newBoardOp;
    private final ObjectId newObjectId;
    private final Timestamp newTimestamp;
    private final UserId newUserId;
    private final ArrayList<Pixel> prevPixelIntensity;
    private final Boolean reset;

    public DrawCurve (
            ArrayList<Pixel> pixels,
            IBoardObjectOperation newBoardOp,
            ObjectId newObjectId,
            Timestamp newTimestamp,
            UserId newUserId,
            ArrayList<Pixel> prevPixelIntensity,
            Boolean reset
    ) {
        this.pixels = pixels;
        this.newBoardOp = newBoardOp;
        this.newObjectId = newObjectId;
        this.newTimestamp = newTimestamp;
        this.newUserId = newUserId;
        this.prevPixelIntensity = prevPixelIntensity;
        this.reset = reset;
    }

    public void run() {
        BoardObject objectCreated =
                CurveBuilder.drawCurve(
                        pixels,
                        newBoardOp,
                        newObjectId,
                        newTimestamp,
                        newUserId,
                        prevPixelIntensity,
                        reset
                );

        /*
         * Sending the create operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(objectCreated);
    }
}
