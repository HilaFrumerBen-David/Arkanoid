package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

public class CollisionStrategyDecorator implements CollisionStrategy {

    private final CollisionStrategy collisionStrategy;

    /**
     * constructor
     * @param collisionStrategy the Collision Strategy
     */
    public CollisionStrategyDecorator(CollisionStrategy collisionStrategy) {
        this.collisionStrategy = collisionStrategy;
    }

    /**
     *
     * @param collidedObj the object that was collided (the brick)
     * @param colliderObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks Counter - how much bricks stay
     */

    public void onCollision(GameObject collidedObj,
                            GameObject colliderObj,
                            Counter bricksCounter){
        this.collisionStrategy.onCollision(collidedObj,colliderObj,bricksCounter);

    }
}

