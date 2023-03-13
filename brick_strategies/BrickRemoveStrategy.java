package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class BrickRemoveStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjects;
    private boolean isRemoved;

    public BrickRemoveStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjects = gameObjectCollection;
        this.isRemoved = false;
    }



    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        gameObjects.removeGameObject(collidedObj);
        if (!isRemoved) {
            bricksCounter.decrement();
            isRemoved = true;
        }
    }
}
