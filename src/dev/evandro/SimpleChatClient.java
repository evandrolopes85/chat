package dev.evandro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleChatClient {
	// Scanner teclado = new Scanner(System.in);

	//ArrayList<String> incoming;
	Scanner outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;

	public static void main(String[] args) {
		SimpleChatClient client = new SimpleChatClient();
		client.go();
	}

	public void go() {
		// TODO Auto-generated method stub
		outgoing = new Scanner(System.in);
		setUpNetworkin();

		Thread readerTheader = new Thread(new IncomingReader());
		readerTheader.start();
		
		while (true) {
			try {
				System.out.println("Digite sua mensagem: ");
				String entrada = outgoing.nextLine();
				writer.println(entrada);
				writer.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setUpNetworkin() {
		// TODO Auto-generated method stub
		try {
			sock = new Socket("127.0.0.1", 5000);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Usuario: " + message + "\n");
					//incoming.add(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
