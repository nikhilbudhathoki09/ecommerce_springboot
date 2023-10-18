package com.ecommerce.the_mesh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int product_id;

	private String product_name;
	private double product_price;
	private String img_path1;
	private String img_path2;
	private String img_path3;
	private int stock;
	private String product_desc;

	@ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "gender_id", referencedColumnName = "gender_id")
	private Gender gender;
	
    
}
