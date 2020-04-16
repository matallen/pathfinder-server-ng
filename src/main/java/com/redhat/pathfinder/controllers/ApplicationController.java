package com.redhat.pathfinder.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.json.bind.JsonbBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.pathfinder.Utils;
import com.redhat.pathfinder.model.Application;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

@Path("/api/pathfinder/customers/{customerId}/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationController{
	public static final Logger log=LoggerFactory.getLogger(ApplicationController.class);
	
	private void validate(Application o){
		if (o.username==null) throw new WebApplicationException("Username must be provided");
	}
	@GET
	public Response get(@PathParam String customerId){
		return Response.ok(Application.findAll(customerId)).build();
	}
	@POST
	public Response create(@PathParam String customerId, @Context HttpServletRequest request) throws IOException{
		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
		Application o=Utils.newJsonb().fromJson(payload, Application.class);
		o.customerId=customerId;
		validate(o);
		o.persist();
		return Response.ok(Application.findAll(customerId)).build();
	}
	@PUT
	@Path("{id}")
	public Response update(@PathParam String customerId, @PathParam String id, @Context HttpServletRequest request) throws IOException{
		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
		Application o=Utils.newJsonb().fromJson(payload, Application.class);
		validate(o);
		Application entity=Application.findById(id);
		if (null==entity) throw new WebApplicationException("Unable to find "+Application.class.getSimpleName()+" with id "+id);
//		Customer c=Customer.findById(customerId);
//		c.members.add(entity);
//		if (!customerId.equals(entity.customerId)) throw new WebApplicationException("Specified user is not a member of the customer");
		
		entity=Application.builder().populate(o, entity);
		entity.update();
		return Response.ok(entity).build();
	}
	@DELETE
	@Path("{id}")
	public Response deleteSingle(@PathParam String customerId, @PathParam String id, @Context HttpServletRequest request) throws IOException{
		Application entity=Application.findById(id);
		if (null==entity) throw new WebApplicationException("Unable to find "+Application.class.getClass().getSimpleName()+" with id "+id);
		validate(entity);
		entity.delete();
		return Response.status(204).build();
	}
//	@DELETE
//	public Response deleteMultple(@Context HttpServletRequest request) throws IOException{
//		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
//    List<String> idsToDelete=JsonbBuilder.create().fromJson(payload, ArrayList.class);
//    
//  	for (String id:idsToDelete){
//  		log.info(1==Customer.delete("id", id)?"deleted "+id+"":"Unable to delete "+id+" - not found");
//  	}
//  	return Response.ok(Customer.listAll()).build();
//	}
}
