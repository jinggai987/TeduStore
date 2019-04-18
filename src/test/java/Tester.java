import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import cn.tedu.store.bean.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.UserService;

public class Tester {
	
	BasicDataSource basicDataSource;
	MapperScannerConfigurer msc;
	SqlSessionFactoryBean ssf;
	DataSourceTransactionManager tx;

	@Test
	public void testDbcpConnection() {
		String file = "spring-dao.xml";
		AbstractApplicationContext ctx
			= new ClassPathXmlApplicationContext(file);
		BasicDataSource ds
			= ctx.getBean("dataSource", BasicDataSource.class);
		System.out.println("测试DBCP连接池：");
		System.out.println("\turl=" + ds.getUrl());
		System.out.println("\tdriverClassName=" + ds.getDriverClassName());
		System.out.println("\tusername=" + ds.getUsername());
		System.out.println("\tpassword=" + ds.getPassword());
		System.out.println("\tinitialSize=" + ds.getInitialSize());
		System.out.println("\tmaxActive=" + ds.getMaxActive());
		ctx.close();
	}
	
	@Test
	public void testCreateUser() {
		String file = "spring-dao.xml";
		AbstractApplicationContext ctx
			= new ClassPathXmlApplicationContext(file);
		UserMapper userMapper
			= ctx.getBean("userMapper", UserMapper.class);
		User user = new User();
		user.setUsername("chengheng");
		user.setPassword("123456");
		user.setPhone("13800138000");
		user.setEmail("chengheng@tedu.cn");
		userMapper.createUser(user );
		ctx.close();
	}
	
	@Test
	public void testFindUserByUsername() {
		String file = "spring-dao.xml";
		AbstractApplicationContext ctx
			= new ClassPathXmlApplicationContext(file);
		UserMapper userMapper
			= ctx.getBean("userMapper", UserMapper.class);
		User u1 = userMapper.findUserByUsername("chengheng");
		User u2 = userMapper.findUserByUsername("admin");
		System.out.println("u1 -> " + u1);
		System.out.println("u2 -> " + u2);
		ctx.close();
	}
	
	@Test
	public void testFindUserService() {
		String file = "spring-dao.xml";
		AbstractApplicationContext ctx
			= new ClassPathXmlApplicationContext(file);
		UserService userService
			= ctx.getBean("userService", UserService.class);
		User u3 = userService.findUserByUsername("chengheng");
		User u4 = userService.findUserByUsername("admin");
		System.out.println("u3 -> " + u3);
		System.out.println("u4 -> " + u4);
		ctx.close();
	}
	
	@Test
	public void testCreateUserService() {
		String file = "spring-dao.xml";
		AbstractApplicationContext ctx
			= new ClassPathXmlApplicationContext(file);
		UserService userService
			= ctx.getBean("userService", UserService.class);
		User user = new User(0, "admin", "admin888", "13800138001", "admin@tedu.cn", 0, "chengheng", null, "chengheng", null);
		userService.register(user);
		ctx.close();
	}
}
