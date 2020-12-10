import java.util.*;
import java.io.*;

public class PC extends Creature{
  public int respect = 40;
  boolean bClean = false;
  boolean bDirty = false;

  public PC(String n, String d){
    name = n;
    description = d;
  }

  public void play(Scanner s){
    String command = s.nextLine();

    if(command.equalsIgnoreCase("help")){
      System.out.println("help = display all commands");
      System.out.println("look = look at the room");
      System.out.println("clean - clean the room");
      System.out.println("dirty - dirty the room");
      System.out.println("north/east/south/west - move to neighbors");
      System.out.println("exit/quit - quit the game");
    
    } else if(command.equalsIgnoreCase("look")){
      this.look();
    
    } else if(command.equalsIgnoreCase("clean")){
      this.clean(this.room);
      bClean = true;

    } else if(command.equalsIgnoreCase("dirty")){
      this.dirty(this.room);
      bDirty = true;
      
    } else if(command.equalsIgnoreCase("north")){
      if(this.room.north != null){
        if(this.room.north.creatures.size() != 10){
          Room lastRoom = this.room;
          this.room.north.addCreature(this);
          lastRoom.removeCreature(this);
          System.out.println(this.name + " is now in room " + this.room.name);	        } else { System.out.println("North neighbor is full"); }
     } else { System.out.println("There is no north neighbor"); }

    } else if(command.equalsIgnoreCase("east")){
      if(this.room.east != null){
        if(this.room.east.creatures.size() != 10){
          Room lastRoom = this.room;
          this.room.east.addCreature(this);
          lastRoom.removeCreature(this);
          System.out.println(this.name + " is now in room " + this.room.name); 
	} else { System.out.println("East Neighbor is full"); }
      } else { System.out.println("There is no east neighbor");}

    } else if(command.equalsIgnoreCase("south")){
      if(this.room.south != null){
        if(this.room.south.creatures.size() != 10){
          Room lastRoom = this.room;
          this.room.south.addCreature(this);
          lastRoom.removeCreature(this);
          System.out.println(this.name + " is now in " + this.room.name);  
	} else { System.out.println("South neighbor is full"); }
      } else { System.out.println("There is no south Neighbor"); } 

    } else if(command.equalsIgnoreCase("west")){
      if(this.room.west != null){
        if(this.room.west.creatures.size() != 10){
          Room lastRoom = this.room;
          this.room.west.addCreature(this);
          lastRoom.removeCreature(this);
          System.out.println(this.name + " is now in " + this.room.name);  
	} else { System.out.println("West neighbor is full"); }
      } else { System.out.println("There is no west neighbor"); }

    } else if(command.substring(command.indexOf(":") + 1, command.length()).equalsIgnoreCase("clean")){
      String name = command.substring(0, command.indexOf(":"));
      Creature c = this.room.creatures.get(name);
      if(c != null){
        makeClean(c);
		bClean = true;
      } else { System.out.println("Creature not found"); }

    } else if(command.substring(command.indexOf(":") + 1, command.length()).equalsIgnoreCase("dirty")){
      String name = command.substring(0, command.indexOf(":"));
      Creature c = this.room.creatures.get(name);
      if(c != null) {
        makeDirty(c); 
		bDirty = true;
      } else { System.out.println("Creature not found"); }

    } else if(command.substring(command.indexOf(":") + 1, command.length()).equalsIgnoreCase("north")){
      String name = command.substring(0, command.indexOf(":"));
      Creature c = this.room.creatures.get(name);
      if(c != null){
        makeNorth(c);  
      } else { System.out.println("Creature not found"); }

    } else if(command.substring(command.indexOf(":") + 1, command.length()).equalsIgnoreCase("east")){
		String name = command.substring(0, command.indexOf(":"));
        Creature c = this.room.creatures.get(name);
        if(c != null){
          makeEast(c);  
      } else { System.out.println("Creature not found"); }
    
    } else if(command.substring(command.indexOf(":") + 1, command.length()).equalsIgnoreCase("south")){
		String name = command.substring(0, command.indexOf(":"));
        Creature c = this.room.creatures.get(name);
        if(c != null){
          makeSouth(c);  
      } else { System.out.println("Creature not found"); }
    
    } else if(command.substring(command.indexOf(":") + 1, command.length()).equalsIgnoreCase("west")){
	  String name = command.substring(0, command.indexOf(":"));
      Creature c = this.room.creatures.get(name);
      if(c != null){
        makeWest(c);  
      } else { System.out.println("Creature not found"); }
    
    } else if(command.equalsIgnoreCase("exit")){
	  System.exit(0);
    
    } else if(command.equalsIgnoreCase("quit")){
	  System.exit(0);
    
    } else {
	  System.out.println("Invalid command");
    
    }
	
	for(Creature c : this.room.creatures.values()){
		if(c instanceof Animal){
			if(bClean){
				c.lickFace(this);
			} else if(bDirty){
				c.growl(this);
			}
		} else if(c instanceof NPC){
			if(bClean){
				c.grumble(this);
			} else if(bDirty){
				c.smile(this);
			}
		}
	}
	
	bClean = false;
	bDirty = false;
	
   /* In order to avoid ConcurrentModificationException,
	* create a separate array to store creatures to be removed
	*/
	
	ArrayList<String> toBeRemoved = new ArrayList<String>();
	
	for(String k : this.room.creatures.keySet()){
	  if(this.room.creatures.get(k) instanceof Animal){
		if(this.room.state.equalsIgnoreCase("dirty")){
		  toBeRemoved.add(k);
		}
	  } else if(this.room.creatures.get(k) instanceof NPC){
		if(this.room.state.equalsIgnoreCase("clean")){
		  toBeRemoved.add(k);
		}
	  }
	}
	
	for(String k : toBeRemoved){
	  Creature c = this.room.creatures.get(k);
	  c.leaveRoom(this.room, this);
	  this.room.creatures.remove(k);
	}
	
	if(this.respect <= 0){
	  System.out.println("The game ends with " + this.name + " in disgrace.");
	  System.exit(0);
		
	} else if(this.respect >= 80){
	  System.out.println("The game ends with " + this.name + " in praise!");
	  System.exit(0);
	}
  }
  
  public void makeClean(Creature c){
	  c.clean(this.room);
		
		if(c instanceof Animal){
			
			for(int i = 0; i < 2; i = i + 1){
				
				c.lickFace(this);
				
			}
			
		} else if(c instanceof NPC){
			
			for(int i = 0; i < 2; i = i + 1){
				
				c.grumble(this);
				
			}
			
		}
  }
  
  public void makeDirty(Creature c){
	  c.dirty(this.room);
		
		if(c instanceof Animal){
			
			for(int i = 0; i < 2; i = i + 1){
				
				c.growl(this);
				
			}
			
		} else if(c instanceof NPC){
			
			for(int i = 0; i < 2; i = i + 1){
				
				c.smile(this);
				
			}
			
		}
  }
  
  public void makeNorth(Creature c){
	  if(this.room.north != null){
			
			if(this.room.north.creatures.size() != 10){
				
				Room lastRoom = c.room;
				
				this.room.north.addCreature(c);
				
				lastRoom.removeCreature(c);
				
				System.out.println(c.name + " is now in room " + c.room.name);
				
			} else { 
			
				System.out.println("North neighbor is full"); 
				
				if(c instanceof Animal){
					
					c.growl(this);
					
				} else if(c instanceof NPC){
					
					c.grumble(this);
					
				}
			}
			
		} else { System.out.println("There is no north neighbor"); }
  }
  
  public void makeEast(Creature c){
	  if(this.room.east != null){
			
			if(this.room.east.creatures.size() != 10){
				
				Room lastRoom = c.room;
				
				this.room.east.addCreature(c);
				
				lastRoom.removeCreature(c);
				
				System.out.println(c.name + " is now in room " + c.room.name);
				
			} else { 
			
				System.out.println("East neighbor is full"); 
				
				if(c instanceof Animal){
					
					c.growl(this);
					
				} else if(c instanceof NPC){
					
					c.grumble(this);
					
				}
			}
			
		} else { System.out.println("There is no east neighbor"); }
  }
  
  public void makeSouth(Creature c){
	  if(this.room.south != null){
			
			if(this.room.south.creatures.size() != 10){
				
				Room lastRoom = c.room;
				
				this.room.south.addCreature(c);
				
				lastRoom.removeCreature(c);
				
				System.out.println(c.name + " is now in room " + c.room.name);
				
			} else { 
			
				System.out.println("South neighbor is full"); 
				
				if(c instanceof Animal){
					
					c.growl(this);
					
				} else if(c instanceof NPC){
					
					c.grumble(this);
					
				}
			}
			
		} else { System.out.println("There is no south neighbor"); }
  }
  
  public void makeWest(Creature c){
	  if(this.room.west != null){
			
			if(this.room.west.creatures.size() != 10){
				
				Room lastRoom = c.room;
				
				this.room.west.addCreature(c);
				
				lastRoom.removeCreature(c);
				
				System.out.println(c.name + " is now in room " + c.room.name);
				
			} else { 
			
				System.out.println("West neighbor is full"); 
				
				if(c instanceof Animal){
					
					c.growl(this);
					
				} else if(c instanceof NPC){
					
					c.grumble(this);
					
				}
			}
			
		} else { System.out.println("There is no west neighbor"); }
  }
}
