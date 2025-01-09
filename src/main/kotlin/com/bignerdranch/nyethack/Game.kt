package com.bignerdranch.nyethack

import helpers.locatePlayerPosition
import helpers.makeRed
import helpers.makeYellow
import kotlin.reflect.typeOf
import kotlin.system.exitProcess

object Game {
    private val worldMap = listOf<List<Room>>(
        listOf(Room("Long road"), MonsterRoom("Mysterious Forest", Werewolf()), Room("Long road"), MonsterRoom("Mystery village"), ),
        listOf(TownSquare(), Tavern(), Room("Back Room"), TownSquare(), MonsterRoom("Knights castle", Dragon())),
        listOf(MonsterRoom("A Long Corridor"), Room("A Generic Room")),
        listOf(MonsterRoom("The Dungeon"), MonsterRoom("The Dungeon prison", Draugr())),
    )
    private var currentRoom = worldMap[0][0];
    private var currentPosition = Coordinate(0, 0)
    private var quitGame = false;
    private var printInfo = true;

    init {
        narrate("Welcome, adventurer!")

        val mortality = if (player.isImmortal) "an immortal" else "a mortal";
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points");
    }

    fun play() {
        while (!quitGame) {
            if (printInfo) {
                narrate("${player.name} of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}");
                currentRoom.enterRoom();
            } else {
                printInfo = true;
            }


            print("> Enter your command: ");
            GameInput(readlnOrNull()).processCommand()
        }
    }

    fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom;
        val currentMonster = monsterRoom?.monster;

        if (currentMonster == null) {
            narrate("There's nothing to fight here");
            return;
        }

        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            player.attack(currentMonster);
            if (currentMonster.healthPoints > 0) {
                currentMonster.attack(player);
            }
            Thread.sleep(1000);
        }

        if (player.healthPoints <= 0 ) {
            narrate("You have been defeated! Thanks for playing");
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated");
            monsterRoom.monster = null;
        }
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: "";
        val command = input.split(" ")[0];
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.lowercase()) {
            "move" -> {
                val direction = Direction.entries.firstOrNull { it.name.equals(argument, ignoreCase = true) }

                if (direction != null) {
                    move(direction);
                } else {
                    narrate("I don't know what direction that is...");
                }
            }

            "map" -> {
                locatePlayerPosition(worldMap, currentPosition);
                printInfo = false;
            }

            "where" -> {
                narrate("You are at ${currentRoom.description()}")
                printInfo = false;
            }

            "ring" -> {
                if (currentRoom is TownSquare) {
                    (currentRoom as TownSquare).ringBell();
                } else {
                    narrate("You can ring the bell only on the Town Square", ::makeYellow);
                }
                printInfo = false;
            }

            "fight" -> fight()

            "quit", "exit" -> {
                narrate("=== The game will be closed! ===", ::makeRed);
                quitGame = true
            }

            else -> {
                narrate("I'm not sure what you're trying to do")
            }
        }
    }

    private fun move(direction: Direction) {
        val newPosition = direction.updateCoordinate(currentPosition);
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x);

        if (newRoom != null) {
            narrate("The hero moves ${direction.name}");
            currentPosition = newPosition;
            currentRoom = newRoom;
        } else {
            narrate("You can't move ${direction.name}");
        }
    }
}