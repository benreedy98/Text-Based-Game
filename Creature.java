import java.util.*;

public abstract class Creature {
  public String name;
  public String description;
  public Room room;

  public void clean(Room r){
    if(r.state.equalsIgnoreCase("dirty")){
      r.state = "half-dirty";
      System.out.println(r.name + " is now " + r.state);
    } else if(r.state.equalsIgnoreCase("half-dirty")){
      r.state = "clean";
      System.out.println(r.name + " is now " + r.state); 
    } else if(r.state.equalsIgnoreCase("clean")){
      System.out.println(r.name + " is already clean"); 
    }
  }

  public void dirty(Room r){
    if(r.state.equalsIgnoreCase("dirty")){
      System.out.println(r.name + " is already dirty"); 
    } else if(r.state.equalsIgnoreCase("half-dirty")){
      r.state = "dirty";
      System.out.println(r.name + " is now dirty"); 
    } else if(r.state.equalsIgnoreCase("clean")){
      r.state = "half-dirty";
      System.out.println(r.name + " is now half-dirty"); 
    }
  }

  public void look(){
    System.out.println("Room: " + this.room.name);
    System.out.println("Description: " + this.room.description);
    System.out.println("State: " + this.room.state);

    if(this.room.north != null){
      System.out.println("North Neighbor: " + this.room.north.name); 
    }

    if(this.room.east != null){
      System.out.println("East Neighbor: " + this.room.east.name); 
    }

    if(this.room.south != null){
      System.out.println("South Neighbor: " + this.room.south.name); 
    }

    if(this.room.west != null){
      System.out.println("West Neighbor: " + this.room.west.name); 
    }

    System.out.println("Creatures:");
    for(Creature i: this.room.creatures.values()){
      System.out.println(i.name + ", " + i.description);
    }
  }

  public void leaveRoom(Room r, PC pc){
    ArrayList<Room> neighbors = new ArrayList<Room>();
   
    if(r.north != null){
      if(r.north.creatures.size() != 10){
        neighbors.add(r.north);
      }
    }

    if(r.east != null){
      if(r.east.creatures.size() != 10){
        neighbors.add(r.east);
      }
    }

    if(r.south != null){
      if(r.south.creatures.size() != 10){
        neighbors.add(r.south);
      } 
    }

    if(r.west != null){
      if(r.west.creatures.size() != 10){
        neighbors.add(r.west);
      }
    }

    if(neighbors.size() == 0){
      System.out.println(this.name + "drilled a hole in the ceiling and crawled out of the house");
      Room lastRoom = this.room;
      this.room.removeCreature(this);
      for(Creature c : this.room.creatures.values()){
        if(c instanceof Animal){
          c.growl(pc);	
	} else if(c instanceof NPC){
          c.grumble(pc);	
	}
      }
    } else {
      int rand = getRandom(0, neighbors.size() - 1);
      neighbors.get(rand).addCreature(this);
      System.out.println(this.name + " is now in room " + this.room.name);

      if(this instanceof Animal){
        if(this.room.state.equalsIgnoreCase("dirty")){
	  this.clean(this.room);
	}  
      } else if(this instanceof NPC){
        if(this.room.state.equalsIgnoreCase("clean")){
	  this.dirty(this.room);
	}
      }
    }
  }

  public int getRandom(int min, int max){
    Random random = new Random();
    return random.nextInt(max - min) + min;
  }

  public void growl(PC pc){
    pc.respect = pc.respect - 1;
    System.out.println(this.name + " growls at " + pc.name);
    System.out.println(pc.name + " respect is " + pc.respect);
  }

  public void lickFace(PC pc){
    pc.respect = pc.respect + 1;
    System.out.println(this.name + " licks " + pc.name + "'s face");
    System.out.println(pc.name + " respect is " + pc.respect);
  }

  public void grumble(PC pc){
    pc.respect = pc.respect - 1;
    System.out.println(this.name + "grumbles at " + pc.name);
    System.out.println(pc.name + " respect is " + pc.respect);
  }

  public void smile(PC pc){
    pc.respect = pc.respect + 1;
    System.out.println(this.name + " smiles at " + pc.name);
    System.out.println(pc.name + " respect is " + pc.respect);
  }
}
