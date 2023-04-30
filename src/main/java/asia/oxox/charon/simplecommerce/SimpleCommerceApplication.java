package asia.oxox.charon.simplecommerce;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class SimpleCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCommerceApplication.class, args);
    }

}
