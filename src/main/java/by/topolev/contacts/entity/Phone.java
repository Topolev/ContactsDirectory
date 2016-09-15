package by.topolev.contacts.entity;

import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.Id;
import by.topolev.contacts.orm.annotation.Table;

/**
 * Created by Vladimir on 11.09.2016.
 */
@Table(name="phone")
public class Phone {
    @Id
    private Integer id;

    @Column(name = "country_code")
    private Integer countryCode;

    @Column(name = "operator_code")
    private Integer operatorCode;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @Column(name = "type_phone")
    private String typePhone;

    @Column(name = "description")
    private String description;

    @Column(name="contact_id")
    private Integer contact_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypePhone() {
        return typePhone;
    }

    public void setTypePhone(String typePhone) {
        this.typePhone = typePhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
