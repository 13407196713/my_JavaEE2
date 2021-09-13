package config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//数据库相关 mybatis

@Configuration // 通过该注解来表明该类是一个Spring的配置，相当于一个xml文件
@PropertySource(value = { "classpath:jdbc.properties" }, ignoreResourceNotFound = true)
//配置多个配置文件  value={"classpath:jdbc.properties","xx","xxx"}
@EnableTransactionManagement // 开启声明式事务的支持
@MapperScan(basePackages = { "dao" }) // 配置扫描MyBatis接口的包路径，与@Mapper注解的接口对应

public class SpringJDBCConfig {
	@Value("${db.url}") // 注入属性文件jdbc.properties中的jdbc.url
	private String jdbcUrl;
	@Value("${db.driverClassName}")
	private String jdbcDriverClassName;
	@Value("${db.username}")
	private String jdbcUsername;
	@Value("${db.password}")
	private String jdbcPassword;
	@Value("${db.maxTotal}")
	private int maxTotal;
	@Value("${db.maxIdle}")
	private int maxIdle;
	@Value("${db.initialSize}")
	private int initialSize;

	//配置数据源
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource myDataSource = new BasicDataSource();
		// 数据库驱动
		myDataSource.setDriverClassName(jdbcDriverClassName);
		// 相应驱动的jdbcUrl
		myDataSource.setUrl(jdbcUrl);
		// 数据库的用户名
		myDataSource.setUsername(jdbcUsername);
		// 数据库的密码
		myDataSource.setPassword(jdbcPassword);
		// 最大连接数
		myDataSource.setMaxTotal(maxTotal);
		// 最大空闲连接数
		myDataSource.setMaxIdle(maxIdle);
		// 初始化连接数
		myDataSource.setInitialSize(initialSize);
		return myDataSource;
	}

	//为数据源添加事务管理器
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dt = new DataSourceTransactionManager();
		dt.setDataSource(dataSource());
		return dt;
	}


	//配置MyBatis工厂，同时指定数据源，并与MyBatis完美整合
	@Bean
	public SqlSessionFactoryBean getSqlSession() {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		Resource r = new ClassPathResource("mybatis/mybatis-config.xml");
		sqlSessionFactory.setConfigLocation(r);
		return sqlSessionFactory;
	}
}
