package com.litografiaartesplanchas.orderservice.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private int id;

    @Column(name = "nombre", nullable = false , length = 100, unique = true)
	private String name;

	@Column(name = "descripcion", nullable = true, columnDefinition = "MEDIUMTEXT")
	private String description;
	
	@Column(name = "precio")
	private double price;
	
	@Column(name = "foto", nullable = true, length = 255)
	private String picture;

    @JoinColumn(name = "id_tipo_servicio", nullable = false)
	private TypeService typeService;

    @OneToMany(mappedBy = "service")
    private List<Orders> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    
    

}
