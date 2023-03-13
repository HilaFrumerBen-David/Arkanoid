package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class CollisionStrategy {


    private final GameObjectCollection gameObjects;

    /**
     * The constructor of the Strategy object.
     *
     * @param gameObjects An object which holds all game objects of the game running.
     */
    public CollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * It decrements the number of active bricks on the screen and removes the current brick from the screen.
     * @param collidedObj the object that was collided (the brick)
     * @param colliderObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks Counter - how much bricks stay
     */
    public void onCollision(GameObject collidedObj,
                            GameObject colliderObj,
                            Counter bricksCounter) {
        bricksCounter.decrement();
        this.gameObjects.removeGameObject(collidedObj);
    }
}