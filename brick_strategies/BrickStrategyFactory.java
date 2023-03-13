package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.GraphicLifeCounter;


import java.util.Random;

public class BrickStrategyFactory {
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Counter hitPaddleCount;
    private final SoundReader soundReader;
    private final GraphicLifeCounter graphicLifeCounter;
    private final float brickWidth;
    private int counterPowerUps = 0;
    public BrickStrategyFactory(WindowController windowController,
                                GraphicLifeCounter graphicLifeCounter,
                                BrickerGameManager gameManager,
                                Vector2 windowDimensions,
                                GameObjectCollection gameObjects,
                                ImageReader imageReader,
                                UserInputListener inputListener,
                                Counter hitPaddleCount,
                                float brickWidth,
                                SoundReader soundReader)
    {
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.gameManager = gameManager;
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.hitPaddleCount = hitPaddleCount;
        this.graphicLifeCounter = graphicLifeCounter;
        this.soundReader = soundReader;
        this.brickWidth = brickWidth;
    }

    public CollisionStrategy getStrategy() {
        return chooseBrickStrategy(6, new BrickRemoveStrategy(gameObjects));
    }
    private CollisionStrategy chooseBrickStrategy(int boundRandom, CollisionStrategy collisionStrategy) {
        Random random = new Random();
        int strategyType = random.nextInt(boundRandom);
        switch (strategyType) {
            case 0:
                return new ExtraPuckStrategy(collisionStrategy, brickWidth / 3, soundReader,
                        imageReader, windowDimensions, gameObjects);
            case 1:
                return new ExtraPaddleStrategy(collisionStrategy, gameObjects, windowDimensions,
                        imageReader, inputListener);
            case 2:
                return new ChangeCameraStrategy(gameManager, gameObjects,windowController, collisionStrategy);
            case 3:
                return new ExtraLifeStrategy(collisionStrategy, gameObjects, imageReader,
                        graphicLifeCounter,windowDimensions);
            case 4:
                counterPowerUps++;
                if (counterPowerUps == 1) {
                    CollisionStrategy newCollisionStrategy = chooseBrickStrategy(5,
                            collisionStrategy);
                    return chooseBrickStrategy(5, newCollisionStrategy);
                }
                if (counterPowerUps == 2) {
                    return chooseBrickStrategy(4, collisionStrategy);
                }
            case 5:
                return new BrickRemoveStrategy(gameObjects);
            default:
                return null;
        }
    }
}
