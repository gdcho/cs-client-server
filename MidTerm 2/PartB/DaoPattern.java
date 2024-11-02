import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Create the base class with abstract serialize method that derived classes will need to implement
abstract class Game {
   String GameType;
   public Game(String gameType) { this.GameType = gameType; }
   abstract String serialize();
}

//Quiz class inherits from abstract Game class
class Quiz extends Game {
   int Id;
   String Question;
   String ContentPath;
   public Quiz() { super("Quiz"); } //this is default constructor
   public Quiz(String constructorParams) { // this constructor parses the constructorParams to set the object state
      super("Quiz");
      String[] keyvaluePairs = constructorParams.split(",");
      for(int i = 0; i < keyvaluePairs.length; i++) {
         String[] keyvaluePair = keyvaluePairs[i].split("=");
         switch (keyvaluePair[0]) {
            case "Id": this.Id = Integer.parseInt(keyvaluePair[1]); break;
            case "Question": this.Question = keyvaluePair[1]; break;
            case "ContentPath": this.ContentPath = keyvaluePair[1];break;
         }
      }  
   }
   void setId(int id) { this.Id = id; }
   void setQuestion (String question) { this.Question = question; }
   void setContentPath (String contentPath) { this.ContentPath = contentPath; }
   //Quiz's implementation of the serialize() method
   String serialize() { return "Id="+this.Id+",Question="+this.Question+",ContentPath="+this.ContentPath; }
}

//Some other class that also inherits from abstract Game class. 
class Puzzle extends Game {
   int Id;
   String Name;
   String Details;
   public Puzzle() { super("Puzzle"); }
   public Puzzle(String constructorParams) { super("Puzzle"); } //implementation not provided for now
   void setId(int id) { this.Id = id; }
   void setName(String name) { this.Name = name; }
   void setDetails(String details) { this.Details = details; }
   //Puzzle's implementation of the serialize method
   String serialize() { return "Id="+this.Id+",Name="+this.Name+",Details="+this.Details; }
}

//An example of the Factory class
// The GameFactory will be used by the Repository to create instances of different types of Game objects
class GameFactory {
   public Game createGame(String gameType, String constructorParameters) {
    if (gameType.equalsIgnoreCase("Quiz")) {
      return new Quiz(constructorParameters);
    } else if (gameType.equalsIgnoreCase("Puzzle")) {
      return new Puzzle(constructorParameters);
    }
    return null;
  }
}

// IRepository interface
// The interfaces allow for component based architecures and design 
interface IRepository 
{
   public List<Game> select(String gameType, String criteria); //construct the Where clause using criteria deserialize each game object in 
   public void insert(Game game);  //deserialize the game object and insert into database using JDBC
}

//The Repository class that implements IRepository.
// Note that the Repository class can manage objects of any sub classes of Game class.
// This is achieved using inheritance from Game class and the Factory design pattern
class Repository implements IRepository {
   List<Game> gameList = null;
   public Repository() { gameList = new ArrayList<Game>(); }
   @Override
   public void insert(Game game) { gameList.add(game) ; }
   @Override
   public List<Game> select(String gameType, String pattern) {   //note the use of Functional Programming in this method
      Stream<Game> gameStream = gameList.stream();
      Stream<Game> newGameStream = gameStream.filter(s -> s.GameType == gameType && s.serialize().contains(pattern));
      List<Game> selectedGames = new ArrayList<Game>();
      GameFactory gameFactory = new GameFactory();
      newGameStream.forEach(s -> selectedGames.add(gameFactory.createGame(s.GameType, s.serialize())));
      return selectedGames;
   }
}
public class DaoPattern
{
   public static void main(String[] args) {
      //repository object should be created in the constructor of the Servlet;
      IRepository repository = new Repository();
 
      //After extracting values from request object in the doPost or doPut method, create the quiz object and insert it into database via Repository
      Quiz quiz = new Quiz(); quiz.setId(1); 
      quiz.setQuestion("Who is the Governal General of Canada");  quiz.setContentPath("youtube.com/xyz");
      repository.insert(quiz);
      
      //another doPost or doPut - another quiz object into the database
      quiz = new Quiz(); quiz.setId(2); 
      quiz.setQuestion("Who is the Prime Minister of Canada");  quiz.setContentPath("youtube.com/abc");
      repository.insert(quiz);

      //Just to show that the above design enables Repository to manage any sub type of Game object
      Puzzle puzzle = new Puzzle(); puzzle.setId(1); puzzle.setName("jigsaw");puzzle.setDetails("quite difficult");
      repository.insert(puzzle);

      //select some quiz(zes) from Repository in the doGet method if you like 
      List<Game> selectedGames = repository.select("Quiz", "Id=2");
      for (Game game : selectedGames) {
         System.out.println(game.serialize());
      }
    }
}
