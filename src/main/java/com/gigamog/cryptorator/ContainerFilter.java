package com.gigamog.cryptorator;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebFilter(filterName = "AResponseFilter", urlPatterns = { "/*" })
public class ContainerFilter implements javax.servlet.Filter {



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse  resp = (HttpServletResponse) response;

		addCORS(resp);

		if (req.getMethod().equals("OPTIONS")) {
			resp.setHeader("Content-Length", "0");
			OutputStream os = resp.getOutputStream();
			os.write("".getBytes());
			os.flush();
			os.close();
		}
		
		
		chain.doFilter(request, response);
		
	}

	public void addCORS(HttpServletResponse resp) {
		resp.setHeader("Server", "Microsoft-IIS/7.0");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		resp.setHeader("Access-Control-Max-Age", "3000");

	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}