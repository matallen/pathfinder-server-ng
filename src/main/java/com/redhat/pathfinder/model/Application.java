package com.redhat.pathfinder.model;

import java.util.HashMap;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection="Applications")
public class Application extends PanacheMongoEntity{

  private String name = null;
  private String description = null;
  private String owner = null;
  private String review = null;
  private HashMap<String, String> customFields = null;
  private StereotypeEnum stereotype = null;
  
  public enum StereotypeEnum {
    TARGETAPP("TARGETAPP"),
    DEPENDENCY("DEPENDENCY"),
    PROFILE("PROFILE");

    private String value;
    StereotypeEnum(String value) {
      this.value = value;
    }
    public String toString() {
      return String.valueOf(value);
    }

    public static StereotypeEnum fromValue(String text) {
      for (StereotypeEnum b : StereotypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
}
