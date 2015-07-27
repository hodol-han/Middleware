package kr.ac.ajou.lazybones.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.ajou.lazybones.managers.NodeManager;
import kr.ac.ajou.lazybones.managers.UserManager;

@Controller
public class NodeController {

	@Autowired
	NodeManager nodeManager;

	@Autowired
	UserManager userManager;

	@RequestMapping(value = "/Node", method = RequestMethod.GET)
	public String node() {
		return "redirect:/Node/List";
	}

	@RequestMapping(value = "/Node/List", method = RequestMethod.GET)
	public String getNodes(HttpServletRequest request, Model model) {

		String userId = (String) request.getSession().getAttribute("userid");

		model.addAttribute("nodes", nodeManager.findNodesByOwner(userId));

		return "nodeList";
	}

	@RequestMapping(value = "/Node/Register", method = RequestMethod.GET)
	public String registerNode() {

		return "nodeForm";
	}

	@RequestMapping(value = "/Node/Register", method = RequestMethod.POST)
	public String registerNode(@RequestParam(value = "serial") String serial,
			@RequestParam(value = "product-name") String productName, @RequestParam(value = "node-name") String name,
			HttpServletRequest request) {

		// TODO: Get user id using issued token.
		String userId = (String) request.getSession().getAttribute("userid");

		nodeManager.registerNode(userId, serial, productName, name);

		return "redirect:/Node/List";
	}

	@RequestMapping(value = "/Node/{id}/", method = RequestMethod.GET)
	public @ResponseBody String query(@PathVariable("ID") Long nodeId, @RequestBody String query) {
		// Echo to test

		return nodeManager.queryToNode(nodeId, query);
	}

}
