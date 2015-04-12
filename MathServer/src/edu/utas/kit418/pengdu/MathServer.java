package edu.utas.kit418.pengdu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

import edu.utas.kit418.pengdu.gui.MathServerGUI;

public class MathServer {
	public static int listeningPort = 4444;
	public static final String PORT_PATTERN = "^([1-5][0-9][0-9][0-9][0-9]|6[0-5][0-5][0-3][0-5]|102[4-9]|10[3-9][0-9]|1[1-9][0-9][0-9]|[2-9][0-9][0-9][0-9])$";
	private static MathServerGUI gui;
	public static String newline = System.getProperty("line.separator");
	private static MathServerThread mainThread;
	public static String stopCode;

	public static void main(String[] args) {
		if (args.length >= 1) {
			if (!args[0].matches(PORT_PATTERN)) {
				printHelp();
				System.exit(1);
			} else {
				listeningPort = Integer.parseInt(args[0]);
			}
		}
		gui = new MathServerGUI();
		gui.setVisible(true);

		generateStopCode();
		startMathServerThread();
	}

	private static void generateStopCode() {
		stopCode = UUID.randomUUID().toString();
	}

	private static void startMathServerThread() {
		mainThread = new MathServerThread();
		mainThread.setServerStateListener(gui);
		mainThread.execute();
	}

	public static void printInfo(String info) {
		System.out.println(info);
		gui.log(info + newline);
	}

	private static void printHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append(newline);
		sb.append("Error: <port> must be a integer ranging from 1024 to 65535");
		sb.append(newline);
		sb.append("Usage: java MathServer <port>");
		sb.append(newline);
		System.out.println(sb.toString());
		gui.log(sb.toString());
	}

	public static void stopServer() {
		mainThread.attemptStopService();
		try {
			Socket s = new Socket(InetAddress.getLoopbackAddress(),
					listeningPort);
			new DataOutputStream(s.getOutputStream()).writeUTF(stopCode);
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void startServer(int port) {
		listeningPort = port;
		startMathServerThread();
	}

}
