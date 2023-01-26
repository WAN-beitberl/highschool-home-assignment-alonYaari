import java.util.Scanner;

import static java.lang.System.exit;

public class menu {
    static Scanner sc = new Scanner(System.in);

    public static void options(){
        int cohice = 0;
        message();
        cohice = sc.nextInt();

        while(cohice!=8){
            switch (cohice){
                case 1:
                    System.out.println("Average school grades: " + jdbc.avgGrades(1));
                    break;
                case 2:
                    System.out.println("Males school grades: " + jdbc.avgGrades(2));
                    break;
                case 3:
                    System.out.println("Females school grades: " + jdbc.avgGrades(3));
                    break;
                case 4:
                    System.out.println("The average height of the people above 2 meters and has a purple car is:" + jdbc.avgHeight());
                    break;
                case 5:
                    System.out.println("Enter student id: ");
                    int id = sc.nextInt();
                    System.out.println("The circle of friends of student number " + id + " are: " + jdbc.findFriends(id,1).toString());
                    break;
                case 6:
                    jdbc.studentTypes();
                    break;
                case 7:
                    System.out.println("Enter student id: ");
                    int ID = sc.nextInt();
                    System.out.println("The average grade of student number " + ID + " is: " + jdbc.getAvg(ID));
                    break;
                case 8:
                    System.out.println("bye bye");
                    exit(1);
                    break;
            }
            message();
            cohice = sc.nextInt();
        }
    }

    public static void message(){
        System.out.println("-------------------------");
        System.out.println("Hello Sima, please pick your preferred option:");
        System.out.println("1 - Average school grades");
        System.out.println("2 - Male school average");
        System.out.println("3 - Female school grades");
        System.out.println("4 - Average height of students above 2 meters, and has a purple car");
        System.out.println("5 - All friends of student number: ");
        System.out.println("6 - To get the percentage of popular, regular and lonely students");
        System.out.println("7 - The average grade of: ");
        System.out.println("8 - To quit: ");
        System.out.println("-------------------------");
    }
}
