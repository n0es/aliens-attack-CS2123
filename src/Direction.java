public class Direction {
  String direction;

  public Direction(String direction) {
    this.direction = direction;
  }

  public boolean equals(String d) {
    return this.direction.equals(d);
  }

}

class MoveDirection implements IFunc<Alien, Alien> {
  Direction direction;

  MoveDirection(Direction direction) {
    this.direction = direction;
  }

  public Alien apply(Alien alien) {
    if (direction.equals("Right")) {
      return alien.moveRight();
    } else if (direction.equals("Left")) {
      return alien.moveLeft();
    } else if (direction.equals("Down")) {
      return alien.moveDown();
    } else {
      return alien;
    }
  }
}

class TickDirection implements IFunc2<Direction, ILo<Alien>, Direction> {
  int max;
  int min;
  int tick;

  TickDirection(int max, int min, int tick) {
    this.max = max;
    this.min = min;
    this.tick = tick;
  }

  IPredicate<Alien> AlienReachedRightEdge(int max) {
    return new IPredicate<Alien>() {
      public boolean apply(Alien alien) {
        return alien.getX() == max-2;
      }
    };
  }

  IPredicate<Alien> AlienReachedLeftEdge(int min) {
    return new IPredicate<Alien>() {
      public boolean apply(Alien alien) {
        return alien.getX() == min+1;
      }
    };
  }

  public Direction apply(Direction d, ILo<Alien> aliens) {
    if (tick==0 && aliens.any(AlienReachedRightEdge(this.max))) {
      if (d.equals("Right")) {
        return new Direction("Down");
      } else {
        return new Direction("Left");
      }
    } else if (tick==0 && aliens.any(AlienReachedLeftEdge(this.min))) {
      if (d.equals("Left")) {
        return new Direction("Down");
      } else {
        return new Direction("Right");
      }
    } else {
      return d;
    }
  }
}