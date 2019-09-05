package com.example.kmitcanteen.Model;

public class Rollno {
    private  String Name;
    private  String Password;
    private String Rollno;

    public Rollno(String name,String password)//,String rollno)
    {
        Name=name;
        Password=password;
        //Rollno=rollno;
    }

    public String getRollno() {
        return Rollno;
    }

    public void setRollno(String rollno) {
        Rollno = rollno;
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
