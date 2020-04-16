package com.redhat.pathfinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyVisibilityStrategy;

public class Utils{

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  public static String toIndentedString(java.lang.Object o) {
  	return toIndentedString(o, 4);
  }
	public static String toIndentedString(java.lang.Object o, int spaces) {
    if (o == null) return "null";
    return o.toString().replace("\n", "\n"+String.format("%"+spaces+"s", " "));
  }
	
	public static Jsonb newJsonb(){
		JsonbConfig cfg=new JsonbConfig();
		cfg.withPropertyVisibilityStrategy(new PropertyVisibilityStrategy(){ // allows for pjo's without setters (ie. immutable) to be deserialized
			@Override public boolean isVisible(Method method){ return true; }
			@Override public boolean isVisible(Field field){ return true; }
		});
		return JsonbBuilder.create(cfg);
	}

}
