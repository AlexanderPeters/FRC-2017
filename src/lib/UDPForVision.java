package lib;

import java.io.IOException;
import java.net.*;

import main.Constants;

public class UDPForVision implements Constants {
	DatagramSocket serverSocket;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	private String[] values;
	private double Time, CamNum, Range, Bearing, Elevation;
	private boolean TargetFound = false;
	
	public UDPForVision() {
		
		try {
			serverSocket = new DatagramSocket(udpPortForComms);
		} catch (SocketException e) {
			System.out.println("Failed to instantiate the server socket.");
			e.printStackTrace();
		}
		
	}

	public void poke() {
		try {
			serverSocket.setSoTimeout(10);
		} catch (SocketException e) {
			System.out.println("Failed to set timeout.");
			e.printStackTrace();
		}

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData()); 
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
		
			int n=12345; //Set this to the id of the camera you want....0 is high target camera, 1 is low target camera (IDK why)
			sendData = String.valueOf(n).getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("SENT: " + String.valueOf(n));
			String corrected = sentence.replaceAll("\u0000.*", "");
			values = corrected.split(",");
			
			Time = Double.parseDouble(values[0]);
			CamNum = Double.parseDouble(values[1]);
			TargetFound = Boolean.parseBoolean(values[2]);
			Range = Double.parseDouble(values[3]);
			Bearing = Double.parseDouble(values[4]);
			Elevation = Double.parseDouble(values[5]);
			System.out.println("Time is " + Time);
			System.out.println("CamNum is " + CamNum);
			System.out.println("TargetFound " + TargetFound);
			System.out.println("Range is " + Range);
			System.out.println("Bearing is " + Bearing);
			System.out.println("Elevation is " + Elevation);
		} catch (IOException e) {
			//System.out.println("Nothing to recieve from comms.");
		}
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
