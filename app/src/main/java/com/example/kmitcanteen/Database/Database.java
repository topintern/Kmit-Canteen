package com.example.kmitcanteen.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.kmitcanteen.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="cart.db";
    private  static  final int DB_VER=1;
    public Database(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }
    public List<Order> getCarts(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String [] sqlSelect={"ID","ProductId","ProductName","Quantity","Price"};
        String sqlTable="OrderDetail";
        qb.setTables(sqlTable);
        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Order> result=new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                result.add(new Order(c.getInt(c.getColumnIndex("ID")),c.getString(c.getColumnIndex("ProductId")),
                       c.getString(c.getColumnIndex("ProductName")),
                               c.getString(c.getColumnIndex("Quantity")),
                                        c.getString(c.getColumnIndex("Price"))));
            }while (c.moveToNext());
        }
        return result;
    }
    public void addToCart(Order order)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price) VALUES ('%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());

        /*String query=String.format("IF EXISTS (SELECT * FROM OrderDetail WHERE ID = %s)" +
                " BEGIN UPDATE OrderDetail set Quantity=Quantity+%s where ID=%s END" +
                        "ELSE BEGIN INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price) VALUES ('%s','%s','%s','%s'); END",
                order.getProductId(),
                order.getQuantity(),
                order.getProductId(),
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());*/
        db.execSQL(query);
    }
    public void cleanCart()
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void updateCart(Order order) {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("UPDATE OrderDetail SET Quantity= %s WHERE ID = %d",order.getQuantity(),order.getID());
        db.execSQL(query);
    }
}
