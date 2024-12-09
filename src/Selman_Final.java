import java.util.Scanner;
import java.lang.Math;

public class Selman_Final {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        String[] studentNames = new String[100];
        double[] courseAverage = new double[100];
        int[] studentID = new int[100];
        double[] scholarship = new double[100];
        String[] letterGrades = new String[100];
        double[] extraCredit = new double[100];

        String courseName;
        String sectionID;
        int students = 0;
        int courses;
        double grade = 0;
        double gradeTotal = 0;
        boolean again = true;
        int count = 0;
        int lowest = 0;
        int highest = 0;
        String temp;
        double classAverage = 0;
        int passing = 0;
        int scholarships = 0;
        int num;

        System.out.print("Number of courses: ");
        courses = input.nextInt();
        input.nextLine(); //consumes return stroke from previous input
        for (int i = 0; i < courses; i++) {
            System.out.print("Course name >> ");
            courseName = input.nextLine();
            System.out.print("Section ID >> ");
            sectionID = input.nextLine();
            while (again) {
                System.out.print("Student name >> ");
                studentNames[students] = input.nextLine();
                System.out.print("Student ID >> ");
                studentID[students] = input.nextInt();
                input.nextLine(); //consumes return stroke from last statement
                for (int j = 2; j >= 0; j--) {
                    switch (j) {
                        case 2:
                            System.out.println("Please input grades for SLO assignments.");
                            break;
                        case 1:
                            System.out.println("Please input grades for program assignments.");
                            break;
                        default:
                            System.out.println("Please input grades for quizzes, exams, and discussions.");
                    }
                    while(grade != -1) {
                        System.out.print("Please enter a grade from 0 to 100 (-1 to end) >> ");
                        grade = input.nextInt();
                        input.nextLine(); //consumes return stroke from last statement
                        if (grade <= 100 && grade >= 0) {
                            gradeTotal += grade;
                            count++;
                        } else if (grade < -1 || grade > 100) System.out.println("Grade should be between 0 and 100. Please try again.");
                    }
                    grade = 0;
                    courseAverage[students] += average(gradeTotal, count, j);
                    count = 0;
                }

                boolean fail = true;
                while (fail) {
                    try {
                        System.out.print("Please enter an extra credit value from 0 to 5 >> ");
                        extraCredit[students] = input.nextInt();
                        input.nextLine();
                        fail = false;
                    } catch (NumberFormatException f) {
                        System.out.print("\nAmount entered must be a number.");
                        fail = true;
                    }
                    System.out.println();   // Goes to next line
                    if (extraCredit[students] >= 0 && extraCredit[students] <= 5) courseAverage[students] += extraCredit[students];
                    else {
                        System.out.print("Extra credit was not between 0 and 5. Please try again.");
                        fail = true;
                    }
                }

                switch ((int) (courseAverage[students]/10)) {
                    case 9:
                        letterGrades[students] = "A";
                        break;
                    case 8:
                        letterGrades[students] = "B";
                        break;
                    case 7:
                        letterGrades[students] = "C";
                        break;
                    default: letterGrades[students] = "F";
                }

                scholarship[students] = Math.round(scholarships(courseAverage[students])* 10.0) / 10.0;

                if (courseAverage[lowest] > courseAverage[students]) lowest = students;
                else if (courseAverage[highest] < courseAverage[students]) highest = students;

                if (courseAverage[students] >= 85) {
                    passing++;
                    scholarships++;
                } else if (courseAverage[students] >= 70) passing++;

                System.out.println("Would you like to enter another student for " + courseName + "?");
                temp = input.nextLine();
                if (temp.contentEquals("y") || temp.contentEquals("Y"))  {
                    students++;
                } else again = false;
                try {
                    num = studentNames[students].indexOf(" ");
                    studentNames[students] = studentNames[students].substring(num);
                } catch (IndexOutOfBoundsException ignored){
                }
            }

            System.out.println("Course: " + courseName + "\tSection ID: " + sectionID);
            for (int k = 0; k < students; k++) {
                print(i, studentNames, studentID, courseAverage, scholarship, k, extraCredit, letterGrades);
                classAverage += courseAverage[k];
            }
            classAverage = classAverage / (students + 1);
            System.out.println("Class average: " + classAverage);
            System.out.println("Passing students: " + passing + "\tStudents who earned scholarships: " + scholarships);
            System.out.println("Student with the highest class average: " + studentNames[highest] + "\tGrade: " + courseAverage[highest]);
            System.out.println("Student with the lowest class average: " + studentNames[lowest] + "\tGrade: " + courseAverage[lowest]);
            students++;
        }
    }

    static double scholarships (double courseAverage) {
        if (courseAverage >= 95) return 750;
        else if (courseAverage >= 85) return 500;
        else if (courseAverage >= 80) return 250;
        else return 0;
    }

    static double average(double gradeTotal, int count, int j) { //Calculates weighted averages based on grade weights.
        final float SLO_WEIGHT = .35F;      // SLO grade weight value
        final float PROGRAM_WEIGHT = .2F;   // Program grade weight value
        final float OTHER_WEIGHT = .15F;    // Quiz, exam, and discussion grade weight value

        return switch (j) {
            case 2 -> (gradeTotal / (count + 1)) * SLO_WEIGHT;  // SLO weighted average
            case 1 -> (gradeTotal / (count + 1)) * PROGRAM_WEIGHT;   // Program wighted average
            default -> (gradeTotal / (count + 1)) * OTHER_WEIGHT; // Quizzes, exams, and discussion weighted averages
        };
    }

    static void print (int i, String[] studentNames, int[] studentID, double[] courseAverage, double[] scholarship, int k, double[] extraCredit, String[] letterGrades) {
        System.out.println("Student Name: " + studentNames[i] + "\tStudent ID: " + studentID[k]);
        System.out.println("Course Average: " + courseAverage[k] + "\tLetter grade: " + letterGrades[k]+ "\tScholarship: $" + scholarship[k] + "\tExtra credit given: " + extraCredit[k]);
        System.out.println("------------------------------------------------------------------------------");
    }
}