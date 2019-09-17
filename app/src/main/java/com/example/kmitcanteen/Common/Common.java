package com.example.kmitcanteen.Common;

import com.example.kmitcanteen.Model.Rollno;

public class Common {
    public static Rollno currentuser;
    public static final String DELETE="Delete";

    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Order Placed!";
        else if (status.equals("1")) {
            return "Please wait! your Food is being prepared!";

        } else
            return "Your food is Ready";
    }
}