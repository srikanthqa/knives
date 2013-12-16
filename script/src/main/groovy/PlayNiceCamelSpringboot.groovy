@Grapes([
	@Grab(group='org.springframework.boot', module='spring-boot-starter-web', version='0.5.0.M6'),
	@GrabExclude(group='org.springframework.boot', module='spring-boot-starter-tomcat'),
	@Grab(group='org.springframework.boot', module='spring-boot-starter-jetty', version='0.5.0.M6'),
	@Grab(group='javax.servlet', module='javax.servlet-api', version='3.1.0', force = true)
	//@Grab(group='org.eclipse.jetty', module='jetty-util', version='9.0.6.v20130930', force=true),
	//@Grab(group='org.eclipse.jetty', module='jetty-servlet', version='9.0.6.v20130930', force=true)
])

import java.util.Arrays
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class WebApp {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(WebApp.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
	
	@Bean
	public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory() {
		return new JettyEmbeddedServletContainerFactory()
	}

}

