package processing.threading;

import processing.boardobject.BoardObject;
import processing.server.board.IServerCommunication;
import processing.server.board.ServerCommunication;
import processing.shape.BoardObjectBuilder;
import processing.utility.*;

/**
 * @author Shruti Umat
 */

public class DrawSquare implements Runnable {
    private final Pixel topLeft;
    private final int sideLength;

    /**
     * Initializes the parameters of the square, namely
     * top left pixel containing the position and intensity, and the side length
     *
     * @param topLeft top left pixel
     * @param sideLength the square's side length
     */
    public DrawSquare (Pixel topLeft, float sideLength) {
        this.topLeft = topLeft;
        this.sideLength = (int) sideLength;
    }

    /**
     * Builds the square as a BoardObject from the initialized parameters
     * and sends to the board server
     */
    public void run() {
        // calculating the bottom right Position from the top right position
        Position topLeftPos = topLeft.position;
        Position bottomRightPos =
                new Position(topLeftPos.r + sideLength, topLeftPos.c + sideLength);

        Intensity intensity = topLeft.intensity;

        BoardObject squareObject = BoardObjectBuilder.drawRectangle(topLeftPos, bottomRightPos, intensity);

        if (squareObject != null) {
            IServerCommunication communicator = new ServerCommunication();
            communicator.sendObject(squareObject);
        }
    }
}
