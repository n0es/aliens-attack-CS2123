import javalib.funworld.*;
import javalib.worldimages.*;

import java.awt.*;

public class Bullet {
  int x;
  int y;

  Bullet(int x, int y) {
    this.x = x;
    this.y = y;
  }

//  WorldImage bulletImage() {
//    return new RectangleImage(10, 10, OutlineMode.SOLID, Color.RED);
//  }
}

class DrawBullets implements IFunc2<Bullet, WorldScene, WorldScene> {
  WorldImage bulletImage = new RectangleImage(4, 12, OutlineMode.SOLID, Color.RED);
  public WorldScene apply(Bullet b, WorldScene scene) {
    return scene.placeImageXY(
      bulletImage,
      b.x,
      b.y
    );
  public boolean collidesWith(Alien alien) {
    return (this.x == alien.getX()) && (this.y == alien.getY());
  }
}

// tick bullets
// if y < 0, remove bullet from linked list
// else, move bullet up by 5
class TickBullets implements IFunc2<Bullet, ILo<Bullet>, ILo<Bullet>> {
  public ILo<Bullet> apply(Bullet b, ILo<Bullet> bullets) {
    if (b.y > 0) {
      return new ConsLo<Bullet>(new Bullet(b.x, b.y - 5), bullets);
    } else {
      return bullets;
    }
  }
}
class BulletHitAlien implements IPredicate2<Bullet, Alien>{
  public boolean apply(Bullet bullet, Alien alien){
    return (bullet.collidesWith(alien));
  }
}
