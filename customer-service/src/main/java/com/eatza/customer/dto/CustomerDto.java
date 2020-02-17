package com.eatza.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomerDto {

	private String firstName;
	private String middleName;
	private String lastName;
	private boolean isActive;
	private String username;
	private String password;
}
