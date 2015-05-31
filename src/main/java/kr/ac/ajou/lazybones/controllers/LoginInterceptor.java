package kr.ac.ajou.lazybones.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("LoginInterceptor called. logininfo session:"
					+ request.getSession().getAttribute("logininfo")
					+ " / userid: "
					+ request.getSession().getAttribute("userid"));

			// store current page path in session
			// request.getSession().setAttribute("currentPage",
			// request.getPathInfo());

			if (request.getSession().getAttribute("logininfo") == null) {
				request.setAttribute("savedUrl", request.getRequestURI());				
				response.sendRedirect("/WasherMan/User/Login");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}