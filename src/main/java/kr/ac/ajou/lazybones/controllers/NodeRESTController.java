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

import kr.ac.ajou.lazybones.managers.LogManager;
import kr.ac.ajou.lazybones.managers.NodeManager;
import kr.ac.ajou.lazybones.managers.RequesterManager;
import kr.ac.ajou.lazybones.managers.UserManager;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;
import kr.ac.ajou.lazybones.templates.QueryForm;
import kr.ac.ajou.lazybones.templates.Result;

@Controller
public class NodeRESTController {

	@Autowired
	UserManager userManager;
	
	@Autowired
	RequesterManager requesterManager;

	@Autowired
	LogManager logManager;
	
	@Autowired
	NodeManager nodeManager;
	
	@RequestMapping(value = "/Node/{id}/Query", method = RequestMethod.POST)
	public @ResponseBody Result query(@PathVariable("id") String nodeId, @RequestBody String queryFormat, HttpServletRequest request) {
		// Echo to test
		Result result = new Result();
		
		ObjectMapper mapper = new ObjectMapper();
		QueryForm query;
		try {
			query = mapper.readValue(queryFormat, QueryForm.class);
		} catch (IOException e) {
			e.printStackTrace();
			result.setResult("Failed");
			result.setReason("Unable to parse query. Please confirm the query format.");
			return result;
		}
		
		UserEntity user = userManager.findUserByKeyhash(query.getCredential());
		NodeEntity node = nodeManager.findNodeById(nodeId);
		String command = query.getCommand();
		
		if(user == null || node == null){
			result.setResult("Failed");
			result.setReason("The user or the node is not exists.");
			return result;			
		}
			
		
		logManager.logUserCommand(user, node, command);
				
		return requesterManager.queryToNode(user, node, command);
	}
	
	

}
