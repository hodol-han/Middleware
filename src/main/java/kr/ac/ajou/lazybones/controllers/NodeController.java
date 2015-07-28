package kr.ac.ajou.lazybones.controllers;

import java.io.IOException;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.ajou.lazybones.managers.NodeManager;
import kr.ac.ajou.lazybones.managers.RequesterManager;
import kr.ac.ajou.lazybones.managers.UserManager;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;
import kr.ac.ajou.lazybones.templates.QueryForm;
import kr.ac.ajou.lazybones.templates.Result;

@Controller
public class NodeController {

	@Autowired
	NodeManager nodeManager;

	@Autowired
	UserManager userManager;

	@Autowired
	RequesterManager requesterManager;

	@RequestMapping(value = "/Node", method = RequestMethod.GET)
	public String node() {
		return "redirect:/Node/List";
	}

	@RequestMapping(value = "/Node/List", method = RequestMethod.GET)
	public String getNodes(HttpServletRequest request, Model model) {

		UserEntity user = this.findUser(request);

		model.addAttribute("nodes", nodeManager.findNodesByOwner(user));

		return "nodeList";
	}

	@RequestMapping(value = "/Node/Register", method = RequestMethod.GET)
	public String registerNode() {

		return "nodeForm";
	}

	@RequestMapping(value = "/Node/Register", method = RequestMethod.POST)
	public String registerNode(@RequestParam(value = "serial") String serial,
			@RequestParam(value = "product-name") String productName, @RequestParam(value = "node-name") String name,
			HttpServletRequest request, Model model) {

		// TODO: Get user id using issued token.
		UserEntity user = this.findUser(request);

		try {
			nodeManager.registerNode(user, serial, productName, name);
		} catch (Exception e) {
			model.addAttribute("result", "Failed to register.");
			return "nodeForm";
		}
		return "redirect:/Node/List";
	}

	@RequestMapping(value = "/Node/{id}/Unregister", method = RequestMethod.POST)
	public String unregisterNode(@PathVariable("id") Long nid, HttpServletRequest request) {

		// TODO: Get user id using issued token.
		UserEntity user = this.findUser(request);
		NodeEntity node = nodeManager.findById(nid);
		if (node == null)
			return "redirect:/Node/List";

		if (node.getOwner().getId().equals(user.getId()))
			nodeManager.unregisterNode(nid);

		return "redirect:/Node/List";
	}

	private UserEntity findUser(HttpServletRequest request) {
		String credential = (String) request.getSession().getAttribute("credential");
		UserEntity user = userManager.findUserByKeyhash(credential);

		return user;
	}

}
