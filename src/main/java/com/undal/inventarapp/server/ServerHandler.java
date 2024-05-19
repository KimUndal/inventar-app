package com.undal.inventarapp.server;

import com.undal.inventarapp.shared.util.RetryCommand;
import com.undal.inventarapp.shared.util.RetryCommandImpl;
import com.undal.inventarapp.shared.util.Util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerHandler {
    private ServerSocket serverSocket;
    private int port = Util.SERVER_PORT;
    public void start(){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Connecting to port: " + port +"...");

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter outMsg;
        private BufferedReader inMsg;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        public void run(){

                try {
                    initializeConnection();
                    communicateWithClient();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        closeConnection();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        }

        private void closeConnection() throws IOException {
            if (inMsg != null) {
                inMsg.close();
            }
            if (outMsg != null) {
                outMsg.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        }

        private void communicateWithClient() throws IOException {
            String message;
            while ((message = inMsg.readLine()) != null) {
                System.out.println("Received message from client: " + message);
                outMsg.println("Server received message: " + message);
            }
        }

        private void initializeConnection() throws IOException {
            outMsg = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            inMsg = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
    }
}
