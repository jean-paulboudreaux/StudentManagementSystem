package src;

import java.io.*;
import java.util.Scanner;

public class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private int gradeLevel;
    private String course = "";
    private int tuitionBalance = 0;
    private static int courseCost = 500;
    private int id = 0;
    Scanner in = new Scanner(System.in);



    public Student() {
        System.out.println("Enter first name: ");
        this.firstName = in.nextLine();
        System.out.println("Enter last name: ");
        this.lastName = in.nextLine();
        System.out.println("Enter grade level : ");
        this.gradeLevel = in.nextInt();

    }
    public void updateID() {
        BufferedWriter out = null;
        try {

            // Read File Contents - score
            BufferedReader br = new BufferedReader(new FileReader("/Users/jean-paulboudreaux/IdeaProjects/StudentManagementSystems/src/id"));
            String storedScore="1000";
            int storedScoreNumber = 1000;
            while ((storedScore = br.readLine()) != null) {
                storedScoreNumber=(Integer.parseInt(storedScore==null?"1000":storedScore));
            }

            // Write File Contents - incremented socre
            out = new BufferedWriter(new FileWriter("/Users/jean-paulboudreaux/IdeaProjects/StudentManagementSystems/src/id", false));
            out.write(String.valueOf(storedScoreNumber+1));
            String updatedNumber = String.valueOf(storedScoreNumber + 1);
            this.studentID = getGradeLevel() + updatedNumber;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void enroll() {
        do {
            System.out.println("Enter a course: (Press f to quit)");
            Scanner in = new Scanner(System.in);
            String courses = in.nextLine();
            if (!courses.equalsIgnoreCase("f")) {
                course = course + "\n" + courses;
                this.tuitionBalance += courseCost;
            } else {
                break;
            }
        }while(1!= 0);

        System.out.println(tuitionBalance);

    }

    public void payTuition(){
        viewBalance();
        System.out.print("Enter your payment: ");
        Scanner in = new Scanner(System.in);
        int payment = in.nextInt();
        tuitionBalance = tuitionBalance - payment;
        System.out.println("Thank you for your payment of $" + payment);
        viewBalance();

    }
    public void viewBalance(){
        System.out.println("Your balance is: $" + tuitionBalance);
    }

    public int getId() {
        return id;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getTuitionBalance() {
        return tuitionBalance;
    }

    public static int getCourseCost() {
        return courseCost;
    }
}
