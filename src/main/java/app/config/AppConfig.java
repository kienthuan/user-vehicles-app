package app.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "thuan.ngo")
public class AppConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostConstruct
	public void initConfig() {
		logger.info("INIT APP CONFIG...");
	}
}
