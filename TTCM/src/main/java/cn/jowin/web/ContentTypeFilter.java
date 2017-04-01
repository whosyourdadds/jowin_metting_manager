package cn.jowin.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ContentTypeFilter 
	implements Filter{
	
	private String contentType;
	
	public void init(FilterConfig cfg) throws ServletException {
		contentType=
			cfg.getInitParameter("contentType");
	}
	
	public void doFilter(ServletRequest req, 
			ServletResponse res, 
			FilterChain chain)
			throws IOException, ServletException {
		System.out.println("contentType:"+contentType);
		res.setContentType(contentType); 
		chain.doFilter(req, res);
	}
	
	public void destroy() {
		
	}
}
