package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.Heart;

public class ExtraLifeStrategy extends CollisionStrategyDecorator{

    private static final String IMAGE_HEART = "assets/heart.png";
    private static final float SIZE_HEART = 20;
    private final float HEART_SPEED =100;
    private final Vector2 windowDimensions;
    private GameObjectCollection gameObjects;
    private ImageReader imageReader;
    private GraphicLifeCounter graphicLifeCounter;
    /**
     * constructor
     *
     * @param collisionStrategy the Collision Strategy
     */
    public ExtraLifeStrategy(CollisionStrategy collisionStrategy,
                             GameObjectCollection gameObjects,
                             ImageReader imageReader,
                             GraphicLifeCounter graphicLifeCounter,
                             Vector2 windowDimensions
                              ) {
        super(collisionStrategy);
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.graphicLifeCounter = graphicLifeCounter;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        Renderable image = this.imageReader.readImage(IMAGE_HEART, true);
        GameObject newHeart = new Heart(image, gameObjects,graphicLifeCounter, windowDimensions,
                collidedObj.getCenter(), new Vector2(SIZE_HEART,SIZE_HEART));
        gameObjects.addGameObject(newHeart);
        newHeart.setVelocity(new Vector2(0,HEART_SPEED));
       }
}
