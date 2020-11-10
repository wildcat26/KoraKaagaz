package processing.threading;

import processing.utility.*;
import infrastructure.validation.logger.*;
import processing.boardobject.BoardObject;
import processing.shape.BoardObjectBuilder;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the draw square operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
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
        ILogger logger = null;
        String threadId = "";

        try {
            logger = LoggerFactory.getLoggerInstance();
            threadId = "[" + Thread.currentThread().getId() + "] ";

            // calculating the bottom right Position from the top right position
            Position topLeftPos = topLeft.position;
            Position bottomRightPos =
                    new Position(topLeftPos.r + sideLength, topLeftPos.c + sideLength);

            Intensity intensity = topLeft.intensity;

            BoardObject squareObject =
                    BoardObjectBuilder.drawRectangle(
                            topLeftPos,
                            bottomRightPos,
                            intensity
                    );

            /*
             * Sending the create operation object to the board server
             */
            try {
                if (squareObject != null) {
                    IServerCommunication communicator = new ServerCommunication();
                    communicator.sendObject(squareObject);

                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.SUCCESS,
                            threadId + "DrawSquare: Object Successfully sent to the Board Server"
                    );
                }
                else {
                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.INFO,
                            threadId + "DrawSquare: Null Object created, not sent to Board Server"
                    );
                }
            }
            catch (Exception e) {
                logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.ERROR,
                        threadId + "DrawSquare: Sending to Board Server Failed"
                );
            }
        }
        catch (Exception e) {
            if (logger == null) {
                // Unable to get logger instance ; cannot log the same
                return;
            }
            else {
                logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.ERROR,
                        threadId + "DrawSquare failed"
                );
            }
        }
    }
}
