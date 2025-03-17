import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextAdventureGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Room> rooms = createRooms();
        Room currentRoom = rooms.get("Entrance");

        System.out.println("Welcome to the Text Adventure Game!");
        System.out.println("Type 'help' for a list of commands.");

        while (true) {
            System.out.println("\n" + currentRoom.getDescription());
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("quit")) {
                System.out.println("Thanks for playing!");
                break;
            } else if (command.equals("help")) {
                printHelp();
            } else if (command.startsWith("go ")) {
                String direction = command.substring(3);
                Room nextRoom = currentRoom.getExit(direction);
                if (nextRoom != null) {
                    currentRoom = nextRoom;
                } else {
                    System.out.println("You can't go that way.");
                }
            } else if (command.equals("look")) {
                System.out.println(currentRoom.getDescription());
            } else if (command.equals("inventory")) {
                System.out.println("You are carrying: " + currentRoom.getInventory());
            } else {
                System.out.println("I don't understand that command.");
            }
        }

        scanner.close();
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  go [direction] - Move in the specified direction (north, south, east, west)");
        System.out.println("  look - Look around the current room");
        System.out.println("  inventory - Check your inventory");
        System.out.println("  quit - Quit the game");
    }

    private static Map<String, Room> createRooms() {
        Map<String, Room> rooms = new HashMap<>();

        Room entrance = new Room("Entrance", "You are at the entrance of a dark cave.");
        Room hallway = new Room("Hallway", "You are in a dimly lit hallway.");
        Room chamber = new Room("Chamber", "You are in a large chamber with a high ceiling.");
        Room treasureRoom = new Room("Treasure Room", "You are in a room filled with treasure!");

        entrance.setExit("north", hallway);
        hallway.setExit("south", entrance);
        hallway.setExit("east", chamber);
        chamber.setExit("west", hallway);
        chamber.setExit("north", treasureRoom);
        treasureRoom.setExit("south", chamber);

        rooms.put("Entrance", entrance);
        rooms.put("Hallway", hallway);
        rooms.put("Chamber", chamber);
        rooms.put("Treasure Room", treasureRoom);

        return rooms;
    }
}

class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private String inventory;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.inventory = "nothing";
    }

    public void setExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder(description);
        sb.append("\nExits: ");
        for (String direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString();
    }

    public String getInventory() {
        return inventory;
    }
}