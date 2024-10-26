import javalib.funworld.WorldScene;
import javalib.worldimages.*;

import java.awt.*;

public class BoundingBox {
    int x;
    int y;
    int width;
    int height;

    public BoundingBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(BoundingBox that) {
        return this.x < that.x + that.width &&
                this.x + this.width > that.x &&
                this.y < that.y + that.height &&
                this.y + this.height > that.y;
    }

    WorldScene draw(WorldScene scene) {
        return scene.placeImageXY(
                new RectangleImage(this.width, this.height, OutlineMode.OUTLINE, Color.WHITE),
                this.x + this.width / 2,
                this.y + this.height / 2
        );
    }
}
