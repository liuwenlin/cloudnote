package cn.tedu.note.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTest {

	public ClassPathXmlApplicationContext ctx;

	@Before
	public void initCtx() {
		ctx = new ClassPathXmlApplicationContext(
				"conf/spring-mvc.xml",
				"conf/spring-mybatis.xml",
				"conf/spring-service.xml",
				"log4j.xml");
	}

	@After
	public void close() {
		ctx.close();
	}

}