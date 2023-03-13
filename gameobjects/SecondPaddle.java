package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class SecondPaddle extends Paddle{

    public static boolean paddleExist;
    private final GameObjectCollection gameObjectCollection;
    private final Counter collisionCounter;


    //todo to add about param
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener    The input listener which waits for user inputs and acts on them.
     * @param windowDimensions The dimensions of the screen, to know the limits for paddle movements.
     * @param minDistEdge  Minimum distance allowed for the paddle from the edge of the walls
     */
    public SecondPaddle(Vector2 topLeftCorner,
                        Vector2 dimensions,
                        Renderable renderable,
                        UserInputListener inputListener,
                        Vector2 windowDimensions,
                        int minDistEdge,
                        GameObjectCollection gameObjectCollection,
                        boolean paddleExist) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.collisionCounter = new Counter();
        this.paddleExist = paddleExist;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision){
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {
            collisionCounter.increment();
        }
        if (collisionCounter.value() == 3){
            gameObjectCollection.removeGameObject(this);
            paddleExist = false;
        }
    }
}
