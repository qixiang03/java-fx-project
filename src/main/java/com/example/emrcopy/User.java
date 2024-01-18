package com.example.emrcopy;

import javafx.scene.chart.XYChart;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String sex;
    private String nric;
    private String telno;
    private double height;
    private double weight;
    private LocalDate dob;
    private List<DataPoint> heartrateDataPoint1 = new ArrayList<>();
    protected static Set<User> database = new HashSet<>();


    public User(String username, String sex, String nric, double height, double weight, String telno){
        this.username = username;
        this.sex = sex;
        this.nric = nric;
        this.height = height;
        this.weight = weight;
        this.telno = telno;
        this.dob = extractBirthdate(nric);
        randomizeHeartRateData();
    }

    public String getUsername(){
        return username;
    }

    public String getSex(){
        return sex;
    }

    public String getnric(){
        return nric;
    }

    public double getHeight(){
        return height;
    }

    public double getWeight(){
        return weight;
    }
    public String getTelno(){return telno;}
    public LocalDate getDob(){return dob;}
    public List<DataPoint> getHeartRateDataPoint(){return heartrateDataPoint1;}


    public static void createUser(String username, String sex, String nric, double height, double weight, String telno){
        User tmp = new User(username, sex, nric, height, weight, telno);
        if(!database.add(tmp)){System.out.println("User Existed!");}
        saveDatabase();
        loadDatabase();
    }

    public static User readUser(String nric) {
        loadDatabase();
        for (User user : database) {
            if (user.getnric().equals(nric)) {
                return user;
            }
        }
        return null;
    }

    public static void updateUser(User user, double height, double weight, String telno) {
            user.height = height;
            user.weight = weight;
            user.telno = telno;

            saveDatabase();
    }

    protected static void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.txt"))) {
            oos.writeObject(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            database = (Set<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(nric, other.nric);
    }

    public void randomizeHeartRateData() {
        Random random = new Random();
        for (int i = 0; i <= 60; i+=5) {
            int heartRate = random.nextInt(50) + 70;  // Generate random heart rate between 70 and 120
            heartrateDataPoint1.add(new DataPoint(i, heartRate));
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(nric);
    }

    public static LocalDate extractBirthdate(String nric) {
        String dateString = nric.substring(0, 6);

        // Parse the date string into a LocalDate object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthdate = LocalDate.parse(dateString, formatter);

        return birthdate;
    }
}

class DataPoint implements Serializable {
    private Number x;
    private Number y;

    public DataPoint(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }
}