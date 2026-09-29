import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Sprite {
    private float speed;
    private int health;
    private Rectangle hitbox;
    public Enemy(Texture texture, float startX, float startY, float newSpeed, int newHealth, float hitboxhight, float hitboxwidth) {
        super(texture);
        speed= newSpeed;
        health= newHealth;
        hitbox = new Rectangle(startX, startY, hitboxwidth, hitboxhight);
        setPosition(startX, startY);
    }
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        hitbox.setPosition(x, y);
    }
    public Rectangle getRectangle(){
        return hitbox;
}

