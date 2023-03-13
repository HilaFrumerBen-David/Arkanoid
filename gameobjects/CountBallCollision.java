package src.gameobjects;

import danogl.GameObject;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class CountBallCollision extends GameObject {

    private final int MAX_BALL_COLLISION = 4;
    private final Ball ball;
    private final Counter collisionCounter;
    private final ChangeCameraStrategy strategy;


    /**
     * Construct a new GameObject instance.
     *
     */
    public CountBallCollision(Ball ball, ChangeCameraStrategy strategy, Counter collisionCounter){
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.collisionCounter = collisionCounter;
        this.strategy = strategy;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int numCollision = this.collisionCounter.value() + MAX_BALL_COLLISION;
        if (numCollision <= this.ball.getCollisionCount()) {
            strategy.turnOffCamera();
        }
    }
}
