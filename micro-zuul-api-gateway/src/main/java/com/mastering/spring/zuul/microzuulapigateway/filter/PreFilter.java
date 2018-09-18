package com.mastering.spring.zuul.microzuulapigateway.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.env.Environment;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.google.gson.Gson;
import com.netflix.discovery.EurekaClient;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

@RefreshScope
public class PreFilter extends ZuulFilter{
	@Autowired
	private Environment environment;
	@Autowired
	private EurekaClient discoveryClient;
	
	@Value("${canaryuser}") 
	private String canaryuser;
	@Value("${canaryflag}") 
	private String canaryflag;

	@Override
	public boolean shouldFilter() {
		
		String path = RequestContext.getCurrentContext().getRequest().getRequestURI();
        System.out.println("##################[ this path is ]: "+path);
        return "/student/echoStudentName/jungpil".equals(path);
        
	}
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return (FilterConstants.SEND_FORWARD_FILTER_ORDER + 1);
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
        JSONObject jsonObject = null;
        
        String line = null;
        StringBuffer jb = new StringBuffer();
        
        //String input = makeRawString(request);
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
              jb.append(line);
          } catch (Exception e) { /*report an error*/ }

          try {
        	  jsonObject = new JSONObject(jb.toString());
        			  
              String tempStr = jsonObject.getString("user");
              System.out.println("tempStr ==>"+tempStr);
              

          } catch (JSONException e) {
            // crash and burn
            e.printStackTrace();
          }
        
        
        try {
             
			if (isCanary(jsonObject))
			{
				/*String newUri ="";
				
				System.out.println("!!!!!!!!appname : " + request.getParameter("service"));
				//newUri=changeToCanary(request.getRequestURI());
				newUri="/student-canary/echoStudentName/jungpil";
				//newUri="/student/echoStudentName/jungpil";
				ctx.set("requestURI", newUri);*/
				
				String reqUri = request.getRequestURI();
				String serviceName = getServiceName(reqUri);
				
				serviceName = getCanaryName(serviceName);
				// Eureka service name is upper case
				serviceName = serviceName.toUpperCase();
				
				String mockURL = discoveryClient.getApplication(serviceName).getInstances().get(0).getHomePageUrl();
				ctx.setRouteHost(new URL(mockURL));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("[Inside preFilter]Request Methos :"+ctx.getRequest().getMethod() + " Request URL:" + ctx.getRequest().getRequestURI().toString());
		return null;
	}
	private String[] getCanaryUserList(){
		String[] userList= canaryuser.split(",");
		System.out.println("user raw list = "+canaryuser);
		return userList;
		
	}
	private String getServiceName(String uri) {
		int firstIndex=uri.indexOf("/");
		firstIndex++;
		int targetIndex=uri.indexOf("/", firstIndex);
		
		return uri.substring(firstIndex,targetIndex);
	}
	private String getCanaryName(String org) {
		
		return org.concat("-canary");
		
	}
	
	private String makeRawString(HttpServletRequest request) {
		StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
		String line;
		String result = null;
		
		try {
			reader = request.getReader();
			while((line = reader.readLine()) != null){
			    buffer.append(line);
			    
			    System.out.println("buffer.toString()===>"+buffer.toString());    
			}
			
			result = buffer.toString();
	        
			    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
       
	}
	
	private String changeToCanaryURI(String uri) {
		int firstIndex=uri.indexOf("/");
		firstIndex++;
		int targetIndex=uri.indexOf("/", firstIndex);
		System.out.println(uri.substring(0, targetIndex) + "-canary" + uri.substring(targetIndex, uri.length()));
		return  uri.substring(0, targetIndex) + "-canary" + uri.substring(targetIndex, uri.length());
	}
	
	private boolean isCanary(JSONObject jsonObject) throws JSONException {
		
		String[] userList=getCanaryUserList();
		System.out.println("userList = " + userList.toString() + " canaryflag = " + canaryflag);
		if(canaryflag.equals("true"))
		{
			if ( Arrays.asList(userList).contains(jsonObject.get("user")) )
			{
				System.out.println("canary is true !!!!!");
				return true;
			}
			else {
				System.out.println("production @@@@@@");
				return false;
			}
		}
		else return false;
		
	}


}
