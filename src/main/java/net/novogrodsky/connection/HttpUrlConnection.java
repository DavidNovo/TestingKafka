package net.novogrodsky.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by david.j.novogrodsky on 5/6/2014.
 */
public class HttpUrlConnection {

    public static void main(String[] args) throws Exception {

    }

    // HTTP GET request
    public String sendGet() throws Exception {

        // this url gives a 403 error
        String url = "http://www.google.com/search";
        String url3= "https://www.google.com/#q=yyy&safe=off";

        // using this url I get a response code of -1
        String url2 = "http://products.igs-ip.net";

        URL obj = new URL(url3);
        HttpURLConnection con;
        con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url3);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }
}
