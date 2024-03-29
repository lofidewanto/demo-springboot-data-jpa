package de.company.crm.server.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import de.company.crm.api.CustomerManagementEndpoint;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceMockMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Disabled("Ignore this one")
	@Test
	public void shouldReturnPersons() throws Exception {
		mockMvc.perform(get("/demo" + CustomerManagementEndpoint.PERSONS + "?start=1&length=10")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("Hello World")));
	}
}
