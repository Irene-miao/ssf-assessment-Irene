package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.batch2.ssf.frontcontroller.model.Login;
import ibf2022.batch2.ssf.frontcontroller.services.AuthenticationService;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path="/")
public class FrontController {

	@Autowired
	private AuthenticationService authSvc;

	@PostMapping(path="/login", consumes = "application/x-www-form-urlencoded", produces = "text/html")
	public String saveLogin(@Valid Login login, BindingResult binding , Model model) throws Exception{
		if(binding.hasErrors()){
			return "view0";
		}
		authSvc.authenticate(login);
		
		return "view1";
	}
	
	
	
}
