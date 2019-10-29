package edu.cmu.andrew.foodish.server.models;

public class Dish {

    private int idDish = -1;
    private int idUser;
    private String DishName;
    private String DishDescription;
    private String DishTags;

    public Dish(int idDish, int idUser, String dishName, String dishDescription, String dishTags) {
        this.idDish = idDish;
        this.idUser = idUser;
        DishName = dishName;
        DishDescription = dishDescription;
        DishTags = dishTags;
    }

    public int getIdDish() {
        return idDish;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getDishName() {
        return DishName;
    }

    public String getDishDescription() {
        return DishDescription;
    }

    public String getDishTags() {
        return DishTags;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public void setDishDescription(String dishDescription) {
        DishDescription = dishDescription;
    }

    public void setDishTags(String dishTags) {
        DishTags = dishTags;
    }
}
