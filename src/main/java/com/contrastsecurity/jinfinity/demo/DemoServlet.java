package com.contrastsecurity.jinfinity.demo;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DemoServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletInputStream inputStream = req.getInputStream();
		try {
			System.out.println("Reading message..");
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			String s = (String)ois.readObject();
			System.out.println("Message was: " + s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
