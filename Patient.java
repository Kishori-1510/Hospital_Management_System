package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public  Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient(){
        System.out.println("Enter Patient Name:");
        String name=scanner.next();
        System.out.println("Enter Patient Age:");
        int age=scanner.nextInt();
        System.out.println("Enter Patient Gender:");
        String gender=scanner.next();

        try {
            String query="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int row=preparedStatement.executeUpdate();
            if(row>0){
                System.out.println(" Patient Inserted Successfully");
            }
            else {
                System.out.println("Patient insertion failed");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void viewPatient(){
        String query= "Select * from patients";
        try {
              PreparedStatement preparedStatement=connection.prepareStatement(query);
              ResultSet rs=preparedStatement.executeQuery();
            System.out.println("Patients Records:");
            System.out.println("+-------------+--------------------+--------+-----------+");
            System.out.println("| Patient ID  |  Name              |   Age  | Gender    |");
            System.out.println("+-------------+--------------------+--------+-----------+");
              while (rs.next()){
                  int id=rs.getInt("id");
                  String name=rs.getString("name");
                  int age=rs.getInt("age");
                  String gender=rs.getString("gender");
                  System.out.printf("| %-12s | %-17s |%-9s|%-12s|\n",id,name,age,gender);
                  System.out.println("+-------------+--------------------+--------+-----------+");
              }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  public  boolean getPatientById(int id){
      String  query="select * from patients where id= (?)";
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
