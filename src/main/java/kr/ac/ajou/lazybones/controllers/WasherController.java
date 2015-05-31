package kr.ac.ajou.lazybones.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WasherController {
	
	@RequestMapping(value = "/Washer", method = RequestMethod.GET)
	public String showWashers(Model model) {
		return "washerList";
	}

	@RequestMapping(value = "/Washer/Detail/{ID}", method = RequestMethod.GET)
	public String showWasherDetail(@PathVariable("ID") String ID, Model model) {
		return "washerDetail";
	}

}
