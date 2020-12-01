package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UserVehicleApplication {

    private static Logger logger = LoggerFactory.getLogger(UserVehicleApplication.class);

    public static void main(String... args) {    	
        ConfigurableApplicationContext ctx = SpringApplication.run(UserVehicleApplication.class, args);
        ctx.registerShutdownHook();
        logger.debug("Application Started ...");
    }
}
