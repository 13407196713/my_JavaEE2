package config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

// 注册 SpringMVCConfig、SpringJDBCConfig、Spring MVC 的 DispatcherServlet

public class WebConfig implements WebApplicationInitializer{
	@Override
	public void onStartup(ServletContext arg0) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(SpringJDBCConfig.class);//注册配置类SpringJDBCConfig
		ctx.register(SpringMVCConfig.class);//注册Spring MVC的Java配置类SpringMVCConfig
		ctx.setServletContext(arg0);//和当前ServletContext关联

		//注册Spring MVC的DispatcherServlet
		Dynamic servlet = arg0.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);

		//注册字符编码过滤器
		javax.servlet.FilterRegistration.Dynamic filter = arg0.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		filter.addMappingForUrlPatterns(null, false, "/*");
	}
}
