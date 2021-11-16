package blackjack;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class GUIController implements Initializable {
    
    @FXML
    private Button loadSaveButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button hitButton;
    @FXML
    private Button standButton;
    @FXML
    private TextField betInput;
    @FXML
    private Text text;
    @FXML
    private Text playersMoneyText;
    @FXML
    private ImageView dealerCard1;
    @FXML
    private ImageView dealerCard2;
    @FXML
    private ImageView dealerCard3;
    @FXML
    private ImageView dealerCard4;
    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard3;
    @FXML
    private ImageView playerCard4;
    @FXML
    private ImageView playerCard5;
    @FXML
    private ImageView playerCard2;
    
    private boolean gameStarted = false;
    private double playersMoney = 0;
    private double betAmount = 0;
    private int round = 1;
    private int playersHandValue = 0;
    private int dealersHandValue = 0;
    private Hand playersHand = new Hand();
    private Hand dealersHand = new Hand();
    private Deck deck = new Deck();
    Card secondDealerCard = deck.getRandomCard();
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    @FXML
    void loadSave(ActionEvent event) {
        File save = new File("save.txt");
        if (save.canRead()) {
            try {
                FileReader fileReader = new FileReader(save);
                try {
                    Scanner moneyReader = new Scanner(fileReader);
                    String savedMoney = moneyReader.nextLine();
                    if (savedMoney == null) {
                        playersMoney = 100;
                    }
                    playersMoney = Double.parseDouble(savedMoney);
                    if (playersMoney == 0) {
                        text.setText("Game over. Resetting..");
                        playersMoney = 100;
                    }
                    updatePlayersMoney(playersMoney);
                    fileReader.close();
                    moneyReader.close();
                    gameStarted = true;
                    round = 1;
                    playerCard1.setImage(null);
                    playerCard2.setImage(null);
                    playerCard3.setImage(null);
                    playerCard4.setImage(null);
                    playerCard5.setImage(null);
                    dealerCard1.setImage(null);
                    dealerCard2.setImage(null);
                    dealerCard3.setImage(null);
                    dealerCard4.setImage(null);
                    text.setText("1.Enter Bet       2.Press Hit");
                } catch (IOException ex) {
                    text.setText("Error! Could not read save file!");
                }
            } catch (FileNotFoundException ex) {
                text.setText("Error! Save file not found");
            }
        }
    }

    @FXML
    void newGame(ActionEvent event) {
        File save = new File("save.txt");
        if (save.exists())
            save.delete();
        try {
            save.createNewFile();
            playersMoney = 100;
            updatePlayersMoney(playersMoney);
            gameStarted = true;
            round = 1;
            playerCard1.setImage(null);
            playerCard2.setImage(null);
            playerCard3.setImage(null);
            playerCard4.setImage(null);
            playerCard5.setImage(null);
            dealerCard1.setImage(null);
            dealerCard2.setImage(null);
            dealerCard3.setImage(null);
            dealerCard4.setImage(null);
            text.setText("1.Enter Bet       2.Press Hit");
        } catch (IOException ex) {
            text.setText("Error creating new game!");
        }
    }

    @FXML
    private void Hit(MouseEvent event) {        
        if (!gameStarted){
            text.setText("Start a game to hit"); // Player must hit start game first for save file to be created 
            return;
        }
                
        Hand playersHand = new Hand();
        Hand dealersHand = new Hand();
                
        setBetAmount();
        
        if (setBetAmount()){ // true if bet amount has been set
        Deck deckOfCards = new Deck();

            switch (round) {
                case 1 -> {
                    // reset cards images from previous round
                    playerCard1.setImage(null);
                    playerCard2.setImage(null);
                    playerCard3.setImage(null);
                    playerCard4.setImage(null);
                    playerCard5.setImage(null);
                    dealerCard1.setImage(null);
                    dealerCard2.setImage(null);
                    dealerCard3.setImage(null);
                    dealerCard4.setImage(null);

                    Card playerCard = deckOfCards.getRandomCard();
                    playersHand.addCard(playerCard);
                    Image cardImage = new Image("images/" + playerCard.face.toString() 
                            + playerCard.suit.toString() + ".png");
                    playerCard1.setImage(cardImage);
                    
                    Card secondPlayerCard = deckOfCards.getRandomCard();
                    Image secondCardImage = new Image("images/" + secondPlayerCard.face.toString() 
                            + secondPlayerCard.suit.toString() + ".png");
                    playerCard2.setImage(secondCardImage);
                    playersHand.addCard(secondPlayerCard);
                    
                    Card dealerCard = deckOfCards.getRandomCard();
                    dealersHand.addCard(dealerCard);
                    Image dealerCardImage = new Image("images/" + dealerCard.face.toString() 
                            + dealerCard.suit.toString() + ".png");
                    dealerCard1.setImage(dealerCardImage);
                    
                    Card secondDealerCard = deckOfCards.getRandomCard();
                    dealersHand.addCard(secondDealerCard);
                    this.secondDealerCard = secondDealerCard;
                    
                    // place next card face down (BLANKCARD.png is the back of a playing card)
                    Image blank = new Image("images/BLANKCARD.png");
                    dealerCard2.setImage(blank);
                    
                    round++;
                    
                    playersHandValue = playersHand.valueOfHand();
                    dealersHandValue = dealersHand.valueOfHand();
                    if (playersHandValue > 21){ // bust?
                        playersHand.clear();
                        dealersHand.clear();
                        playerLost();
                    }
                    else if (playersHandValue == 21) { // check for blackjack (21) if true player wins
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                }
                case 2 -> {
                    Card thirdPlayerCard = deckOfCards.getRandomCard();
                    Image thirdCardImage = new Image("images/" + thirdPlayerCard.face.toString() 
                            + thirdPlayerCard.suit.toString() + ".png");
                    playerCard3.setImage(thirdCardImage);
                    playersHand.addCard(thirdPlayerCard);
                    Image secondDealerCardImage = new Image("images/" + secondDealerCard.face.toString()
                            + secondDealerCard.suit.toString() + ".png");
                    dealerCard2.setImage(secondDealerCardImage);
                     round++;
                    playersHandValue += playersHand.valueOfHand();
                    dealersHandValue = dealersHand.valueOfHand();
                    if (playersHandValue > 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerLost();
                    }
                    else if (playersHandValue == 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                    else if (dealersHandValue > 21){
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                }
                case 3 -> {
                    Card fourthPlayerCard = deckOfCards.getRandomCard();
                    Image fourthCardImage = new Image("images/" + fourthPlayerCard.face.toString() 
                            + fourthPlayerCard.suit.toString() + ".png");
                    playerCard4.setImage(fourthCardImage);
                    playersHand.addCard(fourthPlayerCard);
                    if (dealersHand.valueOfHand() < 17) { //  Dealer must stand on 17
                    Card thirdDealerCard = deckOfCards.getRandomCard();
                    Image thirdDealerCardImage = new Image("images/" + thirdDealerCard.face.toString() 
                            + thirdDealerCard.suit.toString() + ".png");
                    dealerCard3.setImage(thirdDealerCardImage);
                    dealersHand.addCard(thirdDealerCard);
                    }
                    round++;
                    playersHandValue += playersHand.valueOfHand();
                    dealersHandValue += dealersHand.valueOfHand();
                    if (playersHandValue > 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerLost();
                    }
                    else if (playersHandValue == 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                    else if (dealersHandValue > 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                }
                case 4 -> {
                    Card fifthPlayerCard = deckOfCards.getRandomCard();
                    Image fifthCardImage = new Image("images/" + fifthPlayerCard.face.toString() 
                            + fifthPlayerCard.suit.toString() + ".png");
                    playerCard5.setImage(fifthCardImage);
                    playersHand.addCard(fifthPlayerCard);
                    if (dealersHand.valueOfHand() < 17) { // Dealer must stand on 17
                    Card fourthDealerCard = deckOfCards.getRandomCard();
                    Image fourthDealerCardImage = new Image("images/" + fourthDealerCard.face.toString() 
                            + fourthDealerCard.suit.toString() + ".png");
                    dealerCard4.setImage(fourthDealerCardImage);
                    dealersHand.addCard(fourthDealerCard);
                    }
                    round++;
                    dealersHandValue += dealersHand.valueOfHand();
                    playersHandValue += playersHand.valueOfHand();
                    if (playersHandValue > 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerLost();
                    }
                    else if (playersHandValue == 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                    else if (dealersHandValue > 21) {
                        playersHand.clear();
                        dealersHand.clear();
                        playerWon();
                    }
                }
                default -> { 
                // if game goes on five rounds player wins according to 5-card Charlie rule
                    playersHand.clear();
                    dealersHand.clear();
                    playerWon();
                }
            }
                this.deck = deckOfCards;
                } 
        this.playersHand = playersHand;
        this.dealersHand = dealersHand;
    }
    
    private void playerWon() {
        text.setText("You won!");
        playersMoney = playersMoney + betAmount;
        updatePlayersMoney(playersMoney);
        betInput.clear();
        playersHand.clear();
        dealersHand.clear();
        round = 1;
        playersHandValue = 0;
    }
    
    private void playerLost(){
        text.setText("You lost!");
        playersMoney = playersMoney - betAmount;
        updatePlayersMoney(playersMoney);
        betInput.clear();
        playersHand.clear();
        dealersHand.clear();
        round = 1;
        playersHandValue = 0;
    }
    
    @FXML
    private void Stand(MouseEvent event) {
        System.out.println(round);
        if (!gameStarted) {
            text.setText("Start a game to stand"); // Player must hit start game first for save file to be created 
            return;
        }
        if (setBetAmount()){ // true if bet amount has been set
        if (round == 2) { //  Dealer must stand on 17
            Image secondDealerCardImage = new Image("images/" + secondDealerCard.face.toString()
                    + secondDealerCard.suit.toString() + ".png");
            dealerCard2.setImage(secondDealerCardImage);
            round++;
        }
        if (round == 3 && dealersHandValue < 17) { // Dealer must stand on 17
            Card thirdDealerCard = deck.getRandomCard();
            Image thirdDealerCardImage = new Image("images/" + thirdDealerCard.face.toString()
                    + thirdDealerCard.suit.toString() + ".png");
            dealerCard3.setImage(thirdDealerCardImage);
            dealersHand.addCard(thirdDealerCard);
            dealersHandValue += dealersHand.valueOfHand();
            round++;
        }
        if (round == 4 && dealersHandValue < 17) { // Dealer must stand on 17
                Card fourthDealerCard = deck.getRandomCard();
                dealersHand.addCard(fourthDealerCard);
                Image fourthDealerCardImage = new Image("images/" + fourthDealerCard.face.toString()
                        + fourthDealerCard.suit.toString() + ".png");
                dealerCard4.setImage(fourthDealerCardImage);
                dealersHandValue += dealersHand.valueOfHand();
            }

        if (dealersHandValue > 21){
            playerWon();
        }
        else if (dealersHandValue == playersHandValue){
            text.setText("Draw!");
            betInput.clear();
            playersHand.clear();
            dealersHand.clear();
            round = 1;
            playersHandValue = 0;
        }
        else if (dealersHandValue > playersHandValue){
            playerLost();
        }
        else if (playersHandValue > dealersHandValue){
            playerWon();
        }
        betInput.clear();
        playersHand.clear();
        dealersHand.clear();
        round = 1;
        playersHandValue = 0;
        }
    }
    
    @FXML
    private boolean setBetAmount() {        
        try {  
            betAmount = Double.parseDouble(betInput.getText());
            text.setText("");
        } catch (NumberFormatException e) {
            text.setText("Please enter a valid bet");
            return false;
        }
        if (betAmount > playersMoney) {
            text.setText( "You only have $" + playersMoney + " to bet");
            return false;
        }
        if (betAmount <= 0) {
            text.setText("You have to bet something!");
            return false;
        }
        return true;
    }

    private void updatePlayersMoney(double playersMoney) {
        playersMoneyText.setText(String.valueOf(playersMoney));
        try {
            FileWriter fileWriter = new FileWriter("save.txt");
            fileWriter.write(String.valueOf(playersMoney));
            fileWriter.close();
        } catch (IOException ex) {
            text.setText("Error saving game!");
        }
    }
    
}
