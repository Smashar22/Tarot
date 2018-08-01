import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;  //
import javafx.event.EventHandler; //
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.scene.media.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.text.*;
import javafx.util.Duration;


public class Tarot extends Application {
  private static MediaPlayer mediaPlayer;
  public Stage window;
  public Scene scene1, scene2, scene3;
  private Card card1;
  private Card card2;
  private Card card3;
  public int btn_count = 0;

  private Parent createTarot() {
    // code: setup the game ui
    Pane root = new Pane();
    root.setPrefSize(800, 600);  
    Region background = new Region();
    background.setPrefSize(800, 600);
    background.setStyle("-fx-background-color: rgba(250, 250, 250, 1)"); 
    HBox rootlayout = new HBox(5);
    rootlayout.setPadding(new Insets(5, 5, 5, 5));
    Rectangle left = new Rectangle(200, 570);
    left.setArcWidth(50);
    left.setArcHeight(50);
    left.setFill(Color.GREEN);
    left.setOpacity(0.2);
    Rectangle right = new Rectangle(585, 570);
    right.setArcWidth(50);
    right.setArcHeight(50);
    right.setFill(Color.BROWN);
    right.setOpacity(0.3);

    StackPane leftStack = new StackPane(); // Deck Side
    VBox leftVBox = new VBox(15);
    leftVBox.setAlignment(Pos.TOP_CENTER);

    Image imageTop = new Image("top.png");
    ImageView top = new ImageView();
    top.setImage(imageTop);
    top.setFitWidth(80);
    top.setPreserveRatio(true);
    top.setSmooth(true);  

    final Text deck = new Text("Deck");
    deck.setFont(Font.font("Palatino",25));
    Image image = new Image("card_back.jpg");
    ImageView back = new ImageView();
    back.setImage(image);
    back.setFitWidth(150);
    back.setPreserveRatio(true);
    back.setSmooth(true);  
    leftVBox.setTranslateY(16); // 110
    Button btDeal = new Button("Deal");
    // Button btFinish = new Button("Finish");
    Image imageBot = new Image("bottom.png");
    ImageView bot = new ImageView();
    bot.setImage(imageBot);
    bot.setFitWidth(80);
    bot.setPreserveRatio(true);
    bot.setSmooth(true);  

    leftVBox.getChildren().addAll(top, deck, back, btDeal /*btFinish */, bot);
    leftStack.getChildren().addAll(left, leftVBox);



    StackPane rightStack = new StackPane(); // Reading Side
    Image texture = new Image("texture2.png");
    ImageView overlayTexture = new ImageView(); // Texture Affect
    overlayTexture.setImage(texture);
    overlayTexture.setFitWidth(585);
    overlayTexture.setPreserveRatio(true);
    overlayTexture.setSmooth(true);  
    overlayTexture.setTranslateY(0);
    overlayTexture.setTranslateX(0);
    overlayTexture.setOpacity(0.3);

    Image styledLines = new Image("tenseUnderline.png");
    ImageView tenseUnderline = new ImageView(); // Tense Underline Affect
    tenseUnderline.setImage(styledLines);
    tenseUnderline.setFitWidth(585);
    tenseUnderline.setPreserveRatio(true);
    tenseUnderline.setSmooth(true);  
    tenseUnderline.setTranslateY(0);
    tenseUnderline.setTranslateX(0);
    // tenseUnderline.setOpacity(0.3);

    VBox rightVBox = new VBox(40);
    rightVBox.setAlignment(Pos.CENTER);
    HBox c_spread = new HBox(20);
    HBox tense = new HBox(140);
    tense.setAlignment(Pos.CENTER);
    tense.setTranslateY(140);
    final Text past = new Text("past");
    final Text present = new Text("present");
    final Text future = new Text("future");
    past.setFont(Font.font("Palatino", FontPosture.ITALIC, 25));
    present.setFont(Font.font("Palatino", FontPosture.ITALIC, 25));
    future.setFont(Font.font("Palatino", FontPosture.ITALIC, 25));
    tense.getChildren().addAll(past, present, future);
    HBox readingArea = new HBox(10);
    final Text readTitle = new Text("\t   Reading:");
    readTitle.setFont(Font.font("Palatino", FontPosture.ITALIC, 15));
    readingArea.setAlignment(Pos.CENTER_LEFT);
    readingArea.setTranslateY(110);
    readingArea.getChildren().addAll(readTitle);
    StackPane meaningArea = new StackPane();
    Rectangle mArea_bg = new Rectangle(550, 250);
    mArea_bg.setArcWidth(50);
    mArea_bg.setArcHeight(50);
    mArea_bg.setFill(Color.WHITE);
    mArea_bg.setTranslateY(70);
    meaningArea.getChildren().addAll(mArea_bg);
    rightVBox.getChildren().addAll(c_spread, tense, readingArea, meaningArea);
    rightStack.getChildren().addAll(right, overlayTexture, tenseUnderline, rightVBox);
    rootlayout.getChildren().addAll(leftStack, rightStack);
    root.getChildren().addAll(background, rootlayout);


    int deal[] = deckDeal();
    try {
      btDeal.setOnAction(event -> {
        if(btn_count == 0) {
          card1 = new Card();
          card1 = card1.createCard(deal[0]);
          ImageView face1 = new ImageView();
          face1.setImage(card1.getImage(card1));
          face1.setFitWidth(140);
          face1.setPreserveRatio(true);
          face1.setSmooth(true);
          face1.setTranslateY(-150);
          face1.setTranslateX(-200);
          rightStack.getChildren().addAll(face1);
          Text conclusion1 = null;
          try {
            conclusion1 = new Text(card1.getReading(card1, "a"));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
          conclusion1.setFont(Font.font("Palatino", FontPosture.ITALIC, 13));
          conclusion1.setTranslateY(340);
          conclusion1.setTranslateX(240);
          conclusion1.setFill(Color.BLACK);
          Pane tx_area1 = new Pane();
          conclusion1.setWrappingWidth((double)530);
          tx_area1.getChildren().addAll(conclusion1);
          root.getChildren().add(tx_area1);
          // System.gc();
          card2 = new Card();
          card2 = card2.createCard(deal[1]);
          ImageView face2 = new ImageView();
          face2.setImage(card2.getImage(card2));
          face2.setFitWidth(140);
          face2.setPreserveRatio(true);
          face2.setSmooth(true);
          face2.setTranslateY(-150);
          face2.setTranslateX(-10);
          rightStack.getChildren().addAll(face2);
          Text conclusion2 = null;
          try {
            conclusion2 = new Text(card2.getReading(card2, "b"));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
          conclusion2.setFont(Font.font("Palatino", FontPosture.ITALIC, 13));
          conclusion2.setTranslateY(430);
          conclusion2.setTranslateX(240);
          conclusion2.setFill(Color.BLACK);
          Pane tx_area2 = new Pane();
          conclusion2.setWrappingWidth((double)530);
          tx_area2.getChildren().addAll(conclusion2);
          root.getChildren().add(tx_area2);
          // System.gc();
          card3 = new Card();
          card3 = card3.createCard(deal[2]);
          ImageView face3 = new ImageView();
          face3.setImage(card3.getImage(card3));
          face3.setFitWidth(140);
          face3.setPreserveRatio(true);
          face3.setSmooth(true);
          face3.setTranslateY(-150);
          face3.setTranslateX(195);
          rightStack.getChildren().addAll(face3);
          Text conclusion3 = null;
          try {
            conclusion3 = new Text(card3.getReading(card3, "c"));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
          conclusion3.setFont(Font.font("Palatino", FontPosture.ITALIC, 13));
          conclusion3.setTranslateY(510);
          conclusion3.setTranslateX(240);
          conclusion3.setFill(Color.BLACK);
          conclusion3.setWrappingWidth((double)530);
          Pane tx_area3 = new Pane();
          tx_area3.getChildren().addAll(conclusion3);
          root.getChildren().add(tx_area3);
          btn_count = btn_count + 1;
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*
    btFinish.setOnAction(event -> {
      System.exit(0);
    }); */
    return root;
  }

  private Parent createInfo() {





    // emblem.png
    Pane root = new Pane();
    root.setPrefSize(800, 600);  
    Region background = new Region();
    background.setPrefSize(800, 600);
    background.setStyle("-fx-background-color: rgba(250, 250, 250, 1)");
    VBox layout1 = new VBox(30);
    layout1.setTranslateY(215); // 145
    layout1.setTranslateX(140);

    Image emb = new Image("emblem.png");
    ImageView emblem = new ImageView();
    emblem.setImage(emb);
    emblem.setFitWidth(140);
    emblem.setPreserveRatio(true);
    emblem.setSmooth(true);  
    emblem.setTranslateY(50);
    emblem.setTranslateX(330);

    Text explain = new Text("A tarot card reading sounds enigmatic and mysterious, but if you're unfamiliar with the archetypal images it can also be very confusing and even intimidating! Tarot definitions are by no means complete; no single meaning or interpretation is carved in stone. What's important is what your tarot reading means to you. These tarot definitions are meant to help you understand the major arcana and minor arcana, the 78 cards in a tarot deck, and the energy of the cups, wands, pentacles, and swords of the tarot. Let your intuition guide you to the secrets that the tarot has to share.");
    explain.setFont(Font.font("Palatino", FontPosture.ITALIC, 22));
    explain.setFill(Color.BLACK);
    explain.setWrappingWidth((double)525);

    Button return_menu = new Button("Return");
    layout1.getChildren().addAll(explain, return_menu);
    layout1.setAlignment(Pos.TOP_CENTER);
    root.getChildren().addAll(emblem, layout1);
    return_menu.setOnAction(e -> window.setScene(scene1));
    return root;
  }

  public void music(int area) {
    String menuMusic;
    if(area == 0) {
      menuMusic = "menu.wav";
    } else {
      menuMusic = "reading.wav";
    }
    Media sound = new Media(new File(menuMusic).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  public int[] deckDeal() {
    // code: returns an array of three random unique integers

    int cardInts[] = new int[3];
    int seed1, seed2, seed3;
    int drawn_card1, drawn_card2;

    seed1 = new Random().nextInt(78);
    cardInts[0] = seed1;

    drawn_card1 = seed1;  
    seed2 = new Random().nextInt(78);
    while(seed2 == drawn_card1) {
      seed2 = new Random().nextInt(78);
    }
    cardInts[1] = seed2;

    drawn_card2 = seed2;
    seed3 = new Random().nextInt(78);
    while(seed3 == drawn_card2) {
      seed3 = new Random().nextInt(78);
    }
    cardInts[2] = seed3;
    return cardInts;
  }

  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    Pane root = new Pane();
    String url = System.getProperty("user.dir") + "/menu.png";
    Image img = new Image("menu.png");

    Image img2 = new Image("underline.png");
    ImageView squiggle = new ImageView();
    squiggle.setImage(img2);
    squiggle.setFitWidth(360);
    squiggle.setPreserveRatio(true);
    squiggle.setSmooth(true);  
    squiggle.setTranslateY(240);
    squiggle.setTranslateX(227);


    // Layout 1 - children are laid out in a vertical column
    VBox layout1 = new VBox(45);
    layout1.setTranslateY(330);
    layout1.setTranslateX(377);
    Button button1 = new Button("- Play -");
    Button button2 = new Button("~ Info ~");
    Button button3 = new Button("- Quit -");
    button1.setOnAction(e -> window.setScene(new Scene(createTarot())));
    button2.setOnAction(e -> window.setScene(new Scene(createInfo())));
    button3.setOnAction(e -> System.exit(0));
    layout1.getChildren().addAll(button1, button2, button3);
    
    BackgroundImage myBI = new BackgroundImage(img, 
                           BackgroundRepeat.NO_REPEAT, 
                           BackgroundRepeat.NO_REPEAT, 
                           BackgroundPosition.CENTER,
                           new BackgroundSize(800, 600, false, 
                                              false, true, false)
                           );
    root.setBackground(new Background(myBI));
    root.getChildren().addAll(squiggle, layout1);


    scene1 = new Scene(root);
    window.setScene(scene1);
    window.setTitle("Zoltar Speaks: Tarot Reading");
    window.setResizable(false);
    window.setWidth(800);
    window.setHeight(600);
    window.show();
    music(1);
  }
  public static void main(String[] args) {
    launch(args);
  }
}

final class Card extends Parent { 

  public final int pic_id;
  public int value1;
  public int value2;
  public int value3;
  private static Scanner in;
  public FaceMinor face1;
  public FaceMajor face2;
  public Suit suit;

  public static enum Suit{
    SWORDS (0), WANDS (14), PENTACLES (28), CUPS (42), VOID(-1);
    private int value1;
    private static HashMap<Integer, Suit> map1 = new HashMap<>();
    private Suit(int value1) {
      this.value1 = value1;
    }
    public int getInt() {
      return value1;
    }
    static {
      for (Suit s : Suit.values()) {
        map1.put(s.value1, s);
      }
    }
    public static Suit intOf(int s1) {
      return map1.get(s1);
    }
  }
  public static enum FaceMinor {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), 
    NINE(9), TEN(10), JACK(11), KNIGHT(12), QUEEN(13), KING(14), VOID(-1);
    private int value2;
    private static HashMap<Integer, FaceMinor> map2 = new HashMap<>();
    private FaceMinor(int value2) {
      this.value2 = value2;
    }
    public int getInt2() {
      return value2;
    }
    static {
      for (FaceMinor s : FaceMinor.values()) {
        map2.put(s.value2, s);
      }
    }
    public static FaceMinor intOf(int s2) {
      return map2.get(s2);
    }
  }
  public static enum FaceMajor {
    FOOL(57), MAGICIAN(58), HIGH_PRIESTESS(59), EMPRESS(60), EMPEROR(61), 
    HIEROPHANT(62), LOVERS(63), CHARIOT(64), STRENGTH(65), HERMIT(66),
    WHEEL_OF_FORTUNE(67), JUSTICE(68), HANGED_MAN(69), DEATH(70),
    TEMPERANCE(71), DEVIL(72), TOWER(73), STAR(74), MOON(75), SUN(76), 
    JUDGEMENT(77), WORLD(78), VOID(-1);
    private int value3;
    private static HashMap<Integer, FaceMajor> map3 = new HashMap<>();
    private FaceMajor(int value3) {
      this.value3 = value3;
    }
    public int getInt3() {
      return value3;
    }
    static {
      for (FaceMajor s : FaceMajor.values()) {
        map3.put(s.value3, s);
      }
    }
    public static FaceMajor intOf(int s3) {
      return map3.get(s3);
    }
  }
  public Card() {
    // Default constructor
    this.suit = Card.Suit.SWORDS;;
    this.face1 = Card.FaceMinor.ONE;
    this.face2 = Card.FaceMajor.SUN;
    int temp = suit.getInt() + face1.getInt2();
    this.pic_id = temp;
  }
  public Card(Suit suit, FaceMinor face1) {
    this.suit = suit;
    this.face1 = face1;
    int temp = suit.getInt() + face1.getInt2();
    this.pic_id = temp;
  }
  public Card(FaceMajor face2) {
    this.face2 = face2;
    int temp2 = face2.getInt3();
    this.pic_id = temp2;
  }
  public Image getImage(Card card) {
    String pic = Integer.toString(card.pic_id);
    String pic_folder = "file:cards/";

    pic_folder = pic_folder.concat(pic);
    pic_folder = pic_folder.concat(".jpg");
    Image front = new Image(pic_folder);
    return front;
  }
  public String getReading(Card card, String tense) throws FileNotFoundException {
    // code: combines pic_id and tense, returns tarot reading string
    String picID = Integer.toString(card.pic_id);
    picID = picID + tense;

    File file = new File("reading.txt");
    in = new Scanner(file);
    ArrayList<String> words = new ArrayList<String>();
    String all_words;
    int gotReading = 0;

    while(in.hasNext()) {
      String crib = in.next();
      if(gotReading == 1) {
        words.add(crib);
      }
      if(crib.contains(picID)) {
        gotReading++;
      }
      if(gotReading == 2) {
        all_words = String.join(" ", words);
        all_words = all_words.substring(0, all_words.length() - 3);
        in.close();
        return all_words;
      }
    }
    all_words = String.join(" ", words);
    all_words = all_words.substring(0, all_words.length() - 3);
    in.close();
    return all_words;
  }
  public int[] enumSelect(int x) {
    // code: converts random int (0 - 77) into specific card enum inputs
    int a[] = new int[2];
  
    if(x >= 0 && x < 14) {
      a[0] = 0;
      a[1] = (x+1);
    }
    else if(x >= 14 && x < 28) {
      a[0] = 14;
      a[1] = ((x+1) - 14);    
    }
    else if(x >= 28 && x < 42) {
      a[0] = 28;
      a[1] = ((x+1) - 28);
    }
    else if(x >= 42 && x < 56) {
      a[0] = 42;
      a[1] = ((x+1) - 42);
    }
    else if(x >= 56 && x < 78) {
      a[0] = 0; // Value isn't used.
      a[1] = (x+1);
    }
    return a;
  }
  public Card createCard(int card_seed) {
    // code: uses a random integer seed to create all properties of card object
    int seeds[] = enumSelect(card_seed); 
    FaceMinor myF_Min;
    FaceMajor myF_Maj;
    Suit mySuit;
    Card card;

    if(card_seed < 56) {
      mySuit = Suit.intOf(seeds[0]);
      myF_Min = FaceMinor.intOf(seeds[1]);
      card = new Card(mySuit, myF_Min);
    } else {
      myF_Maj = FaceMajor.intOf(seeds[1]);
      card = new Card(myF_Maj);
    }
    return card;
  }
}

