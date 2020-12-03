package app.security.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeaderLoggingFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logHeader((HttpServletRequest)request);
		chain.doFilter(request, response);
	}

	private void logHeader(HttpServletRequest httpServletRequest) {
		StringBuilder headerData = new StringBuilder();
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		headerData.append("Headers : {\n");
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = httpServletRequest.getHeader(headerName);
			headerData.append("\t").append(headerName).append(" : ").append(headerValue).append("\n");
		}

		headerData.append("}");
		logger.debug(headerData.toString());
	}
}
