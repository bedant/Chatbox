import java.io.*;
import java.net.*;

class Server{
   public static void main(String args[]) throws Exception
      {
         DatagramSocket serverSocket_receive = new DatagramSocket(5600);
         DatagramSocket serverSocket_send = new DatagramSocket();
            
            
            while(true)
               {
                  byte[] receiveData = new byte[1024];
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  //receive the message from different ip address
                  serverSocket_receive.receive(receivePacket);
                  String sentence = new String( receivePacket.getData());
                  System.out.println("RECEIVED: " + sentence);
                  //ip address of my machine
                  InetAddress IPAddress = InetAddress.getByName("localhost");
                  byte[] sendData = new byte[1024];
                  sendData = sentence.getBytes();
                  DatagramPacket sendPacket =
                  new DatagramPacket(sendData, sendData.length, IPAddress, 7800);
                  //sending the message to client on the same machine 
                  serverSocket_send.send(sendPacket);
               }
      }
}