package Networks1HW1;
import java.io.*;
import java.net.*;

public class ClientTCP {

	public static void main(String args[]) {
		 try (Socket socket = new Socket("LAPTOP-CO14LE9B", 2003)) {
	            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader userReq = new BufferedReader(new InputStreamReader(System.in));
	            
	            String userInput;
	            System.out.println("Enter your request or type 'exit' to quit");
	            while ((userInput = userReq.readLine()) != null && !userInput.equalsIgnoreCase("exit")) {
	                output.println(userInput);
	                System.out.println("Response: " + input.readLine());
	                System.out.println("Enter request (or 'exit' to quit):");
	            }

	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}

