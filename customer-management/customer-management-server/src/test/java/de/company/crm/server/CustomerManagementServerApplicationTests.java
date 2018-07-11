package de.company.crm.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.company.crm.server.controller.PersonController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerManagementServerApplicationTests {

	@Autowired
	private PersonController personController;

	@Test
	public void contextLoads() {
		assertThat(personController).isNotNull();
	}

}
