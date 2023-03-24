package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.batch2.ssf.frontcontroller.model.Login;
import ibf2022.batch2.ssf.frontcontroller.services.AuthenticationService;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path="/", produces = "text/html")
public class FrontController {

	@Autowired
	private AuthenticationService authSvc;

	@GetMapping
	public String landPage(Model m, Login l) {
		
		m.addAttribute("login", l);
		return "view0";
	}

	@PostMapping(path="/login")
	public String saveLogin(@Valid Login login, BindingResult binding , Model model) throws Exception{
		if(binding.hasErrors()){
			return "view0";
		}
		String resp = authSvc.authenticate(login);
		login.jsonStrToObj(resp);
		String auth = login.getAuth();
		if (!auth.isEmpty()){
			return "redirect:/protected";
		}
		return "view0";
	}

	@GetMapping(path="/protected")
	public String showView(Model m) {
		return "view1";
	}
	
	
	
}
