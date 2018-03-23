# MUD game

Multi User Dungeon game, created using Java RMI.

The game was created as part of the CS3524 (Distributed Systems and Security) course, University of Aberdeen

## To launch:

1. First, compile the code using the ‘make mud’ command

2. Then, start the rmiregistry along a port for it to use (i.e. 50010) by running ‘rmiregistry 50010’ command

3. In a separate terminal, run the ‘java mud.MUDServerMainline 50010 50012’
command, which will launch the MUD’s server mainline. The first port should be the same one you used for the rmiregistry in the previous step, while the second one can be chosen freely

4. In another (third) terminal, run the ‘java mud.MUDClient [hostname] 50010’ command to launch the MUD’s client. You must type your host name, which is the name of the machine that you’re running the game on (localhost works), and pass it the same port that you used with the rmiregistry. The game should start now

## Functionalities:


After launching the client, it will ask you to enter your name and choose which MUD would the player wish to play in. The player will not be allowed to join a MUD if the maximum number of players in a chosen MUD or altogether online has been exceeded.

After joining the MUD, the player can perform various actions by calling certain commands:

###### Move <direction>:
* Moves the player to a selected direction (north/east/south/west). If there isn’t such direction that the player has entered, or there isn’t a path there, the player is informed about it

###### Pick <item>
* Picks up the selected item and adds it to the player’s inventory

###### Drop <item>
* Drops a selected item from the player’s inventory to the ground

###### Inventory
* Displays the list of the items that the player is currently carrying. If there isn’t any items in the inventory, the player is informed about it

###### Location
* Displays the information about the player’s surroundings (current location, available paths, visible items and players)

###### Players
* Displays the list of the players currently playing in the same MUD

###### Help
* Displays the list of the available commands that the player may call

###### Muds
* Displays the list of the currently available MUDs

###### ChangeMUD
* Allows the player to join another currently running MUD. If the MUD has reached the available player limit, the player will not be allowed to join it and be informed about it. When joining another MUD, all the items that the player had in their inventory will be dropped on the ground

###### CreateMUD
* Allows the player to create a new MUD in real time. If the maximum number of MUDs has been exceeded, the player will not be allowed to create a new one and will be informed about it

###### ChangemaxMUDS
* Allows the player to change the maximum number of MUDs running at the same time. This number cannot be lower than the number of currently running MUDs

###### Exit
* Allows the player to exit the game. All the items that the player had in their inventory will be dropped on the ground
