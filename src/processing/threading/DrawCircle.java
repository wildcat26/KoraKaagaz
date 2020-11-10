package processing.threading;

import processing.utility.*;
import processing.shape.BoardObjectBuilder;
import processing.boardobject.BoardObject;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the draw circle operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
 */

public class DrawCircle implements Runnable {
    private final Position center;
    private final Radius radius;
    private final Intensity intensity;

    /**
     * Initialize the parameters needed to create a circle
     *
     * @param center center of the circle
     * @param radius radius of the circle
     * @param intensity intensity of each pixel of the circle
     * @return the circle as a Board Object
     */
    public DrawCircle (
            Position center,
            Radius radius,
            Intensity intensity
    ) {
        this.center = center;
        this.radius = radius;
        this.intensity = intensity;
    }

    /**
     * Builds the circle as a BoardObject from the initialized parameters
     * and sends to the board server
     */
    public void run() {
        BoardObject circleObject =
                BoardObjectBuilder.drawCircle(
                        center,
                        radius,
                        intensity
                );

        /*
         * Sending the create operation object to the board server
         */

        if (circleObject != null) {
            IServerCommunication communicator = new ServerCommunication();
            communicator.sendObject(circleObject);
        }
    }
}
