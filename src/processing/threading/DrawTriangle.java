package processing.threading;

import processing.utility.*;
import processing.boardobject.BoardObject;
import processing.shape.BoardObjectBuilder;
import processing.server.board.IServerCommunication;
import processing.server.board.ServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the draw triangle operation
 *
 * @author Shruti Umat
 */

public class DrawTriangle implements Runnable {
    private final Position vertA;
    private final Position vertB;
    private final Position vertC;
    private final Intensity intensity;

    public DrawTriangle (
            Position vertA,
            Position vertB,
            Position vertC,
            Intensity intensity
    ) {
        this.vertA = vertA;
        this.vertB = vertB;
        this.vertC = vertC;
        this.intensity = intensity;
    }

    public void run() {
        BoardObject triangleObject =
                BoardObjectBuilder.drawTriangle(
                        vertA,
                        vertB,
                        vertC,
                        intensity
                );

        /*
         * Sending the create operation object to the board server
         */
        IServerCommunication communicator = new ServerCommunication();
        communicator.sendObject(triangleObject);
    }
}
