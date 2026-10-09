package io.github.issacemmanuel.game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SwitchScreen {
     public World makeWorld() {
          return new World(new Vector2(0f,-20f), true);
     }
     public void screenSwitch(World currentWorld, World newWorld, Texture backgroundTexture, Stage stage) {
          currentWorld.dispose();
          Image background = new Image(backgroundTexture);
          background.setSize(320f * 2,180f * 2);
          stage.addActor(background);
          }
          
     }