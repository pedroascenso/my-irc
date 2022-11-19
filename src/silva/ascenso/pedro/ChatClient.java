package silva.ascenso.pedro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements Runnable {

    private BufferedReader inputClient;
    private PrintWriter outputClient;
    private Socket clientSocket;
    private ChatServer chatServer;
    private String username;

    public ChatClient(Socket clientSocket, ChatServer chatServer) {

        try {
            this.clientSocket = clientSocket;
            this.chatServer = chatServer;
            inputClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputClient = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public PrintWriter getOutputClient() {
        return outputClient;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        try {
            outputClient.println("Welcome to MyIRC, what's your username?");
            username = inputClient.readLine();
            outputClient.println("Type \"/help\" to know your commands ");
            chatServer.broadcast(this.getUsername() + ": entered MyIRC",this);

            while (true) {

                String message = inputClient.readLine();

                if (message.equals("/quit")) {
                    chatServer.closeClient(this);
                    return;
                }
                if (message.equals("/help")) {
                    outputClient.println("Type: \"/users\" to know all users connected");
                    outputClient.println("Type: \"/username\" to change your username");
                    outputClient.println("Type: \"/whisper username\" to whisper someone");
                    outputClient.println("Type: \"/quit\" to exit MyIRC");
                    continue;
                }
                if (message.startsWith("/whisper")) {
                    String userWhisper = message.split(" ")[1];
                    outputClient.println("Whisper to: " + userWhisper);
                    String whisper = inputClient.readLine();
                    chatServer.whisper(this, userWhisper, whisper);
                    continue;
                }
                if (message.equals("/username")) {
                    outputClient.println("What's your new username?");
                    username = inputClient.readLine();
                    continue;
                }
                if (message.equals("/users")) {
                    outputClient.println(chatServer.usernameList());
                    continue;
                }

                chatServer.broadcast(this.username + ": " + message, this);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
