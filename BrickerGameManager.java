package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.Ball;
import src.gameobjects.Paddle;
import src.gameobjects.Brick;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.NumericLifeCounter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class BrickerGameManager extends GameManager{
    private static final String BALL_IMAGE = "assets/ball.png";
    private static final String COLLISION_SOUND = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_IMAGE = "assets/paddle.png";
    private static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    private static final String BRICK_IMAGE = "assets/brick.png";
    private static final String GRAPHICLIFE_IMAGE = "assets/heart.png" ;
    private static final String MSG_WIN = "You win!";
    private static final String MSG_LOSE = "You Lose!";
    private static final String MSG_PLAY = " Play again?";
    private static final String WINDOW_TITLE = "Bricker";


    public static final float BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 90;
    private static final float BALL_SPEED = 175;
    private static final int BALL_RADIUS = 25;
    private static final float NUM_OF_COLS = 7;
    private static final int ONE = 1;
    private static final int DIST = 20;
    private static final int NUM_OF_ROWS = 8;
    private static final float BRICK_HEIGHT = 20;
    private static final float WINDOW_WIDTH = 700;
    private static final float WINDOW_HEIGHT = 500;
    private static final int LIVES_START = 3;

    private Ball ball;
    private Counter livesCounter;
    private Counter numOfBricks;
    private UserInputListener inputListener;
    private SoundReader soundReader;
    private ImageReader imageReader;
    private  Vector2 windowDimensions;
    private WindowController windowController;
    private GraphicLifeCounter graphicLife;
    private Counter paddleCounter;

    /**
     * constructor
     * @param windowTitle name game
     * @param windowDimensions Dimensions of window's game
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle,windowDimensions);
    }

    /**
     * initialize the Game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController.setTargetFramerate(70);
        this.numOfBricks = new Counter(0);
        this.paddleCounter = new Counter();
        this.livesCounter = new Counter(LIVES_START);
        this.graphicLife = null;
        this.ball = null;

        //create ball
        createBall();
        //create paddle
        createPaddle();
        //create borders
        createBorders();
        //create background
        createBackGround();
        //create lives
        createGraphicLifeCounter();
        createNumericLifeCounter();
        //create bricks
        createBricks();
    }

    /**
     * create Ball
     */
    private void createBall() {
        Renderable ballImage = imageReader.readImage(BALL_IMAGE, true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        gameObjects().addGameObject(ball);
        ballPositionReset();

    }

    /**
     * reset the position of ball
     */
    private void ballPositionReset() {
        ball.setCenter(this.windowDimensions.mult(0.5f));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * create the Paddle
     */
    private void createPaddle() {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE,false);
        GameObject Paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,
                this.inputListener, this.windowDimensions, (int) BORDER_WIDTH);
        Paddle.setCenter(new Vector2(windowDimensions.x()/2,(int)windowDimensions.y()-30));
        gameObjects().addGameObject(Paddle);
    }

    /**
     * create the Numeric Life Counter
     */
    private void createNumericLifeCounter() {
        GameObject numericLife = new NumericLifeCounter(this.livesCounter,
                new Vector2(BORDER_WIDTH + 80, windowDimensions.y()-DIST),
                new Vector2(20,20), gameObjects());
        gameObjects().addGameObject(numericLife, Layer.BACKGROUND);
    }

    /**
     * create the Graphic Life Counter
     */
    private void createGraphicLifeCounter() {
        Renderable graphicLifeImage = imageReader.readImage(GRAPHICLIFE_IMAGE,true);
        GraphicLifeCounter graphicLife = new GraphicLifeCounter(new Vector2(BORDER_WIDTH + ONE,
                windowDimensions.y()- DIST), new Vector2(20,20), this.livesCounter,
                graphicLifeImage, gameObjects(), LIVES_START);
        this.graphicLife = graphicLife;
        gameObjects().addGameObject(graphicLife, Layer.BACKGROUND);
    }

    private void helperCreateBricks( Renderable brickImage,  float brickWidth, Vector2 brickDimensions,
                        BrickStrategyFactory brickStrategiesFactory) {
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                CollisionStrategy collisionStrategy = brickStrategiesFactory.getStrategy();
                GameObject brick = new Brick(new Vector2(BORDER_WIDTH * 4 + (j * brickWidth),
                        BORDER_WIDTH + (i * BRICK_HEIGHT)), brickDimensions, brickImage,
                        collisionStrategy, numOfBricks);
                gameObjects().addGameObject(brick);
                this.numOfBricks.increment();
            }
        }
    }

    /**
     * create the Bricks
     */
    private void createBricks() {
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE,true);
        float brickWidth = (windowDimensions.x() - (2 * BORDER_WIDTH) - (NUM_OF_COLS - ONE)*BORDER_WIDTH)
                / NUM_OF_COLS;
        Vector2 brickDimensions = new Vector2(brickWidth - ONE, BRICK_HEIGHT - ONE);
        BrickStrategyFactory brickStrategiesFactory = new BrickStrategyFactory(
                windowController, graphicLife, this, windowDimensions, gameObjects(),imageReader,
                inputListener, paddleCounter, brickWidth, soundReader);
        helperCreateBricks(brickImage, brickWidth, brickDimensions, brickStrategiesFactory);
    }

    /**
     * create the background of board's game
     */
    private void createBackGround() {
        Renderable backGroundImage = imageReader.readImage(BACKGROUND_IMAGE, false);
        GameObject backGround = new GameObject(Vector2.ZERO, this.windowDimensions,
               backGroundImage);
        backGround.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(backGround, Layer.BACKGROUND);
    }

    /**
     * create the borders of the board's game
     */
    private void createBorders() {
        GameObject leftBorder = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);
        gameObjects().addGameObject(leftBorder);
        GameObject rightBorder = new GameObject(new Vector2(windowDimensions.x()-BORDER_WIDTH,0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),null);
        gameObjects().addGameObject(rightBorder);
        GameObject upBorder = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x() - 5,
                (float) BORDER_WIDTH),null);
        gameObjects().addGameObject(upBorder);
    }

    /**
     * This method overrides the GameManager update method. It checks for game status, and triggers a new
     * game popup.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * this function check if the game end. in case of lose or win.
     * Every time you lose, she updates the livesCounter.
     */
    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight > windowDimensions.y())
            if (this.livesCounter.value() == 1){
                prompt = MSG_LOSE;
            }
            else {
                this.livesCounter.decrement();
                ballPositionReset();
            }
        if((this.numOfBricks.value()==0 && livesCounter.value() >0 )||
                this.inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = MSG_WIN;
        }


        if(!prompt.isEmpty()){
            prompt += MSG_PLAY;
            if(windowController.openYesNoDialog(prompt)){
//                this.numOfBricks.reset();
                windowController.resetGame();
            }
            else
                windowController.closeWindow();
        }
    }

    /**
     * the main function that ran the 'Bricker' game!
     * @param args args command line args
     */
    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}
