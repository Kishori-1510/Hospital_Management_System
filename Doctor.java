package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;
    public  Doctor(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void viewDoctor(){
        String query= "Select * from Doctor";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();
            System.out.println("Doctors Records:");
            System.out.println("+-------------+--------------------+-------------------+");
            System.out.println("| Doctor ID   |  Doctor Name       |   Specialization  |");
            System.out.println("+-------------+--------------------+-------------------+");
            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String specialization=rs.getString("specialization");
                System.out.printf("|%-14s|%-21s|%-19s|\n",id,name,specialization);
                System.out.println("+-------------+--------------------+-------------------+");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  boolean getDoctortById(int id){
        String  query="select * from Doctor where id= (?)";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()){
                return  true;
            }else {
                return  false;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
}
