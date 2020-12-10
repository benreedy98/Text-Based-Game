# Text-Based-Game
Game in which commands are passed to the command line. The game world is parsed from an xml file.
The user will initially be prompted to enter the name of the xml file containing the world info.
Use 'help' command to view full list of commands.
The game world consists of a series of rooms linked via NESW neighbor references.
The world is populated with creatures, of which there are three types: Animal, NPC, and the PC.
The rooms have a state variable which can be 'dirty', 'half-dirty', or 'clean'.
The PC can perform 'clean' or 'dirty' actions to change the state of the room.
The creatures will react differently to 'clean' or 'dirty' actions based on what type of creature they are.
