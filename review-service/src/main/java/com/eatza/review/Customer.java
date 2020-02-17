package com.eatza.review;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Customer {

	private Integer id;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDateTime createDateTime;
	private LocalDateTime updateDateTime;
	private boolean isActive;
	private String username;
	private String password;
	private int port;
	
}
