package test.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Profile("test")
@Configuration
public class TestJpaDbConfig {

	@Value("classpath:db/init_schema.sql")
	private Resource schemaScript;
	
	@Value("${spring.datasource.driver-class-name}")
	private String serverClassName;
	
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUserName;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@PostConstruct
	public void initDb() {
		logger.info("Initialize DB....");
		DatabasePopulatorUtils.execute(databasePopulator(), getDataSource());
	}

	private DataSource getDataSource() {
		return DataSourceBuilder.create()
				.url(dbUrl)
				.username(dbUserName)
				.password(dbPassword)
				.driverClassName(serverClassName).build();
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScripts(schemaScript);
		return populator;
	}
}
