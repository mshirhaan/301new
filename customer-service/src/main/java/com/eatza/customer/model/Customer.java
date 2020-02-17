package com.eatza.customer.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eatza.customer.dto.CustomerDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customers")
@NoArgsConstructor
@Data
public class Customer {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDateTime createDateTime;
	private LocalDateTime updateDateTime;
	private boolean isActive;
	private String username;
	private String password;
	
	@Transient int port;
	
	public Customer(CustomerDto customerDto) {
		this.firstName = customerDto.getFirstName();
		this.middleName = customerDto.getMiddleName();
		this.lastName = customerDto.getLastName();
		this.createDateTime = LocalDateTime.now();
		this.updateDateTime = LocalDateTime.now();
		this.isActive = customerDto.isActive();
		this.username = customerDto.getUsername();
		this.password = customerDto.getPassword();
	}

	public Customer(Integer id, String firstName, String lastName, String username, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.createDateTime = LocalDateTime.now();
		this.updateDateTime = LocalDateTime.now();
	}
	
}
