import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Game {

    public static final int START_LOCATION = 1;
    public static final int[] COMMUNITY_CHEST_LOCATIONS = {3,18,34};
    public static final int[] CHANCE_LOCATIONS = {8,23,37};
    public static final int SQUARE_COUNT = 40;
    public static final int GO_LOCATION = 1;
    public static final int JAIL_LOCATION = 11;
    public static final int FREE_PARKING_LOCATION = 21;
    public static final int GO_TO_JAIL_LOCATION = 31;


    private Square[] squares;
    private ArrayList[] cards; //index 0: chance cards, index 1: Community chest Cards.
    private Die[] dice;
    private Player[] players;

    Game(){
        dice = new Die[2];
        players = new Player[2];
        cards = new ArrayList[2];
        squares = new Square[SQUARE_COUNT];
        dice[0] = new Die();
        dice[1] = new Die();
        players[0] = new Player(this);
        players[1] = new Player(this);
        cards[0] = new ArrayList<>();
        cards[1] = new ArrayList<>();
    }
    Game(String listPath, String propertyPath){
        this();
        JSONArray chanceList;
        JSONArray communityChestList;
        JSONArray companyList;
        JSONArray landList;
        JSONArray railRoadList;
        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject actionsJSON = (JSONObject) jsonParser.parse(new String(Files.readAllBytes(Paths.get(listPath))));
            JSONObject propertiesJSON = (JSONObject) jsonParser.parse(new String(Files.readAllBytes(Paths.get(propertyPath))));

            chanceList = (JSONArray) actionsJSON.get("chanceList");
            communityChestList = (JSONArray) actionsJSON.get("communityChestList");


            landList = (JSONArray) propertiesJSON.get("1");
            railRoadList = (JSONArray) propertiesJSON.get("2");
            companyList = (JSONArray) propertiesJSON.get("3");

            for(Object obj: chanceList){
                String data = (String)((JSONObject)obj).get("item");
                Card card = new Card(data);
                cards[Card.CHANCE_CARDS].add(card);
            }
            for(Object obj: communityChestList){
                String data = (String)((JSONObject)obj).get("item");
                Card card = new Card(data);
                cards[Card.COMMUNITY_CHEST_CARDS].add(card);
            }
            System.out.println("Cards Added.");
            for(Object obj: landList){
                String locationStr =  (String)((JSONObject)obj).get("id");
                String name = (String)((JSONObject)obj).get("name");
                String costStr = (String)((JSONObject)obj).get("cost");
                Land land = new Land(name, Integer.parseInt(costStr));
                squares[Integer.parseInt(locationStr) - 1] = land;
            }
            for(Object obj: railRoadList){
                String locationStr =  (String)((JSONObject)obj).get("id");
                String name = (String)((JSONObject)obj).get("name");
                String costStr = (String)((JSONObject)obj).get("cost");
                RailRoad railRoad = new RailRoad(name, Integer.parseInt(costStr));
                squares[Integer.parseInt(locationStr) - 1] = railRoad;
            }
            for(Object obj: companyList){
                String locationStr =  (String)((JSONObject)obj).get("id");
                String name = (String)((JSONObject)obj).get("name");
                String costStr = (String)((JSONObject)obj).get("cost");
                Company company = new Company(name, Integer.parseInt(costStr));
                squares[Integer.parseInt(locationStr) - 1] = company;
            }
            for(int location : COMMUNITY_CHEST_LOCATIONS){
                squares[location - 1] = new ActionSquare("Community Chest", 0);
            }
            for(int location : CHANCE_LOCATIONS){
                squares[location - 1] = new ActionSquare("Chance", 0);
            }
            System.out.println("Squares added.(Except sides!)");
            for(Square square: squares){
                if(square != null)
                    System.out.println(square);
            }


        }catch(IOException | ParseException e){
            e.printStackTrace();
        }

    }

    public Square[] getSquares() {
        return squares;
    }

    public void setSquares(Square[] squares) {
        this.squares = squares;
    }


    public int[] rollDice(){
        int[] res = new int[2];
        res[0] = dice[0].roll();
        res[1] = dice[1].roll();
        return res;
    }
}
