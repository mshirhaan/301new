package com.eatza.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="ordered_items")
@Getter @Setter @NoArgsConstructor
public class OrderedItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int quantity;
	private double price;
	private Long itemId;


	public OrderedItem(String name, int quantity, double price, Order order, Long itemId) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.order=order;
		this.itemId = itemId;
	}


	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;

	
	




}
