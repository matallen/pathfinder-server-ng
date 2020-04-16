package com.redhat.pathfinder.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.pathfinder.MapBuilder;
import com.redhat.pathfinder.Utils;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class AuthController{
	public static final Logger log=LoggerFactory.getLogger(AuthController.class);
	
	@POST
	public Response login(@Context HttpServletRequest request) throws IOException{
		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
		System.out.println("/login payload = "+payload);
		Map<String,String> auth=(Map<String,String>)Utils.newJsonb().fromJson(payload, Map.class);
		return Response.ok(
				new MapBuilder<String,String>().put("token", "aJWTToken").put("username", auth.get("username")).put("displayName", auth.get("username")).build()
		).build();
	}
	

//  private Member _currentUser=null;
//  protected Member getCurrentUser(){
//    if (null==_currentUser || !SecurityContextHolder.getContext().getAuthentication().getName().equals(_currentUser.getUsername())){
//      String username=SecurityContextHolder.getContext().getAuthentication().getName();
//      _currentUser=Member.  membersRepo.findOne(username);
//      log.debug("_currentUser is {}", _currentUser);
//    }
//    return _currentUser;
//  }
  
//  public boolean isAuthorizedFor(Customer c){
//    Member currentUser=getCurrentUser();
//    boolean result= currentUser.getRoles().contains("SUPER")
//              || 
//           (currentUser.getRoles().contains("ADMIN")
//      		 && currentUser.getCustomerId()!=null
//  				 && currentUser.getCustomerId().equals(c.getId()));
//    return result;
//  }
  
	
}