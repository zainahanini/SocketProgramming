package Networks1HW1;
import java.io.*;
import java.net.*;

public class ServerTCP {
	private static final String FILE_NAME = "vehicles.txt";
	
	private static void main(String argv[]) {
		try (ServerSocket serverSocket = new ServerSocket(2003)){
			System.out.println("Server is ON");
			
			while (true) {
				Socket connectionSocket = serverSocket.accept();
				
				BufferedReader input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());
				
				String request = input.readLine();
				String response = processReq(request);
				
				 output.writeUTF(response);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String processReq(String request) {
		 String[] req = request.split(":");
		 String cases = req[0].trim();
	        String data = req.length > 1 ? req[1].trim() : "";
	        
	        switch (cases) {
	        case "INQ":
	        	return inquire(data);
	        case "ADD":
	        	return add(data);
	        case "DEL":
	        	return delete(data);
	        case "UPD":
	        	return update(data);
	        default:
	        	return "Please enter a valid request";
	        
	        
	        }
	}

	private static String update(String data) {
		String[] parts = data.split(",");
		String id = parts[0].trim();
		
		 File inputFile = new File(FILE_NAME);
	     File tempFile = new File("upd.txt");
	     
	     boolean found = false;
	     
	        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith(id)) {
	                    writer.write(data);
	                    writer.newLine();
	                    found = true;
	                } else {
	                    writer.write(line);
	                    writer.newLine();
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error updating vehicle";
	        }

	        if (!found) {
	            return "Vehicle is not found";
	        }

	        inputFile.delete();
	        tempFile.renameTo(inputFile);

	        return "Vehicle updated successfully!!";
	    }
	      

	private static String delete(String id) {
		File inputFile = new File(FILE_NAME);
        File tempFile = new File("del.txt");

        boolean found = false;
        
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
			     
			String line;
			while ((line = reader.readLine()) != null){
				if(!line.startsWith(id)) {
					 writer.write(line);
	                    writer.newLine();
	                } else {
	                    found = true;
				}
			}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Error deleting vehicles";
			}
		if (!found) {
			return "Enter a valid vehicle ID";
		}
		inputFile.delete();
        tempFile.renameTo(inputFile);

        return "Vehicle deleted successfully!!";
    }
	
	private static String add(String data) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			writer.write(data);
			writer.newLine();
			return "Vehicle added successfully!!";
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error adding vehicle";
		}
		return null;
	}

	private static String inquire(String id) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(id)) {
					return line;
				}
				
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return "Vehicle not found";
	}

}
