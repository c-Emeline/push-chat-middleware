package receiver;

import connection.ConnectionImpl;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import msgapplication.MsgApplication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {

	private ArrayList<String> clients;
	private String clientName;
	private ArrayList<ArrayList<String>> messagesReceived = new ArrayList<>();
	private ArrayList<Label> messageLabels;

	public ReceiverImpl(String clientName) throws RemoteException {
		super();
		this.clientName = clientName;
	}


	@Override
	public void receive(String from, String message) throws RemoteException {
		this.messagesReceived.add(new ArrayList<String>(Arrays.asList(from, message)));
		System.out.println(from+" : "+message);
		int size = messageLabels.size();
		if (this.messagesReceived.size()<messageLabels.size()){
			size = messagesReceived.size();
		}
		for (int i=0;i<size;i++){
			messageLabels.get(i).setText(messagesReceived.get(i).get(0)+" : "+messagesReceived.get(i).get(1));
		}
	}

	@Override
	public void initClients(ArrayList<String> clients) throws RemoteException {
		this.clients = clients;
}

	@Override
	public void addClient(String client) throws RemoteException {
		this.clients.add(client);
	}

	@Override
	public void remClient(String client) throws RemoteException {
		this.clients.remove(client);
	}

	public String getClientName() throws RemoteException {
		return this.clientName;
	}

	public ArrayList<String> getClients() throws RemoteException {
		return this.clients;
	}

	public void printError(String error) throws RemoteException {
		System.out.println(error);
	}

	public void setMessageLabels(ArrayList<Label> messageLabels){
		this.messageLabels = messageLabels;
	}
}