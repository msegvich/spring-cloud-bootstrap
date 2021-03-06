package com.slalom.cloud.adapter.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class AdapterWebserviceConfig {
	@Bean
	  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext)
	  {
	    MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
	    messageDispatcherServlet.setApplicationContext(applicationContext);
	    messageDispatcherServlet.setTransformWsdlLocations(true);

	    ServletRegistrationBean result = new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");

	    return result;
	  }

	  @Bean(name = "users")
	  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema userSchema)
	  {
	    DefaultWsdl11Definition result = new DefaultWsdl11Definition();

	    result.setPortTypeName("UserPort");
	    result.setLocationUri(Constants.LOCATION_URI);
	    result.setTargetNamespace(Constants.NAMESPACE_URI);
	    result.setSchema(userSchema);

	    return result;
	  }

	  @Bean
	  public XsdSchema userSchema()
	  {
	    XsdSchema result = new SimpleXsdSchema(new ClassPathResource("user.xsd"));

	    return result;
	  }
}
