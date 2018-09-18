package com.mastering.spring.zuul.microzuulapigateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class StaticFilter extends ZuulFilter {
 
	private static Logger log = LoggerFactory.getLogger(StaticFilter.class);
	
    @Override
    public String filterType() {
        return "pre";
    }
 
    @Override
    public int filterOrder() {
        return 0;
    }
 
    @Override
    public boolean shouldFilter() {
        String path = RequestContext.getCurrentContext().getRequest().getRequestURI();
        log.info("##################[ this path is ]: "+path);
        return "/micro-a/random".equals(path);
    }
 
    @Override
    public Object run() {
 
        RequestContext ctx = RequestContext.getCurrentContext();
        // Set the default response code for static filters to be 200
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        // first StaticResponseFilter instance to match wins, others do not set body and/or status
        if (ctx.getResponseBody() == null) {
        	//message를 유연하게 변경 가능하도록 
            ctx.setResponseBody("This Service is unavailable !!!!! \n You should call your Manager!");
            ctx.setSendZuulResponse(false);
        }
        return null;
    }
}
