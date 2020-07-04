/*
Computer Security
by Talha Mangarah - tmang002 - 33551591
-Built using Java 13, JavaFX 14 with Gradle 6.2.2
-Supports Java 11+, Gradle 6+
-To run GUI:, use the prebuilt image/script (./build/image/bin/me.talhamangarah.cipher (.bat for windows))
    or Gradle: "./gradlew run"
    Gradle: https://gradle.org/
-Screen resolution min.: 1120x685
*/
package me.talhamangarah.cipher;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.BigInteger;

public class Main extends Application {
    //Stage & Scene vars set here so we can switch between multiple scenes.
    Stage window;
    Scene initial, rsa, part2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //set up GUI stage/settings
        window = primaryStage;

        BorderPane BPane = new BorderPane();
        BPane.setTop(homeLabel());
        BPane.setCenter(addHBox());

        initial = new Scene(BPane, appSize('x'), appSize('y'));
        window.setScene(initial);
        window.setTitle("tmang002 - Comp Sec CW");

        window.setX(appSize('x'));
        window.setY(appSize('y'));
        window.setWidth(appSize('x'));
        window.setHeight(appSize('y'));

        //Max & Min for window
        window.setMaxWidth(appSize('x'));
        window.setMaxHeight(appSize('y'));
        window.setMinWidth(appSize('x'));
        window.setMinHeight(appSize('y'));

        window.show();
    }

    //App settings
    public String useFont(){
        return "Arial";
    }

    public double appSize(char s){
        //Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        if(s == 'x') {
            //return primaryScreenBounds.getWidth() / 1.5;
            return 1120;
        }
        else{
            //return primaryScreenBounds.getHeight() / 1.5;
            return 685;
        }
    }

    //Labels
    public Label homeLabel(){
        Label homeLabel = new Label("Home");
        homeLabel.setFont(Font.font(useFont(), FontWeight.BOLD, FontPosture.REGULAR,38));
        homeLabel.setUnderline(true);

        return homeLabel;
    }

    public Label rsaLabel(){
        Label rsaLabel = new Label("RSA");
        rsaLabel.setFont(Font.font(useFont(), FontWeight.BOLD, FontPosture.REGULAR,38));
        rsaLabel.setUnderline(true);

        return rsaLabel;
    }

    public Label part2Label(){
        Label part2Label = new Label("Part 2");
        part2Label.setFont(Font.font(useFont(), FontWeight.BOLD, FontPosture.REGULAR,38));
        part2Label.setUnderline(true);

        return part2Label;
    }

    //Home HBox
    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(50);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonRSA = new Button("RSA");
        buttonRSA.setFont(Font.font(useFont(), FontWeight.BOLD, FontPosture.REGULAR,28));
        buttonRSA.setPrefSize(150, 25);
        buttonRSA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                window.setScene(rsaScene());
            }
        } );


        Button buttonPart2 = new Button("Part 2");
        buttonPart2.setFont(Font.font(useFont(), FontWeight.BOLD, FontPosture.REGULAR,28));
        buttonPart2.setPrefSize(150, 25);
        buttonPart2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                window.setScene(part2Scene());
            }
        } );

        hbox.getChildren().addAll(buttonRSA, buttonPart2);

        return hbox;
    }

    //RSA UI
    public HBox rsaToHomeButton(){
        HBox hbox = new HBox();
        hbox.setSpacing(appSize('x')-232);

        hbox.getChildren().add(rsaLabel());
        addStackToHome(hbox);

        return hbox;
    }

    //stack pane to build UI elements on top of each other
    public void addStackToHome(HBox hb){
        StackPane stack = new StackPane();
        Button homeRSA = new Button("Home");
        homeRSA.setFont(Font.font(useFont(), FontWeight.BOLD, FontPosture.REGULAR,28));
        homeRSA.setPrefSize(150, 25);
        //homeRSA.setAlignment(Pos.CENTER);
        homeRSA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                window.setScene(initial);
            }
        } );

        stack.getChildren().add(homeRSA);
        stack.setAlignment(Pos.CENTER_LEFT);
        hb.getChildren().add(stack);
        HBox.setHgrow(stack, Priority.ALWAYS);

    }

    //Returns an arrow depending on the direction required
    public Text arrow(String arrowType){
        switch(arrowType) {
            case "right":
                return new Text("→");
            case "left":
                return new Text("←");
            case "down":
                return new Text("↓");
            default:
                System.out.println("Wrong input for arrow");
                return new Text("a");
        }
    }

    //UI down arrow
    public HBox downArrow(){
        HBox downArrow1 = new HBox();
        Text downArrow = arrow("down");
        downArrow1.getChildren().add(downArrow);
        downArrow1.setMaxSize(150, 10);
        downArrow1.setAlignment(Pos.CENTER);
        return downArrow1;
    }

    //UI left arrow
    public HBox leftArrow(){
        HBox leftArrow1 = new HBox();
        Text leftArrow = arrow("left");
        leftArrow1.getChildren().add(leftArrow);
        leftArrow1.setMaxSize(300, 10);
        leftArrow1.setAlignment(Pos.CENTER_RIGHT);
        return leftArrow1;
    }

    //UI right arrow
    public HBox rightArrow(){
        HBox rightArrow1 = new HBox();
        Text rightArrow = arrow("right");
        rightArrow1.getChildren().add(rightArrow);
        rightArrow1.setMaxSize(300, 10);
        rightArrow1.setAlignment(Pos.CENTER_RIGHT);
        return rightArrow1;
    }

    //scroll panes for cipher text so it doesn't take up all the UI
    public ScrollPane cipherTextScroll(Text cipherText) {
        VBox cipherVBox = new VBox();
        cipherVBox.getChildren().add(cipherText);
        ScrollPane cipherScroll = new ScrollPane(cipherVBox);
        cipherScroll.setMaxHeight(15);
        cipherScroll.setMaxWidth(325);
        return cipherScroll;
    }

    //scroll panes for plain text so it doesn't take up all the UI
    public ScrollPane plainTextScroll(Text plainText) {
        VBox plainVBox = new VBox(plainText);
        ScrollPane plainScroll = new ScrollPane(plainVBox);
        plainScroll.setMaxHeight(10);
        plainScroll.setMaxWidth(200);
        return plainScroll;
    }

    //Main RSA UI grid
    public GridPane rsaGrid(){
        //set up initial grid, ui elements such as button & text for alice,bob&charlie
        GridPane rsaGrid = new GridPane();
        Text alice = new Text("Alice");
        Text charlie = new Text("Charlie");
        Text bob = new Text("Bob");
        alice.setFont(Font.font(useFont()));
        charlie.setFont(Font.font(useFont()));
        bob.setFont(Font.font(useFont()));
        Label messageLabel=new Label("Enter message, <1024 bits");
        messageLabel.setFont(Font.font(useFont()));
        TextField messageTextField=new TextField();
        Button messageButton = new Button("Submit");
        var cipMessageRef = new Object() {
            String userMessage;
            Text cipherText;
            Text cipherText2;
            Text plainTextBytes;
            Text plainText;
            BigInteger encrypt;
            BigInteger decrypt;
            Boolean nodeRemoval;
            {
                nodeRemoval = false;
            }
        };

        //key generation
        RSA key = new RSA(1024);

        //public key text
        Text pubKeyText = new Text("Public key(e,n)=\n("+ key.publicKey +"\n,\n"+ key.modulus +")");
        pubKeyText.setWrappingWidth(175);
        VBox row6 = new VBox(pubKeyText);
        ScrollPane keyScroll = new ScrollPane(row6);
        keyScroll.setMaxHeight(10);
        keyScroll.setMaxWidth(200);

        //private key text
        Text privKeyText = new Text("Private key(d)=\n"+ key.privateKey);
        privKeyText.setWrappingWidth(175);
        VBox row6priv = new VBox(privKeyText);
        ScrollPane privKeyScroll = new ScrollPane(row6priv);
        privKeyScroll.setMaxHeight(10);
        privKeyScroll.setMaxWidth(200);

        //Names row
        rsaGrid.add(alice,0,0);
        rsaGrid.add(charlie,1,0);
        rsaGrid.add(bob,2,0);

        //message box
        HBox message = new HBox();
        message.getChildren().addAll(messageLabel, messageTextField, messageButton);

        //row5 text
        Text row5Text = new Text("'Let's talk via RSA'");
        Text row5Text2 = new Text("Public can see the method of communication - RSA");
        Text row5Text3 = new Text("'Agree, let's set up the keys'");

        //row6&7
        Text row6Text1 = new Text("public key(e,n)");
        Text row6Text2 = new Text("Alice gets public key(e,n)");
        Text row7Text = new Text("Charlie has public key & algorithm used");
        Text row7Text1 = new Text("Encrypts message: c = m^e mod n");
        Text row7Text2 = new Text("Waiting for message to be entered for encryption");
        row7Text2.setFill(Color.RED);

        //disable button if textfield is empty
        messageButton.disableProperty().bind(
                Bindings.createBooleanBinding( () ->
                        messageTextField.getText().trim().isEmpty(), messageTextField.textProperty()
                )
        );

        //encrypt & decrypt message + set up button actions
            /*cipMessageRef refers to the Object that stores some variables for easy access for the button action as was
            recommended by the IDE*/
        messageButton.setOnAction(e -> {
            //assigns the user entered text
            cipMessageRef.userMessage = messageTextField.getText();

            //plain text as bytes for the start UI grid
            byte[] bytes = cipMessageRef.userMessage.getBytes();
            BigInteger messageBytes = new BigInteger(bytes);
            cipMessageRef.plainTextBytes = new Text("m as bytes:\n" + messageBytes.toString());
            cipMessageRef.plainTextBytes.setWrappingWidth(300);

            //encrypts the text and applies it to UI text
            cipMessageRef.encrypt = key.encrypt(messageBytes);
            cipMessageRef.cipherText = new Text(cipMessageRef.encrypt.toString());
            cipMessageRef.cipherText.setWrappingWidth(300);
            cipMessageRef.cipherText2 = new Text(cipMessageRef.encrypt.toString());
            cipMessageRef.cipherText2.setWrappingWidth(300);

            //decrypt
            cipMessageRef.decrypt = key.decrypt(cipMessageRef.encrypt);
            //Decrypted back to plain
            byte[] cipherToPlainArray = cipMessageRef.decrypt.toByteArray();
            String cipherToPlain = new String(cipherToPlainArray);

            //plain text for UI
            Text plain = new Text(cipherToPlain);
            plain.setWrappingWidth(175);

            //plain text as bytes for UI
            cipMessageRef.plainText = new Text("cipher to plain as bytes:\n" + cipMessageRef.decrypt.toString());
            cipMessageRef.plainText.setWrappingWidth(175);

            //remove the placeholder text, if it's been removed then it won't remove again
            while (!cipMessageRef.nodeRemoval) {
                rsaGrid.getChildren().remove(21);
                rsaGrid.getChildren().remove(27);
                cipMessageRef.nodeRemoval = true;
            }
            //add the new calculated plain and cipher text to the UI
            rsaGrid.add(cipherTextScroll(cipMessageRef.plainTextBytes), 0, 4, 10,3);
            rsaGrid.add(plainTextScroll(cipMessageRef.plainText),2,34,10,3);
            rsaGrid.add(plainTextScroll(plain), 2, 39, 10,3);
            rsaGrid.add(cipherTextScroll(cipMessageRef.cipherText), 0, 21, 20, 10);
            rsaGrid.add(cipherTextScroll(cipMessageRef.cipherText2), 1, 31, 20, 10);
        });

        //row 8 & 9 text
        Text row8Text1 = new Text("Alice sends the cipher text");
        Text row8Text2 = new Text("Cipher text: c");
        Text row8Text3 = new Text("Decrypts message: m = c^d mod n");
        Text row8Text4 = new Text("Waiting for message to be entered\n for decryption");
        row8Text4.setFill(Color.RED);
        Text row9Text1 = new Text("Charlie has:\n cipher text c\n public key(e,n)\n method:RSA");

        //add ui elements to the UI grid with positions
        rsaGrid.add(message, 0, 1, 150,30);
        rsaGrid.add(downArrow(), 0, 6, 3,4);
        rsaGrid.add(row5Text, 0, 5, 20, 10);
        rsaGrid.add(rightArrow(), 0, 5, 10,10);
        rsaGrid.add(row5Text2,1,5,20,10);
        rsaGrid.add(rightArrow(), 1, 5, 10,10);
        rsaGrid.add(row5Text3, 2, 5, 20,10);
        rsaGrid.add(downArrow(), 2,10,10,5);
        rsaGrid.add(keyScroll, 2,13,2,4);
        rsaGrid.add(privKeyScroll, 2,16,2,4);
        rsaGrid.add(row6Text1,1,13,20,10);
        rsaGrid.add(leftArrow(),1,13,10,10);
        rsaGrid.add(leftArrow(),0,13,10,10);
        rsaGrid.add(row6Text2,0,13,20,10);
        rsaGrid.add(downArrow(),1,18,10,5);
        rsaGrid.add(row7Text,1,18,20,10);
        rsaGrid.add(downArrow(),0,18,10,5);
        rsaGrid.add(row7Text1, 0, 18, 20,10);
        rsaGrid.add(row7Text2,0,21,20,10);
        rsaGrid.add(downArrow(), 0, 27,10,5);
        rsaGrid.add(row8Text1, 0, 28,20,10);
        rsaGrid.add(rightArrow(), 0, 28, 10, 10);
        rsaGrid.add(row8Text2, 1, 28, 20,10);
        rsaGrid.add(rightArrow(),1,28,10,10);
        rsaGrid.add(row8Text3, 2, 28, 20,10);
        rsaGrid.add(row8Text4,2, 30, 20, 10);
        rsaGrid.add(downArrow(), 1, 36, 10,5);
        rsaGrid.add(downArrow(),2,36,10,5);
        rsaGrid.add(row9Text1, 1, 39, 20,10);

        //Grid padding etc.
        rsaGrid.setPadding(new Insets(10,100,0,100));
        rsaGrid.setHgap(appSize('x')/3);
        rsaGrid.setVgap(10);

        return rsaGrid;
    }

    public Scene rsaScene(){
        //Use borderpane for UI to split up the UI
        BorderPane BPane = new BorderPane();
        BPane.setTop(rsaToHomeButton());
        BPane.setCenter(rsaGrid());

        rsa = new Scene(BPane, appSize('x'), appSize('y'));

        return rsa;
    }

    //Part 2 UI
    public HBox part2toHomeButton(){
        HBox hbox = new HBox();
        hbox.setSpacing(appSize('x')-255);

        hbox.getChildren().add(part2Label());
        addStackToHome(hbox);

        return hbox;
    }

    public GridPane part2Grid(){
        //set up initial grid, ui elements such as button & text for alice,bob&charlie
        GridPane p2Grid = new GridPane();
        Text a = new Text("A");
        Text s = new Text("S");
        Text b = new Text("B");
        a.setFont(Font.font(useFont()));
        s.setFont(Font.font(useFont()));
        b.setFont(Font.font(useFont()));

        //key generation
        Part2 s_key = new Part2(1024);
        Part2 a_key = new Part2(1024);
        Part2 b_key = new Part2(1024);

        //s public key text
        Text s_pubKeyText = new Text("Public key ks=\n("+ s_key.publicKey +")");
        s_pubKeyText.setWrappingWidth(175);
        VBox s_pubKeyRow = new VBox(s_pubKeyText);
        ScrollPane s_keyScroll = new ScrollPane(s_pubKeyRow);
        s_keyScroll.setMaxHeight(10);
        s_keyScroll.setMaxWidth(200);

        //s to a key for b
        BigInteger bKeySignature = s_key.generateAndVerifySignature(s_key.privateKey,s_key.modulus,b_key.publicKey);
        Text b_pubKeyText = new Text("Public key Kb=\n("+ b_key.publicKey +")\n signature="+ bKeySignature);
        b_pubKeyText.setWrappingWidth(175);
        VBox b_pubKeyRow = new VBox(b_pubKeyText);
        ScrollPane b_keyScroll = new ScrollPane(b_pubKeyRow);
        b_keyScroll.setMaxHeight(10);
        b_keyScroll.setMaxWidth(200);

        //b key hash
        int bKeyHash = Math.abs(b_key.publicKey.hashCode());
        Text bHashText = new Text("B pub key hash for verif.\n" + bKeyHash);

        //a signature verification from s for b key
        Text b_verifText = new Text("Signature verification=\n("+ s_key.encrypt(bKeySignature) +")");

        //a to b nonce
        BigInteger nonceAtoB = new BigInteger(b_key.nonceGenerator());
        BigInteger encryptedNonceB = b_key.encrypt(nonceAtoB);
        Text aToBNonce = new Text("Nonce nA\n"+nonceAtoB.toString());
        Text aToBNonceEnc = new Text("Encrypted nonce that only B can read\n"+encryptedNonceB);
        Text bNonceDecrypt = new Text("Decrypted nonce\n"+b_key.decrypt(encryptedNonceB));

        //s to b for a key
        BigInteger aKeySignature = s_key.generateAndVerifySignature(s_key.privateKey,s_key.modulus,a_key.publicKey);

        Text a_pubKeyText = new Text("Public key Ka=\n("+ a_key.publicKey +")\n signature="+ aKeySignature);

        //a key hash
        int aKeyHash = Math.abs(a_key.publicKey.hashCode());
        Text aHashText = new Text("A pub key hash for verif.\n" + aKeyHash);

        //b signature verification from s for a key
        Text a_verifText = new Text("Signature verification=\n("+ s_key.encrypt(aKeySignature) +")");

        //b to a nonce
        BigInteger nonceBtoA = new BigInteger(a_key.nonceGenerator());
        BigInteger encryptedNonceA = a_key.encrypt(nonceBtoA);
        BigInteger encryptedNonceBbyB = a_key.encrypt(nonceAtoB);
        Text bToANonce = new Text("Nonce nB\n"+nonceBtoA.toString()+"\nEncrypted nonce (nB)Ka that only A can read\n"+encryptedNonceA+"\nEncrypted nonce (nA)Ka that only A can read\n"+encryptedNonceBbyB);
        Text aNonceDecrypt = new Text("Decrypted nonce nB\n"+a_key.decrypt(encryptedNonceA));
        Text bNonceDecryptbyB = new Text("Decrypted nonce nA\n"+a_key.decrypt(encryptedNonceBbyB));

        //a to b nonce with b's nonce
        BigInteger nonceAtoBwithBsNonce = b_key.encrypt(nonceBtoA);
        BigInteger nonceBdecryptedFromA = b_key.decrypt(nonceAtoBwithBsNonce);
        Text encryptedBNonceFromA = new Text("Encrypted B Nonce from A to B: "+nonceAtoBwithBsNonce);
        Text decryptedBNonceFromA = new Text("Decrypted B Nonce from A: "+nonceBdecryptedFromA);

        //Names row
        p2Grid.add(a,0,0);
        p2Grid.add(s,1,0);
        p2Grid.add(b,2,0);

        //text labels for grids to explain flow
        Text aToS1 = new Text("A -> S : A,B");
        Text sToA1 = new Text("S -> A:(Kb,B)ks");
        Text sToA2 = new Text("(Kb,B)ks");
        Text aToB1 = new Text("A -> B: (nA,A)Kb");
        Text bToS1 = new Text("B -> S:B, A");
        Text sToB1 = new Text("S -> B: (Ka,A)ks");
        Text sToB2 = new Text("(Ka,A)ks");
        Text bToA = new Text("B -> A: (nA,nB)Ka");
        Text aToB2 = new Text("A -> B: (nB)Kb");
        Text aToB3 = new Text("(nB)Kb");

        Text bKeyAndSign = new Text("B Key & Signature(with key hashed)");
        Text aKeyAndSign = new Text("A Key & Signature(with key hashed)");

        //add ui elements to the UI grid with positions
        p2Grid.add(s_keyScroll,1,1,15,1);
        p2Grid.add(aToS1, 0, 0, 20,10);
        p2Grid.add(rightArrow(), 0, 0, 2, 10);
        p2Grid.add(sToA1,1,0,20,10);
        p2Grid.add(downArrow(),1,5,3,4);
        p2Grid.add(plainTextScroll(aHashText),0,0,10,2);
        p2Grid.add(plainTextScroll(bHashText),2,0,10,2);
        p2Grid.add(bKeyAndSign, 1, 7,20,5);
        p2Grid.add(b_keyScroll,1,11,10,2);
        p2Grid.add(leftArrow(), 0,7,2,5);
        p2Grid.add(sToA2,0,7,20,5);
        p2Grid.add(plainTextScroll(b_verifText), 0,11 ,10,2);
        p2Grid.add(downArrow(),0,13,3,4);
        p2Grid.add(aToB1,0,11,20,10);
        p2Grid.add(plainTextScroll(aToBNonce),0,18,10,1);
        p2Grid.add(plainTextScroll(aToBNonceEnc),0,20,10,2);
        p2Grid.add(rightArrow(),1,11,1,10);
        p2Grid.add(plainTextScroll(bNonceDecrypt),2,11,20,10);
        p2Grid.add(downArrow(),2,12,1,12);
        p2Grid.add(bToS1,2,15,20,10);
        p2Grid.add(leftArrow(),1,19,2,2);
        p2Grid.add(sToB1,1,19,20,2);
        p2Grid.add(downArrow(),1,21,2,2);
        p2Grid.add(aKeyAndSign,1,22,20,1);
        p2Grid.add(plainTextScroll(a_pubKeyText),1,23,20,3);
        p2Grid.add(rightArrow(),1,24,2,1);
        p2Grid.add(plainTextScroll(a_verifText),2,23,20,5);
        p2Grid.add(sToB2,2,24,20,1);
        p2Grid.add(downArrow(),2,26,1,2);
        p2Grid.add(bToA,2,27,20,2);
        p2Grid.add(plainTextScroll(bToANonce),2,28,20,2);
        p2Grid.add(leftArrow(),1,28,3,1);
        p2Grid.add(plainTextScroll(aNonceDecrypt),0,27,20,1);
        p2Grid.add(plainTextScroll(bNonceDecryptbyB),0,28,20,1);
        p2Grid.add(downArrow(),0,28,1,5);
        p2Grid.add(aToB2,0,29,20,1);
        p2Grid.add(plainTextScroll(encryptedBNonceFromA),0,30,20,1);
        p2Grid.add(rightArrow(),0,30,2,1);
        p2Grid.add(aToB3,2,30,20,1);
        p2Grid.add(plainTextScroll(decryptedBNonceFromA),2,30,20,1);

        //Grid padding etc.
        p2Grid.setPadding(new Insets(10,100,0,100));
        p2Grid.setHgap(appSize('x')/3);
        p2Grid.setVgap(10);

        return p2Grid;
    }

    public Scene part2Scene(){
        //BorderPane to split UI
        BorderPane BPane = new BorderPane();
        BPane.setTop(part2toHomeButton());
        BPane.setCenter(part2Grid());

        part2 = new Scene(BPane, appSize('x'), appSize('y'));

        return part2;
    }
}
