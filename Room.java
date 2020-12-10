import java.util.*;

public class Room {
  public String name;
  public String description;
  public String state;
  public Room north;
  public Room east;
  public Room south;
  public Room west;
  HashMap<String, Creature> creatures = new HashMap<String, Creature>();

  public Room(String n){
    name = n;
  }

  public Room(String n, String d, String s){
    name = n;
    description = d;
    state = s;
  }

  public void addCreature(Creature c){
    creatures.put(c.name, c);
    c.room = this;
  }

  public void removeCreature(Creature c){
    this.creatures.remove(c.name);
  }


}
