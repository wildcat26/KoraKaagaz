package processing.threading;

import processing.utility.*;
import infrastructure.validation.logger.*;
import processing.boardobject.BoardObject;
import processing.shape.BoardObjectBuilder;
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

    /**
     * Initializes the parameters to build a rectangle, namely
     * top left and bottom right positions and intensity
     *
     * @param topLeft top left position of the rectangle
     * @param bottomRight bottom right position of the rectangle
     * @param intensity intensity of the rectangle
     */
    public DrawRectangle (
        Position topLeft,
        Position bottomRight,
        Intensity intensity
    ) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.intensity = intensity;
    }

    /**
     * Builds the rectangle as a BoardObject from the initialized parameters
     * and sends to the board server
     */
    public void run() {
        ILogger logger = null;
        String threadId = "";

        try {
            logger = LoggerFactory.getLoggerInstance();
            threadId = "[" + Thread.currentThread().getId() + "] ";

            BoardObject rectangleObject =
                    BoardObjectBuilder.drawRectangle(
                            topLeft,
                            bottomRight,
                            intensity
                    );

            logger.log(
                    ModuleID.PROCESSING,
                    LogLevel.INFO,
                    threadId + "BoardObjectBuilder.drawRectangle Successful"
            );

            /*
             * Sending the create operation object to the board server
             */
            try {
                if (rectangleObject != null) {
                    IServerCommunication communicator = new ServerCommunication();
                    communicator.sendObject(rectangleObject);

                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.SUCCESS,
                            threadId + "DrawRectangle: Object Successfully sent to Board Server"
                    );
                }
                else {
                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.INFO,
                            threadId + "DrawRectangle: Null Object created, not sent to Board Server"
                    );
                }
            }
            catch (Exception e) {
                logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.ERROR,
                        threadId + "DrawRectangle: Sending to Board Server Failed"
                );
            }
        }
        catch (Exception e) {
            if (logger == null) {
                // Unable to get logger instance ; cannot log the same
                return;
            } else {
                logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.ERROR,
                        threadId + "DrawRectangle failed"
                );
            }
        }
    }
}
