package breed.tech.service.manager.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Component;

import static breed.tech.service.constants.Constants.STRIPE_SECRET_KEY;

@Component
public class StripeProductManager {

    public StripeProductManager() {
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    public Product createProduct(String name, String description) throws StripeException {
        ProductCreateParams productCreateParams = ProductCreateParams.builder()
                .setName(name)
                .setDescription(description)
                .build();
        Product product = Product.create(productCreateParams);
        return product;
    }

    public Product getProduct(String productId) throws StripeException {
        Product product = Product.retrieve(productId);
        return product;
    }

}