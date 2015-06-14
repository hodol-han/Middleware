package kr.ac.ajou.lazybones.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WasherController {

	@Autowired
	WasherManager washerManager;

	@RequestMapping(value = "/Washer", method = RequestMethod.GET)
	public String showWashers(Model model) {

		Map<String, Integer> map = washerManager.getWasherSubscriberNumbers();

		model.addAttribute("washers", map);

		return "washerList";
	}

	@RequestMapping(value = "/Washer/Detail/{Name}", method = RequestMethod.GET)
	public String showWasherDetail(@PathVariable("Name") String name,
			Model model) {
		ReservationQueue queue = washerManager.getReservationQueue(name);

		Reservation[] reservations = queue.reservations();
		System.out.println(reservations);
		
		model.addAttribute("name", name);
		model.addAttribute("size", reservations.length);
		model.addAttribute("reservations", reservations);

		return "washerDetail";
	}

	@RequestMapping(value = "/Washer/Enqueue/{Name}", method = RequestMethod.POST)
	public String enqueueReservation(@PathVariable("Name") String name,
			@RequestParam(value = "duration") long duration, Model model,
			HttpServletRequest request) {

		String who = (String) request.getSession().getAttribute("userid");

		ReservationQueue queue = washerManager.getReservationQueue(name);
		queue.enqueue(who, duration);

		return "redirect:/Washer/Detail/" + name;
	}

	@RequestMapping(value = "/Washer/Cancel/{Name}/{Index}", method = RequestMethod.POST)
	public String cancelReservation(@PathVariable("Name") String name,
			@PathVariable("Index") int index, Model model,
			HttpServletRequest request) {

		ReservationQueue queue = washerManager.getReservationQueue(name);
		queue.remove(index);

		return "redirect:/Washer/Detail/" + name;
	}

	
	@RequestMapping(value = "/Washer/Register/{Name}", method = RequestMethod.GET)
	@ResponseBody
	public String register(@PathVariable("Name") String name) {
		// System.out.println(name);

		if (washerManager.addWasher(name))
			return "OK";
		else
			return "Failed";
	}

	@RequestMapping(value = "/Washer/Unregister/{Name}", method = RequestMethod.GET)
	@ResponseBody
	public String unregister(@PathVariable("Name") String name) {
		if (washerManager.removeWasher(name))
			return "OK";
		else
			return "Failed";
	}

}
