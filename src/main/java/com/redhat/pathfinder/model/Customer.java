package com.redhat.pathfinder.model;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.redhat.pathfinder.Utils;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;

@MongoEntity(collection="Customers")
public class Customer extends PanacheMongoEntity{
//	@BsonProperty("customerId")
//	public String id;
//	public String customerId;
	public String name;
	public String description;
	public String assessor;
	public String vertical;
//	private String size;
//	private String RTILink;
//	private List<Member> members;
//	public final Integer percentageComplete;
//	public final Integer appCount;
//	public final Integer memberCount;
	
	// Setters required for jsonb marshaling (hate having methods for the sake of a framework.. lets try something different)
//	public Customer2 setId(String v){this.id=v; return this;}
//	public Customer setName(String v){this.name=v; return this;}
//	public Customer setDescription(String v){this.description=v; return this;}
	
//	public String getId(){return super.id.toString();}                   
//	public String getName(){return name;}               
//	public String getDescription(){return description;} 
//	public String getAssessor(){return assessor;}
//	public String getVertical(){return vertical;}
	
	public static Customer findById(String id){
		return findById(new ObjectId(id));
	}
//		System.out.println("1.count="+find("_id", id).count());
//		System.out.println("2.count="+find("_id", new ObjectId(id)).count());
//		System.out.println("3.count="+find("id", id).count());
//		System.out.println("4.count="+find("id", new ObjectId(id)).count());
//		System.out.println("all.count="+findAll().count());
//		
//		return find("_id", new ObjectId(id)).firstResult();
//	}
	
	public static Builder builder(){
		return new Builder();
	}
	public static class Builder extends Customer{
//		public String getId(){return id;}                   public Builder id(String v){id=v; return this;}
		public String getName(){return name;}               public Builder name(String v){name=v; return this;}
		public String getDescription(){return description;} public Builder description(String v){description=v; return this;}
		public String getAssessor(){return assessor;}       public Builder assessor(String v){assessor=v; return this;}
		public String getVertical(){return vertical;}       public Builder vertical(String v){vertical=v; return this;}
		public Customer build(){
			Customer c=new Customer();
//			c.id=super.id;
			c.name=super.name;
			c.description=super.description;
			c.assessor=super.assessor;
			c.vertical=super.vertical;
			return c;
		}
		public Customer populate(Customer src, Customer dst){
			dst.name=src.name;
			dst.description=src.description;
			dst.assessor=src.assessor;
			dst.vertical=src.vertical;
			return dst;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer)) 
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(other.id, this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
	
	public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(this.getClass().getSimpleName()+"{");
    sb.append("id:").append(Utils.toIndentedString(id));
    sb.append(", name:").append(Utils.toIndentedString(name));
    sb.append(", description:").append(Utils.toIndentedString(description));
    sb.append("}");
    return sb.toString();
	}
}
