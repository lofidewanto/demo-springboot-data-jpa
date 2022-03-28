package de.company.crm.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import de.company.crm.api.domain.Person;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.service.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;
import de.company.crm.server.repository.AddressRepository;
import de.company.crm.server.repository.PersonRepository;

@SpringBootTest
public class PersonServiceMockTest {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceMockTest.class);

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

	@BeforeEach
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

	@Test
	void testCreateAddressFromPersonWithException() throws CreatePersonException {
		// Prepare
		logger.info("Prepare...");
		given(addressRepository.save(any())).willReturn(address);

		// CUT
		Exception exception = assertThrows(CreatePersonException.class, () -> {
			personService.createAddressFromPerson(address, person);
		});

		String actualMessage = exception.getMessage();

        assertEquals(null, actualMessage);
	}

}
