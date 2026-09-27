import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class extends Enemy {
    private float speed;
    private int health;
    private Rectangle hitbox;
    public Enemy(Texture texture, float startX, float startY, float newSpeed, int newHealth, float hitboxhight, float hitboxwidth) {
        super(texture);
        newSpeed = speed;
        newHealth = health;
        setPosition(startX, startY);
        hitbox = new Rectangle(startX, startY, hitboxwidth, hitboxhight);
    }
