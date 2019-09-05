package com.example.kmitcanteen.Model;

public class Food {
    private String Name, Image, Price, MenuId;

    public Food() {

    }

    public Food(String image, String menuId, String name,String price) {
        Name = name;
       Image = image;
        Price = price;
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }





    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }


    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
