package kr.ac.ajou.lazybones.components;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import kr.ac.ajou.lazybones.repos.entities.RealReservation;
import kr.ac.ajou.lazybones.washerapp.Washer.Reservation;
import kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueue;
import kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueueHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WasherManager {
	private final String port = "1050";
	private final String host = "210.107.197.213";

	private Map<String, ReservationQueue> washerReservationQueues;

	private Timer timer = new Timer();
	private NamingContextExt ncRef;

	private class RefreshTask extends TimerTask {

		private WasherManager manager;

		public RefreshTask(WasherManager manager) {
			this.manager = manager;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			getWasherFromServer();
		}

		/*
		 * This is for test. Getting registered washers from orbd and into
		 * washerReservationQueues. <reference>
		 * http://www.massapi.com/class/org/omg/CosNaming/BindingListHolder.html
		 * http://www.informit.com/articles/article.aspx?p=23266&seqNum=9
		 */
		private boolean getWasherFromServer() {
			Map<String, ReservationQueue> map = new HashMap<>();
			try {
				BindingListHolder bl = new BindingListHolder();
				BindingIteratorHolder blIt = new BindingIteratorHolder();
				ncRef.list(1000, bl, blIt);
				Binding bindings[] = bl.value;
				ReservationQueue reservationQueue;

				// Store every registered washer and its queue
				for (int i = 0; i < bindings.length; i++) {
					int lastIx = bindings[i].binding_name.length - 1;
					// check to see if this is a naming context with Object type
					if (bindings[i].binding_type == BindingType.nobject) {
						String washerName = bindings[i].binding_name[lastIx].id;
						reservationQueue = ReservationQueueHelper.narrow(ncRef
								.resolve_str(washerName));
						map.put(washerName, reservationQueue);
					}
				}

				// Set current map of ReservationQueue into WasherManager
				manager.setWashers(map);

				// // Print washer list for test
				// Iterator<String> iterator = map.keySet()
				// .iterator();
				// while (iterator.hasNext()) {
				// String key = iterator.next();
				// System.out.println("key=" + key);
				// Reservation[] reservations = map.get(key)
				// .reservations();
				// for (Reservation reservation : reservations) {
				// System.out.println("who: " + reservation.who
				// + ", duration: " + reservation.duration);
				// }
				// }
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	}

	// It connects to orbd and set NamingContext reference.
	// It will be used for adding washers.
	@PostConstruct
	public void setup() throws InvalidName {
		// HashMap of <WasherName, ReservationQueue>
		washerReservationQueues = new HashMap<String, ReservationQueue>();

		// properties for ORB. You can change hostname and port here.
		Properties props = new Properties();

		try {

			props.put("org.omg.CORBA.ORBInitialPort", this.port);
			props.put("org.omg.CORBA.ORBInitialHost", this.host);

			String[] emptyArgs = new String[2];
			emptyArgs[0] = "Welcome to the";
			emptyArgs[1] = "Jungle";

			// STEP 1: create and initialize the ORB
			ORB orb = ORB.init(emptyArgs, props);

			// STEP 2: get the root naming context (naming service)
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			ncRef = NamingContextExtHelper.narrow(objRef);

			// get registered washers from orbd at fixed rate (5000)
			timer.scheduleAtFixedRate(new RefreshTask(this), 0, 5000);

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

	/**
	 * Deprecated due to the automated synchronization
	 * 
	 * @param name
	 * @return
	 */
	public boolean addWasher(String name) {
		System.out.println(name);
		ReservationQueue washer;
		try {
			washer = ReservationQueueHelper.narrow(ncRef.resolve_str(name));
			if (!washer._non_existent()) {
				washerReservationQueues.put(name, washer);
				return true;
			} else
				return false;
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Not found in the Naming Context: " + name);
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Deprecated due to the automated synchronization
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeWasher(String name) {
		ReservationQueue washerQueue = washerReservationQueues.get(name);
		if (washerQueue != null) {
			if (!washerQueue._non_existent())
				washerQueue._release();

			washerReservationQueues.remove(name);
			return true;
		}
		return false;
	}

	public Map<String, ReservationQueue> getReservationQueues() {
		Map<String, ReservationQueue> map = new HashMap<>();
		for (Entry<String, ReservationQueue> item : washerReservationQueues
				.entrySet()) {
			if (!item.getValue()._non_existent())
				map.put(item.getKey(), item.getValue());
		}

		return map;
	}

	public Map<String, Integer> getWasherSubscriberNumbers() {
		// Test for getting washer from orbd
		// testGettingWasherFromServer();
		System.out.println(washerReservationQueues);

		Map<String, Integer> map = new HashMap<>();
		for (Entry<String, ReservationQueue> item : washerReservationQueues
				.entrySet()) {
			try {
				map.put(item.getKey(), item.getValue().size());
			} catch (Exception e) {
				System.out.println("Some problem exists on " + item.getKey());
			}
		}
		System.out.println(map);

		return map;

	}

	public Set<String> getReservationQueueNames() {
		return washerReservationQueues.keySet();
	}

	public ReservationQueue getReservationQueue(String name) {
		return washerReservationQueues.get(name);
	}

	public void setWashers(Map<String, ReservationQueue> washerReservationQueues) {
		this.washerReservationQueues = washerReservationQueues;
	}

	public void enqueue(String washerName, String who, long duration) {
		washerReservationQueues.get("name").enqueue(who, duration);
	}

	public void remove(String washerName, int index) {
		washerReservationQueues.get("name").remove(index);
	}

	public Map<String, Reservation[]> getReservationWaitingTimes() {

		Map<String, Reservation[]> map = new HashMap<>();
		for (Entry<String, ReservationQueue> item : washerReservationQueues
				.entrySet()) {
			if (!item.getValue()._non_existent())
				map.put(item.getKey(), item.getValue()
						.reservationWaitingTimes());
		}

		return map;
	}

	public Map<String, Reservation[]> getReservationWaitingTimesBy(String who) {

		Map<String, Reservation[]> map = new HashMap<>();
		for (Entry<String, ReservationQueue> item : washerReservationQueues
				.entrySet()) {
			if (!item.getValue()._non_existent())
				map.put(item.getKey(), item.getValue()
						.reservationWaitingTimesBy(who));
		}

		return map;
	}

	public Map<String, RealReservation[]> getRealReservationsBy(String who) {
		Map<String, RealReservation[]> map = new HashMap<>();
		Calendar now = Calendar.getInstance();

		for (Entry<String, ReservationQueue> entry : this.washerReservationQueues
				.entrySet()) {
			String washerName = entry.getKey();
			ReservationQueue queue = entry.getValue();
			long accumulatedTime = 0l;
			
			List<RealReservation> list = new ArrayList<>();
			for (Reservation reservation : queue.reservations()) {
				Calendar temp = (Calendar) now.clone();
				
				temp.add(Calendar.MINUTE, (int) accumulatedTime);
				Date from = (Date) temp.getTime().clone();
				temp.add(Calendar.MINUTE, (int)reservation.getDuration());
				Date to = (Date) temp.getTime().clone();
				
				accumulatedTime += reservation.getDuration();
										
				if(reservation.getWho().equals(who)){				
					RealReservation realReservation = new RealReservation(washerName, who, from, to);
					list.add(realReservation);
				}
			}
			map.put(washerName, list.toArray(new RealReservation[list.size()]));
		}
		return map;

	}

}
