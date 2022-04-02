import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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
    private Banker banker;

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
        banker = new Banker(this);

        players[0].setName("Player 1");
        players[1].setName("Player 2");
    }
    Game(String listPath, String propertyPath, String outputFilePath){
        this();
        JSONArray chanceList;
        JSONArray communityChestList;
        JSONArray companyList;
        JSONArray landList;
        JSONArray railRoadList;
        File fout = null;
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try{
            fout = new File(outputFilePath);
            fos = new FileOutputStream(fout);
            bw = new BufferedWriter(new OutputStreamWriter(fos));


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
            for(Object obj: landList){
                String locationStr =  (String)((JSONObject)obj).get("id");
                String name = (String)((JSONObject)obj).get("name");
                String costStr = (String)((JSONObject)obj).get("cost");
                Land land = new Land(name, Integer.parseInt(costStr));
                squares[Integer.parseInt(locationStr) - 1] = land;
                banker.getProperties().add(land);
            }
            for(Object obj: railRoadList){
                String locationStr =  (String)((JSONObject)obj).get("id");
                String name = (String)((JSONObject)obj).get("name");
                String costStr = (String)((JSONObject)obj).get("cost");
                RailRoad railRoad = new RailRoad(name, Integer.parseInt(costStr));
                squares[Integer.parseInt(locationStr) - 1] = railRoad;
                banker.getProperties().add(railRoad);
            }
            for(Object obj: companyList){
                String locationStr =  (String)((JSONObject)obj).get("id");
                String name = (String)((JSONObject)obj).get("name");
                String costStr = (String)((JSONObject)obj).get("cost");
                Company company = new Company(name, Integer.parseInt(costStr));
                squares[Integer.parseInt(locationStr) - 1] = company;
                banker.getProperties().add(company);
            }
            for(int location : COMMUNITY_CHEST_LOCATIONS){
                squares[location - 1] = new ActionSquare("community chest", 0);
            }
            for(int location : CHANCE_LOCATIONS){
                squares[location - 1] = new ActionSquare("chance", 0);
            }
            squares[GO_LOCATION - 1] = new SideSquare(SideSquare.GO);
            squares[JAIL_LOCATION - 1] = new SideSquare(SideSquare.JAIL);
            squares[FREE_PARKING_LOCATION - 1] = new SideSquare(SideSquare.FREE_PARKING);
            squares[GO_TO_JAIL_LOCATION - 1] = new SideSquare(SideSquare.GO_TO_JAIL);

        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
        Scanner scan = null;
        try {
            scan = new Scanner(new File("command.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int turn = 0;
        try{
            while(scan.hasNext()){
                String line = scan.nextLine();
                if(line.equals("show()")){
                    printSummary(outputFilePath, bw);
                }
                else{
                    String info = line.split(";")[1];
                    int dice = Integer.parseInt(info);
                    //System.out.println(players[turn].move(dice));
                    bw.write(players[turn].move(dice));
                    bw.newLine();
                    turn = turn == 0 ? 1 : 0;
                    if(players[0].getMoney() <= 0 || players[1].getMoney() <= 0)
                        break;
                }
            }
            printSummary(outputFilePath, bw);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void printSummary(String outputFilePath, BufferedWriter bw) throws Exception{
        //System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        bw.write("-------------------------------------------------------------------------------------------------------------------------");
        bw.newLine();
        //System.out.print(players[0].getName() + "\t" +players[0].getMoney() + "\thave: ");
        bw.write(players[0].getName() + "\t" +players[0].getMoney() + "\thave: ");
        for(int i = 0; i < players[0].getProperties().size() - 1; i++){
            //System.out.print(players[0].getProperties().get(i).getName() + ",");
            bw.write(players[0].getProperties().get(i).getName() + ",");
        }
        //System.out.println(players[0].getProperties().get(players[0].getProperties().size() - 1).getName());
        bw.write(players[0].getProperties().get(players[0].getProperties().size() - 1).getName());
        bw.newLine();
        //System.out.print(players[1].getName() + "\t" + players[1].getMoney() + "\thave: ");
        bw.write(players[1].getName() + "\t" + players[1].getMoney() + "\thave: ");
        for(int i = 0; i < players[1].getProperties().size() - 1; i++){
            //System.out.println(players[1].getProperties().get(i).getName() + ",");
            bw.write(players[1].getProperties().get(i).getName() + ",");
            bw.newLine();
        }
        //System.out.println(players[1].getProperties().get(players[1].getProperties().size() - 1).getName());
        bw.write(players[1].getProperties().get(players[1].getProperties().size() - 1).getName());
        bw.newLine();
//        System.out.println("Banker\t" + banker.getMoney());
        bw.write("Banker\t" + banker.getMoney());
        bw.newLine();
        if(players[0].getMoney() >= players[1].getMoney()){
            //System.out.println("Winner\t" + "Player 1");
            bw.write("Winner\t" + "Player 1");
            bw.newLine();
        }

        else{
            //System.out.println("Winner\t" + "Player 2");
            bw.write("Winner\t" + "Player 2");
            bw.newLine();
        }

        //System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        bw.write("-------------------------------------------------------------------------------------------------------------------------");
        bw.newLine();
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

    public Banker getBanker() {
        return banker;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public ArrayList[] getCards() {
        return cards;
    }
}
