package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;


public class ExtraPuckStrategy extends CollisionStrategyDecorator{

    private static final int PUCKS_TO_ADD = 3;
    private int PUCKS_SPEED = 140;
    private static final String PUCK_IMAGE = "assets/mockBall.png";
    private static final String PUCK_SOUND = "assets/blop_cut_silenced.wav";
    private final GameObjectCollection gameObjects;
    private final Renderable puckImage;
    private final Sound puckSound;
    private final float sizeOfPuck;
    private final Vector2 windowDimensions;


    /**
     * constructor
     *
     * @param collisionStrategy the Collision Strategy
     */
    public ExtraPuckStrategy(CollisionStrategy collisionStrategy,
                             float sizeOfPuck,
                             SoundReader soundReader,
                             ImageReader imageReader,
                             Vector2 windowDimensions,
                             GameObjectCollection gameObjects) {
        super(collisionStrategy);
        this.sizeOfPuck = sizeOfPuck;
        this.puckImage = imageReader.readImage(PUCK_IMAGE, true);
        this.puckSound = soundReader.readSound(PUCK_SOUND);
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;

    }

    private GameObject createPuck(GameObject collidedObj)
    {
        GameObject puck = new Puck(Vector2.ZERO, new Vector2(sizeOfPuck, sizeOfPuck),
                puckImage, puckSound, windowDimensions, gameObjects);
        puck.setCenter(collidedObj.getCenter());
        return puck;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        for (int i = 0; i < PUCKS_TO_ADD; i++) {
            GameObject puck = createPuck(collidedObj);
            Random rand = new Random();
            float puckX = PUCKS_SPEED * rand.nextFloat();
            float puckY = PUCKS_SPEED * rand.nextFloat();
            puck.setVelocity(new Vector2(puckX, puckY));
            this.gameObjects.addGameObject(puck);
        }
    }
}
