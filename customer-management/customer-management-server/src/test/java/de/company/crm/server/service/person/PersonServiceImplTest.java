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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.company.crm.api.domain.Address;
import de.company.crm.api.domain.Person;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;
import de.company.crm.server.repository.AddressRepository;
import de.company.crm.server.repository.PersonRepository;
import de.company.crm.server.service.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@InjectMocks
	private PersonService personService = new PersonServiceImpl();

	@Mock
	private PersonRepository personRepository;

	@Mock
	private AddressRepository addressRepository;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateAddressFromPerson() throws CreatePersonException {
		// Prepare
		Person person = new PersonImpl();
		Address address = new AddressImpl();

		doReturn(person).when(personRepository).save((PersonImpl) person);
		doReturn(address).when(addressRepository).save((AddressImpl) address);

		// CUT
		Person createAddressFromPerson = personService.createAddressFromPerson(address, person);

		// Asserts
		assertNotNull(createAddressFromPerson);
	}

}
