package processing.threading;

import processing.utility.*;
import infrastructure.validation.logger.*;
import processing.boardobject.BoardObject;
import processing.shape.BoardObjectBuilder;
import processing.server.board.ServerCommunication;
import processing.server.board.IServerCommunication;

/**
 * Wrapper class implementing Runnable interface for threading of the draw line operation
 *
 * @author Shruti Umat
 * @reviewer Satchit Desai
 */

public class DrawLine implements Runnable {
    private final Position pointA;
    private final Position pointB;
    private final Intensity intensity;

    /**
     * Initializes the parameters needed to construct a line, namely
     * two positions and the line's intensity
     *
     * @param pointA position of one of the points making the line
     * @param pointB position of the other point making the line
     * @param intensity intensity of the line
     */
    public DrawLine (
        Position pointA,
        Position pointB,
        Intensity intensity
    ) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.intensity = intensity;
    }

    /**
     * Builds the line as a BoardObject from the initialized parameters
     * and sends to the board server
     */
    public void run() {
        ILogger logger = null;
        String threadId = "";

        try {
            logger = LoggerFactory.getLoggerInstance();
            threadId = "[" + Thread.currentThread().getId() + "] ";

            BoardObject lineObject =
                    BoardObjectBuilder.drawSegment(
                            pointA,
                            pointB,
                            intensity
                    );

            logger.log(
                    ModuleID.PROCESSING,
                    LogLevel.INFO,
                    threadId + "BoardObjectBuilder.drawSegment Successful"
            );

            /*
             * Sending the create operation object to the board server
             */
            try {
                if (lineObject != null) {
                    IServerCommunication communicator = new ServerCommunication();
                    communicator.sendObject(lineObject);

                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.SUCCESS,
                            threadId + "DrawLine: Object Successfully sent to the Board Server"
                    );
                }
                else {
                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.INFO,
                            threadId + "DrawLine: Null Object created, not sent to Board Server"
                    );
                }
            }
            catch (Exception e) {
                logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.ERROR,
                        threadId + "DrawLine: Sending to Board Server Failed"
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
                        threadId + "DrawLine failed"
                );
            }
        }
    }
}
