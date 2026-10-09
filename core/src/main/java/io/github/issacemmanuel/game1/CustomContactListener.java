package io.github.issacemmanuel.game1;
import java.util.HashSet;

import com.badlogic.gdx.physics.box2d.*;
public class CustomContactListener implements ContactListener {
     public final HashSet<Fixture> groundFixturesUnderfoot = new HashSet<>();
     @Override 
     public void beginContact(Contact contact) {
          Fixture fixA = contact.getFixtureA();
          Fixture fixB = contact.getFixtureB();

          if(isA_and_B(fixA, fixB, "FootBox", "Ground")) {
               Fixture ground = "Ground".equals(fixA.getUserData()) ? fixA : fixB;
               groundFixturesUnderfoot.add(ground);
          }
          if(isA_and_B(fixA, fixB, "FootBox", "Portal")) {
               System.out.println("Portal Touched!!!!");
               PlayScreen.transitionQueued = 1;
          }
     }
     @Override 
     public void endContact(Contact contact){
          Fixture fixA = contact.getFixtureA();
          Fixture fixB = contact.getFixtureB();
          if(isA_and_B(fixA, fixB, "FootBox", "Ground")) {
               Fixture ground = "Ground".equals(fixA.getUserData()) ? fixA : fixB;
               groundFixturesUnderfoot.remove(ground);
          }
          if(isA_and_B(fixA, fixB, "FootBox", "Portal")) {
               PlayScreen.transitionQueued = 0;
          }
     }
     @Override
     public void preSolve(Contact contact, Manifold oldManifold) {
          
     }

     @Override
     public void postSolve(Contact contact, ContactImpulse impulse) {

     }
     private boolean isA_and_B(Fixture fixA, Fixture fixB, String tag1, String tag2){
          Object data1 = fixA.getUserData();
          Object data2 = fixB.getUserData();
          if(data1 == null || data2 == null){
               return false;
          }
          return (data1.equals(tag1) && data2.equals(tag2) || data1.equals(tag2) && data2.equals(tag1));
     }

}