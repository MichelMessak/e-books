package fr.esiea.ebooks.control.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to manage the end of a session
 * @author Michel Messak
 */
public class LogoutController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Redirect to the index page
            ModelAndView mv = new ModelAndView("index");
            return mv;
        
    }
}
