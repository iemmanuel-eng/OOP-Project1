package io.github.issacemmanuel.game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
     final private Body playerBody;
     public boolean looping = true;
     public boolean isFacingLeft = false;
     public TextureRegion currentFrame;
     public TextureAtlas atlas;
     public Animation<TextureRegion> runningAnimation;
     public Batch batch;
     public float offsetX;
     float elapsedTime;


     public static float PPM = 100.0f;

     public Player(Vector2 startPosition, World world, String textureAtlasPath) {
          atlas = new TextureAtlas(Gdx.files.internal(textureAtlasPath));
          runningAnimation = new Animation<>(.5f, atlas.findRegions("idle"));
          

          BodyDef bdef = new BodyDef();
          bdef.type = BodyDef.BodyType.DynamicBody;
          bdef.position.set(startPosition.x / PPM, startPosition.y / PPM);
          playerBody = world.createBody(bdef);

          PolygonShape shape = new PolygonShape();
          shape.setAsBox((20f / 2f) / PPM, (36f / 2f) / PPM);

          FixtureDef fdef = new FixtureDef();
          fdef.shape = shape;
          playerBody.createFixture(fdef);
          shape.dispose();

          playerBody.setUserData(this);
          setBounds(startPosition.x + 40f, startPosition.y, 20f, 36f);

          BodyDef footbox = new BodyDef();
          footbox.type = BodyDef.BodyType.DynamicBody;
          footbox.position.set(startPosition.x / PPM, startPosition.y / PPM);

          PolygonShape footshape = new PolygonShape();
          footshape.setAsBox((20f /2f) / PPM, (36f / 2f) / PPM );

          
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
     public void draw(Batch playerBatch, float parentAlpha) {
          batch = playerBatch;
          super.draw(batch, parentAlpha);
          elapsedTime += Gdx.graphics.getDeltaTime();
          currentFrame = runningAnimation.getKeyFrame(elapsedTime, looping);
          if(isFacingLeft && !currentFrame.isFlipX()) {
               currentFrame.flip(true, false);
          }
          if(!isFacingLeft && currentFrame.isFlipX()) {
               currentFrame.flip(true, false);
          }
          if (currentFrame instanceof TextureAtlas.AtlasRegion) {
               TextureAtlas.AtlasRegion region = (TextureAtlas.AtlasRegion) currentFrame;
               offsetX = region.offsetX;
               if(currentFrame.isFlipX()) {
                    offsetX = region.originalWidth - region.packedWidth - region.offsetX;
               }
               batch.draw(
                    region, getX()-13f + offsetX, 
                    getY() + region.offsetY, getOriginX() - offsetX,
                    getOriginY() - region.offsetY,
                    region.packedWidth,
                    region.packedHeight, getScaleX(), getScaleY(), getRotation()
               );
          }
          else{
               batch.draw(currentFrame, getX(), getY(), 102.4f, 128.5f);
          }
          
     }
     public Body getPlayerBody() {
          return playerBody;
     }
     public void setIsFacingLeft(boolean isFacingLeft) {
          this.isFacingLeft = isFacingLeft;
     }
     public void setLooping(boolean islooping) {
          looping = islooping;
     }
     public void setRunningAnimation(float frameDuration, String keyWord, String newTextureAtlasPath) {
          atlas = new TextureAtlas(Gdx.files.internal(newTextureAtlasPath));
          this.runningAnimation = new Animation<>(frameDuration, atlas.findRegions(keyWord));
     }
}
