package connection;

import emitter.Emitter;
import receiver.Receiver;
import receiver.ReceiverImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    Emitter connect(String nickname, Receiver rcv) throws RemoteException;
    void disconnect(String nickname) throws RemoteException;
}
