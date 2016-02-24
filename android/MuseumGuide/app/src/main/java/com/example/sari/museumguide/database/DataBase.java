package com.example.sari.museumguide.database;

/*
 * mysql> CREATE TABLE java_objects (
 * id INT AUTO_INCREMENT,
 * name varchar(128),
 * object_value BLOB,
 * primary key (id));
 **/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase {
    static final String WRITE_OBJECT_SQL = "INSERT INTO java_objects(name, object_value) VALUES (?, ?)";

    static final String READ_OBJECT_SQL = "SELECT * FROM student WHERE student_id = ?";

    public static Connection getConnection() throws Exception {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "android";
        String password = "android";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static long writeJavaObject(Connection conn, Object object) throws Exception {
        String className = object.getClass().getName();
        PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);

        // set input parameters
        pstmt.setString(1, className);
        pstmt.setObject(2, object);
        pstmt.executeUpdate();

        // get the generated key for the id
        ResultSet rs = pstmt.getGeneratedKeys();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        pstmt.close();
        System.out.println("writeJavaObject: done serializing: " + className);
        return id;
    }

    public static Object readJavaObject(Connection conn, long id) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        Object object = rs.getObject(1);
        String className = object.getClass().getName();

        rs.close();
        pstmt.close();
        System.out.println("readJavaObject: done de-serializing: " + className);
        return object;
    }
    public static void main(String args[])throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            System.out.println("conn=" + conn);
            conn.setAutoCommit(false);
            List<Object> list = new ArrayList<Object>();
            list.add("This is a short string.");
            list.add(new Integer(1234));
            list.add(new Date());

            //long objectID = writeJavaObject(conn, list);
            //conn.commit();
           // System.out.println("Serialized objectID => " + objectID);
            //List listFromDatabase = (List) readJavaObject(conn, 1);
            String someStringFromDataBase =(String) readJavaObject(conn, 1);
            System.out.println("[After De-Serialization] list=" + someStringFromDataBase);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            conn.close();
        }
    }
}

