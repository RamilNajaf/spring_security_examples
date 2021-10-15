package rc.bootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @GetMapping("index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView view=new ModelAndView();
        view.setViewName("profile/index");
        Principal principal = request.getUserPrincipal();
        view.addObject("user",principal.getName());
        return view;
    }
}
