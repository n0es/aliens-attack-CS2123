public class Direction {
  String direction;

  public Direction(String direction) {
    this.direction = direction;
  }

  public IFunc<Alien, Alien> Move() {
    if(this.direction.equals("Right")){
      return new MoveAlienRight();
    }else if(this.direction.equals("Left")){
      return new MoveAlienLeft();
    }else if(this.direction.equals("Down")){
      return new MoveAlienDown();
    }else{
      return new MoveAlienUp();
    }
  }
}
