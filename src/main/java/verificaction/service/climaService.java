package verificaction.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class climaService {

    public String getClima(String city) throws IOException {
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/json\r\n"
                + "\r\n";
        String path = "src/main/resources/public/index.html";
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city.substring(7)+"&appid=f005282529df8a0027e9b46a868b22b6");
        try (BufferedReader reader= new BufferedReader(new InputStreamReader(url.openStream()))){
            String input;
            while ((input=reader.readLine())!=null){
                System.out.println(input);
                outputLine+=input;
            }
        }
        return outputLine;
    }
}
