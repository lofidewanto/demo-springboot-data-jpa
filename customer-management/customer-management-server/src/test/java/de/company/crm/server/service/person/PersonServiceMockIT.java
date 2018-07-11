package de.company.crm.server.service.person;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import de.company.crm.api.domain.Person;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;
import de.company.crm.server.repository.AddressRepository;
import de.company.crm.server.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceMockIT {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceMockIT.class);

	@Autowired
	private PersonService personService;

	@Spy
	private PersonImpl person;

	@Spy
	private AddressImpl address;

	@MockBean
	private AddressRepository addressRepository;

	@MockBean
	private PersonRepository personRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateAddressFromPerson() throws CreatePersonException {
		// Prepare
		logger.info("Prepare...");
		given(addressRepository.save(any())).willReturn(address);
		given(personRepository.save(any())).willReturn(person);

		// CUT
		Person createAddressFromPerson = personService.createAddressFromPerson(address, person);

		// Asserts
		assertNotNull(createAddressFromPerson);
	}

	@Test(expected = CreatePersonException.class)
	public void testCreateAddressFromPersonWithException() throws CreatePersonException {
		// Prepare
		logger.info("Prepare...");
		given(addressRepository.save(any())).willReturn(address);

		// CUT
		Person createAddressFromPerson = personService.createAddressFromPerson(address, person);

		// Asserts
		assertNotNull(createAddressFromPerson);
	}

}
