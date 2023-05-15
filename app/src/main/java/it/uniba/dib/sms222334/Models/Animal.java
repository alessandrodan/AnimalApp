package it.uniba.dib.sms222334.Models;

import android.media.Image;
import java.util.LinkedList;

public class Animal extends Document {

    //in carico, smarrito, adottato, assistico, randagio
    public enum stateList{LOST,IN_CHARGE,ADOPTED,ASSISTED,STRAY}
    private String name;
    private Owner owner;
    private int age;
    private stateList state;
    private String microchip;
    private String species;
    private String race;
    private String photo;

    private LinkedList<String> images;

    private LinkedList<String> videos;

    //arraylist<visite>
    //arraylist<patologia>
    //arraylist<cibo>
    //arraylist<video>
    //arraylist<foto>
    //arraylist<spesa>
    //arraylist<relazioni>

    private Animal(String name, Owner owner, int age, stateList state, String species, String race, String photo, String microchip){
        this.name = name;
        this.owner = owner;
        this.age = age;
        this.state = state;
        this.species = species;
        this.race = race;
        this.photo = photo;
        this.microchip = microchip;

        images = new LinkedList<>();
        videos = new LinkedList<>();
    }

    public static class Builder{
        private String bname;
        private Owner bowner;
        private int bage;
        private stateList bstate;
        private String bspecies;
        private String brace;
        private String bphoto;
        private String bmicrochip;

        private Builder(final stateList state){
            this.bstate=state;
        }

        public static Animal.Builder create(final stateList state){
            return new Animal.Builder(state);
        }

        public Animal.Builder setName(final String name){
            this.bname=name;
            return this;
        }

        public Animal.Builder setOwner(final Owner owner){
            this.bowner=owner;
            return this;
        }

        public Animal.Builder setAge(int age){
            this.bage=age;
            return this;
        }

        public Animal.Builder setSpecies(final String species){
            this.bspecies=species;
            return this;
        }

        public Animal.Builder setMicrochip(final String microchip){
            this.bmicrochip=microchip;
            return this;
        }

        public Animal.Builder setPhoto(final String photo){
            this.bphoto=photo;
            return this;
        }

        public Animal.Builder setRace(final String race){
            this.brace=race;
            return this;
        }

        public Animal build(){
            return new Animal(bname,bowner,bage,bstate,bspecies,brace,bphoto,bmicrochip);
        }
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public int getAge() {
        return age;
    }

    public stateList getState() {
        return state;
    }

    public String getSpecies() {
        return species;
    }

    public String getRace() {
        return race;
    }

    public String getPhoto() {
        return photo;
    }

    public LinkedList<String> getImages() {
        return images;
    }

    public LinkedList<String> getVideos() {
        return videos;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void addImage(String image) {
        images.add(image);
    }

    public void addVideo(String video) {
        videos.add(video);
    }
}
