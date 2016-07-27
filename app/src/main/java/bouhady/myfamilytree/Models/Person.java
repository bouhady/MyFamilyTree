package bouhady.myfamilytree.Models;

import java.io.Serializable;

/**
 * Created by Yaniv Bouhadana on 17/07/2016.
 */
public class Person implements Serializable {
    protected int personID;
    protected String firstName;
    protected String lastName;
    protected int yearOfBirth;
    protected int yearOfDeath;
    protected Gender gender;


    public Person( String firstName, String lastName, int yearOfBirth, int yearOfDeath,Gender gender) {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
    }

    public Person() {

    }

    public Gender getGender() {
        return gender;
    }

    public Person setGender(Gender gender) {
        this.gender = gender;
        return this;
    }
    public Person setGender(int gender) {

        this.gender = Gender.values()[gender];
        return this;
    }
    public Person setGender(String gender) {

        this.gender = Gender.valueOf(gender);
        return this;
    }


    public int getPersonID() {
        return personID;
    }

    public Person setPersonID(int personID) {
        this.personID = personID;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public Person setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        return this;
    }

    public int getYearOfDeath() {
        return yearOfDeath;
    }

    public Person setYearOfDeath(int yearOfDeath) {
        this.yearOfDeath = yearOfDeath;
        return this;
    }

    public boolean isAlive()
    {
        return (yearOfDeath < 0);
    }

    public String toNameHeader() {
        return (lastName+", "+firstName);
    }



}
