package fi.metropolia.practisecalorie.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    int id;


    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String gender;
    private int age;
    private int weight;
    private int height;
    private int calorieRequirement;


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

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getCalorieRequirement() {
        return calorieRequirement;
    }

    public int getAge() {
        return age;
    }

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