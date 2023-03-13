package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;

import danogl.gui.rendering.Renderable;

import danogl.util.Vector2;

public class Heart extends GameObject {

    private static final float HEART_SPEED = 100;
    private static final float HEART_SIZE = 20;
    private final GameObjectCollection gameObjects;
    private final GraphicLifeCounter graphicLifeCounter;
    private final Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */

    public Heart(Renderable renderable,
                 GameObjectCollection gameObjects,
                 GraphicLifeCounter graphicLifeCounter,
                 Vector2 windowDimensions,
                 Vector2 topLeftCorner,
                 Vector2 dimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.graphicLifeCounter = graphicLifeCounter;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle && !(other instanceof SecondPaddle));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (windowDimensions.y() < this.getCenter().y()) {
            gameObjects.removeGameObject(this);
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)) {
            graphicLifeCounter.extraGraphicLife();
            gameObjects.removeGameObject(this);
        }
    }
}