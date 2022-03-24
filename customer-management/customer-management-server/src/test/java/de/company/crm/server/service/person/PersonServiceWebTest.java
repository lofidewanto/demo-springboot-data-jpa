package de.company.crm.server.service.person;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import de.company.crm.api.CustomerManagementEndpoint;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.controller.PersonController;
import de.company.crm.server.repository.PersonRepository;

@WebMvcTest(PersonController.class)
public class PersonServiceWebTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService personService;

	@MockBean
	private PersonRepository personRepository;

	@Disabled("Ignore this one")
	@Test
	public void shouldReturnPersons() throws Exception {
		mockMvc.perform(get("/demo" + CustomerManagementEndpoint.PERSONS + "?start=1&length=10")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("Hello World")));
	}
}
