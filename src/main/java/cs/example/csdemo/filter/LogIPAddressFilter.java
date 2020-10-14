package cs.example.csdemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import brave.Tracer;
import cs.example.csdemo.utils.Constraint.LogConstraint;
@Configuration
public class LogIPAddressFilter extends OncePerRequestFilter {
	
	@Autowired
	private Tracer tracer;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ThreadContext.put(LogConstraint.IP_KEY.value(), request.getRemoteAddr());
		ThreadContext.put(LogConstraint.REQUEST_ID.value(), tracer.currentSpan().context().traceIdString());
		filterChain.doFilter(request, response);
		ThreadContext.clearAll();
	}

}
