package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {

    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;
    private static final int LIVES_START = 3;
    private GameObject[] livesArr;
    /**
     * Construct a new GameObject instance.
     *
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height in window coordinates.
     * @param widgetRenderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param livesCounter  the counter which holds current lives count
     * @param gameObjectsCollection the collection of all game objects currently in the game
     * @param numOfLives  number of current lives
     */

    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter,
                              Renderable widgetRenderable, GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.livesArr = new GameObject[LIVES_START];
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.gameObjectsCollection = gameObjectsCollection;
        for (int i=0; i < this.numOfLives; i++){
            Vector2 pos = new Vector2(widgetTopLeftCorner.x() + (widgetDimensions.x())*i,
                    widgetTopLeftCorner.y());
            livesArr[i] = new GameObject(pos,widgetDimensions,widgetRenderable);
            this.gameObjectsCollection.addGameObject(livesArr[i], Layer.BACKGROUND);
        }

    }

    /**
     * This method is overwritten from GameObject It removes hearts from the screen if there are more hearts
     * than there are lives left
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value() < this.numOfLives){
            this.numOfLives --;
            this.gameObjectsCollection.removeGameObject(this.livesArr[this.numOfLives], Layer.BACKGROUND);
            this.livesArr[this.numOfLives] = null;
        }
    }
}
