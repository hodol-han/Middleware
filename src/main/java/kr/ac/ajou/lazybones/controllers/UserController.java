/*
 * Controller for handling User data
 */

package kr.ac.ajou.lazybones.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.ajou.lazybones.entitymanager.UserEntityManagerImpl;
import kr.ac.ajou.lazybones.repos.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	private UserEntityManagerImpl userEntityManagerImpl;

	@Autowired
	public UserController(UserEntityManagerImpl userEntityManagerImpl) {
		super();
		this.userEntityManagerImpl = userEntityManagerImpl;
	}

	/*
	 *  Show Register page.
	 */
	@RequestMapping(value = "/User/Register", method = RequestMethod.GET)
	public String showRegistrationForm() {
		return "register";
	}

	/*
	 *  Process Registration
	 */
	@RequestMapping(value = "/User/Register", method = RequestMethod.POST)
	public String processRegistration(@RequestParam(value = "id") String id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "password") String pwd) {
		try {
			// If ID exists already
			if(userEntityManagerImpl.findById(id) != null) {
				System.err.println("Fail to register: same ID already exists");
				return "redirect:/User/Register";
			}
			
			// For testing
			System.out.println(id + name + pwd);
			
			// If no ID --> save it.
			userEntityManagerImpl.insert(id, name, pwd);
			
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
	 *  Show Unregister page
	 */
		@RequestMapping(value = "/User/Unregister", method = RequestMethod.GET)
		public String showUnregisterForm(HttpServletRequest request, Model model)
				throws IOException {
			return "unregister";
		}
	
	/*
	 *  Process Unregistration
	 */
		@RequestMapping(value = "/User/Unregister", method = RequestMethod.POST)
		public String processUnrregistration(@RequestParam(value = "id") String id,
				@RequestParam(value = "name") String name,
				@RequestParam(value = "password") String pwd, HttpServletRequest request) {
			try {
				// Get and delete user.
				User user = new User(id, name, pwd);
				userEntityManagerImpl.delete(user);
				
				// Invalidate session
				request.getSession().invalidate();
				
				//For testing
				System.out.println("Sucess to unregister: " + id + ", " + name + ", " + pwd);
			} catch (Exception e) {
				System.err.println("Fail to unregister");
			}
			return "index";
		}

	/*
	 *  Show Login page.
	 */
	@RequestMapping(value = "/User/Login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	/*
	 *  Process Login 
	 */
	@RequestMapping(value = "/User/Login", method = RequestMethod.POST)
	public String processLogin(@RequestParam(value = "id") String id, @RequestParam(value = "password") String pwd, HttpServletRequest request) {
		try {
			User user = userEntityManagerImpl.findById(id);
			
			// If user does not exist
			if(user == null) {
				//For testing
				System.out.println("Login failed: " + id + ", " + pwd + "- User doesn't exists");
		
				return "redirect:/User/Login";
			}
			
			// if pwd is correct
			if(pwd.equals(user.getPwd())) {
				System.out.println("pwd correct");
				
				// record session
				request.getSession().setAttribute("logininfo", true);
				request.getSession().setAttribute("userid", user.getId());
				
				return "redirect:/";
			}
			
			// if pwd is not correct
			System.out.println("pwd not correct");
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/User/Login";
		}

		return "redirect:/User/Login";
	}

	/*
	 *  Login check
	 */
	@RequestMapping(value = "/User/Logincheck")
	public void loginCheck(HttpServletRequest request) {
		// making session
		request.getSession().setAttribute("logininfo", true);
	}

	/*
	 *  Logout Process
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
	 *  Show Modify page
	 */
		@RequestMapping(value = "/User/Modify", method = RequestMethod.GET)
		public String showModifyForm(HttpServletRequest request, Model model) throws IOException {
			User user = userEntityManagerImpl.findById(request.getSession().getAttribute("userid").toString());
			model.addAttribute(user);
			return "modify";
		}
	
	/*
	 *  Process Modification
	 */
	@RequestMapping(value = "/User/Modify", method = RequestMethod.POST)
	public String processModify(@RequestParam(value = "name") String name,
			@RequestParam(value = "password") String pwd, HttpServletRequest request) {
		try {
			// Find by user id of session
			User user = userEntityManagerImpl.findById(request.getSession().getAttribute("userid").toString());
			System.out.println(user.getId() + user.getName() + user.getPwd());
			
			// Change name and password
			user.setName(name);
			user.setPwd(pwd);
			
			// Update user
			userEntityManagerImpl.update(user);
			
			// If registration succeed, go to login page.
			return "redirect:/User/Modify";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fail to modify");
			return "redirect:/User/Modify";
		}
	}
}