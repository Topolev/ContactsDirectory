package by.topolev.contacts.entity;

import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.Id;

public class Address {
	@Id
	private Integer id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="city")
	private String city;
	
	@Column(name="street")
	private String street;
	
	@Column(name="build")
	private int build;
	
	@Column(name="flat")
	private int flat;
	
	@Column(name="index")
	private int index;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getBuild() {
		return build;
	}
	public void setBuild(int build) {
		this.build = build;
	}
	public int getFlat() {
		return flat;
	}
	public void setFlat(int flat) {
		this.flat = flat;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, country: %s, city: %s, street: %s, %d-%d", id,country,city,street,build,flat);
	}
	
	
}
