package io.github.issacemmanuel.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

//All of the imports that we use in our code

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends ApplicationAdapter {
    //Opening the main class

    private SpriteBatch batch;
    private Texture image;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Sprite player;
    //Creating all of our variables, specifically the Sprite, SpriteBatch, Texture and Viewport of our player/game


    @Override
    public void create() {
        /*This is basically the constructor. All it does is initialize all the assets we want to use in game.
        Sound effects, music, pngs, all of that goes here.
        */

        batch = new SpriteBatch();
        image = new Texture("placeholderPerson.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8,5);
        player = new Sprite(image);
        player.setSize(1,1);
        player.setPosition(0,0);
        player.setOriginCenter();
        //Initializing all the variables, but pay special attention to Viewport, since thats how we change the size of our gameworld.
    }
    @Override 
    public void resize(int width, int height) {
     viewport.update(width, height, true);
     // We aren't using resize right now, but we'll probably use it sometime soon, to move the camera around or smth.
    }

    @Override
    public void render() {
        /*This method is the most important. it loops every frame, and holds the controls
        for most of our game's aspects, like displaying objects and checking hitboxes.
         */

     input();
     logic();
     draw();
    }

    private void input() {
     float speed = 2.0f;
     //Speed is how fast we make our characters move.

     float delta = Gdx.graphics.getDeltaTime();
     /*DeltaTime is just a number for how many seconds have passed since the last frame.
     We use this number to make sure that everyone has the same experience no matter how many
     FPS they acheive.
     */

     if (Gdx.input.isKeyPressed(Input.Keys.D)) {
          player.translateX(speed * delta);
     }
     if (Gdx.input.isKeyPressed(Input.Keys.A)) {
          player.translateX( -1* speed * delta);
     }
     if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        player.translateY(speed * delta);
     }
     if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        player.translateY(-1 * speed * delta);
     }
     }
     //This is the WASD movement code, every frame, it checks if WASD was pressed and translates accordingly.

    private void logic() {

    }
    private void draw() {
     ScreenUtils.clear(Color.BLACK);
     viewport.apply();
     spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
     spriteBatch.begin();
     player.draw(spriteBatch);
     spriteBatch.end();
     /*
     The clear part just makes sure we reset our frame every second to black, but it can really by any color.
     Make sure to apply the viewport so that we can see the things. Here's the important thing, MAKE SURE that all
     drawing code is placed between the spriteBatch.begin() and spriteBatch.end(), it will break otherwise.
      */
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
