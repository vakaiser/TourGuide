package at.htlgkr.tourguide;

import java.io.Serializable;
import java.util.List;

public class Country implements Serializable {

    private String name;
    private int population;
    private String capitol;
    private String description;

    private List<Sehenswuerdigkeiten> places;
    private List<Sehenswuerdigkeiten> foods;

    public Country(String name, int population, String capitol, String description, List<Sehenswuerdigkeiten> places, List<Sehenswuerdigkeiten> foods) {
        this.name = name;
        this.population = population;
        this.capitol = capitol;
        this.description = description;
        this.places = places;
        this.foods = foods;
    }

    public void addPlace(Place p) {
        places.add(p);
    }

    public void addFood(Sehenswuerdigkeiten f) {
        foods.add(f);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCapitol() {
        return capitol;
    }

    public void setCapitol(String capitol) {
        this.capitol = capitol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Sehenswuerdigkeiten> getPlaces() {
        return places;
    }

    public void setPlaces(List<Sehenswuerdigkeiten> places) {
        this.places = places;
    }

    public List<Sehenswuerdigkeiten> getFoods() {
        return foods;
    }

    public void setFoods(List<Sehenswuerdigkeiten> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", capitol='" + capitol + '\'' +
                ", description='" + description + '\'' +
                ", places=" + places +
                ", foods=" + foods +
                '}';
    }
}
