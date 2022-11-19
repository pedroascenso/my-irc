package silva.ascenso.pedro;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private ServerSocket serverSocket;
    private List<ChatClient> clientList;
    private ExecutorService threadPool;
    private int port;

    /**
     * Initialize the ChatServer
     * @param port server port
     * @param threadNum number of threads in thread pool
     */
    public ChatServer(int port, int threadNum){
        this.port = port;
        threadPool = Executors.newFixedThreadPool(threadNum);
        clientList = Collections.synchronizedList(new LinkedList<>());
    }
    /**
     * Server is waiting for client connections
     */
    public void listen(){
        try {
            serverSocket = new ServerSocket(this.port);
            while(true){
                ChatClient client = new ChatClient(serverSocket.accept(), this);
                addClient(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Broadcast the message through all the connected clients
     * @param message message to be broadcasted
     * @param sender client that send the message
     */
    public void broadcast(String message, ChatClient sender){
        for (ChatClient receiver : clientList) {
            if (sender != receiver){
                receiver.getOutputClient().println(message);
            }
        }
    }
    /**
     * Builds a string with all the users
     * @return
     */
    public String usernameList(){

        StringBuilder users = new StringBuilder();

        for (ChatClient chatClient : clientList) {
            users.append(chatClient.getUsername()+", ");
        }
        return users.toString();
    }
    /**
     * Send a direct message from a client to another
     * @param user client who is sending a message
     * @param userWhisper client who is receiving a message
     * @param message message that is being sent
     */
    public void whisper(ChatClient user, String userWhisper, String message){

        for (ChatClient chatClient : clientList) {

            if(chatClient.getUsername().equals(userWhisper)){
                chatClient.getOutputClient().println(user.getUsername()+" whispered you: "+message);
            }
        }
    }
    /**
     * Add the client to client list and submit it to the thread pool
     * @param client the client to be connected
     */
    public void addClient(ChatClient client){
        clientList.add(client);
        System.out.println(client.getUsername() + ": entered MyIRC");
        //broadcast(client.getUsername() + ": entered MyIRC",client);
        threadPool.submit(client);
    }
    /**
     * Remove the client from the client list and close his socket
     * @param client the client to be disconnected
     */
    public void closeClient(ChatClient client){
        try {
            clientList.remove(client);
            System.out.println(client.getUsername() + ": exited MyIRC");
            broadcast(client.getUsername() + ": exited MyIRC",client);
            client.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ChatServer irc = new ChatServer(6666,100);
        irc.listen();
    }

}
