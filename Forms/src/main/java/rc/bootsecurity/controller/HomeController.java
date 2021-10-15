package rc.bootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("index")
    public String index(){
        return "index";
    }



    @GetMapping("login")
    public String login(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        if(principal!=null){
            return "redirect:/index";
        }

        return "login";
    }
}
