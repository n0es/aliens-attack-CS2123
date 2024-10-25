public class Keys {
  boolean left;
  boolean right;
  boolean space;

  Keys() {
    this(false, false, false);
  }

  Keys(boolean left, boolean right, boolean space) {
    this.left = left;
    this.right = right;
    this.space = space;
  }

  public Keys press(String key) {
    if (key.equals("left")) {
      return new Keys(true, this.right, this.space);
    } else if (key.equals("right")) {
      return new Keys(this.left, true, this.space);
    } else if (key.equals(" ")) {
      return new Keys(this.left, this.right, true);
    } else {
      return this;
    }
  }

  public Keys release(String key) {
    if (key.equals("left")) {
      return new Keys(false, this.right, this.space);
    } else if (key.equals("right")) {
      return new Keys(this.left, false, this.space);
    } else if (key.equals(" ")) {
      return new Keys(this.left, this.right, false);
    } else {
      return this;
    }
  }

  public boolean isPressed(String key) {
    if (key.equals("left")) {
      return this.left;
    } else if (key.equals("right")) {
      return this.right;
    } else if (key.equals(" ")) {
      return this.space;
    } else {
      return false;
    }
  }
}
