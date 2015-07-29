package kr.ac.ajou.lazybones.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.ajou.lazybones.managers.UserManager;
import kr.ac.ajou.lazybones.templates.GetCredentialForm;

@Controller
public class UserRESTController {

	@Autowired
	UserManager userManager;

	@RequestMapping(value = "/User/Credential", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getCredential(@RequestBody String formString) {
		// Echo to test

		ObjectMapper mapper = new ObjectMapper();
		GetCredentialForm form;
		try {
			form = mapper.readValue(formString, GetCredentialForm.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"result\":\"failed\", \"reason\":\"failed to getting credential.\"}";
		}

		String id = form.getId();
		String pwd = form.getPassword();

		if (userManager.isValidLogin(id, pwd)) {
			// TODO: HARDCODED BLOCK
			return "{\"result\":\"succeed\",\"credential\":\"" + userManager.findById(id).getUserKey() + "\"}";
		} else
			return "{\"result\":\"failed\", \"reason\":\"failed to getting credential.\"}";

	}

}
