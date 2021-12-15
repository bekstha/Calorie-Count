package fi.metropolia.practisecalorie.user;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * creates a user in users table
 */
@Entity (tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    int id;


    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String password;
    private final String gender;
    private final int age;
    private final int weight;
    private final int height;
    private final int calorieRequirement;

    /**
     * Creates a user with following parameters
     * @param id primary key auto-generated when a user is created
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param userName username selected by the user
     * @param password password set by the user
     * @param gender user's biological representation
     * @param age user's age
     * @param weight user's weight
     * @param height user's height
     * @param calorieRequirement calculated by the program after user enters their physical parameters
     */
    public User(int id, String firstName, String lastName, String userName, String password, String gender,int age, int weight, int height, int calorieRequirement) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.calorieRequirement = calorieRequirement;
    }

    /**
     * gets user's id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * gets user's firstname
     * @return firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * gets user's lastname
     * @return lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * gets username
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * gets user's password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * gets user's gender
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * gets user's weight
     * @return weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * gets user's height
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * gets user's calorie requirement
     * @return calorie requirement
     */
    public int getCalorieRequirement() {
        return calorieRequirement;
    }

    /**
     * gets user's age
     * @return age
     */
    public int getAge() {
        return age;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", calorieRequirement=" + calorieRequirement +
                '}';
    }
}
