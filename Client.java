import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



class Client
{
  //declaring swing objects
  static JLabel statusLabel; 
  static JFrame mainFrame;
  static JTextArea text;
  static JTextArea sendfld,ipadd,serverport;
  static JLabel header,sendbox,chatlabel,iplabel,portlabel;
  static void swingWorkerSample () 
  { 
    mainFrame = new JFrame("Chatbox");
    //terminate proram when window is closed 
    mainFrame.addWindowListener(new WindowAdapter() 
    { 

      @Override
      public void windowClosing(WindowEvent e) 
      { 
        System.exit(0); 
      } 
      
    }); 
     
    iplabel=new JLabel("IP Add:");
    portlabel=new JLabel("Port:");
    header=new JLabel("Chatbox");
    
    header.setFont(new Font("Courier New", Font.BOLD, 22));
    header.setBounds(280,10,100,40);
    chatlabel=new JLabel("Chats");
    chatlabel.setFont(new Font("Courier New", Font.ITALIC, 12));
    chatlabel.setBounds(30,100,100,40);
    sendbox=new JLabel("Write to send");
    sendbox.setFont(new Font("Courier New", Font.ITALIC, 12));
    sendbox.setBounds(30,330,100,40);
    
    //text area for displaying chats
    text = new JTextArea();
    //text area for ip address
    ipadd = new JTextArea();
    //text area for port
    serverport=new JTextArea();
    
    JScrollPane scrollPane = new JScrollPane(text);
    scrollPane.setBounds(30,130,540,200);
    //scrollPane.setVerticalScrollBarPolicy(
      //          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    //scrollPane.setPreferredSize(new Dimension(540, 200));
    text.setEditable(false);
    //text area for message to be sent
    sendfld=new JTextArea();
    //setting bounds
       sendfld.setBounds(30,360,540,50);
       ipadd.setBounds(140,60,100,20);
       iplabel.setBounds(30,60,100,20);
       serverport.setBounds(140,90,100,20);
       portlabel.setBounds(30,90,100,20);
    //setting fonts
    text.setFont(new Font("Times New Roman",Font.PLAIN,14));
    sendfld.setFont(new Font("Times New Roman",Font.PLAIN,14));
    //creating send button
    JButton btn = new JButton("Send"); 
    btn.setBounds(265,420,105,30); 
    
    //defining what happens if send buttton is clicked
    btn.addActionListener(new ActionListener() 
    { 

      @Override
      public void actionPerformed(ActionEvent e) 
      { 
        System.out.println("Button clicked, message sent"); 
        send(); 
      } 
      
    }); 
    //adding components to mainFrame
    mainFrame.add(btn); 
    //mainFrame.add(text);
    mainFrame.add(sendfld);
    mainFrame.add(header);
    mainFrame.add(chatlabel);
    mainFrame.add(sendbox);
    mainFrame.add(scrollPane);
    mainFrame.add(ipadd);
    mainFrame.add(serverport);
    mainFrame.add(iplabel);
    mainFrame.add(portlabel);
    //setting the size
    mainFrame.setSize(600,530);
    mainFrame.setLayout(null);
    mainFrame.setVisible(true); 
    
  }




static class Rec_thread extends Thread
  {
    public void run()
    {
    
      while(true)
      {
        byte[] receiveData = new byte[1024];
        try
        {
          DatagramSocket clientSocket_receive = new DatagramSocket(7800);
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket_receive.receive(receivePacket);
          //modifiedSentence stores the received message
          String modifiedSentence = new String(receivePacket.getData());
          
          int i;
          //computing the length of string received
          for(i=0;i<modifiedSentence.length();i++)
          {
            if((int)modifiedSentence.charAt(i)==0)
              break;
          }
          //Creating a string for the message to be displayed
          String disp_sentence="";
          for(int j=0;j<i;j++)
          {
              disp_sentence+=modifiedSentence.charAt(j);
          }
          //displaying the received message
          text.setText(text.getText()+"                                                  Received::"+disp_sentence+'\n');
        }
        catch(Exception e)
        {}
        
      }
    }
  }
  public static void send()
  {
    BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
    try
    {
      DatagramSocket clientSocket_send = new DatagramSocket();
      //ipadress of friends machine from text area
        InetAddress IPAddress = InetAddress.getByName(ipadd.getText());
        byte[] sendData = new byte[1024];
        {
          //message from the sendfld
          String sentence = sendfld.getText()+'\n';
          //clearing the sendfld after storing message in sentence
          sendfld.setText("");
          //updating text(chats)
          text.setText(text.getText()+"Sent::"+sentence);
          sendData = sentence.getBytes();
          //port from port text area
          int port = Integer.parseInt(serverport.getText());
          //sending the message
         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
         clientSocket_send.send(sendPacket);
        }
    }
    catch(Exception e){}
   }
   
   public static void main(String args[]) throws Exception
   {
      Send_thread st=new Send_thread();
      Rec_thread rt=new Rec_thread();
      rt.start(); 
      swingWorkerSample(); 
    }
      
}