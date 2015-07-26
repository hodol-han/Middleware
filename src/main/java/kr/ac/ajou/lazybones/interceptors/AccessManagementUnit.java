/*
 * Login Intercepter.
 */

package kr.ac.ajou.lazybones.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AccessManagementUnit extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			//For testing
			System.out.println("LoginInterceptor called. logininfo session:"
					+ request.getSession().getAttribute("logininfo")
					+ " / userid: "
					+ request.getSession().getAttribute("userid"));

			// Get session --> if null --> Return to Login page. 
			if (request.getSession().getAttribute("logininfo") == null) {
				request.setAttribute("savedUrl", request.getRequestURI());				
				response.sendRedirect("/Middleware/User/Login");
				
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}