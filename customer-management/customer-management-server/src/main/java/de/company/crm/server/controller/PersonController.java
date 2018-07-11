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
package de.company.crm.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.company.crm.api.CustomerManagementEndpoint;
import de.company.crm.api.domain.Address;
import de.company.crm.api.domain.Person;
import de.company.crm.api.dto.AddressDto;
import de.company.crm.api.dto.PersonDto;
import de.company.crm.api.exception.CreatePersonException;
import de.company.crm.api.service.person.PersonService;
import de.company.crm.server.domain.AddressImpl;
import de.company.crm.server.domain.PersonImpl;
import de.company.crm.server.repository.PersonRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
public class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonRepository personRepository;

	/**
	 * Using simple @{@link ResponseBody}.
	 *
	 * @param start
	 * @param length
	 * @return
	 */
	@ApiOperation(value = "Returns persons with start and length.", nickname = "getPersons", httpMethod = "GET", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@GetMapping(value = CustomerManagementEndpoint.PERSONS, params = { "start", "length" })
	public @ResponseBody List<PersonDto> getPersons(@RequestParam("start") Integer start,
			@RequestParam("length") Integer length) {
		addTestDateToDb();
		logger.info("Method getPersons begins...");
		ArrayList<PersonDto> persons = new ArrayList<>();
		Collection<Person> findAllPersons = personService.findAllPersons(start, length);
		for (Person person : findAllPersons) {
			PersonDto personDto = buildPerson(person);
			persons.add(personDto);
		}

		return persons;
	}

	/**
	 * Using {@link ResponseEntity}.
	 *
	 * @param personName
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@ApiOperation(value = "Returns filtered persons by three parameters.", nickname = "filterPersons", httpMethod = "GET", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@GetMapping(value = CustomerManagementEndpoint.PERSONS, params = { "nameSuggestBox", "fromDateTimePicker",
			"untilDateTimePicker" })
	public ResponseEntity<List<PersonDto>> filterPersons(@RequestParam("nameSuggestBox") String personName,
			@RequestParam(value = "fromDateTimePicker", required = false) Date fromDate,
			@RequestParam(value = "untilDateTimePicker", required = false) Date toDate) {
		logger.info("Method filterPersons begins...");

		List<PersonDto> persons = new ArrayList<>();
		PersonDto dto = new PersonDto();

		if (personName == null || personName.equals("")) {
			personName = ".. Empty ..";
		}

		dto.setName(personName);
		dto.setNickname("muster");
		persons.add(dto);

		dto = new PersonDto();
		dto.setName("Bauer_Filter");
		dto.setNickname("baur");

		persons.add(dto);

		return new ResponseEntity<List<PersonDto>>(persons, HttpStatus.OK);
	}

	public PersonDto createAddressFromPerson(PersonDto personDto, AddressDto addressDto) {
		// Mapping to entity
		Person person = new PersonImpl("muster");
		person.setName("Mustermann");

		Address address = new AddressImpl();

		Person createdPerson = null;
		try {
			createdPerson = personService.createAddressFromPerson(address, person);
		} catch (CreatePersonException e) {
			logger.error("Error: ", e);
		}

		// Mapping to DTO
		PersonDto createdPersonDto = buildPerson(createdPerson);

		return createdPersonDto;
	}

	private PersonDto buildPerson(Person person) {
		PersonDto personDto = new PersonDto();
		personDto.setName(person.getName());
		personDto.setNickname(person.getNickname());
		return personDto;
	}

	private void addTestDateToDb() {
		PersonImpl personImpl = new PersonImpl("muster");
		personImpl.setName("Mustermann");
		personRepository.save(personImpl);

		personImpl = new PersonImpl("baur");
		personImpl.setName("Bauer");
		personRepository.save(personImpl);
	}

}
