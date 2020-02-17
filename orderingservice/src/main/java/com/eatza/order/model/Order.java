package com.eatza.order.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter @Setter @NoArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private Long customerId;
	private String status;
	private Long restaurantId;
	
	@CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
    
    
	
    
	public Order(Long customerId, String status, Long restaurantId) {
		this.customerId = customerId;
		this.status = status;
		this.restaurantId = restaurantId;
	}
	

}
