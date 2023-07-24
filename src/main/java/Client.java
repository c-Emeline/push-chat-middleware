import connection.Connection;
import emitter.Emitter;
import emitter.EmitterImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import msgapplication.MsgApplication;
import receiver.Receiver;
import receiver.ReceiverImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

public class Client extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(MsgApplication.createConnectionPane(primaryStage), 300, 300));
		primaryStage.setTitle("Connection");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

	//old version without interface
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection myComponent;
		try {
			//example of a RMI URL used to retrieve a remote reference
			myComponent = (Connection) Naming.lookup("rmi://localhost:1099/Connection");

			Receiver titiRcv = new ReceiverImpl("Titi");
			Emitter titiEmt = myComponent.connect("Titi", titiRcv);

			ReceiverImpl tutuRcv = new ReceiverImpl("Tutu");
			Emitter tutuEmt = myComponent.connect("Tutu", tutuRcv);

			tutuEmt.sendMessage("Titi", "Salut !");
			titiEmt.sendMessage("Tutu", "Heyyy <]:D");



		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}

	}*/

}
