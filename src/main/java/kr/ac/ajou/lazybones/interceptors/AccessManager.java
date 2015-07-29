/*
 * Login Intercepter.
 */

package kr.ac.ajou.lazybones.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.ac.ajou.lazybones.managers.UserManager;

public class AccessManager extends HandlerInterceptorAdapter {

	@Autowired
	UserManager userManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			// For testing
			String credential = (String) request.getSession().getAttribute("credential");

			// Get session --> if null --> Return to Login page.
			if (credential == null) {
				response.sendRedirect("/User/Login");
				return false;
			}

			System.out.println("LoginInterceptor called. Credential: " + credential);

			if (userManager.findUserByKeyhash(credential) == null) {
				request.getSession().invalidate();
				response.sendRedirect("/User/Login");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}