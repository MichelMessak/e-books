package fr.esiea.ebooks.util;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;

/**
 * Util for e-books
 * @author Michel Messak
 */
public class Util {


    //Set all the data and the value into inputs to have all the data from the contact to modify, deleting it or adding a new address
    public static String replaceValuesHTMLContact(String chain, int ID) throws Exception {
        try {

    ContactsList contactList = ContactsList.getInstance();
            for (int i = 0; i < 6; i++) {
                switch (i){
                    case 0 : chain = chain.replace("{" + i + "}",contactList.getContact(ID).getFirstName());break;
                    case 1 : chain = chain.replace("{" + i + "}",contactList.getContact(ID).getLastName());break;
                    case 2 : chain = chain.replace("{" + i + "}",contactList.getContact(ID).getBirthday());break;
                    case 3 : chain = chain.replace("{" + i + "}",contactList.getContact(ID).getEmail());break;
                    case 4 : chain = chain.replace("{" + i + "}",String.valueOf(contactList.getContact(ID).isActif()));break;
                    default : chain = chain.replace("{" + i + "}",contactList.getContact(ID).getID());break;
                }

            }
            return chain;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //Set all the data and the value into inputs to have all the data from the address to modify, deleting it
    public static String replaceValuesHTMLAddress(String chain, Contact contact,int ID) throws Exception {
        try {

            for (int i = 0; i < 7; i++) {
                switch (i){
                    case 0 :chain = chain.replace("{" + i + "}",String.valueOf(ID));break;
                    case 1 : chain = chain.replace("{" + i + "}",contact.getAdress(ID).getNumber());break;
                    case 2 : chain = chain.replace("{" + i + "}",contact.getAdress(ID).getStreet());break;
                    case 3 : chain = chain.replace("{" + i + "}",contact.getAdress(ID).getPostalCode());break;
                    case 4 : chain = chain.replace("{" + i + "}",contact.getAdress(ID).getCity());break;
                    case 5 : chain = chain.replace("{" + i + "}",contact.getID());break;
                    default : chain = chain.replace("{" + i + "}",contact.getAdress(ID).getID());break;
                }

            }
            return chain;
        } catch (Exception ex) {
            throw ex;
        }
    }

}
