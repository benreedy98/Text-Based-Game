import org.xml.sax.Attributes;
import org.xml.sax.helpers.*;
import java.util.*;

public class MyHandler extends DefaultHandler{
  PC pc;
  Room lastRoom;
  HashMap<String, Room> rooms = new HashMap<String, Room>();

  private Room findOrCreateRoom(String name){
    Room r = rooms.get(name);
    if(r != null){ return r; } else {
      Room retValue = new Room(name);
      rooms.put(name, retValue);
      return retValue;
    }
  }

  @Override
  public void startElement(
  String uri, String localName, String qName, Attributes attributes){
    if(qName.equals("room")){
      String name = attributes.getValue("name");
      Room currentRoom = findOrCreateRoom(name);
      currentRoom.description = attributes.getValue("description");
      currentRoom.state = attributes.getValue("state");

      String northName = attributes.getValue("north");
      if(northName != null){
        Room northNeighbor = findOrCreateRoom(northName);
        currentRoom.north = northNeighbor;
        northNeighbor.south = currentRoom;	
      }

      String eastName = attributes.getValue("east");
      if(eastName != null){
        Room eastNeighbor = findOrCreateRoom(eastName);
	currentRoom.east = eastNeighbor;
	eastNeighbor.west = currentRoom;
      }

      String southName = attributes.getValue("south");
      if(southName != null){
        Room southNeighbor = findOrCreateRoom(southName);
        currentRoom.south = southNeighbor;
        southNeighbor.north = currentRoom;       
      }

      String westName = attributes.getValue("west");
      if(westName != null){
        Room westNeighbor = findOrCreateRoom(westName);
        currentRoom.west = westNeighbor;
        westNeighbor.east = currentRoom;	
      }

      lastRoom = currentRoom;

    } else if(qName.equals("animal")){
      String name = attributes.getValue("name");
      String description = attributes.getValue("description");
      Animal currentCreature = new Animal(name, description);
      lastRoom.addCreature(currentCreature);
      
    } else if(qName.equals("NPC")){
      String name = attributes.getValue("name");
      String description = attributes.getValue("description");
      NPC currentCreature = new NPC(name, description);
       
    } else if(qName.equals("PC")){
      String name = attributes.getValue("name");
      String description = attributes.getValue("description");
      PC currentCreature = new PC(name, description);
      lastRoom.addCreature(currentCreature);
      pc = currentCreature;
    }
  }

  @Override
  public void endDocument(){
    Scanner scanner = new Scanner(System.in);
    while(true){ pc.play(scanner); }
  }
}
