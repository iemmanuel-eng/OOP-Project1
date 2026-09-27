package io.github.issacemmanuel.game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
     final private Texture texture;
     final private Body playerBody;
     final private World world;

     public static float PPM = 100.0f;

     public Player(Vector2 startPosition, Texture playerTexture, World world) {
          this.texture  = playerTexture;
          this.world = world;

          BodyDef bdef = new BodyDef();
          bdef.type = BodyDef.BodyType.DynamicBody;
          bdef.position.set(startPosition.x / PPM, startPosition.y / PPM);
          playerBody = world.createBody(bdef);

          PolygonShape shape = new PolygonShape();
          shape.setAsBox((texture.getWidth() / 2f) / PPM, (texture.getHeight() / 2f) / PPM);

          FixtureDef fdef = new FixtureDef();
          fdef.shape = shape;
          playerBody.createFixture(fdef);
          shape.dispose();

          playerBody.setUserData(this);
          setBounds(startPosition.x, startPosition.y, texture.getWidth(), texture.getHeight());
     }
     @Override
     public void act(float delta) {
          super.act(delta);
           float PosX = (playerBody.getPosition().x * PPM - getWidth() / 2f);
           float PosY = (playerBody.getPosition().y * PPM - getHeight() / 2f);
           setPosition(PosX, PosY);
           setRotation((float) Math.toDegrees(playerBody.getAngle()));
     }
     @Override 
     public void draw(Batch batch, float parentAlpha) {
          super.draw(batch, parentAlpha);
          batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(),getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
     }
     public Body getPlayerBody() {
          return playerBody;
     }
}
