package fr.esiea.ebooks.util;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;

/**
 * Util for FileControl
 * @author Michel Messak
 */
public class Util {

    public static String GetSHA256(String data) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(data.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : hash) {

                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Object createClassInstance(String className, Object[] args) {
        if (args.length > 0) {
            Class[] argClasses = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argClasses[i] = args[i].getClass();
            }
            return createClassInstance(className, args, argClasses);
        } else {
            return createClassInstance(className, args, new Class[]{});
        }
    }

    public static Object createClassInstance(String className, Object[] args, Class[] argClasses) {
        Class esClass;

        try {
            esClass = Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {

            return null;
        }

        Constructor esInstConst;
        try {
            
            esInstConst = esClass.getConstructor(argClasses);
        } catch (java.lang.NoSuchMethodException e) {
           return null;
        }

        Object obj = null;
        try {
            obj = esInstConst.newInstance(args);
        } catch (java.lang.InstantiationException e) {
             return null;
        } catch (java.lang.IllegalAccessException e) {
            return null;
        } catch (java.lang.reflect.InvocationTargetException e) {
           e.getTargetException().printStackTrace();
            return null;
        }

        return obj;
    }

    public static Object invokeClassMethod(Object obj, String methodName, Object[] args) {
        Class[] classArgs = null;
        if (args != null) {
            classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
        }
        Object returns = invokeClassMethod(obj, methodName, args, classArgs);
        classArgs = null;
        return returns;
    }

    public static Object invokeClassMethod(Object obj, String methodName, Object[] args, Class[] classArgs) {
        Class objClass;
        Object ret;
        objClass = obj.getClass();

        Method method;
        try {
            method = objClass.getMethod(methodName, classArgs);
        } catch (NoSuchMethodException e) {
            return null;
        }

        try {
            ret = method.invoke(obj, args);
        } catch (java.lang.IllegalAccessException e) {
            
            return null;
        } catch (java.lang.reflect.InvocationTargetException e) {
           
            return null;
        }
        return ret;
    }

    

    public static String[] GetColumnNamesFromQuery(String query) {
        int ini = query.toUpperCase().indexOf("SELECT");
        int fin = query.toUpperCase().indexOf("FROM");
        if (ini >= 0 && fin >= 0) {
            String fields = query.substring(ini + 6, fin - 1).trim();
            return fields.split(",");
        }
        return null;
    }

    public static String replaceValuesHTML(String chain, int ID) throws Exception {
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

    public static String Empty(Object obj)
    {
        if(obj==null) return ""; else return obj.toString();
    }
}
