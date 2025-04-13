package Snake;

import Libraries.General.Position;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LogCompact {

    public static int stateShown = 0;
    public static int totalStates = 0;

    //initializes the log
    public static void logInit() throws IOException {
        JsonArrayBuilder statesBuilder = Json.createArrayBuilder();

        //adds all the info to the builder
        statesBuilder.add(Json.createObjectBuilder()
                .add("dir", "NONE")
                .add("apple", Json.createArrayBuilder().add(Screen.head.getApple().getPos().getX()).add(Screen.head.getApple().getPos().getY())));

        //writes the updated json
        JsonWriter writer = Json.createWriter(new FileWriter("log.json"));
        writer.writeObject(
                Json.createObjectBuilder().add("states", statesBuilder.build()).build());
        writer.close();
    }

    //adds the state that the snake is currently at
    public static void addState() throws IOException {
        JsonArrayBuilder statesBuilder = Json.createArrayBuilder();

        //adds all past states to the builder
        JsonObject past = Json.createReader(new FileReader("log.json")).readObject();
        for (int n = 0; n < past.getJsonArray("states").size(); n++) {
            statesBuilder.add(past.getJsonArray("states").getJsonObject(n));
        }

        //adds all the info to the builder
        statesBuilder.add(Json.createObjectBuilder()
                .add("dir", Screen.head.getDir())
                .add("apple", Json.createArrayBuilder().add(Screen.head.getApple().getPos().getX()).add(Screen.head.getApple().getPos().getY())));

        //writes the updated json
        JsonWriter writer = Json.createWriter(new FileWriter("log.json"));
        writer.writeObject(
                Json.createObjectBuilder().add("states", statesBuilder.build()).build());
        writer.close();
        stateShown++;
        totalStates++;
    }

    //calls state asked for
    public static void callState(int stateNumber) throws FileNotFoundException {
        JsonArray statesArray = Json.createReader(new FileReader("log.json")).readObject().getJsonArray("states");
        if (stateNumber >= 0 && stateNumber < statesArray.size()) {
            Screen.head.getBody().clear();
            Screen.score = 0;
            Snake.snake.setTitle("Snake | Score: " + Screen.score);
            Screen.head.setPos(new Position(Screen.grid.getXs().get(7), Screen.grid.getYs().get(7 )));
            JsonArray startAppleArray = statesArray.getJsonObject(0).getJsonArray("apple");
            Screen.head.getApple().setPos(new Position(startAppleArray.getInt(0), startAppleArray.getInt(1)));
            for (int n = 1; n <= stateNumber; n++) {
                JsonObject state = statesArray.getJsonObject(n);
                Screen.head.setNext(state.getString("dir"));
                Screen.head.move();
                JsonArray appleArray = state.getJsonArray("apple");
                Screen.head.getApple().setPos(new Position(appleArray.getInt(0), appleArray.getInt(1)));
            }
            stateShown = stateNumber;
        }
    }
}
