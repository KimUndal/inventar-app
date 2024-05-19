package com.undal.inventarapp.client;

import com.undal.inventarapp.shared.util.RetryCommand;
import com.undal.inventarapp.shared.util.RetryCommandImpl;
import com.undal.inventarapp.shared.util.Util;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private Socket clientSocket;
    private PrintWriter outMsg;
    private BufferedReader inMsg;

    public void startConnection(){
        RetryCommand retryCommand = new RetryCommandImpl(3, 3000, ()->{
            try {
                clientSocket = new Socket("Localhost", Util.SERVER_PORT);
                outMsg = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                inMsg = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                new Thread(new ServerListener()).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            retryCommand.execute();
        } catch (Exception e) {
            System.err.println("Failed to establish connection after retries: " + e.getMessage());
        }
    }

    public void sendMessage(String msg){
        outMsg.println(msg);
    }
    public void stopConnection() {
        try {
            inMsg.close();
            outMsg.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private class ServerListener implements Runnable {
        public void run() {
            try {
                String response;
                while ((response = inMsg.readLine()) != null) {
                    System.out.println("Received from server: " + response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ClientHandler client = new ClientHandler();
        try {
            client.startConnection();
            client.sendMessage("Hello Server");
            client.sendMessage("How are you?");
            client.sendMessage("exit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
