package ir.aspireapps.springmart.actuator;

import ir.aspireapps.springmart.repo.ProductRepository;
import ir.aspireapps.springmart.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationHealthIndicator implements HealthIndicator {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("application", "SpringMart")
                .withDetail("status", "Running")
                .withDetail("cache", "Redis")
                .withDetail("users", userRepository.count())
                .withDetail("products",productRepository.count())
                .build();
    }
}
