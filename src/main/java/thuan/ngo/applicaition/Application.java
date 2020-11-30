package thuan.ngo.applicaition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "thuan.ngo.config")
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String... args) {    	
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.registerShutdownHook();
        logger.debug("Application Started ...");
    }
}
