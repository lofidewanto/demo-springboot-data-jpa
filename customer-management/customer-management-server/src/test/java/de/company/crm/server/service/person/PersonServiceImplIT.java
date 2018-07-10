/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.company.crm.server.service.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.company.crm.api.domain.Address;
import de.company.crm.api.domain.Person;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceImplIT {

	@Autowired
	private PersonService personService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateAddressFromPerson() throws CreatePersonException {
		// Prepare
		Person person = new PersonImpl();
		person.setName("Hans");
		Address address = new AddressImpl();
		address.setStreet("Koblenzer Str. 1");

		// CUT
		Person createAddressFromPerson = personService.createAddressFromPerson(address, person);

		// Asserts
		assertNotNull(createAddressFromPerson);
	}

	@Test
	public void testFindAllPersons() throws CreatePersonException {
		// Prepare
		Person person = new PersonImpl();
		person.setName("Jens");
		Address address = new AddressImpl();
		address.setStreet("Koblenzer Str. 2");

		Person createAddressFromPerson = personService.createAddressFromPerson(address, person);
		assertNotNull(createAddressFromPerson);

		// CUT
		Collection<Person> persons = personService.findAllPersons(0, 10);

		// Asserts
		assertEquals(1, persons.size());
	}

	@Test
	public void testFindPersonsByName() throws CreatePersonException {
		// Prepare
		Person person1 = new PersonImpl();
		person1.setName("Lofi");
		Address address1 = new AddressImpl();
		address1.setStreet("Koblenzer Str. 3");

		Person person2 = new PersonImpl();
		person2.setName("Aloha");
		Address address2 = new AddressImpl();
		address2.setStreet("Koblenzer Str. 4");

		Person createAddressFromPerson1 = personService.createAddressFromPerson(address1, person1);
		assertNotNull(createAddressFromPerson1);

		Person createAddressFromPerson2 = personService.createAddressFromPerson(address2, person2);
		assertNotNull(createAddressFromPerson2);

		// CUT
		Collection<Person> persons = personService.findPersonsByName("Lofi");

		// Asserts
		assertEquals(1, persons.size());
	}

}
