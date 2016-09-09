package by.topolev.contacts.entity;

import java.util.Date;

import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.Id;
import by.topolev.contacts.orm.annotation.OneToOne;
import by.topolev.contacts.orm.annotation.Table;

@Table(name = "contact")
public class Contact {
	@Id
	private Integer id;

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "last_name")
	private String lastname;

	@Column(name = "middle_name")
	private String middlename;

	@Column(name = "sex")
	private String sex;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "website")
	private String website;

	@Column(name = "email")
	private String email;

	@Column(name = "work_place")
	private String workplace;

	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name="photo")
	private String photo;

	@OneToOne(clazz = "by.topolev.contacts.entity.Address", table = "address")
	private Address address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(" id: " + id);
		str.append(" first name: " + firstname);
		str.append(" sex: " + sex);
		return str.toString();
	}

}
