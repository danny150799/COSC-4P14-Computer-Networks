import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * Danny Nguyen
 * 6334502
 * dn17hg
 * COSC 4P14 Lab 2 question 4
 * Server side
 *
 * This class represents the server side of the ping program using UDP socket. The server side receives a message from
 * client, reply back with the same content of the received message and print out the received message. The server might
 * or might not send out the message based a random number.
 *
 * RUN THIS FIRST THAN THE CLIENT
 */

public class server {
    final int PORT = 12345;                         //Port #
    final double LOST = 0.2;                        //Lost rate
    final int BSIZE = 512;
    final long LOST_TIME = 1000;
    final long MIN_DELAY = 1;
    final long MAX_DELAY = 999;
    DatagramSocket socket;                          //UDP socket

    public server() throws IOException{
        socket = new DatagramSocket(PORT);
        System.out.println("Start listening...");

        while(true){
            byte[] buffer = new byte[BSIZE];
            DatagramPacket dPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(dPacket);                                            //Receives the message
            String message = new String(dPacket.getData()).trim();
            System.out.println("Client sent: " + message);
            double random = Math.random();                                      //Generate random number between 0 and 1

            if(random<LOST){                                                    //Won't send if less than LOST
                try {
                    TimeUnit.MILLISECONDS.sleep(LOST_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Not sending " + message);
            }else {
                try {
                    TimeUnit.MILLISECONDS.sleep((int)(Math.random()*MAX_DELAY +MIN_DELAY));   //Delay the message between 1ms and 999ms
                    System.out.println("Delay the message " + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                InetAddress address = dPacket.getAddress();                     //Get the IP address of the received packet
                int cPort = dPacket.getPort();                                  //Get the Port # of the received packet
                DatagramPacket sPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, address, cPort);
                socket.send(sPacket);                                           //Send a reply message
            }
        }
    }
    public static void main (String[] args) throws IOException{ server a = new server();}
}
