package processing.threading;

import processing.utility.*;
import processing.shape.BoardObjectBuilder;
import processing.boardobject.BoardObject;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the draw rectangle operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
 */

public class DrawRectangle implements Runnable {
    private final Position topLeft;
    private final Position bottomRight;
    private final Intensity intensity;

    public DrawRectangle (
            Position topLeft,
            Position bottomRight,
            Intensity intensity
    ) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.intensity = intensity;
    }

    public void run() {
        BoardObject rectangeObject =
                BoardObjectBuilder.drawRectangle(
                        topLeft,
                        bottomRight,
                        intensity
                );

        /*
         * Sending the create operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(rectangeObject);
    }
}
