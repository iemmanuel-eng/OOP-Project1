package io.github.issacemmanuel.game1;

import com.badlogic.gdx.Game;

//All of the imports that we use in our code

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends Game {
    @Override 
    public void create() {
        this.setScreen(new PlayScreen());
    }
}

