import java.io.*;
import java.util.*;

class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return name + " - " + marks;
    }
}

public class StudentGradeManager {
    static final String FILE_NAME = "students.txt";
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();
        int choice;
        do {
            System.out.println("\n====== STUDENT GRADE MANAGEMENT SYSTEM ======");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Show Statistics (Average, Highest, Lowest)");
            System.out.println("4. Save to File");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> displayStudents();
                case 3 -> showStats();
                case 4 -> saveToFile();
                case 5 -> {
                    saveToFile();
                    System.out.println("Exiting... Data saved successfully!");
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter marks (0–100): ");
        double marks = getDoubleInput();
        students.add(new Student(name, marks));
        System.out.println("✅ Student added successfully!");
    }

    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
    }

    static void showStats() {
        if (students.isEmpty()) {
            System.out.println("No student data available.");
            return;
        }
        double total = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        String top = "", low = "";

        for (Student s : students) {
            total += s.marks;
            if (s.marks > max) {
                max = s.marks;
                top = s.name;
            }
            if (s.marks < min) {
                min = s.marks;
                low = s.name;
            }
        }

        double avg = total / students.size();
        System.out.printf("\n📊 Average Marks: %.2f\n", avg);
        System.out.println("🏆 Topper: " + top + " (" + max + ")");
        System.out.println("📉 Lowest: " + low + " (" + min + ")");
    }

    static void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                bw.write(s.name + "," + s.marks);
                bw.newLine();
            }
            System.out.println("💾 Data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    static void loadFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    double marks = Double.parseDouble(parts[1]);
                    students.add(new Student(name, marks));
                }
            }
            System.out.println("📂 Loaded " + students.size() + " students from file.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid decimal number: ");
            }
        }
    }
}
