package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.CountBallCollision;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends CollisionStrategyDecorator {

    private final WindowController windowController;
    private final GameObjectCollection gameObjects;
    private final BrickerGameManager gameManager;
    private final Counter collisionCounter;
    private CountBallCollision ballCount;
    private Ball followBall;


    /**
     * constructor
     *
     * @param collisionStrategy the Collision Strategy
     */
    public ChangeCameraStrategy(BrickerGameManager gameManager,
                                GameObjectCollection gameObjects,
                                WindowController windowController,
                                CollisionStrategy collisionStrategy) {
        super(collisionStrategy);
        this.windowController = windowController;
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.collisionCounter = new Counter();
    }

    private void onCollisionHelper(GameObject colliderObj)
    {
        collisionCounter.increaseBy(followBall.getCollisionCount());
        ballCount = new CountBallCollision(followBall, this, collisionCounter);
        gameManager.setCamera(
                new Camera(colliderObj,
                        Vector2.ZERO,
                        windowController.getWindowDimensions().mult(1.2f),
                        windowController.getWindowDimensions()));
        gameObjects.addGameObject(ballCount);
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        this.followBall = (Ball) colliderObj;
        if ((gameManager.getCamera() == null) && !(colliderObj instanceof Puck)) {
            onCollisionHelper(colliderObj);
        }
    }

    public void turnOffCamera(){
        gameObjects.removeGameObject(this.ballCount);
        this.ballCount = null;
        gameManager.setCamera(null);
    }
}
