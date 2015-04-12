package edu.utas.kit418.pengdu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyMathServiceClient {

	private static DataOutputStream cout;
	private static DataInputStream cin;

	public static void main(String[] args) {
		ArgsPaser ap = new ArgsPaser();
		if (!ap.parser(args)) {
			ap.printHelp();
			System.exit(1);
		}

		String ip = "127.0.0.1";
		int port = 4444;
		String query = "sqrt,0";

		if (ap.amountOfArgs == 4) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
			if (args[2].equals("prime") && args[3].indexOf('.') != -1)
				args[3] = args[3].substring(0, args[3].indexOf('.'));
			query = String.join(",", args[2], args[3]);
		}

		if (ap.amountOfArgs == 3) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
			query = String.join(args[2], ",0");
		}
		if (ap.amountOfArgs == 2) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		}
		if (ap.amountOfArgs == 1) {
			ip = args[0];
		}

		Socket c;
		long start;
		long end;
			try {
				c = new Socket(ip, port);
				cout = new DataOutputStream(c.getOutputStream());
				cin = new DataInputStream(c.getInputStream());
				start = System.currentTimeMillis();
				cout.writeUTF(query);
				System.out.println();
				String result = cin.readUTF();
				end = System.currentTimeMillis();
				System.out.println("Query " + query
						+ " completed successfully, result: " + result+", time eplased: "+ (end - start)+" ms");
				c.close();
			} catch (UnknownHostException e) {
				printError(e.getMessage());
			} catch (IOException e) {
				printError(e.getMessage());
			}

	}

	public static void printError(String msg) {
		printInfo("Error: " + msg);
	}

	public static void printInfo(String msg) {
		System.out.println(msg);
	}

	public static void printUsage(String msg) {
		printInfo("Usage: " + msg);
	}

}
