import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
public class CurrencyConverter {
    public final String apiUrl;
    public final String apiKey1;
    public CurrencyConverter(String apiKey) {
        this.apiUrl = "http://api.exchangerate.host/convert?access_key=" + apiKey;
        this.apiKey1 = apiKey;
    }
    public void showAllCurrencies() throws Exception {
        String apiUrl1 = "http://api.exchangerate.host/list?access_key="+ apiKey1;
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl1).openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String jsonResponse = reader.lines().collect(Collectors.joining());
            String parsedData = parseCountry(jsonResponse);
            System.out.println("Parsed Country Data:\n" + parsedData);
        }
    }
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) throws Exception {
        String url = apiUrl + "&from=" + fromCurrency + "&to=" + toCurrency + "&amount=" + amount;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String jsonResponse = reader.lines().collect(Collectors.joining());
            double result = parseResult(jsonResponse);
            return result;
        }
    }
    private String parseCountry(String jsonResponse) {
        try {
            int currenciesIndex = jsonResponse.indexOf("\"currencies\":") + 13;
            int endIndex = jsonResponse.lastIndexOf("}");
            String currenciesData = jsonResponse.substring(currenciesIndex, endIndex);

            // Split the currencies data into individual key-value pairs
            String[] currencyPairs = currenciesData.split(",");
            StringBuilder result = new StringBuilder();

            for (String pair : currencyPairs) {
                // Split the key-value pair into code and name
                String[] keyValue = pair.split(":");
                String code = keyValue[0].replaceAll("\"", "").trim();
                String name = keyValue[1].replaceAll("\"", "").trim();
                result.append(code).append(": ").append(name).append("\n");
            }

            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }
    }
    private double parseResult(String jsonResponse) {
        try {
            // Extract the "result" value from the JSON response
            int startIndex = jsonResponse.indexOf("\"result\":") + 9;
            int endIndex = jsonResponse.indexOf("}", startIndex);
            String resultStr = jsonResponse.substring(startIndex, endIndex);
            return Double.parseDouble(resultStr);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }
    }
}
