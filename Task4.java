import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task4 {
    
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        try {
            System.out.println("Currency Converter");
            System.out.print("Enter base currency (e.g. USD): ");
            String from = sc.nextLine().toUpperCase();
            
            System.out.print("Enter target currency (e.g. EUR): ");
            String to = sc.nextLine().toUpperCase();
            
            System.out.print("Enter amount to convert: ");
            double amount = sc.nextDouble();
            
            double rate = getExchangeRate(from, to);
            double converted = amount * rate;
            
            System.out.printf("\n%.2f %s = %.2f %s\n", 
                amount, from, converted, to);
        } catch (Exception e) {
            System.out.println("Error converting currency: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
    
    private static double getExchangeRate(String from, String to) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + from))
                .build();
        
        HttpResponse<String> response = client.send(
            request, HttpResponse.BodyHandlers.ofString());
        
        String json = response.body();
        int start = json.indexOf(to) + 5;
        int end = json.indexOf(",", start);
        
        return Double.parseDouble(json.substring(start, end));
    }
}