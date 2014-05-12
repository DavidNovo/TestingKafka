package net.novogrodsky.connection;

import java.io.*;
import java.net.Socket;

/**
 * Created by david.j.novogrodsky on 5/6/2014.
 */
public class TCPServerSocket {
    private static final String AGENT_STRING = "NTRIP";
    private static final int HTTP_PORT = 2101;
    private static final String HOST_NAME = "www.euref-ip.net";
    private static final String MOUNT_POINT = "SYNDN0";
    private static final String USER_NAME = "USER96";
    private static final String USER_PASS = "hfjfkp";

    Socket clientSocket;
    DataOutputStream outToServer;
    BufferedReader inFromServer;
    String encryptedAccess;


    public static void main(String args[]) throws Exception {
        TCPServerSocket newSocket = new TCPServerSocket();
        OutputStream toConsole = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
        newSocket.streamData(toConsole);
    }

    /**
     * This method will retrieve the source table from the Ntrip castor.
     */
    public String getSourceTable() {

        try {
            clientSocket = new Socket(HOST_NAME, HTTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("problem opening socket getSourceTable");
        }
        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("problem getting data output stream getSourceTable");
        }

        try {
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Request data from webserver
        try {
            outToServer.writeBytes("GET /" + MOUNT_POINT + " HTTP/1.0\r\n"
                    + "User-Agent: NTRIP \r\n"
                    + "Authorization: Basic " + encryptedAccess + "\r\n\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Read first line from webserver's reply
        String reply;
        reply = null;

        StringBuilder results = new StringBuilder();

        try {
            reply = inFromServer.readLine();
            results.append(inFromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Recurse until no lines are left (Never happens - infinite loop)
        while (reply != null) {
            System.out.println(reply);
            try {
                results.append(inFromServer.readLine());
                reply = inFromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            outToServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inFromServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.toString();
    }

    /**
     * This method will stream data to the Ntrip server.
     */
    public void streamData(OutputStream serialOut) {
        try {

            // Create TCP/IP socket
            clientSocket = new Socket(HOST_NAME, HTTP_PORT);

            // Create output stream to webserver
            outToServer = new DataOutputStream(clientSocket.
                    getOutputStream());

            // Create input stream to webserver
            inFromServer = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));

            // Encode username and password with Base64
            //   encryptedAccess = Base64.encode(USER_NAME + USER_PASS);
            encryptedAccess = "dXNlcjk2OmhmamZrcA==";

            // Request data from webserver
            outToServer.writeBytes("GET /" + MOUNT_POINT + " HTTP/1.0\r\n"
                    + "User-Agent: NTRIP NtripLinuxClient/1.9\r\n"
                    + "Authorization: Basic " + encryptedAccess + "\r\n\r\n");


            // Read first line from webserver's reply
            String reply = inFromServer.readLine();

            // Recurse until no lines are left (Never happens - infinite loop)
            while (reply != null) {
                System.out.println(reply);

                // Write line to given output stream (eg. serial port)
                //serialOut.write((reply + "\r\n").getBytes());

                // Next line
                reply = inFromServer.readLine();
            }

            serialOut.close();
            outToServer.close();
            inFromServer.close();
            clientSocket.close();

        } catch (Exception e) {
            System.out.println("Exception @ getting NTrip data stream");
        }
    }

}

