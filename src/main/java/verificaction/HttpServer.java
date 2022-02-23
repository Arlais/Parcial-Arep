package verificaction;

import verificaction.service.climaService;

import java.net.*;
import java.io.*;
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = null;
            inputLine = in.readLine();
            if(inputLine!="") {
                try {
                    System.out.println("Received: " + inputLine);
                    if (inputLine.split(" ")[1].split("\\?")[0].equals("/Consulta")) {
                        climaService clima = new climaService();
                        outputLine = clima.getClima(inputLine.split(" ")[1].split("\\?")[1]);
                    } else if (inputLine.split(" ")[1].equals("/Clima")) {
                        outputLine = "HTTP/1.1 200 OK\r\n"
                                + "Content-Type: text/html\r\n"
                                + "\r\n";
                        String path = "src/main/resources/public/index.html";
                        BufferedReader reader = new BufferedReader(new FileReader(path));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            outputLine += line + "\n";
                        }
                    }else{
                        outputLine = "HTTP/1.1 200 OK\r\n"
                                + "Content-Type: text/html\r\n"
                                + "\r\n";
                        String path = "src/main/resources/public/index.html";
                        BufferedReader reader = new BufferedReader(new FileReader(path));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            outputLine += line + "\n";
                        }
                    }
                }catch(Exception e){
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n";
                    String path = "src/main/resources/public/index.html";
                    BufferedReader reader = new BufferedReader(new FileReader(path));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        outputLine += line + "\n";
                    }
                }

            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
