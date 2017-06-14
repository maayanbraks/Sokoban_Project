package controller.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

	void handleClient(InputStream inFromClient, OutputStream outToClient) throws IOException;
	void stop();
	void addMsg(String msg)  throws InterruptedException;
}
