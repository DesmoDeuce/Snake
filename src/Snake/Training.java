package Snake;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Training {

    public static JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    public static void training1st() {
        System.out.println("started");
        for (Head heads : Head.heads) {
            new Trainee(heads, new int[]{
                    new Random().nextInt(10) + 1,
                    new Random().nextInt(10) + 1,
                    new Random().nextInt(10) + 1
            });
        }
        System.out.println("training " + Trainee.trainees.size());
        boolean alive;
        do {
            alive = false;
            for (Trainee trainees : Trainee.trainees) {
                if (trainees.getHead().isAlive()) {
                    alive = true;
                    DangerCake.cake(trainees.getHead(), trainees.getInts()[0], trainees.getInts()[1], trainees.getInts()[2]);
                    int oldScore = trainees.getHead().getScore();
                    trainees.getHead().move();
                    if (oldScore != trainees.getHead().getScore()) {
                        trainees.setMoves(0);
                        System.out.println("score: " + trainees.getHead().getScore());
                    }
                    trainees.setMoves(trainees.getMoves() + 1);
                    if (trainees.getMoves() > 1000) {
                        trainees.getHead().setAlive(false);
                    }
                }
            }
        } while (alive);
        System.out.println("finished");
        JsonArrayBuilder currentArray = Json.createArrayBuilder();
        for (Trainee trainees : Trainee.trainees) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("surrounded", trainees.getInts()[0]);
            objectBuilder.add("split", trainees.getInts()[1]);
            objectBuilder.add("edge", trainees.getInts()[2]);
            objectBuilder.add("score", trainees.getHead().getScore());
            currentArray.add(objectBuilder.build());
        }
        arrayBuilder.add(currentArray.build());
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void training2nd() {
        System.out.println("started");
        for (Head heads : Head.heads) {
            int surrounded = 7;
            int s = new Random().nextInt(197);
            if (100 <= s && s < 150) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    surrounded++;
                } else {
                    surrounded--;
                }
            }
            if (150 <= s && s < 175) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    surrounded += 2;
                } else {
                    surrounded -= 2;
                }
            }
            if (175 <= s && s < 188) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    surrounded += 3;
                } else {
                    surrounded -= 3;
                }
            }
            if (188 <= s && s < 194) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    surrounded += 4;
                } else {
                    surrounded -= 4;
                }
            }
            if (194 <= s) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    surrounded += 5;
                } else {
                    surrounded -= 5;
                }
            }
            int split = 9;
            int p = new Random().nextInt(197);
            if (100 <= p && p < 150) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    split++;
                } else {
                    split--;
                }
            }
            if (150 <= p && p < 175) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    split += 2;
                } else {
                    split -= 2;
                }
            }
            if (175 <= p && p < 188) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    split += 3;
                } else {
                    split -= 3;
                }
            }
            if (188 <= p && p < 194) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    split += 4;
                } else {
                    split -= 4;
                }
            }
            if (194 <= p) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    split += 5;
                } else {
                    split -= 5;
                }
            }
            int edge = 2;
            int e = new Random().nextInt(197);
            if (100 <= e && e < 150) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    edge++;
                } else {
                    edge--;
                }
            }
            if (150 <= e && e < 175) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    edge += 2;
                } else {
                    edge -= 2;
                }
            }
            if (175 <= e && e < 188) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    edge += 3;
                } else {
                    edge -= 3;
                }
            }
            if (188 <= e && e < 194) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    edge += 4;
                } else {
                    edge -= 4;
                }
            }
            if (194 <= e) {
                int r = new Random().nextInt(2);
                if (r == 0) {
                    edge += 5;
                } else {
                    edge -= 5;
                }
            }
            if (surrounded < 1) {
                surrounded = 1;
            }
            if (split < 1) {
                split = 1;
            }
            if (edge < 1) {
                edge = 1;
            }
            new Trainee(heads, new int[] {
                    surrounded,
                    split,
                    edge
            });
        }
        System.out.println("training " + Trainee.trainees.size());
        boolean alive;
        do {
            alive = false;
            for (Trainee trainees : Trainee.trainees) {
                if (trainees.getHead().isAlive()) {
                    alive = true;
                    DangerCake.cake(trainees.getHead(), trainees.getInts()[0], trainees.getInts()[1], trainees.getInts()[2]);
                    int oldScore = trainees.getHead().getScore();
                    trainees.getHead().move();
                    if (oldScore != trainees.getHead().getScore()) {
                        trainees.setMoves(0);
                        System.out.println("score: " + trainees.getHead().getScore());
                    }
                    trainees.setMoves(trainees.getMoves() + 1);
                    if (trainees.getMoves() > 1000) {
                        trainees.getHead().setAlive(false);
                    }
                }
            }

        } while (alive);
        System.out.println("finished");
        JsonArrayBuilder currentArray = Json.createArrayBuilder();
        for (Trainee trainees : Trainee.trainees) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("surrounded", trainees.getInts()[0]);
            objectBuilder.add("split", trainees.getInts()[1]);
            objectBuilder.add("edge", trainees.getInts()[2]);
            objectBuilder.add("score", trainees.getHead().getScore());
            currentArray.add(objectBuilder.build());
        }
        arrayBuilder.add(currentArray.build());
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write() throws IOException {
        JsonWriter writer = Json.createWriter(new FileWriter("training.json"));
        writer.writeArray(arrayBuilder.build());
        writer.close();
        System.out.println("printed");
    }
}
