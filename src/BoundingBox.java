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
}
