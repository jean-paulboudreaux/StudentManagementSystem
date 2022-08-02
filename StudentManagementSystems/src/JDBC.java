package src;

import com.sun.security.jgss.GSSUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JDBC {
    public static void main(String[] args) {
        System.out.println("Select one from the following options: \n" +
                "1. Add a student \n" +
                "2. Search for a student \n" +
                "3. View all students \n" +
                "4. Pay a student's balance \n" +
                "5. Exit");
        Scanner in = new Scanner(System.in);
        int userResponse = in.nextInt();
        while (userResponse != 5) {
            if (userResponse == 1) {

                try {
                    Statement statement = null;
                    ResultSet resultSet = null;
                    Student student = new Student();
                    student.updateID();
                    student.enroll();
                    student.payTuition();

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "vegasBully2022!");

                    statement = connection.createStatement();
                    String sql = "INSERT INTO name " + "VALUES (" + student.getStudentID() + ",'" + student.getFirstName() + "','" + student.getLastName() + "','" + student.getGradeLevel() + "'," + student.getTuitionBalance() + ")";

                    statement.executeUpdate(sql);

                    //resultSet = statement.executeQuery("select * from name");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (userResponse == 2) {
                findStudent(false);

            }

            if (userResponse == 3) {
                System.out.println("STUDENT ID   FIRST NAME     LAST NAME     GRADE LEVEL      BALANCE");

                try {
                    String query = "SELECT * FROM NAME";
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "vegasBully2022!");
                    Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery(query);
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString("id"));
                        System.out.print(" ");
                        System.out.print(resultSet.getString("firstname"));
                        System.out.print(" ");
                        System.out.print(resultSet.getString("lastname"));
                        System.out.print(" ");
                        System.out.print(resultSet.getString("grade"));
                        ;
                        System.out.print(" ");
                        System.out.print("$" + resultSet.getString("balance"));
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println(e);


                }


            }
            if(userResponse == 4){
                findStudent(true);

            }

            System.out.println("\n" + "Select one from the following options: \n" +
                    "1. Add a student \n" +
                    "2. Search for a student \n" +
                    "3. View all students \n" +
                    "4. Pay a student's balance \n" +
                    "5. Exit");
            System.out.println();
            userResponse = in.nextInt();

        }
    }

    public static void getTable(String compare, String columnName, boolean payBalOrNotPayBal) {
        try {
           String query = "SELECT * FROM NAME";
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "vegasBully2022!");
           Statement stmt = connection.createStatement();
           ResultSet rs = stmt.executeQuery(query);


           while(rs.next()){
               String columnStrName = rs.getString(columnName);
                   if(columnStrName.equalsIgnoreCase(compare)) {
                       System.out.print(rs.getString("firstname") + " ");
                       System.out.print(rs.getString("lastname")+ " ");
                       System.out.print(rs.getString("id")+ " ");
                       System.out.print(rs.getString("grade")+ " ");
                       System.out.print("$" + rs.getString("balance"));
                       if(payBalOrNotPayBal){
                           payBalance(Integer.parseInt(rs.getString("balance")), rs.getString(columnName), columnName);
                       }
                   }
               }

        } catch (Exception e) {
            System.out.println(e);
        }
        }

        public static void findStudent(boolean payBalOrNotPayBal){
            Scanner in = new Scanner(System.in);
            System.out.println("Search by: \n" +
                    "1. First name \n" +
                    "2. Last name \n" +
                    "3. Student id");
            int userRes = in.nextInt();
            in.nextLine();
            if(!payBalOrNotPayBal) {
                if (userRes == 1) {
                    System.out.print("Enter first name: ");
                    String userCompareName = in.nextLine();
                    getTable(userCompareName, "firstname", false);
                } else if (userRes == 2) {
                    System.out.print("Enter last name: ");
                    String userCompareName = in.nextLine();
                    getTable(userCompareName, "lastname", false);
                } else if (userRes == 3) {
                    System.out.print("Enter student id: ");
                    String userCompareName = in.nextLine();
                    getTable(userCompareName, "id",false);
                }
            }
            else{
                if (userRes == 1) {
                    System.out.print("Enter first name: ");
                    String userCompareName = in.nextLine();
                    getTable(userCompareName, "firstname", true);
                } else if (userRes == 2) {
                    System.out.print("Enter last name: ");
                    String userCompareName = in.nextLine();
                    getTable(userCompareName, "lastname", true);
                } else if (userRes == 3) {
                    System.out.print("Enter student id: ");
                    String userCompareName = in.nextLine();
                    getTable(userCompareName, "id",true);
                }
            }
        }
        public static void payBalance(int balanceBeforePayment, String columnContent, String columnName){
            System.out.println("Enter amount you want to pay: ");
            Scanner in = new Scanner(System.in);
            int amountWantPaid = in.nextInt();
            String query = "";
            if(amountWantPaid > 0){
                query = "UPDATE name SET balance=? WHERE " + columnName + "='"+ columnContent + "'";

            }
            else{
                return;
            }
            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "vegasBully2022!");
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, balanceBeforePayment - amountWantPaid);
                ps.executeUpdate();
                System.out.println("Thank you for your payment. Your remaining balance is $" + String.valueOf(balanceBeforePayment - amountWantPaid));

            }catch(Exception e){
                System.out.println(e);
            }


        }


    }

