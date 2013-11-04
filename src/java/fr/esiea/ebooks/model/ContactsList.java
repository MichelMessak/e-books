package fr.esiea.ebooks.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a singleton. It create the contact List used in all the program.
 *
 */

 public final class ContactsList {

     private static volatile ContactsList instance = null;

     private List <Contact> contactsList = new ArrayList<Contact>();

     private ContactsList() {
         super();
     }

     public final static ContactsList getInstance() {

         if (ContactsList.instance == null) {


            synchronized(ContactsList.class) {
              if (ContactsList.instance == null) {
            	  ContactsList.instance = new ContactsList();
              }
            }
         }
         return ContactsList.instance;
     }

     public void addContact (Contact contact){
    	 contactsList.add(contact);
     }

     public int size (){
	return contactsList.size();

     }

     /**
      *
      * This method get back the contact at the position "position" in the contact list
      *
      */

public Contact getContact (int position){

    	 int count = 0;

    	 for (Contact contact : contactsList){
    		 if (count == position)
    			 return contact;

    		 count++;
    	 }

		return null;

      }

/**
 *
 * This method delete a contact of the contact list
 *
 */

	public void deleteContact (int position){

		 contactsList.remove(position);
	}


        //This method permit us to know the contact position (it is a king of equal for the contact objet)
        public int getContactPosition (Contact contact){
            for (int i = 0;i<this.size();i++){
                if (this.getContact(i).getFirstName().equals(contact.getFirstName()))
                    if (this.getContact(i).getLastName().equals(contact.getLastName()))
                        if (this.getContact(i).getBirthday().equals(contact.getBirthday()))
                            if (this.getContact(i).getEmail().equals(contact.getEmail()))
                                if (this.getContact(i).isActif()==contact.isActif())
                                    return i;
            }
            return -1;
        }

 }