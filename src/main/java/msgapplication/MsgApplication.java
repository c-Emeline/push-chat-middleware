package msgapplication;

import connection.Connection;
import emitter.Emitter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import receiver.Receiver;
import receiver.ReceiverImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static javafx.geometry.Pos.CENTER;

public class MsgApplication {

    public static VBox createConnectionPane(Stage primaryStage){
        GridPane gp = new GridPane();
        Label convLabel = new Label("Username");
        TextField userName = new TextField();
        Label connectionErrorLabel = new Label("");

        Button btn = new Button("Connect");
        btn.setOnAction(
                (evt) -> {
                    ReceiverImpl userRcv = null;
                    Emitter userEmt = null;
                    boolean connected = false;
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        Connection myComponent = (Connection) Naming.lookup("rmi://localhost:1099/Connection");
                        userRcv = new ReceiverImpl(userName.getText());
                        userEmt = myComponent.connect(userName.getText(), userRcv);

                    } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }

                    primaryStage.setScene(new Scene(createMessagePane(userEmt, userRcv, primaryStage), 500, 500));
                    primaryStage.setTitle(userName.getText()+" tchat 8_8");
                }
        );


        gp.add( convLabel, 0, 0 );
        gp.add( userName, 0, 1 );
        gp.add(btn, 0,2);
        gp.add( connectionErrorLabel, 0, 3 );

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        VBox vbox = new VBox(hbox);
        vbox.setAlignment(CENTER);

        return vbox;
    }


    public static VBox createMessagePane(Emitter userEmt, ReceiverImpl userRcv, Stage primaryStage){
        GridPane gp = new GridPane();
        Label msgLabel = new Label("Message");

        TextField message = new TextField();
        Label receiver = new Label("Receiver");
        TextField receiverName = new TextField();

        Button btnSend = new Button("Send");
        Label errorLabel = new Label("");
        Button btnDisconnect = new Button("Disconnect");

        Label msgLabel1 = new Label(""); Label msgLabel2 = new Label(""); Label msgLabel3 = new Label(""); Label msgLabel4 = new Label("");
        Label msgLabel5 = new Label(""); Label msgLabel6 = new Label(""); Label msgLabel7 = new Label(""); Label msgLabel8 = new Label("");

        ArrayList<Label> msgLabels = new ArrayList<>();
        msgLabels.add(msgLabel1); msgLabels.add(msgLabel2); msgLabels.add(msgLabel3); msgLabels.add(msgLabel4);
        msgLabels.add(msgLabel5); msgLabels.add(msgLabel6); msgLabels.add(msgLabel7); msgLabels.add(msgLabel8);

        userRcv.setMessageLabels(msgLabels);

        btnSend.setOnAction(
                (evt) -> {
                    boolean receiverConnected = false;
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        userEmt.sendMessage(receiverName.getText(), message.getText());

                    } catch (RemoteException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }
                    message.setText("");
                    receiverName.setText("");
                }
        );

        btnDisconnect.setOnAction(
                (evt) -> {
                    try {
                        //example of a RMI URL used to retrieve a remote reference
                        Connection myComponent = (Connection) Naming.lookup("rmi://localhost:1099/Connection");
                        myComponent.disconnect(userEmt.getClientName());

                    } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        // TODO Auto-generated catch blocks
                        e.printStackTrace();
                    }

                    primaryStage.setScene(new Scene(createConnectionPane(primaryStage), 300, 300));
                }
        );


        gp.add(msgLabel1, 0, 1);
        gp.add(msgLabel2, 0, 1);
        gp.add(msgLabel3, 0, 1);
        gp.add(msgLabel4, 0, 1);
        gp.add(msgLabel5, 0, 1);
        gp.add(msgLabel6, 0, 1);
        gp.add(msgLabel7, 0, 1);
        gp.add(msgLabel8, 0, 1);
        gp.add( msgLabel, 0, 94 );
        gp.add( message, 0, 95 );
        gp.add( receiver, 0, 96 );
        gp.add( receiverName, 0, 97 );
        gp.add( btnSend, 0, 98 );
        gp.add( errorLabel, 0, 99);
        gp.add( btnDisconnect, 0, 101 );

        HBox hbox = new HBox(gp);
        hbox.setAlignment(CENTER);

        VBox vbox = new VBox(hbox);
        vbox.setAlignment(CENTER);

        return vbox;
    }

}
