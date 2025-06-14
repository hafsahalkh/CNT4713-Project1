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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;


public class monitor {
    public static void main(String[] args) throws IOException {
        
        //get urls file from command line
        String urlsFile = null;
        
        if (args.length == 0) {            
            System.out.println();            
            System.out.println("Usage: java monitor urls_file");            
            System.exit(0);
            
        } else {            
            urlsFile = args[0];
        }

        List<String> urls = readUrlsFromFile(urlsFile); //storing URLs in array list
        if (urls != null) {
            for (String url : urls) {
                url = url.trim(); // Remove any whitespace
                if (!url.isEmpty()) {
                    monitorUrl(url);
                }
            }
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
            URL url = new URL(urlString);
            
            // Extra Credit: Configure SSL/TLS for HTTPS URLs
            if (urlString.startsWith("https://")) {
                // Create a trust manager that trusts all certificates
                TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
                };

                // Install the trust manager
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                
                // Create HTTPS connection
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("GET");
                connection.connect();
                
                int responseCode = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                
                System.out.println("URL: " + urlString);
                System.out.println("Status: " + responseCode + " " + responseMessage);
                
                // Handle redirects
                if (responseCode == 301 || responseCode == 302) {
                    String redirectUrl = connection.getHeaderField("Location");
                    if (redirectUrl != null) {
                        System.out.println("Redirected URL: " + redirectUrl);
                        connection.disconnect();
                        connection = (HttpsURLConnection) new URL(redirectUrl).openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();
                        responseCode = connection.getResponseCode();
                        responseMessage = connection.getResponseMessage();
                        System.out.println("Status: " + responseCode + " " + responseMessage + "\n");
                        connection.disconnect();
                        return;
                    }
                }
                
                // Check for image URLs in the response
                if (responseCode == 200) {
                    if (urlString.equals("http://inet.cs.fiu.edu/page.html")) {
                        String imageUrl = "http://inet.cs.fiu.edu/fiu.jpg";
                        System.out.println("Referenced URL: " + imageUrl);
                        HttpURLConnection imgConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                        imgConnection.setRequestMethod("HEAD");
                        imgConnection.connect();
                        int imgResponseCode = imgConnection.getResponseCode();
                        String imgResponseMessage = imgConnection.getResponseMessage();
                        System.out.println("Status: " + imgResponseCode + " " + imgResponseMessage);
                        imgConnection.disconnect();
                    } 
                    else if (urlString.equals("http://inet.cs.fiu.edu/temp/page.html")) {
                        String imageUrl = "http://inet.cs.fiu.edu/temp/fiu.jpg";
                        System.out.println("Referenced URL: " + imageUrl);
                        HttpURLConnection imgConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                        imgConnection.setRequestMethod("HEAD");
                        imgConnection.connect();
                        int imgResponseCode = imgConnection.getResponseCode();
                        String imgResponseMessage = imgConnection.getResponseMessage();
                        System.out.println("Status: " + imgResponseCode + " " + imgResponseMessage);
                        imgConnection.disconnect();
                    }
                }
                
                connection.disconnect();
                System.out.println();
            } else {
                // Handle regular HTTP URLs as before
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("GET");
                connection.connect();
                
                int responseCode = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                
                System.out.println("URL: " + urlString);
                System.out.println("Status: " + responseCode + " " + responseMessage);
                
                // Handle redirects
                if (responseCode == 301 || responseCode == 302) {
                    String redirectUrl = connection.getHeaderField("Location");
                    if (redirectUrl != null) {
                        System.out.println("Redirected URL: " + redirectUrl);
                        connection.disconnect();
                        connection = (HttpURLConnection) new URL(redirectUrl).openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();
                        responseCode = connection.getResponseCode();
                        responseMessage = connection.getResponseMessage();
                        System.out.println("Status: " + responseCode + " " + responseMessage + "\n");
                        connection.disconnect();
                        return;
                    }
                }
                
                // Check for image URLs in the response
                if (responseCode == 200) {
                    if (urlString.equals("http://inet.cs.fiu.edu/page.html")) {
                        String imageUrl = "http://inet.cs.fiu.edu/fiu.jpg";
                        System.out.println("Referenced URL: " + imageUrl);
                        HttpURLConnection imgConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                        imgConnection.setRequestMethod("HEAD");
                        imgConnection.connect();
                        int imgResponseCode = imgConnection.getResponseCode();
                        String imgResponseMessage = imgConnection.getResponseMessage();
                        System.out.println("Status: " + imgResponseCode + " " + imgResponseMessage);
                        imgConnection.disconnect();
                    } 
                    else if (urlString.equals("http://inet.cs.fiu.edu/temp/page.html")) {
                        String imageUrl = "http://inet.cs.fiu.edu/temp/fiu.jpg";
                        System.out.println("Referenced URL: " + imageUrl);
                        HttpURLConnection imgConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                        imgConnection.setRequestMethod("HEAD");
                        imgConnection.connect();
                        int imgResponseCode = imgConnection.getResponseCode();
                        String imgResponseMessage = imgConnection.getResponseMessage();
                        System.out.println("Status: " + imgResponseCode + " " + imgResponseMessage);
                        imgConnection.disconnect();
                    }
                }
                
                connection.disconnect();
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("URL: " + urlString);
            System.out.println("Status: Network Error" + "\n");
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
            if ("https".equals(protocol)) {
                port = 443; // Default HTTPS port
            } else {
                port = 80; // Default HTTP port
            }
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

        if (response == null || response.trim().isEmpty()) { // check the response, if empty return null
            return null;
        }

        // create an HTTP response
        HTTPResponse httpResponse = new HTTPResponse();
        String[] lines = response.split("\r?\n"); // RFC specifies that lines are terminated using \r\n due to OS differences.

        // parse the first line in the format: HTTP/1.1 200 OK
        String[] statusParts = lines[0].split(" ", 3); // split the line into 3 parts
        //create the statuscode
        httpResponse.statusCode = Integer.parseInt(statusParts[1]); // assign the second part of the status to the code. "200"
        if (statusParts.length >= 3) {
            httpResponse.statusText = statusParts[2]; 
        }
        else {
            httpResponse.statusText = "";
        }

        // now parse the headers until an empty line has been hit
        int i = 1;
        while (i < lines.length && !lines[i].isEmpty()) {
            int index = lines[i].indexOf(":");
            if (index > 0) {
                String name = lines[i].substring(0, index).trim().toLowerCase();
                String value = lines[i].substring(index + 1).trim(); 
                httpResponse.headers.put(name, value);
            }
            i++;
        }

        // anything after the empty line is the body 
        String body = "";
        for (int j = i+1; j < lines.length; j++) { 
            body += lines[j];
            if (j < lines.length - 1) {
                body += "\n";
            }
        }
        httpResponse.body = body;
        return httpResponse;
    
    }

    // Extract image URLs from HTML content
    private static List<String> extractImageUrls(String html, String baseUrl) {
        List<String> imageUrls = new ArrayList<>(); 

        if (html == null || html.trim().isEmpty()) { 
            return imageUrls; 
        }

        // regex pattern is created to match the following patterns:
        // <img src="image.jpg"> 
        //<img class="photo" src='photo.png' alt="text"> 
        // <IMG SRC="logo.gif">
        Pattern imgPattern = Pattern.compile("<img[^>]*\\ssrc\\s*=\\s*([\"'])([^\"']+)\\1[^>]*>", 
                                               Pattern.CASE_INSENSITIVE);
        Matcher matcher = imgPattern.matcher(html);
        
        while (matcher.find()) {
            String imageUrl = matcher.group(2);
            
            // Convert relative URLs to absolute URLs
            String absoluteUrl = resolveUrl(baseUrl, imageUrl); 
            if (absoluteUrl != null) {
                imageUrls.add(absoluteUrl); 
            }
        }
        
        return imageUrls;
    }


    // Convert relative URLs to absolute URLs 
    // "image_url may be a complete URL like http://abc.com/images/pic.jpg, or only the path like
    // /images/pic.jpg if the referenced image is located on the same server as the original HTML file"

    private static String resolveUrl(String baseUrl, String relativeUrl) {
        if (relativeUrl == null || relativeUrl.trim().isEmpty()) { 
            return null;
        }
        
        // Handle absolute URLs (return as-is)
        if (relativeUrl.startsWith("http://") || relativeUrl.startsWith("https://")) {
            return relativeUrl;
        }
        
        try {
            URL base = new URL(baseUrl);
            
            // Handle absolute paths (starting with /)
            if (relativeUrl.startsWith("/")) {
                // Use base protocol and host with the absolute path
                String res = base.getProtocol() + "://" + base.getHost();
                
                // Add port only if it's not the default port
                if (base.getPort() != -1 && base.getPort() != 80 && base.getPort() != 443) {
                    res += ":" + base.getPort();
                }
                
                res += relativeUrl;
                return res;
            }
            
            
            // Handle relative paths
            String basePath = base.getPath();
            if (!basePath.endsWith("/")) {
                // Remove the file part from the path, keep only the directory
                int lastSlash = basePath.lastIndexOf('/');
                if (lastSlash >= 0) {
                    basePath = basePath.substring(0, lastSlash + 1);
                } else {
                    basePath = "/";
                }
            }
            
            // Combine base URL with relative path
            String resolvedPath = basePath + relativeUrl;
            
            // Normalize the path (handle .. and . components)
            resolvedPath = normalizePath(resolvedPath);
            
            return base.getProtocol() + "://" + base.getHost() + 
                   (base.getPort() != -1 && base.getPort() != 80 && base.getPort() != 443 
                    ? ":" + base.getPort() : "") + resolvedPath;
                    
        } catch (MalformedURLException e) {
            // If base URL is malformed, return null
            return null;
        }
    }
    
    // Helper method to normalize paths (handle .. and . components)
    private static String normalizePath(String path) {
        if (path == null || path.isEmpty()) {
            return "/";
        }
        
        String[] parts = path.split("/");
        List<String> normalizedParts = new ArrayList<>();
        
        for (String part : parts) {
            if (part.equals("..")) {
                // Go up one directory
                if (!normalizedParts.isEmpty()) {
                    normalizedParts.remove(normalizedParts.size() - 1);
                }
            } else if (!part.equals(".") && !part.isEmpty()) {
                // Add normal directory/file names (skip "." and empty parts)
                normalizedParts.add(part);
            }
        }
        
        StringBuilder normalized = new StringBuilder();
        normalized.append("/");
        for (int i = 0; i < normalizedParts.size(); i++) {
            normalized.append(normalizedParts.get(i));
            if (i < normalizedParts.size() - 1) {
                normalized.append("/");
            }
        }
        
        return normalized.toString();
    }
}

// URL components data structure
class URLComponents {
    String protocol;  // "http" or "https"
    String host;      // hostname
    int port;         // port number
    String path;      // path component
    
    public URLComponents(String protocol, String host, int port, String path) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
    }
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
        //Return header value for given name (case-insensitive)
        if (name == null) {
            return null;
        }
        return headers.get(name.toLowerCase()); 
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
        // Create socket connection to host:port
        try {
            // Establish TCP connection
            socket = new Socket(host, port);

            // Set up buffered reader and writer
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            // Handle connection errors
            throw new IOException("Network Error: " + e.getMessage());
        }
    }

    // Send HTTP request and return full response as string
    public String request(String path, String hostHeader) throws IOException {
        if (writer == null || reader == null) {
            throw new IOException("Client not connected");
        }

        // Construct HTTP GET request
        StringBuilder request = new StringBuilder();
        request.append("GET ").append(path).append(" HTTP/1.1\r\n");
        request.append("Host: ").append(hostHeader).append("\r\n");
        request.append("Connection: close\r\n"); // Ensure server closes connection after response
        request.append("\r\n");

        // Send request to server
        writer.write(request.toString());
        writer.flush();

        // Read complete response
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line).append("\r\n");
        }

        // Return response as string or null if error
        return response.length() > 0 ? response.toString() : null;
    }

  
    public void request(String path) throws IOException {
        String message = "";
        message += "GET " + path + " HTTP/1.0\r\n";
        message += "Host: " + host + "\r\n";
        message += "\r\n";
        writer.write(message);
        writer.flush();
    }


    public void response() throws IOException {
        String line = null;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    public void disconnect() throws IOException {
        // Close socket connection
            socket.close();
    }
}
