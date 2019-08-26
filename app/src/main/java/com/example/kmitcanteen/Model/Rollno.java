package com.example.kmitcanteen.Model;

public class Rollno {
    private  String Name;
    private  String Password;

    public Rollno(String name,String password)
    {
        Name=name;
        Password=password;
    }
    public  Rollno()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }


}
