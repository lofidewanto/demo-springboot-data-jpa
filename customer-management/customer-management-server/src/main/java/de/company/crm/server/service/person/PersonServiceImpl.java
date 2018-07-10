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

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.company.crm.api.domain.Address;
import de.company.crm.api.domain.Person;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.exception.FinderException;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;
import de.company.crm.server.repository.AddressRepository;
import de.company.crm.server.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Transactional
	@Override
	public Person createAddressFromPerson(Address address, Person person) throws CreatePersonException {
		// Create a Person and add an Address to it
		Address addressSaved = addressRepository.save((AddressImpl) address);

		logger.info("Following address created: " + addressSaved.getStreet());

		person.addAddress(address);

		try {
			Person personSaved = personRepository.save((PersonImpl) person);

			logger.info("Following person created: " + personSaved.getName());

			return personSaved;
		} catch (Exception e) {
			logger.error("Error saving the person and address - exception: " + e);
			throw new CreatePersonException();
		}
	}

	@Transactional
	@Override
	public Collection<Person> findAllPersons(Integer start, Integer length) {
		Collection<Person> personsAsCollection = new ArrayList<>();
		Pageable pageable = PageRequest.of(start, length);
		Iterable<PersonImpl> persons = personRepository.findAll(pageable);

		persons.forEach(person -> {
			personsAsCollection.add(person);
		});

		logger.info("Find all persons with start and length amount: " + personsAsCollection.size());

		return personsAsCollection;
	}

	@Transactional
	@Override
	public Collection<Person> findPersonsByName(String name) throws FinderException {
		Collection<PersonImpl> persons = personRepository.findByName(name);

		Collection<Person> personCollection = new ArrayList<>(persons);

		return personCollection;
	}

}
