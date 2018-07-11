package de.company.crm.server.service.person;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.company.crm.api.CustomerManagementEndpoint;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceMockMvcIT {

	@Autowired
	private MockMvc mockMvc;

	@Ignore
	@Test
	public void shouldReturnPersons() throws Exception {
		mockMvc.perform(get("/demo" + CustomerManagementEndpoint.PERSONS + "?start=1&length=10")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("Hello World")));
	}
}
