package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

public class NumericLifeCounter extends GameObject {

    private Counter livesCounter;
    private GameObjectCollection gameObjectCollection;
    private TextRenderable textRenderable;
    /**
     * Construct a new GameObject instance.
     * @param livesCounter  The counter of how many lives are left right now.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjectCollection  the collection of all game objects currently in the game
     */

    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, new TextRenderable(""));
        this.gameObjectCollection = gameObjectCollection;
        this.livesCounter = livesCounter;
        this.textRenderable = new TextRenderable(Integer.toString(this.livesCounter.value()));
        this.renderer().setRenderable(this.textRenderable);
    }

    /**
     * This method is overwritten from GameObject. It sets the string value of the text object to the number
     * of current lives left.
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
        this.textRenderable.setString(Integer.toString(livesCounter.value()));
        if (livesCounter.value() == 3) {
            textRenderable.setColor(Color.green);
        }
        if (livesCounter.value() == 2) {
            textRenderable.setColor(Color.yellow);
        }
        if (livesCounter.value() == 1) {
            textRenderable.setColor(Color.red);
        }
    }
}
