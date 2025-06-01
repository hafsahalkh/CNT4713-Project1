import java.net.Socket;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class monitor {
    public static void main(String[] args) throws IOException {
        
        // get urls file from command line
        String urlsFile = null;
        
        if (args.length == 0) {
            
            System.out.println();
            
            System.out.println("Usage: java monitor urls_file");
            
            System.exit(0);
        } else {
            
            urlsFile = args[0];
        }

        // TODO: Read URLs from file and monitor each one
        List<String> urls = readUrlsFromFile(urlsFile);
        
        for (String url : urls) {
            
            monitorUrl(url);
        }
    }

    //reading the URLS from the file
    private static List<String> readUrlsFromFile(String filename) {
          try {
              
            return Files.readAllLines(Paths.get(filename));//reading the file using the filename provided by user
              
        } catch (IOException e) {
              
            System.err.println("Cannot read the file: " + e.getMessage());//error handling
              
            return null;
        }
    }

    //monitoring URLs
    private static void monitorUrl(String urlString) {
    try {
        URL url = new URL(urlString);  //defining URL object
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //opening the connection
        
        connection.setInstanceFollowRedirects(true); //enabling redirects
        
        connection.setRequestMethod("GET"); //setting the GET request
        
        connection.connect(); //connecting to the server

        int responseCode = connection.getResponseCode(); //retreiving status code from HTTP
        
        String responseMessage = connection.getResponseMessage(); // retreiving HTTP status

        System.out.println(urlString " + responseCode + " " + responseMessage); //displaying the result

        connection.disconnect(); //closing the connection
        
    } catch (IOException e) {
        
        System.err.println(urlString + " Error: " + e.getMessage()); //error handling
    }
}


    // Parse URL into components
    private static URLComponents parseUrl(String urlString) {
    try {
        
        URL url = new URL(urlString);//creating URL object
        
        String protocol = url.getProtocol();//getting the protocol from the URL
        
        String host = url.getHost();//getting the host from the URL
        
        int port = url.getPort();//getting the port from the URL
        
        if (port == -1) {
            
            port = 80;//setting to port 80 for HTTP if port is not provided
        }
        
        String path = url.getPath();//getting the path from the URL
        
        if (path == null || path.isEmpty()) {//setting the path character if it does not exist
            
            path = "/";
        }
        return new URLComponents(protocol, host, port, path);//returning the components that were parsed from the URL
        
    } catch (MalformedURLException e) {
        
        System.err.println("Error with URL " + urlString);//error handling
        
        return null;
    }
}


    // Parse HTTP response into structured data
    private static HTTPResponse parseHttpResponse(String response) {
        // TODO: Parse status line to get status code and text
        // TODO: Parse headers into map
        // TODO: Extract body content
        return null;
    }

    // Extract image URLs from HTML content
    private static List<String> extractImageUrls(String html, String baseUrl) {
        // TODO: Use regex to find <img src="..."> tags
        // TODO: Extract src attribute values
        // TODO: Convert relative URLs to absolute URLs
        return null;
    }

    // Convert relative URLs to absolute URLs
    private static String resolveUrl(String baseUrl, String relativeUrl) {
        // TODO: Handle absolute URLs (return as-is)
        // TODO: Handle relative paths (combine with base URL)
        // TODO: Handle absolute paths (use base protocol and host)
        return null;
    }
}

// URL components data structure
class URLComponents {
    String protocol;  // "http" or "https"
    String host;      // hostname
    int port;         // port number
    String path;      // path component
}

// HTTP response data structure
class HTTPResponse {
    int statusCode;                        // HTTP status code (200, 404, etc.)
    String statusText;                     // HTTP status text ("OK", "Not Found", etc.)
    Map<String, String> headers;           // HTTP headers
    String body;                           // Response body content
    
    public HTTPResponse() {
        headers = new HashMap<>();
    }
    
    // Helper method to get header value (case-insensitive)
    public String getHeader(String name) {
        // TODO: Return header value for given name (case-insensitive)
        return null;
    }
}

// Enhanced HTTP Client
class HTTPClient {
    private String host = null;
    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public HTTPClient(String host, int port) throws IOException {
        this.host = host;
        // TODO: Create socket connection to host:port
        // TODO: Set up buffered reader and writer
        // TODO: Handle connection errors
    }

    // Send HTTP request and return full response as string
    public String request(String path, String hostHeader) throws IOException {
        // TODO: Construct HTTP GET request
        // TODO: Send request to server
        // TODO: Read complete response
        // TODO: Return response as string or null if error
        return null;
    }

    // Legacy method for compatibility with original code
    public void request(String path) throws IOException {
        String message = "";
        message += "GET " + path + " HTTP/1.0\r\n";
        message += "Host: " + host + "\r\n";
        message += "\r\n";
        writer.write(message);
        writer.flush();
    }

    // Legacy method for compatibility with original code
    public void response() throws IOException {
        String line = null;
        while((line=reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    public void disconnect() throws IOException {
        // TODO: Close socket connection
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
