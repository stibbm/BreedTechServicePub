package breed.tech.service.manager.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static breed.tech.service.constants.Constants.STRIPE_SECRET_KEY;

@Component
public class StripePriceManager {

    public StripePriceManager() {
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    public Price createPrice(Integer unitAmount, String currency, String productId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("unit_amount", unitAmount);
        params.put("currency", currency);
        params.put("product", productId);
        Price price = Price.create(params);
        return price;
    }

    public Price getPrice(String priceId) throws StripeException {
        Price price = Price.retrieve(priceId);
        return price;
    }

}