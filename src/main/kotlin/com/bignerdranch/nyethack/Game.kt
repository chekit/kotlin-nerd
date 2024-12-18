package com.bignerdranch.nyethack

object Game {
    private val worldMap = listOf<List<Room>>(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(Room("A Long Corridor"), Room("A Generic Room")),
        listOf(Room("The Dungeon")),
    )
    private var currentRoom = worldMap[0][0];
    private var currentPosition = Coordinate(0, 0)

    init {
        narrate("Welcome, adventurer!")

        val mortality = if (player.isImmortal) "an immortal" else "a mortal";
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points");
    }

    fun play() {
        while (true) {
            narrate("${player.name} of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}");
            currentRoom.enterRoom();

            print("> Enter your command: ");
            GameInput(readlnOrNull()).processCommand()
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
            else -> narrate("I'm not sure what you're trying to do");
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