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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.company.crm.api.CustomerManagementEndpoint;
import de.company.crm.api.dto.UserDto;
import de.company.crm.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Returns logged-in users.", nickname = "getLoginUser", httpMethod = "GET", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = UserDto.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@GetMapping(value = CustomerManagementEndpoint.USERS + "/{id}")
	public @ResponseBody UserDto getLoginUser(@PathVariable("id") String id) {
		String loginUser = userService.getLoginUser();

		UserDto userDto = buildUser(loginUser);

		return userDto;
	}

	private UserDto buildUser(String loginUser) {
		UserDto userDto = new UserDto();
		userDto.setName(loginUser);
		return userDto;
	}
}
