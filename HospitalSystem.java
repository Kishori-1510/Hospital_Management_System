package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.SortedMap;

public class HospitalSystem {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","kishori15");
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection,scanner);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println(" 1.Add Patients");
                System.out.println(" 2.View Patients");
                System.out.println(" 3.View Doctors");
                System.out.println(" 4.Book Appointment");
                System.out.println(" 5.Exit");
                System.out.println("=================================================================");
                System.out.println("Enter your choice:");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                         bookAppoinment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("please enter2 valid choice....");
                }
            }
        }catch (Exception e){

        }
    }
    public static void bookAppoinment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter Patient Id:");
        int patientId=scanner.nextInt();
        System.out.println("Enter Doctor Id:");
        int doctorId=scanner.nextInt();
        System.out.println("Enter Appoinment date(YYYY-MM-DD): ");
        String appoinmentDate=scanner.next();
        if (patient.getPatientById(patientId)  && doctor.getDoctortById(doctorId)){
           if (checkDoctorAvailability(doctorId,appoinmentDate,connection)){
               String query="INSERT INTO  appointments(patient_id,doctor_id,appointment_date)VALUES (?,?,?)";
               try {
                   PreparedStatement preparedStatement=connection.prepareStatement(query);
                   preparedStatement.setInt(1,patientId);
                   preparedStatement.setInt(2,doctorId);
                   preparedStatement.setString(3,appoinmentDate);
                   int row=preparedStatement.executeUpdate();
                   if(row>0){
                       System.out.println("Appoinment Book");
                   }else{
                       System.out.println("Failed to Book Appointment");
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
           }else {
               System.out.println("Doctor is not available on this date");
           }
        }else {
            System.out.println("either patient or doctor does not exist");
        }

    }
    public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id= ? AND appointment_date= ? ";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()){
                int count=rs.getInt(1);
                if(count==0){
                    return  true;
                }else {
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
