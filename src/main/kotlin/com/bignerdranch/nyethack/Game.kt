package com.bignerdranch.nyethack

import com.bignerdranch.nyethack.locations.MonsterRoom
import com.bignerdranch.nyethack.locations.Room
import com.bignerdranch.nyethack.locations.Tavern
import com.bignerdranch.nyethack.locations.TownSquare
import helpers.locatePlayerPosition
import helpers.makeRed
import helpers.makeYellow
import kotlin.system.exitProcess

object Game {
    private val worldMap = listOf<List<Room>>(
        listOf(
            Room("Long road"),
            MonsterRoom("Mysterious Forest", Werewolf()),
            Room("Long road"),
            MonsterRoom("Mystery village"),
        ),
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

    fun takeLoot() {
        val loot = currentRoom.lootBox.takeLoot();

        if (loot == null) {
            narrate("${player.name} approaches the loot box, but it is empty.")
        } else {
            narrate("${player.name} now has a ${loot.name}")
            player.inventory += loot;
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

            "take" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    takeLoot()
                } else {
                    narrate("I don't know what you're trying to take")
                }
                printInfo = false;
            }

            "sell" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    sellLoot();
                } else {
                    narrate("I don't know what you're trying to sell")
                }
                printInfo = false;
            }

            "quit", "exit" -> {
                narrate("=== The game will be closed! ===", ::makeRed);
                quitGame = true
            }

            else -> {
                narrate("I'm not sure what you're trying to do")
            }
        }
    }

    private fun fight() {
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

        if (player.healthPoints <= 0) {
            narrate("You have been defeated! Thanks for playing");
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated");
            monsterRoom.monster = null;
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

    private fun sellLoot() {
        when (val currentRoom = currentRoom) {
            is TownSquare -> {
                if (player.inventory.size == 0) {
                    narrate("You have nothing to sell.", ::makeYellow)
                    return
                }

                player.inventory.forEach {
                    if (it is Sellable) {
                        val sellPrice = currentRoom.sellItem(it)
                        narrate("Sold ${it.name} for $sellPrice gold")
                        player.gold += sellPrice;
                    } else {
                        narrate("Your item can't be sold", ::makeRed)
                    }
                }
            }

            else -> narrate("You can't sell anything here", ::makeRed)
        }
    }
}