import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends Actor {
    private float speed;
    private int damage;
    private int health;
    private Rectangle hitbox;
    private String name;
    private float knowckback;
    private Texture texture;
    public Enemy(Texture texture, float startX, float startY, String newname, float newSpeed, int newHealth, int newdamage, float knockback, float hitboxhight, float hitboxwidth) {
        this.texture = texture;
        speed= newSpeed;
        health= newHealth;
        damage = newdamage;
        name = newname;
        hitbox = new Rectangle(startX, startY, hitboxwidth, hitboxhight);
        setPosition(startX, startY);
    } 
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        hitbox.setPosition(x, y);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
    public Rectangle getRectangle(){
        return hitbox;
    }
     public static Enemy getCuber(TextureRegion texture, float x, float y){
         return new Enemy(texture, x, y, "Cuber" , 150f, 20, 5, 10, 25, 25);
         
}

