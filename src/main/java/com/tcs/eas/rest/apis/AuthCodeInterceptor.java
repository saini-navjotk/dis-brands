package com.tcs.eas.rest.apis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.eas.rest.apis.log.LoggingService;

@Component
public class AuthCodeInterceptor implements HandlerInterceptor {
	@Autowired
    LoggingService loggingService;
	   @Override
	   public boolean preHandle(
	      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		   loggingService.logResponse(request, response, handler);
		   return true;
	   }
	   @Override
	   public void postHandle(
	      HttpServletRequest request, HttpServletResponse response, Object handler, 
	      ModelAndView modelAndView) throws Exception {
		   loggingService.logResponse(request, response, handler);
	   }
	   
	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
	      Object handler, Exception exception) throws Exception {
		   loggingService.logResponse(request, response, handler);
	   }
	}