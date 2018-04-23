package models;


import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;


@Entity
public class Game extends Model {

@Id

@Constraints.Required
public Long id;
@Constraints.Required
public String name;
@Constraints.Required
public String age;
@Constraints.Required
public String description;
@ManyToOne
public Category category;



public Game(){

}


public Game(Long id, String name, String age, String description){
    this.id = id;
    this.name = name;
    this.age = age;
    this.description = description;
}


public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getname() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}

public String getAge() {
    return this.age;
}

public void setAge(String age) {
    this.age = age;
}

public String getDescription() {
    return this.description;
}

public void setDescription(String description) {
    this.description = description;
}

public Category getCategory() {
    return this.category;
}

public void setCategory(Category cat) {
    this.category = cat;
}


public static Finder<Long,Game> find = new Finder<Long,Game>(Game.class);

    public static List<Game> findAll() {
        return Game.find.all();
}



}