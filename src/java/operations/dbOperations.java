/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operations;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import controller.Person;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.awt.X11.XConstants;

/**
 *
 * @author shibo
 */
public class dbOperations {

    public static Connection con;
    Statement st;
    ResultSet rs;
    String drivermanager;
    String url;
    String usr, psw;
    PreparedStatement pst;
// init();

    public void init() {
        usr = "root";
        psw = "root";
        drivermanager = "com.mysql.jdbc.Driver";

        url = "jdbc:mysql://localhost:3306/shibo";

        try {
            Class.forName(drivermanager);
            con = (Connection) DriverManager.getConnection(url, usr, psw);
            st = (Statement) con.createStatement();

            System.out.println("sucessful");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void insert(Person p) throws SQLException {
        //init();
        pst = con.prepareStatement("insert into Person(email,username,password) values(?,?,?)");
        pst.setString(1, p.getEmail());
        pst.setString(2, p.getUsername());
        pst.setString(3, p.getPassword());
        ;

        int i = pst.executeUpdate();
        System.out.println(i + " records inserted");

    }

    public static void main(String[] args) throws SQLException {
        operations.dbOperations e = new dbOperations();
        e.init();
        e.run("select * from Person where username  = 'dsds' ");
         //System.out.println(e.run("insert into Person(email,username,password) values('fdfd','fdfd','fdfd') "));
        //System.out.println(e.run("delete from Person"));
        //System.out.println(e.run("update Person set username='dsdsdsds' where email='fdfd'"));

    }

    public boolean login(String parameter, String parameter0) {
        boolean flag = false;
        try {
            pst = con.prepareStatement("select * from Person where email=? and password=?");
            pst.setString(1, parameter);
            pst.setString(2, parameter0);
            rs = pst.executeQuery();
            if (rs.next()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flag;
    }

    public ResultSet search(String name) throws SQLException {
        pst = con.prepareStatement("select * from Person where username  like ?");
        pst.setString(1, "%" + name);
        rs = pst.executeQuery();
       // if(rs.next())System.out.println("kjfkjkfd");

        return rs;
    }
 public ResultSet search() throws SQLException {
        pst = con.prepareStatement("select * from Person ");
       
        rs = pst.executeQuery();
       // if(rs.next())System.out.println("kjfkjkfd");

        return rs;
    }
  

    public Object run(String command) throws SQLException {
        ArrayList<Person> list=new ArrayList<Person>();
        //Boolean flag = runAnyCommand(command);
        Object data = null;
         Boolean s=false;
     st=(Statement) con.createStatement();
            System.out.println("trueeeeeeeeeeee");
            try {
                System.out.println("");
                s=st.execute(command);
                System.out.println("  sdkslkdsl" + s);
                if(s){
                
                rs=search();
                
                    System.out.println("in if by the way");
                
                while(rs.next()){
                    list.add(new Person(rs.getString(1), rs.getString(2), rs.getString(3)));
                    System.out.println(""+rs.getString(2));}
                data=list;
                }
                else{
                data=st.getUpdateCount();
                
                }

            } catch (SQLException ex) {
                System.out.println(""+ex.getMessage());
                data=false;
            }

        

        return data;

    }

}
