package kr.ac.ajou.lazybones.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.ajou.lazybones.managers.LogManager;
import kr.ac.ajou.lazybones.managers.NodeManager;
import kr.ac.ajou.lazybones.managers.UserManager;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.NodeHistoryEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;
import kr.ac.ajou.lazybones.repos.entities.UserHistoryEntity;
import kr.ac.ajou.lazybones.templates.AuthorizationForm;
import kr.ac.ajou.lazybones.templates.NodeSensorLogForm;
import kr.ac.ajou.lazybones.templates.Result;
import kr.ac.ajou.lazybones.templates.UserCommandLogForm;

@Controller
public class LogRESTController {

	@Autowired
	UserManager userManager;

	@Autowired
	NodeManager nodeManager;

	@Autowired
	LogManager logManager;

	@RequestMapping(value = "/Log/User/{id}/", method = RequestMethod.POST)
	public @ResponseBody Result getUserCommandLog(@PathVariable("id") String userId, @RequestBody String auth) {

		AuthorizationForm form;

		Result result = new Result();
		ObjectMapper mapper = new ObjectMapper();
		try {
			form = mapper.readValue(auth, AuthorizationForm.class);
		} catch (IOException e) {
			result.setResult("Failed");
			result.setReason("Please confirm the request format.");
			return result;
		}
		String credential = form.getCredential();
		UserEntity user = getUserByCredential(credential);

		List<UserHistoryEntity> logs = logManager.getUserCommandLogsByUser(user);
//		List<UserCommandLogForm> logsToTransfer = new ArrayList<>();
//
//		for (UserHistoryEntity log : logs) {
//			logsToTransfer.add(log.transform());
//		}

		try {
			result.setData(mapper.writeValueAsString(logs));
			result.setResult("Succeed");
		} catch (JsonProcessingException e) {
			result.setResult("Failed");
			result.setReason("Json Parsing failed.");
			return result;
		}

		return result;
	}

	@RequestMapping(value = "/Log/Node/{id}/Command", method = RequestMethod.POST)
	public @ResponseBody Result getNodeCommandLog(@PathVariable("id") Integer nodeId, @RequestBody String auth) {

		AuthorizationForm form;

		Result result = new Result();
		ObjectMapper mapper = new ObjectMapper();
		try {
			form = mapper.readValue(auth, AuthorizationForm.class);
		} catch (IOException e) {
			result.setResult("Failed");
			result.setReason("Please confirm the request format.");
			return result;
		}
		String credential = form.getCredential();
		UserEntity user = getUserByCredential(credential);
		NodeEntity node = nodeManager.findNodeById(nodeId);

		List<UserHistoryEntity> logs = logManager.getUserCommandLogsByNode(user, node);

//		List<UserCommandLogForm> logsToTransfer = new ArrayList<>();
//
//		for (UserCommandLogEntity log : logs) {
//			logsToTransfer.add(log.transform());
//		}

		try {
			result.setData(mapper.writeValueAsString(logs));
			result.setResult("Succeed");
		} catch (JsonProcessingException e) {
			result.setResult("Failed");
			result.setReason("Json Parsing failed.");
			return result;
		}

		return result;
	}

	@RequestMapping(value = "/Log/Node/{id}/Sensor", method = RequestMethod.POST)
	public @ResponseBody Result getNodeSensorLog(@PathVariable("id") Integer nodeId, @RequestBody String auth) {
		
		AuthorizationForm form;

		Result result = new Result();
		ObjectMapper mapper = new ObjectMapper();
		try {
			form = mapper.readValue(auth, AuthorizationForm.class);
		} catch (IOException e) {
			result.setResult("Failed");
			result.setReason("Please confirm the request format.");
			return result;
		}
		String credential = form.getCredential();
		UserEntity user = getUserByCredential(credential);
		NodeEntity node = nodeManager.findNodeById(nodeId);

		List<NodeHistoryEntity> logs = logManager.getNodeDataLog(user, node);
		
//		List<NodeSensorLogForm> logsToTransfer = new ArrayList<>();
//
//		for (NodeSensorLogEntity log : logs) {
//			logsToTransfer.add(log.transform());
//		}

		try {
			result.setData(mapper.writeValueAsString(logs));
			result.setResult("Succeed");
		} catch (JsonProcessingException e) {
			result.setResult("Failed");
			result.setReason("Json Parsing failed.");
			return result;
		}

		return result;
	}

	public UserEntity getUserByCredential(String credential) {
		return userManager.findUserByKeyhash(credential);
	}

}
