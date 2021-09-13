package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;


//配置视图解析器、静态资源上传

@Configuration
@EnableWebMvc // 开启spring mvc的支持
@ComponentScan(basePackages = { "controller", "service"}) // 扫描基本包
public class SpringMVCConfig implements WebMvcConfigurer {
	//配置视图解析器
	@Bean
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	//配置静态资源（不需要DispatcherServlet转发的请求）
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	//配置上传文件的相关设置
	@Bean("multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		//设置请求的编码格式，默认为iso-8859-1
		cmr.setDefaultEncoding("UTF-8");
		//设置允许上传文件的最大值，单位为字节
		cmr.setMaxUploadSize(5400000);
		//设置上传文件的临时路径
		//workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\fileUpload
		Resource uploadTempDir = new FileSystemResource("fileUpload/temp");
		try {
			cmr.setUploadTempDir(uploadTempDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmr;
	}
}
