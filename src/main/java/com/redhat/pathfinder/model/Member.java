package com.redhat.pathfinder.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.bson.types.ObjectId;

import com.redhat.pathfinder.Utils;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

@MongoEntity(collection="Members")
public class Member extends PanacheMongoEntity{
//	  public String id;
	  public String username;
	  public String email;
	  public String password;
	  public String displayName;
	  public String customerId;

//	  public String getUsername()    { return username; }
//	  public String getEmail()       { return email; }
//	  public String getPassword()    { return password; }
//	  public String getDisplayName() { return displayName; }
//	  public String getCustomerId()  { return customerId; }
	  
		public static Member findById(String id){
			return findById(new ObjectId(id));
		}
	  public static List<Member> findAll(String customerId){
	  	return Member.find("customerId", customerId).list();
	  }
	  
	  
		public static Builder builder(){
			return new Builder();
		}
		public static class Builder extends Member{
//			public String getId(){return id;}                   public Builder id(String v){id=v; return this;}
			public String getUsername(){return username;}       public Builder name(String v){username=v; return this;}
			public String getEmail(){return email;} 						public Builder email(String v){email=v; return this;}
			public String getPassword(){return email;} 					public Builder password(String v){email=v; return this;}
			public String getDisplayName(){return email;} 			public Builder displayName(String v){email=v; return this;}
			public String getCustomerId(){return email;} 				public Builder customerId(String v){email=v; return this;}
			public Member build(){
				Member v=new Member();
//				v.id=id;
				v.username=username;
				v.email=email;
				v.password=password;
				v.displayName=displayName;
				v.customerId=customerId;
				return v;
			}
			public Member populate(Member src, Member dst){
//			  dst.id=src.id;
			  dst.username=src.username;
			  dst.email=src.email;
			  dst.password=src.email;
			  dst.displayName=src.displayName;
//			  dst.customerId=src.customerId;
				return dst;
			}
		}
//	  public Member build(){
//		  Member n=new Member();
//		  n.id=id;
//		  n.username=username;
//		  n.email=email;
//		  n.password=password;
//		  n.displayName=displayName;
//		  n.customerId=customerId;
//		  return n;
//	  }
//	  public Member setId(String v) { this.id=v; return this; }
//	  public Member setUsername(String v) { this.username=v; return this; }
//	  public Member setEmail(String v) { this.email=v; return this; }
//	  public Member setPassword(String v) { this.password=v; return this; }
//	  public Member displayName(String v) { this.displayName=v; return this; }
//	  public Member customerId(String v) { this.customerId=v; return this; }
	  
	  
	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Member memberType = (Member) o;
	    return Objects.equals(this.id, memberType.id) &&
	        Objects.equals(this.username, memberType.username) &&
	        Objects.equals(this.email, memberType.email) &&
	        Objects.equals(this.customerId, memberType.customerId);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(id, username, email, password, displayName, customerId);
	  }

	  @Override
	  public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getSimpleName()+"{");
      sb.append("id:").append(Utils.toIndentedString(id));
      sb.append(",username: ").append(Utils.toIndentedString(username));
      sb.append(",email: ").append(Utils.toIndentedString(email));
      sb.append(",displayName: ").append(Utils.toIndentedString(displayName));
      sb.append(",customerId: ").append(Utils.toIndentedString(customerId));
      sb.append("}");
      return sb.toString();
	  }
}
