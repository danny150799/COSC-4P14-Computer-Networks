import java.io.IOException;
import java.net.*;

/**
 * Danny Nguyen
 * 6334502
 * dn17hg
 * COSC 4P14 Lab 2 question 4
 * Client side
 *
 * This class represents the client side of the ping program using UDP socket. The client sends a message which is the
 * PING+#, receives a reply message from server in return and print out the Round Trip Time in ms.
 *
 * RUN THIS AFTER RUNNING THE SERVER
 */
public class client {
    final int PORT = 12345;                         //Port #
    final int TIMEOUT = 1000;                       //Timeout is 1s, after 1s the packet is considered lost.
    final int BSIZE = 512;
    DatagramSocket socket;                          //UDP socket
    InetAddress address;                            //IP Address

    public client() throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getLocalHost();
        System.out.println("Start sending pings.");

        String ping = "PING";

        for(int i = 1; i<=10; i++){                  //Send and receive 10 messages
            String temPing = ping + i ;
            DatagramPacket dPacket = new DatagramPacket(temPing.getBytes(), temPing.getBytes().length, address, PORT);
            socket.send(dPacket);                    //Send the message

            long str, end;
            str = System.currentTimeMillis();
            socket.setSoTimeout(TIMEOUT);            //After 1s, it will give a SocketTimeoutException
            try {
                byte[] buffer = new byte[BSIZE];
                DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(rPacket);              //Receive reply message from server

                end = System.currentTimeMillis();
                long rtt = end - str;                 //Calculate the Round Trip Time
                System.out.println(temPing + "'s RTT is " + rtt + "ms");    //Print out the successful packet and its RTT
            } catch (Exception e) {
                System.out.println(temPing + " is lost.");                  //Print out packet lost
            }
        }
            socket.close();                             //Close socket after 10 messages
        }

    public static void main (String[] args) throws Exception{
        client a = new client();
    }
}