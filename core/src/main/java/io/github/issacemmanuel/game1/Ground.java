package io.github.issacemmanuel.game1;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground {
     public static void makeGround(float groundX, float groundY, World world, float width, float height) {
          BodyDef groundDef = new BodyDef();
          groundDef.type = BodyDef.BodyType.StaticBody;
          groundDef.position.set(groundX / Player.PPM, groundY / Player.PPM);
          Body groundBody = world.createBody(groundDef);
          PolygonShape groundShape = new PolygonShape();
          float halfWidth = width / Player.PPM;
          float halfHeight = height / Player.PPM;
          FixtureDef groundFix = new FixtureDef();
          groundFix.shape = groundShape;
          groundFix.friction = 0.4f;
          groundShape.setAsBox(halfWidth, halfHeight);
          groundBody.createFixture(groundFix);
          groundShape.dispose();
     }

     
}
