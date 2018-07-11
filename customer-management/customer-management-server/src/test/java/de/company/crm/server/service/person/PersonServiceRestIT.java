package de.company.crm.server.service.person;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.company.crm.api.CustomerManagementEndpoint;
import de.company.crm.api.domain.Address;
import de.company.crm.api.domain.Person;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonServiceRestIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PersonService personService;

	private void createAddressFromPerson() throws CreatePersonException {
		Person person = new PersonImpl();
		person.setName("Hans");
		Address address = new AddressImpl();
		address.setStreet("Koblenzer Str. 1");

		Person createAddressFromPerson = personService.createAddressFromPerson(address, person);

		assertThat(createAddressFromPerson).isNotNull();
	}

	@Before
	public void startUp() {
	}

	@Transactional
	@Test
	public void getPersons() throws CreatePersonException {
		// Prepare
		createAddressFromPerson();

		// CUT
		assertThat(this.restTemplate.getForObject(
				"http://localhost:" + port + "/demo" + CustomerManagementEndpoint.PERSONS + "?start=1&length=10",
				String.class)).contains("");
	}
}
