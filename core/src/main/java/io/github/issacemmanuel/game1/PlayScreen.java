package io.github.issacemmanuel.game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen extends ScreenAdapter{
         private Texture image;
         private float speed;
         private Player player1;
         private World world;
         private Stage stage;
         private static final float TIME_STEP = 1 / 60f;
         private float physicsAccumulator = 0f;

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
          return (new InputProcessor() {
               @Override 
               public boolean keyDown(int keyCode) {
                    if (player1 == null || player1.getPlayerBody() == null) return false;

                    switch(keyCode) {
                         case Input.Keys.A:
                         case Input.Keys.LEFT:
                              player1.getPlayerBody().applyLinearImpulse(new Vector2(-speed, 0), player1.getPlayerBody().getWorldCenter(), true);
                              return true;
                         case Input.Keys.D:
                         case Input.Keys.RIGHT:
                              player1.getPlayerBody().applyLinearImpulse(new Vector2(speed, 0), player1.getPlayerBody().getWorldCenter(), true);
                              return true;
                         case Input.Keys.SPACE:
                              player1.getPlayerBody().applyLinearImpulse(new Vector2(0, (float) speed), player1.getPlayerBody().getWorldCenter(), true);
                              return true;
                    }
                    return false;
               }
               @Override
               public boolean keyUp(int keyCode) {
                    if (player1 == null || player1.getPlayerBody() == null) return false;
                    if(keyCode == Input.Keys.A || keyCode == Input.Keys.LEFT || keyCode == Input.Keys.D || keyCode == Input.Keys.RIGHT) {
                         Vector2 velocity = player1.getPlayerBody().getLinearVelocity();
                         player1.getPlayerBody().setLinearVelocity(0f, velocity.y);
                    }
                    return false;
               }
               @Override public boolean keyTyped(char character) {return false; }
               @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}
               @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}
               @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {return false;}
               @Override public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}
               @Override public boolean mouseMoved(int screenX, int screenY) {return false;}
               @Override public boolean scrolled(float amountX, float amnountY) {return false;}

          });
     }
     @Override 
     public void show() {

          image = new Texture("placeholderperson.png");
          stage = new Stage(new FitViewport(image.getWidth() * 8,image.getHeight() * 5));
          world = new World(new Vector2(0f,-20f), true);
          player1 = new Player(new Vector2(image.getWidth() * 4,image.getHeight() * 2), image, world);
          stage.addActor(player1);
          Ground.makeGround(image.getWidth() * 4, 0, world, 8 * Player.PPM, 10);
          speed = 20f;
          InputMultiplexer multiplexer = new InputMultiplexer();
          multiplexer.addProcessor(stage);
          multiplexer.addProcessor(setUpInputProcessor());
          Gdx.input.setInputProcessor(multiplexer);
     }
     @Override 
     public void render(float delta) {
          ScreenUtils.clear(Color.BLACK);
          stepPhysicsSimulation(delta);
          stage.act(delta);
          stage.draw();


     }
     @Override 
     public void dispose() {
          if(stage != null) {
               stage.dispose();
          }
          if(world != null) {
               world.dispose();
          }
          if(image != null) {
               image.dispose();
          }
     }
     @Override
     public void resize(int width, int height) {
          stage.getViewport().update(width, height);
     }
     
}
