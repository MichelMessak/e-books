package fr.esiea.ebooks.control.security;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller for the Authentication
 * @author Michel Messak
 */
public class AuthController implements Controller {

    ContactsList contactList = ContactsList.getInstance();

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
       /* try {

            String user_id = (String) request.getParameter("user_id");
            String user_pwd = (String) request.getParameter("user_pwd");

                fc4Repository.Init();

                if (user_id == null || user_pwd == null) {
                    return SessionManager.getLoginView(request, "L'utilisateur n'est pas enregistré");
                }

                String userIp = SessionManager.getIp(request);

                HttpSession session = request.getSession(false);
                String lastUserID = null;
                Integer lastUserAttempt = 0;
                if (session != null) {
                    lastUserID = (String) session.getAttribute("user_id_last");
                    lastUserAttempt = (Integer) session.getAttribute("user_id_count");

                    if (lastUserID != null) {
                        if (lastUserID.equals(user_id)) {
                            if (lastUserAttempt == null) {
                                lastUserAttempt = 0;
                            }

                            int maxAttempts = fc4Repository.getMaxFailAttempts();
                            if (lastUserAttempt >= maxAttempts ) {

                                session.setAttribute("user_id_count", 0);
                                    if (UserDAO.isUserExist(user_id)){
                                        UserDAO.Disable(user_id);
                                        ActivityDAO.insert(user_id, "L'utilisateur " + user_id + " est devenu inactif pour avoir atteint le seuil maximal d'identification erronées", userIp);

                                        return SessionManager.getLoginView(request, "L'utilisateur est devenu inactif pour avoir atteint le seuil maximal d'identification erronées");
                                        }

                                    else {

                                        ModelAndView mv = new ModelAndView("index");
                                        mv.addObject("error", "Utilisateur/Mot de passe invalide" );
                                        Log.write("Identification incorect pour l'utilisateur [" + user_id + "]");
                                        return mv;

                                    }


                            } lastUserAttempt++;
                            session.setAttribute("user_id_count", lastUserAttempt);
                        } else {
                            session.setAttribute("user_id_last", user_id);
                            session.setAttribute("user_id_count", (Integer) 1);
                        }
                    } else {
                        session.setAttribute("user_id_last", user_id);
                        session.setAttribute("user_id_count", (Integer) 1);
                    }
                } else {
                    session = request.getSession(true);
                    session.setAttribute("user_id_last", user_id);
                    session.setAttribute("user_id_count", (Integer) 1);
                }


                User user = UserDAO.Authenticate(user_id, user_pwd);
                if (user != null) {

                    if ("I".equals(user.getStatus())) {
                        ModelAndView mv = new ModelAndView("index");
                        Log.write("Tentative de connexion échoué car l'utilisateur [" + user_id + "] est inactif");
                        mv.addObject("error", "Utilisateur inactif.");
                        return mv;
                    }

                    if (user.isReset_password()) {
                        Log.write("Changement de mot de passe obligatoire pour l'utilisateur [" + user_id + "]");
                        user.setPassword(null);
                        user.setConfirmationPassword(null);
                        RedirectView rv = new RedirectView("setPassword.form");
                        ModelAndView mv = new ModelAndView(rv);
                        session = request.getSession(true);
                        session.setAttribute("user_id", user_id);
                        session.setAttribute("isReset_password", user.isReset_password());
                        session.setAttribute("user_id_tochange", user_id);

                        return mv;
                    }

                    session = request.getSession(true);
                    session.setAttribute("user_id", user_id);
                    String user_name = user.getName();
                    session.setAttribute("user_name", user_name);*/

                    contactList.addContact(new Contact("Messak", "Michel", "michel.messak@gmail.com", new Date (91, 05, 10), true));
                    contactList.getContact(0).setAddress("36", "Balard", "75015", "Paris");
                     contactList.addContact(new Contact("Dispa", "Cécile", "dispas@et.esiea.fr", new Date (88, 05, 24), true));
                      contactList.addContact(new Contact("Béjuit", "Thomas", "bejuit@et.esiea.fr", new Date (91, 0, 18), true));
                      
                    ModelAndView mv = new ModelAndView("redirect:reportContact.do");
                    mv.addObject("isFilterSubmit", "Consult");

                    return mv;
                } /*else {
                    session = request.getSession(false);
                    lastUserAttempt = 0;
                    if (session != null) {
                        lastUserAttempt = (Integer) session.getAttribute("user_id_count");
                    }

                    ModelAndView mv = new ModelAndView("index");
                    mv.addObject("error", "Utilisateur/Mot de passe incorrect");
                    Log.write("Identification incorrect pour l'utilisateur [" + user_id + "]");
                    return mv;
                }


        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }

    }*/

}
