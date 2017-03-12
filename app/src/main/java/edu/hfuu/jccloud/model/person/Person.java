package edu.hfuu.jccloud.model.person;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Person extends RealmObject {

    private String name;
    private int age;
    private Dog favoriteDog;
    private RealmList<Dog> dogs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dog getFavoriteDog() {
        return favoriteDog;
    }

    public void setFavoriteDog(Dog favoriteDog) {
        this.favoriteDog = favoriteDog;
    }

    public RealmList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(RealmList<Dog> dogs) {
        this.dogs = dogs;
    }
}
