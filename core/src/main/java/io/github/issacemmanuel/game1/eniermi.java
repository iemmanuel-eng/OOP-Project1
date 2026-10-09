import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    private float speed;
    private int damage;
    private int health;
    private Rectangle hitbox;
    private String name;
    private float knowckback;
    private Texture texture;
    private Body enemyBody;
    private World world;
    public Enemy(Texture texture, float startX, float startY, String newname, float newSpeed, int newHealth, int newdamage, float knockback, float hitboxhight, float hitboxwidth, World world) {
        this.texture = texture;
        speed= newSpeed;
        health= newHealth;
        this.world = world;

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(
    (startX + hitboxwidth / 2f) / Player.PPM,
    (startY + hitboxhight / 2f) / Player.PPM
);
        enemyBody = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hitboxwidth / 2f / Player.PPM, hitboxhight / 2f / Player.PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1f;
        enemyBody.createFixture(fdef);
        shape.dispose();
        enemyBody.setUserData(this);
        damage = newdamage;
        name = newname;
        hitbox = new Rectangle(startX, startY, hitboxwidth, hitboxhight);
        setSize(texture.getWidth(), texture.getHeight());
        setPosition(startX, startY);
    }
  @Override
public void act(float delta) {
    super.act(delta);
    float posX = enemyBody.getPosition().x * Player.PPM - getWidth() / 2f;
    float posY = enemyBody.getPosition().y * Player.PPM - getHeight() / 2f;
    setPosition(posX, posY);
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

public Rectangle getRectangle() {
    return hitbox;
}

public static Enemy getCuber(Texture texture, float x, float y, World world) {
    return new Enemy(texture, x, y, "Cuber", 150f, 20, 5, 10, 25, 25, world);
}
}
