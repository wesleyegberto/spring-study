package com.github.wesleyegberto.sampleapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleAppApplicationTests {
	@Rule
	public GenericContainer<?> mysql = new GenericContainer<>("mysql")
		.withEnv("MYSQL_USER", "user")
		.withEnv("MYSQL_ROOT_PASSWORD", "super_secret")
		.withExposedPorts(3306)
		.waitingFor(Wait.forListeningPort());

	@Test
	public void contextLoads() {
	}

}
