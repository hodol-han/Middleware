package kr.ac.ajou.lazybones.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ac.ajou.lazybones.components.NodeManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for washer. List washers, view detail of washer, make a reservation.
 * @author AJOU
 *
 */
@Controller
public class NodeController {

	
	@Autowired
	NodeManager nodeManager;

	@RequestMapping(value = "/Node", method = RequestMethod.GET)
	public String node(){
		return "redirect:/Node/List";
	}
	
	@RequestMapping(value = "/Node/List", method = RequestMethod.GET)
	public String getNodes(){

		
		return "nodeList";
	}
	
	@RequestMapping(value = "/Node/Register", method = RequestMethod.GET)
	public String registerNode(){
		
		return "nodeForm";
	}

	
	

}
