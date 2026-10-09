package io.github.issacemmanuel.game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen extends ScreenAdapter{

         private float speed;
         private CustomContactListener listener = new CustomContactListener();
         private Image background;
         private float dashCoolDown = 0f;
         private Texture backgroundTexture = new Texture(Gdx.files.internal("Background1 Layers/pixil-frame-0.png"));
         private Image foreground;
         private Texture foregroundTexture = new Texture("Background1 Layers/background4.png");
         private Player player1;
         private World world;
         private Stage stage;
         private static final float TIME_STEP = 1 / 60f;
         private float physicsAccumulator = 0f;
         private Box2DDebugRenderer debugRenderer;
         public static int transitionQueued;
         private int stageNumber = 1;

        private Enemy cuber;
        private Texture cuberTexture;

     public void stepPhysicsSimulation(float delta) {
          float frametime = Math.min(delta, 0.25f);
          physicsAccumulator += frametime;
          while(physicsAccumulator >= TIME_STEP) {
               world.step(TIME_STEP, 6, 2);
               physicsAccumulator -= TIME_STEP;
          }
     }
     public void setSpeed(float newSpeed) {
          this.speed = newSpeed;
     }
     private InputProcessor setUpInputProcessor() {
          return (new InputAdapter() {
               @Override 
               public boolean keyDown(int keyCode) {
                    if (player1 == null || player1.getPlayerBody() == null) return false;

                    switch(keyCode) {
                         case Input.Keys.A:
                         case Input.Keys.LEFT:
                              player1.setIsFacingLeft(false);
                              player1.setRunningAnimation(.1f, "walking", "Walking_Animation.atlas");
                              return true;
                         case Input.Keys.D:
                         case Input.Keys.RIGHT:
                              player1.setIsFacingLeft(true);
                              player1.setRunningAnimation(.1f, "walking", "Walking_Animation.atlas");
                              return true;
                         case Input.Keys.SPACE:
                              boolean isPlayerGrounded = !listener.groundFixturesUnderfoot.isEmpty();
                              float yVel = player1.getPlayerBody().getLinearVelocity().y;
                              if(isPlayerGrounded && Math.abs(yVel) < 0.05f){
                              Vector2 vel = player1.getPlayerBody().getLinearVelocity();
                              player1.getPlayerBody().setLinearVelocity(vel.x, 0);
                              player1.getPlayerBody().applyLinearImpulse(new Vector2(0, (float) 6), player1.getPlayerBody().getWorldCenter(), true);
                              }
                              return true;
                    }
                    return false;
               }
               @Override
               public boolean keyUp(int keyCode) {
                    if (player1 == null || player1.getPlayerBody() == null) return false;
                    if(keyCode == Input.Keys.A || keyCode == Input.Keys.LEFT || keyCode == Input.Keys.D || keyCode == Input.Keys.RIGHT) {
                         if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                              player1.setRunningAnimation(.5f, "idle", "Idle_Animation.atlas");
                         }
                    }
                    return false;
               }
          });
     }
     private void movement() {
          int moveLeft = -1;
               if (player1.isFacingLeft) {
                    moveLeft = 1;
               } 
          if (player1 == null || player1.getPlayerBody() == null) return;
          Vector2 vel = player1.getPlayerBody().getLinearVelocity();
          if(dashCoolDown > 0) {
               dashCoolDown -= Gdx.graphics.getDeltaTime();
          }

          float velX = 0f;
          if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
               velX = (-speed / 7f);
          }
          if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
               velX = speed / 7f;
          }
          if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && dashCoolDown <= 0) {
               player1.getPlayerBody().applyLinearImpulse(new Vector2(moveLeft * 40f, 0f), player1.getPlayerBody().getPosition(), true);
               dashCoolDown = 1.5f;
               return;
          }
          if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && (Gdx.input.isKeyPressed(Input.Keys.A) | Gdx.input.isKeyPressed(Input.Keys.D))) {
               velX = moveLeft * speed / 4f;
          }
          player1.getPlayerBody().setLinearVelocity(velX, vel.y);
     }
     @Override 
     public void show() {
          //Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
          //Gdx.graphics.setFullscreenMode(currentMode);
          stage = new Stage(new FitViewport(38* 15f,36 *9f));
          world = new World(new Vector2(0f,-20f), true);
          background = new Image(backgroundTexture);
          background.setSize(320f * 2,180f * 2);
          foreground = new Image(foregroundTexture);
          foreground.setSize(320f *2, 180f *2);
          player1 = new Player(new Vector2(10f, 200f), world, "Idle_Animation.atlas");


          stage.addActor(background);
          stage.addActor(player1);
         
          cuberTexture = new Texture("cuber (1)-frame0.png");
          cuber = Enemy.getCuber(cuberTexture, 300, 200,world);
          stage.addActor(cuber);
         
          Ground.makeGround(image.getWidth() * 4, 0, world, 8 * Player.PPM, 10);
          speed = 20f;
          stage.addActor(foreground);
          debugRenderer = new Box2DDebugRenderer();

          Ground.makeGround(320f, 108f, world, 640f, 2f);


          Ground.makeGround(0f, 0f, world, 2f, 360f);

          Ground.makeGround(540f, 0f, world, 2f, 240f);

          Ground.makeGround(60f, 143f, world, 75f, 2f);

          Ground.makeGround(587f, 245f, world, 50f, 2f);
          Ground.makeGround(640f, 270f, world, 10f, 40f, true);
         
          speed = 15f;
          InputMultiplexer multiplexer = new InputMultiplexer();
          multiplexer.addProcessor(stage);
          multiplexer.addProcessor(setUpInputProcessor());
          Gdx.input.setInputProcessor(multiplexer);
     }
        public void queueTransition() {
          transitionQueued--;
     }
     @Override 
     public void render(float delta) {
          if(transitionQueued > 0 && stageNumber == 1){
               initiateTransition(backgroundTexture2, new Vector2(110f, 100f), 2);
               Ground.makeGround(320f, 0f, world, 640f, 10f);
               Ground.makeGround(40f, 50f, world, 40f, 100f);
               Ground.makeGround(40f, 270f, world, 60f, 50f);
               Ground.makeGround(0f, 180f, world, 2f, 360f);
               Ground.makeGround(220f, 80f, world, 4f, 80f);
               Ground.makeGround(220f, 160f, world, 40f, 4f );
               Ground.makeGround(345f, 170f, world, 15f, 130f);
               Ground.makeGround(600f, 220f, world, 60f, 140f);
               Ground.makeGround(570f, 10f, world,20f, 40f, true);
               stageNumber = 2;
          }
          else if(transitionQueued > 0 && stageNumber == 2){
               initiateTransition(backgroundTexture3, new Vector2(40f, 200f), 3);
               Ground.makeGround(20f, 30f, world, 80f, 76f);
               Ground.makeGround(0, 180, world, 2, 320);
               Ground.makeGround(140f, 210f, world, 70f, 5f);
               Ground.makeGround(205f, 170f, world, 5f, 170f);
               Ground.makeGround(300f, 210f, world, 7f, 115f);
               Ground.makeGround(332f, 20f, world, 42f, 10f);
               Ground.makeGround(600f, 100f, world, 90f, 9f);
               Ground.makeGround(600f, 190f, world, 95f, 9f);
               Ground.makeGround(240f, 125f, world, 10f, 5f);
               stageNumber = 0;
          }
          movement();
          ScreenUtils.clear(Color.BLACK);
          stepPhysicsSimulation(delta);
          if(player1 != null) {
          stage.act(delta);
          stage.draw();
          debugRenderer.render(world, stage.getCamera().combined.cpy().scl(Player.PPM));
          }
    public void initiateTransition(Texture newBack, Vector2 playerStartPosition, int stageNumber) {
          transitionQueued--;
          world.dispose();
          world = new World(new Vector2(0f,-20f), true);
          world.setContactListener(listener);
          player1.remove();
          background.remove();
          if(foreground != null){
            foreground.remove();
          }
          background = new Image(newBack);
          background.setSize(320f * 2,180f * 2);
          stage.addActor(background);
          player1.toFront();
          player1 = new Player(playerStartPosition, world, "Idle_Animation.atlas");
          stage.addActor(player1);

     }
     }
     @Override 
     public void dispose() {
          if(stage != null) {
               stage.dispose();
          }
          if(world != null) {
               world.dispose();
          }
          if(debugRenderer != null) {
               debugRenderer.dispose();
          }
          if(cuberTexture != null) {
                cuberTexture.dispose();
}
     }
     @Override
     public void resize(int width, int height) {
          stage.getViewport().update(width, height, false);
     }
     
}
