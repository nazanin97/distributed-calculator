import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket socket;
    int port = 9090;
    String ipAdd = "127.0.0.1";
    InputStream fromServerStream;
    OutputStream toServerStream;
    DataInputStream reader;
    PrintWriter writer;

    public Client(){
        System.out.println("Enter your calculation request");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            socket = new Socket(ipAdd, port);

            fromServerStream = socket.getInputStream();
            toServerStream = socket.getOutputStream();
            reader = new DataInputStream(fromServerStream);
            writer = new PrintWriter(toServerStream, true);

            // send message to server
            writer.println(input);

            // read server message
            String result = reader.readLine();
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        new Client();
    }

}
