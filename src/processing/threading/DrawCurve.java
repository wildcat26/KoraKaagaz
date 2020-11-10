package processing.threading;

import java.util.ArrayList;

import processing.utility.*;
import processing.CurveBuilder;
import infrastructure.validation.logger.*;
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

    /**
     * Initializes the parameters needed for the draw curve operation
     *
     * @param pixels list of pixels belonging to the curve to be drawn
     * @param newBoardOp board object operation
     * @param newObjectId board object's object id
     * @param newTimestamp timestamp of the object
     * @param newUserId user id of the creator of the object
     * @param prevPixelIntensity previous pixel intensity
     * @param reset true only if this is a reset object
     */
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

    /**
     * Performs the draw curve operation and sends the object to the board server
     */
    public void run() {
        ILogger logger = null;
        String threadId = "";

        try {
            logger = LoggerFactory.getLoggerInstance();
            threadId = "[" + Thread.currentThread().getId() + "] ";

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
            try {
                if (objectCreated != null) {
                    IServerCommunication communicator = new ServerCommunication();
                    communicator.sendObject(objectCreated);

                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.SUCCESS,
                            threadId + "DrawCurve: Object Successfully sent to the Board Server"
                    );
                } else {
                    logger.log(
                            ModuleID.PROCESSING,
                            LogLevel.INFO,
                            threadId + "DrawCurve: Null Object created, not sent to Board Server"
                    );
                }
            } catch (Exception e) {
                logger.log(
                        ModuleID.PROCESSING,
                        LogLevel.ERROR,
                        threadId + "DrawCurve: Sending to Board Server Failed"
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
                        threadId + "DrawCurve failed"
                );
            }
        }
    }
}
