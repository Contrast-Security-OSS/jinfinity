package com.contrastsecurity.jinfinity.demo;


public class Launcher {

	private static final String CLIENT = "client";
	private static final String SERVER = "server";

	public static void main(String[] args) throws Exception {
		
		String command = null;
		if(args.length != 1 || (command=validateCommand(args[0])) == null) {
			printUsage();
			System.exit(-1);
		}
		
		if(SERVER.equals(command)) {
			new DemoServer().runServer();
		} else { // CLIENT
			new DemoClient().runAttack();
		}
		
	}

	private static String validateCommand(String command) {
		if(SERVER.equalsIgnoreCase(command) || CLIENT.equalsIgnoreCase(command)) {
			return command;
		}
		return null;
	}

	private static void printUsage() {
		System.out.println("[jinfinity - death by a thousand Java bites] https://github.com/Contrast-Security-OSS/jinfinity");
		System.out.println("  To run the server for the demo, run:  java -jar jinfinity.jar server");
		System.out.println("  To run the client for the demo, run:  java -jar jinfinity.jar client");
	}
	
}
