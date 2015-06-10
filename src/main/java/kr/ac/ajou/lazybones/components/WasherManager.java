package kr.ac.ajou.lazybones.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import kr.ac.ajou.lazybones.washerapp.Washer.Washer;
import kr.ac.ajou.lazybones.washerapp.Washer.WasherHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
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
	private final String host = "localhost";

	private Map<String, Washer> washers;

	private NamingContextExt ncRef;

	// It connects to orbd and set NamingContext reference.
	// It will be used for adding washers.
	@PostConstruct
	public void setup() throws InvalidName {
		// properties for ORB. You can change hostname and port here.
		washers = new HashMap<>();

		Properties props = new Properties();
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
	}

	public boolean addWasher(String name) {
		Washer washer;
		try {
			washer = WasherHelper.narrow(ncRef.resolve_str(name));
			if (!washer._non_existent()) {
				washers.put(name, washer);
				return true;
			} else
				return false;
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
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
		Washer washer = washers.get(name);
		if (washer != null) {
			if (!(washer._non_existent()))
				washer._release();

			washers.remove(name);
			return true;
		}
		return false;
	}

	public Map<String, Washer> getWashers() {
		Map<String, Washer> map = new HashMap<>();
		for (Entry<String, Washer> item : washers.entrySet()) {
			if (!item.getValue()._non_existent())
				map.put(item.getKey(), item.getValue());
			else
				map.remove(item.getKey());
		}

		return washers;
	}

	public void setWashers(Map<String, Washer> washers) {
		this.washers = washers;
	}

}
