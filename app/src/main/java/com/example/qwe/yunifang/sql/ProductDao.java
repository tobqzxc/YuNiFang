package com.example.qwe.yunifang.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by qwe on 2016/12/12.
 */
public class ProductDao {

    private MySqliteOpenHelper mySqliteOpenHelper;

    public ProductDao(Context context) {
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    public void add(Product product, int number) {
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from product where id=?", new String[]{product.getId()});
        while (cursor.moveToNext()) {
            int num = cursor.getInt(cursor.getColumnIndex("num"));
            int NewNum = num + number;
            update(num, NewNum);
            return ;
        }
        db.execSQL("insert into product (id,name,price,url,num) values(?,?,?,?,?)", new Object[]{product.getId(), product.getName(), product.getPrice(), product.getUrl(), product.getNum()});
        db.close();
    }

    public void delete(Product product) {
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("delete from product where name = ?", new String[]{product.getName()});
        db.close();
    }

    public void update(int oldNum, int newNum) {
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("update product set num =? where num = ?", new Object[]{newNum, oldNum});
        db.close();
    }

    //放到成员位置
    ArrayList<Product> productArrayList = new ArrayList<Product>();

    public ArrayList<Product> query() {
        //获取可读数据库
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //游标集
        Cursor cursor = db.rawQuery("select * from product ", null);
        //清空集合
        productArrayList.clear();
        //如果游标一直能往下移动
        while (cursor.moveToNext()) {
            //先获取当前列名所在列的索引值 再获取该列的数据
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            int num = cursor.getInt(cursor.getColumnIndex("num"));
            productArrayList.add(new Product(name,price,id,url,num));
        }
        return productArrayList;
    }


}
