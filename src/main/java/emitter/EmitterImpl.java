package emitter;

import javafx.scene.layout.GridPane;
import receiver.Receiver;
import receiver.ReceiverImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmitterImpl extends UnicastRemoteObject implements Emitter{

    String clientName;
    Receiver receiver;


    public EmitterImpl(String clientName, Receiver receiver) throws RemoteException {
        super();
        this.clientName = clientName;
        this.receiver = receiver;
    }

    @Override
    public void sendMessage(String to, String message) throws RemoteException {
        if (receiver.getClients().contains(to)){
            this.receiver.receive(this.clientName, message);
        }
        else {
            receiver.printError("User "+to+" is disconnected");
        }
    }

    public String getClientName() throws RemoteException {
        return clientName;
    }

}
