package connection;

import emitter.Emitter;
import emitter.EmitterImpl;
import receiver.Receiver;
import receiver.ReceiverImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ConnectionImpl extends UnicastRemoteObject implements Connection {

    private ArrayList<String> clientsName = new ArrayList<>();
    private ArrayList<EmitterImpl> emitters = new ArrayList<>();
    private ArrayList<Receiver> receivers = new ArrayList<>();

    public ConnectionImpl() throws RemoteException {
        super();
    }

    @Override
    public Emitter connect(String nickname, Receiver rcv) throws RemoteException {

        EmitterImpl clientEmt = null;

        //Client not connected
        if (!clientsName.contains(nickname)) {
            clientsName.add(nickname);

            clientEmt = new EmitterImpl(nickname, rcv);
            emitters.add(clientEmt);

            rcv.initClients(clientsName);
            for (Receiver r : receivers) {
                r.addClient(nickname);
            }
            receivers.add(rcv);

            System.out.println("User " + nickname + " connected !");
        }

        //If client already connected, return its emitter
        else {
            for (EmitterImpl em : emitters){
                if (em.getClientName().equals(nickname)){
                    clientEmt = em;
                }
            }
        }

        return clientEmt;
    }

    @Override
    public void disconnect(String nickname) throws RemoteException {
        for (EmitterImpl em : emitters){
            if (em.getClientName().equals(nickname)){
                UnicastRemoteObject.unexportObject(em, true);
                emitters.remove(em);
                clientsName.remove(nickname);
            }
        }

        //Actualization of clients for the receivers
        for (Receiver rcv : receivers){
            if (rcv.getClientName().equals(nickname)){
                UnicastRemoteObject.unexportObject(rcv, true);
                receivers.remove(rcv);
                clientsName.remove(nickname);
            }
            rcv.remClient(nickname);
        }

        System.out.println("User "+nickname+" disconnected !");
    }

    public ArrayList<String> getClientsName() {
        return clientsName;
    }

    public ArrayList<EmitterImpl> getDialogues() {
        return emitters;
    }
}
