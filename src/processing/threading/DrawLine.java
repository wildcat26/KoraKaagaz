package processing.threading;

import processing.utility.*;
import processing.shape.BoardObjectBuilder;
import processing.boardobject.BoardObject;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the draw line operation
 *
 * @author Shruti Umat
 */

public class DrawLine implements Runnable {
    private final Position pointA;
    private final Position pointB;
    private final Intensity intensity;

    public DrawLine (
            Position pointA,
            Position pointB,
            Intensity intensity
    ) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.intensity = intensity;
    }

    public void run() {
        BoardObject lineObject =
                BoardObjectBuilder.drawSegment(
                        pointA,
                        pointB,
                        intensity
                );

        /*
         * Sending the create operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(lineObject);
    }
}
