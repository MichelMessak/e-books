/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.esiea.ebooks.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Contact {

    private String ID;
    private String lastName;
    private String firstName;
    private String email;
    private Date birthday;
    private boolean actif;
    private List <Address> listAdress = new ArrayList<Address>();

    public Contact (){

    }

    public Contact (String lastName, String firstName, String email, Date Birthday, boolean actif){
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.birthday = Birthday;
        this.actif = actif;
        this.ID = UUID.randomUUID().toString();
       
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getBirthday() {
        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("dd-MM-yyyy");
                
        return formater.format(this.birthday);
    }

    public void setBirthday(Date Birthday) {
        this.birthday = Birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress (String number, String street, String postalCode, String city){
        listAdress.add(new Address(number, street, postalCode, city,this.getID()));
    }

    public void setAddress (Address newAddress){
        listAdress.add(newAddress);
    }

    public Address getAdress (int position){

        return listAdress.get(position);
    }

    public List getAllAdress (){

        return listAdress;
    }

    public void createID(){
        this.setID(UUID.randomUUID().toString());
    }

}
