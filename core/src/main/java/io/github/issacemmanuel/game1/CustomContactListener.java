package io.github.issacemmanuel.game1;
//This line above conencts all of our documents into one package and is necessary across all of them.

import java.util.HashSet;
import com.badlogic.gdx.physics.box2d.*;
//Just some imports.

public class CustomContactListener implements ContactListener {
     public final HashSet<Fixture> groundFixturesUnderfoot = new HashSet<>();
    //Making a record of all the bodies of the grounds underneath the player's feet.
     @Override 
     public void beginContact(Contact contact) {
          Fixture fixA = contact.getFixtureA();
          Fixture fixB = contact.getFixtureB();
         //Takes the contact from the world and checks if 2 things are in contact.
          if(isA_and_B(fixA, fixB, "FootBox", "Ground")) {
               Fixture ground = "Ground".equals(fixA.getUserData()) ? fixA : fixB;
               groundFixturesUnderfoot.add(ground);
          }
         //This is between the footbox and the ground.
          if(isA_and_B(fixA, fixB, "FootBox", "Portal")) {
               System.out.println("Portal Touched!!!!");
               PlayScreen.transitionQueued = 1;
          }
         //This is between the footbox and the portal.
     }
     @Override 
     public void endContact(Contact contact){
          Fixture fixA = contact.getFixtureA();
          Fixture fixB = contact.getFixtureB();
          if(isA_and_B(fixA, fixB, "FootBox", "Ground")) {
               Fixture ground = "Ground".equals(fixA.getUserData()) ? fixA : fixB;
               groundFixturesUnderfoot.remove(ground);
              //This method is to mark the end of contact between 2 hitboxes.
          }
          if(isA_and_B(fixA, fixB, "FootBox", "Portal")) {
               PlayScreen.transitionQueued = 0;
              //This signals to our playscreen that a player has touched a portal, which does more things inside the playscreen.
          }
     }
     @Override
     public void preSolve(Contact contact, Manifold oldManifold) {
          
     }

     @Override
     public void postSolve(Contact contact, ContactImpulse impulse) {

     }
    //The methods above are just to make sure that the new CustomContactListener is properly implementing ContactListener.

    
     private boolean isA_and_B(Fixture fixA, Fixture fixB, String tag1, String tag2){
          Object data1 = fixA.getUserData();
          Object data2 = fixB.getUserData();
          if(data1 == null || data2 == null){
               return false;
          }
          return (data1.equals(tag1) && data2.equals(tag2) || data1.equals(tag2) && data2.equals(tag1));
     }
    //This is the private method we use to identify what 2 objects are touching.

}
