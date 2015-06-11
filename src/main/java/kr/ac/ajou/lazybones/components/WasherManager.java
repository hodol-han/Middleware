package kr.ac.ajou.lazybones.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

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
	ReservationQueue reservationQueue;

	private NamingContextExt ncRef;

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

			// Test for Getting registered washers from orbd
			// testGettingWasherFromServer();
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

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
			else
				washerReservationQueues.remove(item.getKey());
		}

		return map;
	}

	/*
	 * This is for test.
	 * Getting registered washers from orbd and into washerReservationQueues.
	 * <reference>
	 * http://www.massapi.com/class/org/omg/CosNaming/BindingListHolder.html
	 * http://www.informit.com/articles/article.aspx?p=23266&seqNum=9
	 */
	public boolean testGettingWasherFromServer() {
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
					washerReservationQueues.put(washerName, reservationQueue);
				}
			}

			// Print washer list for test
			Iterator<String> iterator = washerReservationQueues.keySet()
					.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				System.out.println("key=" + key);
				Reservation[] reservations = washerReservationQueues.get(key)
						.reservations();
				for (Reservation reservation : reservations) {
					System.out.println("who: " + reservation.who
							+ ", duration: " + reservation.duration);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public Map<String, Integer> getWasherSubscriberNumbers() {
		// Test for getting washer from orbd
		//testGettingWasherFromServer();

		Map<String, Integer> map = new HashMap<>();
		for (Entry<String, ReservationQueue> item : washerReservationQueues
				.entrySet()) {
			if (!item.getValue()._non_existent())
				map.put(item.getKey(), item.getValue().size());
			else
				washerReservationQueues.remove(item.getKey());
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

}
