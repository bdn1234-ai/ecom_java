package com.example.ecom.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.ecom.service.CommonService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CommnServiceImpl implements CommonService {

	@Value("${rupee.sign}")
	public String rupeeSign;
	
	@Override
	public void removeSessionMessage() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
				.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("succMsg");
		session.removeAttribute("errorMsg");
	}
	
	
}
