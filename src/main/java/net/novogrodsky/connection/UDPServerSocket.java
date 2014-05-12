package net.novogrodsky.connection;

/**
 * Created by david.j.novogrodsky on 5/6/2014.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerSocket {


    public static void main(String args[]) throws Exception {
        UDPServerSocket testUDPSocket = new UDPServerSocket();
        testUDPSocket.getInformation();
    }

    public static void getInformation() throws IOException {
        String AGENT_STRING = "NTRIP";
        int PORT = 2101;
        String HOST_NAME = "www.euref-ip.net";
        String HOST_NAME2 = "www.google.com";
        int PORT2 = 8080;
        String MOUNT_POINT = "SYNDN0";
        String USER_NAME = "USER96";
        String USER_PASS = "hfjfkp";

        System.out.println("hello");
        DatagramSocket clientSocket = new DatagramSocket();

        InetAddress IPAddress;
        IPAddress = InetAddress.getByName(HOST_NAME2);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String sentence = "hello";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket;
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT2);
        System.out.println("***" + sendPacket.toString());
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

        System.out.println(receivePacket.getData());

        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("***FROM SERVER:" + modifiedSentence);

        clientSocket.close();
    }
}
