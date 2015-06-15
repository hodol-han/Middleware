package kr.ac.ajou.lazybones.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ac.ajou.lazybones.components.WasherManager;
import kr.ac.ajou.lazybones.washerapp.Washer.Reservation;
import kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueue;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReservationController {

	@Autowired
	WasherManager washerManager;
	
	private Map<String, Reservation[]> reservations = null;

	@RequestMapping(value = "/Reservation", method = RequestMethod.GET)
	public String showReservation(HttpServletRequest request, Model model) {

		// For getting washer name list
		Map<String, Integer> map = washerManager.getWasherSubscriberNumbers();
		
		// User ID (session)
		String uid = (String) request.getSession().getAttribute("userid");
		
		// Iter for each washer
		for( String key : map.keySet() ){
			ReservationQueue queue = washerManager.getReservationQueue(key);
			Reservation[] reservation = queue.reservationsBy(uid);
			reservations.put(key, reservation);
        }
		
		model.addAttribute("reservations", reservations);

		return "washerList";
	}
}
