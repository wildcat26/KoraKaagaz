package processing.threading;

import processing.utility.*;
import infrastructure.validation.logger.*;
import processing.boardobject.BoardObject;
import processing.shape.BoardObjectBuilder;
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
        ILogger logger = null;
        String threadId = "";

        try {
            logger = LoggerFactory.getLoggerInstance();
            threadId = "[" + Thread.currentThread().getId() + "] ";

            BoardObject circleObject =
                BoardObjectBuilder.drawCircle(
                    center,
                    radius,
                    intensity
                );

            logger.log(
                    ModuleID.PROCESSING,
                    LogLevel.INFO,
                    threadId + "BoardObjectBuilder.drawCircle Successful"
            );

            /*
             * Sending the create operation object to the board server
             */

            try {
                if (circleObject != null) {
                    IServerCommunication communicator = new ServerCommunication();
                    communicator.sendObject(circleObject);

                    logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.SUCCESS,
                        threadId + "DrawCircle: Object Successfully sent to the Board Server"
                    );
                }
                else {
                    logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.INFO,
                        threadId + "DrawCircle: Null Object created, not sent to Board Server"
                    );
                }
            }
            catch (Exception e) {
                logger.log(
                    ModuleID.PROCESSING,
                    LogLevel.ERROR,
                    threadId + "DrawCircle: Sending to Board Server Failed"
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
                    threadId + "DrawCircle failed"
                );
            }
        }
    }
}
