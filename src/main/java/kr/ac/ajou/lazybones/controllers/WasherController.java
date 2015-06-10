package kr.ac.ajou.lazybones.controllers;

import java.util.Map;

import kr.ac.ajou.lazybones.components.WasherManager;
import kr.ac.ajou.lazybones.washerapp.Washer.Reservation;
import kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueue;
import kr.ac.ajou.lazybones.washerapp.Washer.Washer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WasherController {

	@Autowired
	WasherManager washerManager;

	@RequestMapping(value = "/Washer", method = RequestMethod.GET)
	public String showWashers(Model model) {

		Map<String, ReservationQueue> map = washerManager.getReservationQueues();
		model.addAttribute("washers", map);

		return "washerList";
	}

	@RequestMapping(value = "/Washer/Detail/{Name}", method = RequestMethod.GET)
	public String showWasherDetail(@PathVariable("Name") String name,
			Model model) {
		Map<String, ReservationQueue> map = washerManager.getReservationQueues();
		ReservationQueue reservationQueue = map.get(name);
		if (reservationQueue == null) {
			// ERROR HANDLING
		}
		model.addAttribute("queue", reservationQueue);

		return "washerDetail";
	}
	
	@RequestMapping(value = "/Washer/Register/{Name}", method = RequestMethod.GET)
	@ResponseBody
	public String register(@PathVariable("Name") String name){
		//System.out.println(name);
		
		if(washerManager.addWasher(name))
			return "OK";
		else
			return "Failed";
	}

	@RequestMapping(value = "/Washer/Unregister/{Name}", method = RequestMethod.GET)
	@ResponseBody
	public String unregister(@PathVariable("Name") String name){
		if(washerManager.removeWasher(name))
			return "OK";
		else
			return "Failed";
	}

}
