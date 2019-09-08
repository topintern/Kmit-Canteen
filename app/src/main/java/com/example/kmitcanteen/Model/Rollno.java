package com.example.kmitcanteen.Model;

public class Rollno {
    private  String Name;
    private  String Password;
    private String rollno;


    public Rollno(String name,String password)//,String rollno)
    {
        Name=name;
        Password=password;
        //Rollno=rollno;
    }
     public Rollno()
     {

     }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }
}
