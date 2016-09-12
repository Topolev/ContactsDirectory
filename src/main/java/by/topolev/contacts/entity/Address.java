package by.topolev.contacts.entity;

import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.Id;
import by.topolev.contacts.orm.annotation.Table;

@Table(name="address")
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
	private Integer build;
	
	@Column(name="flat")
	private Integer flat;
	
	@Column(name="ind")
	private Integer ind;

	@Column(name="contact_id")
	private Integer contact_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getBuild() {
		return build;
	}

	public void setBuild(Integer build) {
		this.build = build;
	}

	public Integer getFlat() {
		return flat;
	}

	public void setFlat(Integer flat) {
		this.flat = flat;
	}

	public Integer getInd() {
		return ind;
	}

	public void setIndex(Integer index) {
		this.ind = index;
	}

	public Integer getContact_id() {
		return contact_id;
	}

	public void setContact_id(Integer contact_id) {
		this.contact_id = contact_id;
	}

	@Override
	public String toString(){
		return String.format("id: %d, country: %s, city: %s, street: %s, %d-%d", id,country,city,street,build,flat);
	}
	
	
}
