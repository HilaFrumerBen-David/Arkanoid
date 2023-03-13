package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 *
 */
public interface CollisionStrategy {

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * @param collidedObj the object that was collided (the brick)
     * @param colliderObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks Counter - how much bricks stay
     */
    public void onCollision(GameObject collidedObj,
                            GameObject colliderObj,
                            Counter bricksCounter) ;
}
