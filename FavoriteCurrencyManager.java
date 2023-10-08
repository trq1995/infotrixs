import java.util.ArrayList;
import java.util.List;
public class FavoriteCurrencyManager {
    private List<Currency> favoriteCurrencies;
    public FavoriteCurrencyManager() {
        favoriteCurrencies = new ArrayList<>();
    }
    public void addFavoriteCurrency(Currency currency) {
        favoriteCurrencies.add(currency);
    }
    public List<Currency> getFavoriteCurrencies() {
        return favoriteCurrencies;
    }
    public void updateFavoriteCurrency(Currency oldCurrency, Currency newCurrency) {
        favoriteCurrencies.remove(oldCurrency);
        favoriteCurrencies.add(newCurrency);
    }
    public boolean hasFavoriteCurrencies() {
        return !favoriteCurrencies.isEmpty();
    }
}
