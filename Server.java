import sun.jvm.hotspot.runtime.Thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int port = 9090;
    ServerSocket server;
    InputStream fromClientStream;
    OutputStream toClientStream;
    DataInputStream reader;
    PrintWriter writer;
    String response;

    boolean isInt(String ops[]){
        for (int i = 1; i < ops.length; i++) {
            for (int j = 0; j < ops[i].length(); j++) {
                if (!Character.isDigit(ops[i].charAt(j)))
                    return false;
            }
        }
        return true;
    }
    void add(String op1, String op2){
         response = "" + (Integer.parseInt(op1) + Integer.parseInt(op2));
    }
    void subtract(String op1, String op2){
        response = "" + (Integer.parseInt(op1) - Integer.parseInt(op2));
    }
    void mul(String op1, String op2){
        response = "" + (Integer.parseInt(op1) * Integer.parseInt(op2));
    }
    void divide(String op1, String op2){
        response = "" + (Integer.parseInt(op1) / Integer.parseInt(op2));
    }
    void cos(String op1){
        response = "" + Math.cos(Integer.parseInt(op1));
    }
    void sin(String op1){
        response = "" + Math.sin(Integer.parseInt(op1));
    }
    void tan(String op1){
        response = "" + Math.tan(Integer.parseInt(op1));
    }
    void cot(String op1){
       response = "" +  (1/ Math.tan(Integer.parseInt(op1)));
    }



    public Server() {
        try {
            server = new ServerSocket(port);
            Socket client = server.accept();


            // input stream (stream from client)
            fromClientStream = client.getInputStream();

            // output stream (stream to client)
            toClientStream = client.getOutputStream();

            reader = new DataInputStream(fromClientStream);
            writer = new PrintWriter(toClientStream, true);

            // Receive client request
            String request = reader.readLine();
            long t1 = System.nanoTime();
            String[] ops1 = request.split("\\$");
            String[] ops = new String[ops1.length - 1];
            for (int i = 0; i < ops.length; i++) {
                ops[i] = ops1[i+1];
            }

            if (ops.length > 3 || ops.length < 2 || !isInt(ops))
                response = "Wrong request!";

            else if (ops.length == 3) {
                switch (ops[0]){
                    case  "Add":
                        add(ops[1], ops[2]);
                        break;
                    case "Subtract":
                        subtract(ops[1], ops[2]);
                        break;
                    case "Divide":
                        if (ops[2].equals(" "))
                            response = "Can not divide!";
                        else
                            divide(ops[1], ops[2]);
                        break;
                    case "Multiply":
                        mul(ops[1], ops[2]);
                        break;
                    default: {
                        response = "Wrong operator!";
                        break;
                    }
                }
            }

            else {
                switch (ops[0]){
                    case "Sin":
                        sin(ops[1]);
                        break;
                    case "Cos":
                        cos(ops[1]);
                        break;
                    case "Tan":
                        tan(ops[1]);
                        break;
                    case "Cot":
                        cot(ops[1]);
                        break;
                    default:
                        response = "Wrong operator!";
                        break;
                }
            }

            long t2 = System.nanoTime();
            System.out.println(t2-t1);
            // send result to client
            writer.println("$ "+ (t2 - t1) + " ns $ " + response + " $");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
         new Server();
    }
}
