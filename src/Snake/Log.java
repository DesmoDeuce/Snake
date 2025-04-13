package Snake;

import Libraries.General.Position;
import Libraries.Sprites.RectSprite;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

    public static int stateShown = 0;
    public static int totalStates = 0;

    //initializes the log
    public static void logInit() throws IOException {
        JsonArrayBuilder statesBuilder = Json.createArrayBuilder();

        //adds the pos of all the bodies to the builder
        JsonArrayBuilder bodyBuilder = Json.createArrayBuilder();
        for (RectSprite body : Screen.head.getBody()) {
            Position pos = body.getPos();
            bodyBuilder.add(Json.createArrayBuilder().add(pos.getX()).add(pos.getY()));
        }
        //adds all info to the builder
        statesBuilder.add(Json.createObjectBuilder()
                .add("head", Json.createArrayBuilder().add(Screen.head.getPos().getX()).add(Screen.head.getPos().getY()))
                .add("body", bodyBuilder)
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

        //adds the position of all the bodies to the builder
        JsonArrayBuilder bodyBuilder = Json.createArrayBuilder();
        for (RectSprite body : Screen.head.getBody()) {
            Position pos = body.getPos();
            bodyBuilder.add(Json.createArrayBuilder().add(pos.getX()).add(pos.getY()));
        }
        //adds all info to the builder
        statesBuilder.add(Json.createObjectBuilder()
                .add("head", Json.createArrayBuilder().add(Screen.head.getPos().getX()).add(Screen.head.getPos().getY()))
                .add("body", bodyBuilder)
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
        JsonReader reader = Json.createReader(new FileReader("log.json"));
        JsonArray statesArray = reader.readObject().getJsonArray("states");
        if (stateNumber >= 0 && stateNumber < statesArray.size()) {
            JsonObject state = statesArray.getJsonObject(stateNumber);

            //sets the heads position
            JsonArray headPosArray = state.getJsonArray("head");
            Screen.head.setPos(new Position(headPosArray.getInt(0), headPosArray.getInt(1)));
            //sets the bodies positions
            for (RectSprite body : Screen.head.getBody()) {
                JsonArray bodiesArray = state.getJsonArray("body");

                if (!(bodiesArray.size() - 1 < Screen.head.getBody().indexOf(body))) {
                    JsonArray bodyPosArray = bodiesArray.getJsonArray(Screen.head.getBody().indexOf(body));
                    body.setPos(new Position(bodyPosArray.getInt(0), bodyPosArray.getInt(1)));
                } else if (!bodiesArray.isEmpty()) {
                    JsonArray bodyPosArray = bodiesArray.getJsonArray(bodiesArray.size() - 1);
                    body.setPos(new Position(bodyPosArray.getInt(0), bodyPosArray.getInt(1)));
                } else {
                    body.setPos(Screen.head.getPos());
                }
            }
            //sets the apples position
            JsonArray applePosArray = state.getJsonArray("apple");
            Screen.head.getApple().setPos(new Position(applePosArray.getInt(0), applePosArray.getInt(1)));
            stateShown = stateNumber;
        }
    }
}
