/*
 * Controller for handling User data
 */

package kr.ac.ajou.lazybones.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.ajou.lazybones.managers.UserManager;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	private UserManager userEntityManager;

	@Autowired
	public UserController(UserManager userEntityManagerImpl) {
		super();
		this.userEntityManager = userEntityManagerImpl;
	}

	/*
	 * Show Register page.
	 */
	@RequestMapping(value = "/User/Register", method = RequestMethod.GET)
	public String showRegistrationForm() {
		return "register";
	}

	/*
	 * Process Registration
	 */
	@RequestMapping(value = "/User/Register", method = RequestMethod.POST)
	public String processRegistration(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
			@RequestParam(value = "password") String pwd) {
		try {
			// If ID exists already
			if (userEntityManager.findById(id) != null) {
				System.err.println("Fail to register: same ID already exists");
				return "redirect:/User/Register";
			}

			// For testing
			System.out.println(id + name + pwd);

			// If no ID --> save it.
			userEntityManager.insert(id, name, pwd);

			// For testing
			System.out.println("Sucess to register: " + id + ", " + name + ", " + pwd);

			// If registration succeed, go to login page.
			return "redirect:/User/Login";
		} catch (Exception e) {
			System.err.println("Fail to register");
			return "redirect:/User/Register";
		}
	}

	/*
	 * Show Unregister page
	 */
	@RequestMapping(value = "/User/Unregister", method = RequestMethod.GET)
	public String showUnregisterForm(HttpServletRequest request, Model model) throws IOException {
		return "unregister";
	}

	/*
	 * Process Unregistration
	 */
	@RequestMapping(value = "/User/Unregister", method = RequestMethod.POST)
	public String processUnrregistration(@RequestParam(value = "id") String id,
			@RequestParam(value = "name") String name, @RequestParam(value = "password") String pwd,
			HttpServletRequest request) {
		try {
			// Get and delete user.
			UserEntity user = userEntityManager.findById(id);
			String key = (String) request.getSession().getAttribute("credential");

			if (user.getUserKey().equals(key))
				if (user.getPwd().equals(pwd)) {
					userEntityManager.delete(user);

					// Invalidate session
					request.getSession().invalidate();

					// For testing
					System.out.println("Sucess to unregister: " + id + ", " + name + ", " + pwd);
				} else
					System.out.println("You're trying to hack other user, huh?");
		} catch (Exception e) {
			System.err.println("Fail to unregister");
		}
		return "index";
	}

	/*
	 * Show Login page.
	 */
	@RequestMapping(value = "/User/Login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {

		Boolean isLoggedIn = (Boolean) request.getSession().getAttribute("logininfo");

		if (isLoggedIn != null)
			if (isLoggedIn == true)
				return "redirect:/";

		return "login";
	}

	/*
	 * Process Login
	 */
	@RequestMapping(value = "/User/Login", method = RequestMethod.POST)
	public String processLogin(@RequestParam(value = "id") String id, @RequestParam(value = "password") String pwd,
			HttpServletRequest request, Model model) {
		try {
			if (userEntityManager.isValidLogin(id, pwd)) {
				UserEntity user = userEntityManager.findById(id);

				request.getSession().setAttribute("credential", user.getUserKey());
				request.getSession().setAttribute("logininfo", true);
				request.getSession().setAttribute("userid", user.getUserID());

				return "redirect:/Node/";

			} else {
				System.out.println("Login failed. ");
				model.addAttribute("result", "Login failed! Try Again.");
				return "login";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}

	/*
	 * Logout Process
	 */
	@RequestMapping(value = "/User/Logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// deleting session
			request.getSession().invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	/*
	 * Show Modify page
	 */
	@RequestMapping(value = "/User/Modify", method = RequestMethod.GET)
	public String showModifyForm(HttpServletRequest request, Model model) throws IOException {
		String keyhash = (String) request.getSession().getAttribute("credential");
		UserEntity user = userEntityManager.findUserByKeyhash(keyhash);

		model.addAttribute("user", user);

		return "modify";
	}

	/*
	 * Process Modification
	 */
	@RequestMapping(value = "/User/Modify", method = RequestMethod.POST)
	public String processModify(@RequestParam(value = "name") String name, @RequestParam(value = "password") String pwd,
			HttpServletRequest request) {
		try {
			// Find by user id of session
			UserEntity user = userEntityManager
					.findUserByKeyhash(request.getSession().getAttribute("credential").toString());
					// System.out.println(user.getId() + user.getName() +
					// user.getPwd());

			// Change name and password
			user.setName(name);
			user.setPwd(pwd);

			// Update user
			userEntityManager.update(user);

			// If registration succeed, go to login page.
			return "redirect:/User/Modify";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fail to modify");
			return "redirect:/User/Modify";
		}
	}
}