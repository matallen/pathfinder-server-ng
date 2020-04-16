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
import com.redhat.pathfinder.model.Customer;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

@Path("/api/pathfinder/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController{
	public static final Logger log=LoggerFactory.getLogger(CustomerController.class);
	
	@GET
	public Response get(){
		return Response.ok(Customer.listAll()).build();
	}
	@POST
	public Response create(@Context HttpServletRequest request) throws IOException{
		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
		Customer c=Utils.newJsonb().fromJson(payload, Customer.class);
		if (c.name==null) throw new WebApplicationException("Name must be provided");
		if (Customer.count("name", c.name)>0) throw new WebApplicationException("Customer with name "+c.name+" already exists");
		c.persist();
		return Response.ok(Customer.listAll()).build();
	}
	@PUT
	@Path("{id}")
	public Response update(@PathParam String id, @Context HttpServletRequest request) throws IOException{
		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
		Customer c=Utils.newJsonb().fromJson(payload, Customer.class);
		if (c.name==null) throw new WebApplicationException("Name must be provided");
		Customer entity=Customer.findById(id);
		if (null==entity) throw new WebApplicationException("Unable to find "+Customer.class.getSimpleName()+" with id "+id);
		entity=Customer.builder().populate(c, entity);
		entity.update();
		return Response.ok(entity).build();
	}
	@DELETE
	@Path("{id}")
	public Response deleteSingle(@PathParam String id, @Context HttpServletRequest request) throws IOException{
		Customer entity=Customer.findById(id);
		if (null==entity) throw new WebApplicationException("Unable to find "+Customer.class.getClass().getSimpleName()+" with id "+id);
		entity.delete();
		return Response.status(204).build();
	}
	@DELETE
	public Response deleteMultple(@Context HttpServletRequest request) throws IOException{
		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
    List<String> idsToDelete=JsonbBuilder.create().fromJson(payload, ArrayList.class);
    
//    List<Customer> customers=Customer.listAll();
  	for (String id:idsToDelete){
  		log.info(1==Customer.delete("id", id)?"deleted "+id+"":"Unable to delete "+id+" - not found");
//  		log.info(customers.removeIf(item -> item.id.toString().contentEquals(id))?"deleted '+id+'...":"Unable to delete "+id+" - not found");
  	}
  	return Response.ok(Customer.listAll()).build();
	}
//	private <T> T payloadToObject(HttpServletRequest request, Class<T> clazz) throws IOException{
//		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
//		return Utils.newJsonb().fromJson(payload, clazz);
//	}
			
	
//	private Customer2 addOrUpdate(Customer2 value){
////		Customer2 c=Customer2.builder()
////				.id(null!=value.getId()?value.getId():UUID.randomUUID().toString())
////				.name(value.getName())
////				.description(value.getDescription())
////				.build();
//		
//		if (null!=value.id){
//			Customer2.findById(value.id);
//			value.update();
//		}else{
//			value.id=UUID.randomUUID().toString();
//			value.persist();
//		}
//		return value;
//	}

//	@POST
//	@Path("/{customerId}")
//	public Response update(@Context HttpServletRequest request, @PathParam("customerId") String customerId) throws IOException{
//		log.debug("[@POST] /customers/"+customerId);
//		String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
//		Customer c=Utils.newJsonb().fromJson(payload, Customer.class);
//		log.debug("payload="+payload);
//    log.debug("customer="+c);
//		
//		return Response.ok(addOrUpdate(c)).build();
//	}
//		log.info("payload="+payload);
//    log.info("customer="+c);
	
	
//		System.out.println(Customer.listAll().size());
//		customers.removeIf(item -> item.getId().contentEquals(c.getId())); // remove if exists so we can overwrite/update with new entity
//		customers.add(c);// repo.save(c); // overwrite existing document if a customer exists with the same id
	
//	@DELETE
//	public Response delete(@Context HttpServletRequest request) throws IOException{
//    log.debug("[@DELETE] /customers");
//    
//    String payload=IOUtils.toString(request.getInputStream(), "UTF-8");
//    log.debug("payload="+payload);
////    Customer c=Utils.newJsonb().fromJson(payload, Customer.class);
//    
//    //fruits.removeIf(existingFruit -> existingFruit.getName().contentEquals(fruit.getName()));
//    
//    List<String> idsToDelete=JsonbBuilder.create().fromJson(payload, ArrayList.class);
//    
//    List<Customer> customers=Customer.listAll();
//  	for (String id:idsToDelete){
//  		log.info(customers.removeIf(item -> item.id.contentEquals(id))?"deleted '+id+'...":"Unable to delete '+id+' - not found");
//  	}
//  
//    return Response.ok(Customer.listAll()).build();
//    
////    for(String customerId: payload){
////      log.debug("customersDelete: deleting customer [{}]", customerId);
////      try{
////        
////        Customer c=custRepo.findOne(customerId);
////        if (c==null){
////          log.error("customersDelete....customer not found {}", customerId);
////          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
////        
////        log.debug("customersDelete: customer [{}] had "+(c.getApplications()!=null?c.getApplications().size():0)+" apps", customerId);
////        if (null!=c.getApplications()){
////          for (Applications app:c.getApplications()){
////          	if (app==null) continue;  // TODO: Remove this MAT!!!!
////            log.debug("customersDelete: deleting customer [{}] application [{}]", customerId, app.getId());
////            
////            if (null!=app.getAssessments()){
////              for(Assessments ass:app.getAssessments()){
////                log.debug("customersDelete: deleting customer [{}] application [{}] assessment [{}]", customerId, app.getId(), ass.getId());
////                
////                assmRepo.delete(ass.getId());
////              }
////            }
////            
////            if (null!=app.getReview()){
////              reviewRepository.delete(app.getReview().getId());
////            }
////            
////            appsRepo.delete(app.getId());
////          }
////        }
////        
////        custRepo.delete(customerId);
////        
////      }catch(Exception e){
////        log.error("Error deleting customer ["+customerId+"] ", e.getMessage(), e);
////        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////      }
////    }
////    return new ResponseEntity<>(HttpStatus.OK);
//	}
//}




//static class JSON<T>{
//public T toObject(InputStream is, Class<T> type){
//	return (T)JsonbBuilder.create().fromJson(is, type);
//}
//public T toObject(String is, Class<T> type){
//	return (T)JsonbBuilder.create().fromJson(is, type);
//}
//}
//
//public static void main(String[] asd){
//Customer c=new JSON<Customer>().toObject("{\"id\":\"1\",\"description\":\"Tropical fruit\",\"name\":\"Banana\"}", Customer.class);
//System.out.println(c);
//}
}
