package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;


@Entity
public class Category extends Model {

    @Id
    private Long id;

    @Constraints.Required
    private String name;
    
    @OneToMany
    private List<Game> games;

    public Category() {
    }

    public Category(Long id, String name, List<Game> games) {
        this.id = id;
        this.name = name;
        this.games = games;
    }

    public static Finder<Long,Category> find = new Finder<Long,Category>(Category.class);


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return this.games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
    
    
    
    public static List<Category> findAll() {
        return Category.find.query().where().orderBy("name asc").findList();
    }

        
   
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<>(); 

        for(Category c: Category.findAll()){
            options.put(c.getId().toString(), c.getName());

        }
   return options;

    }




}
