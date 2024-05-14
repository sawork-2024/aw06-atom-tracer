package com.example.webpos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private double price;
	private String image;

	public Product(String id, String title, double price, String img) {
		try {
			this.id = Long.parseLong(id);
		} catch (NumberFormatException e) {
			this.id = 0L;
		}
		this.name = title;
		this.price = price;
		this.image = img;
	}

	@Override
	public String toString() {
		return getId() + "\t" + getName() + "\t" + getPrice();
	}

}
