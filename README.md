# MUD game

Multi User Dungeon game, created using Java RMI.

The game was created as part of the CS3524 (Distributed Systems and Security) course, University of Aberdeen

## To launch:

First, run the 'make game' command on your terminal

Then:

1st terminal: rmiregistry 50010

2nd terminal: java mud.MUDServerMainline 50010 50012

3rd terminal: java mud.MUDClient localhost 50010
