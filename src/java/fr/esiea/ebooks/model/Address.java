/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.esiea.ebooks.model;

import java.util.UUID;


public class Address {

    private String number;
    private String street;
    private String postalCode;
    private String city;
    private String IDContact;
    private String ID;

    public Address (){

    }

    public Address (String number, String street, String postalCode, String city, String IDContact){

            this.number = number;
            this.street = street;
            this.postalCode = postalCode;
            this.city = city;
            this.IDContact = IDContact;
            this.ID = UUID.randomUUID().toString();
    }

     public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDContact() {
        return IDContact;
    }

    public void setIDContact(String IDContact) {
        this.IDContact = IDContact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


}
