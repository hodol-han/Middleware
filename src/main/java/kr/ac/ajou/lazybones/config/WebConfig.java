package kr.ac.ajou.lazybones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Class for configuration.
 * @author AJOU
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("kr.ac.ajou.lazybones")
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//  super.addResourceHandlers(registry);
	  registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	  registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/views/css/");
	  registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/views/images/");
  }

}
