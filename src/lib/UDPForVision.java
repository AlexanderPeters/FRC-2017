package lib;

import java.io.IOException;
import java.net.*;

import main.Constants;

public class UDPForVision implements Constants {
	
	DatagramSocket serverSocket;
	byte[] receiveData;
	byte[] sendData;
	
	private double Time, CamNum, Range, Bearing, Elevation;
	private boolean TargetFound = false;


	/*public DatagramSocket serverSocket;
	public byte[] receiveData = new byte[1024];
	private String[] values;
	private double Time, CamNum, Range, Bearing, Elevation;
	private boolean TargetFound = false;
	public DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	//amos code below:
	public DatagramSocket sendSocket;
	public byte[] sendData = new byte[1024];
	public DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);*/
	
	public UDPForVision() throws SocketException {
		
		serverSocket = new DatagramSocket(5802);
		receiveData = new byte[1024];
		sendData = new byte[1024];
		/*try {
			serverSocket = new DatagramSocket(udpPort);
			sendSocket = new DatagramSocket(udpPortForLogging);
		} catch (SocketException e) {
			//e.printStackTrace(); //was adding a bunch of noise to the print statements
			System.out.println("Could not create socket");
		}*/
	}

	public void poke() throws IOException {
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
		String sentence = new String(receivePacket.getData()); 
		System.out.println("RECEIVED: " + sentence);
		InetAddress IPAddress = receivePacket.getAddress();
		int port = receivePacket.getPort();
	
		int n=12345; //Set this to the id of the camera you want....0 is high target camera, 1 is low target camera (IDK why)
		sendData = String.valueOf(n).getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		serverSocket.send(sendPacket);
		System.out.println("SENT: " + String.valueOf(n));
		/*serverSocket.receive(receivePacket);
		String sentence = new String(receivePacket.getData());
		InetAddress IPAddress = InetAddress.getByName("10.4.13.19");
		int n = 12345;
		sendData=String.valueOf(n).getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,udpPortForLogging);
		sendSocket.send(sendPacket);
		System.out.println("sent: "+String.valueOf(n));
		String corrected = sentence.replaceAll("\u0000.*", "");
		values = corrected.split(",");
		
		//if(values != null) {
			Time = Double.parseDouble(values[0]);
			CamNum = Double.parseDouble(values[1]);
			TargetFound = Boolean.parseBoolean(values[2]);
			Range = Double.parseDouble(values[3]);
			Bearing = Double.parseDouble(values[4]);
			Elevation = Double.parseDouble(values[5]);
			//Add an if statement to prevent out of bounds exceptions when values[x] is called
			System.out.println("Time is " + Time);
			System.out.println("CamNum is " + CamNum);
			System.out.println("TargetFound " + TargetFound);
			System.out.println("Range is " + Range);
			System.out.println("Bearing is " + Bearing);
			System.out.println("Elevation is " + Elevation);
		//}
*/
	}
	
	public double getBearing() {
		return Bearing;
	}
	public double getRange() {
		return Range;
	}
	public boolean getTargetFound() {
		return TargetFound;
	}
}
