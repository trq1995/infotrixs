import java.util.List;
import java.util.Scanner;
public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FavoriteCurrencyManager fcm = new FavoriteCurrencyManager();
        CurrencyConverter cc = new CurrencyConverter("7bbf9db1adefa46f910b39dfa2930684");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Show All currencies");
            System.out.println("2. Convert currency");
            System.out.println("3. Add favorite currency");
            System.out.println("4. Update favorite currency");
            System.out.println("5. Show favorite currencies");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    try {
                        cc.showAllCurrencies();
                    } catch (Exception e) {
                        System.out.println("Error fetching and displaying currencies: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter currency code from: ");
                    String fromCurrency = sc.nextLine().toUpperCase();
                    System.out.print("Enter currency code to: ");
                    String toCurrency = sc.nextLine().toUpperCase();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();

                    try {
                        double convertedAmount = cc.convertCurrency(fromCurrency, toCurrency, amount);
                        System.out.println(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);
                    } catch (Exception e) {
                        System.out.println("Error converting currency: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter currency code: ");
                    String code = sc.nextLine().toUpperCase();
                    System.out.print("Enter currency name : ");
                    String name = sc.nextLine();
                    Currency newCurrency = new Currency(code, name);
                    fcm.addFavoriteCurrency(newCurrency);
                    System.out.println("Currency added to favorites: " + newCurrency);
                    break;

                case 4:
                    System.out.print("Enter the currency code to update: ");
                    String oldCode = sc.nextLine().toUpperCase();
                    Currency oldCurrency = fcm.getFavoriteCurrencies()
                            .stream()
                            .filter(currency -> currency.getCode().equals(oldCode))
                            .findFirst()
                            .orElse(null);

                    if (oldCurrency == null) {
                        System.out.println("Currency not found in favorites.");
                        break;
                    }

                    System.out.print("Enter new currency code: ");
                    String newCode = sc.nextLine().toUpperCase();
                    System.out.print("Enter new currency name: ");
                    String newName = sc.nextLine();
                    Currency newCurrencyInfo = new Currency(newCode, newName);

                    fcm.updateFavoriteCurrency(oldCurrency, newCurrencyInfo);
                    System.out.println("Currency updated successfully: " + newCurrencyInfo);
                    break;

                case 5:
                    System.out.println("Favorite currencies:");
                    List<Currency> favorites = fcm.getFavoriteCurrencies();
                    if (favorites.isEmpty()) {
                        System.out.println("No favorite currencies added yet.");
                    } else {
                        for (Currency c : favorites) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
