package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.SecondPaddle;

public class ExtraPaddleStrategy extends CollisionStrategyDecorator {

    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 85;
    private static final String PADDLE_IMAGE = "assets/botGood.png";
//    private final Counter counterPaddles;
    private Renderable paddleImage;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final GameObjectCollection gameObjects;

    private final Boolean paddleExist;


    /**
     * constructor
     *
     * @param collisionStrategy the Collision Strategy
     */
    public ExtraPaddleStrategy(CollisionStrategy collisionStrategy, GameObjectCollection gameObjects,
                               Vector2 windowDimensions,
                               ImageReader imageReader, UserInputListener inputListener) {
        super(collisionStrategy);
        this.paddleImage = imageReader.readImage(PADDLE_IMAGE, true);
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.gameObjects = gameObjects;
        this.paddleExist = false;
    }


    private void createPaddle() {
        Vector2 temp = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        GameObject extraPaddle = new SecondPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH,
                PADDLE_HEIGHT), paddleImage, inputListener, windowDimensions, BORDER_WIDTH,
                gameObjects, paddleExist);
        extraPaddle.setCenter(temp);
        gameObjects.addGameObject(extraPaddle);
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        if (!SecondPaddle.paddleExist) {
            createPaddle();
            SecondPaddle.paddleExist = true;
        }
    }
}

