import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StudentGrade {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter Number of Subjects: ");
        int numSubjects = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String[] subjects = new String[numSubjects];
        int[] marks = new int[numSubjects];

        int totalMarks = 0;
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter Subject " + (i + 1) + " Name: ");
            subjects[i] = scanner.nextLine();
            System.out.print("Enter Marks for " + subjects[i] + ": ");
            marks[i] = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            totalMarks += marks[i];
        }

        double percentage = (double) totalMarks / numSubjects;
        String grade = calculateGrade(percentage);

        String htmlContent = generateHTML(studentName, subjects, marks, totalMarks, percentage, grade);
        saveToFile(studentName + "_GradeReport.html", htmlContent);

        System.out.println("\n✅ Report generated successfully! Open the HTML file to view.");
    }

    private static String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }

    private static String generateHTML(String name, String[] subjects, int[] marks, int total, double percentage, String grade) {
        StringBuilder subjectsData = new StringBuilder();
        StringBuilder chartData = new StringBuilder();

        for (int i = 0; i < subjects.length; i++) {
            subjectsData.append("<tr><td>").append(subjects[i]).append("</td><td>").append(marks[i]).append("</td></tr>");
            chartData.append("{ label: '").append(subjects[i]).append("', y: ").append(marks[i]).append(" },");
        }

        return "<!DOCTYPE html>\n" +
       "<html lang=\"en\">\n" +
       "<head>\n" +
       "    <meta charset=\"UTF-8\">\n" +
       "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
       "    <title>Student Grade Report</title>\n" +
       "    <style>\n" +
       "        @keyframes gradientAnimation {\n" +
       "            0% { background-position: 0% 50%; }\n" +
       "            50% { background-position: 100% 50%; }\n" +
       "            100% { background-position: 0% 50%; }\n" +
       "        }\n" +
       "        body {\n" +
       "            font-family: Arial, sans-serif;\n" +
       "            background: linear-gradient(120deg,rgb(0, 123, 255),rgb(255, 20, 145));\n" +
       "            background-size: 200% 200%;\n" +
       "            animation: gradientAnimation 15s infinite ease-in-out;\n" +
       "            color: white;\n" +
       "            text-align: center;\n" +
       "            margin: 0;\n" +
       "            padding: 0;\n" +
       "        }\n" +
       "        .container {\n" +
       "            max-width: 600px;\n" +
       "            margin: 50px auto;\n" +
       "            padding: 20px;\n" +
       "            background: rgba(0, 0, 0, 0.8);\n" +
       "            border-radius: 10px;\n" +
       "        }\n" +
       "        h2 { animation: fadeIn 1.5s ease-in-out; }\n" +
       "        @keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }\n" +
       "        table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n" +
       "        td, th { border: 1px solid white; padding: 10px; text-align: center; }\n" +
       "        .grade-box { font-size: 24px; font-weight: bold; background: yellow; color: black; padding: 10px; display: inline-block; border-radius: 5px; animation: popUp 1s ease-in-out; }\n" +
       "        @keyframes popUp { 0% { transform: scale(0); } 100% { transform: scale(1); } }\n" +
       "    </style>\n" +
       "</head>\n" +
       "<body>\n" +
       "    <div class=\"container\">\n" +
       "        <h2>Student Grade Report</h2>\n" +
       "        <p><strong>Name:</strong> " + name + "</p>\n" +
       "        <table>\n" +
       "            <tr><th>Subject</th><th>Marks</th></tr>\n" +
       "            " + subjectsData.toString() + "\n" +
       "        </table>\n" +
       "        <p><strong>Total Marks:</strong> " + total + "</p>\n" +
       "        <p><strong>Percentage:</strong> " + String.format("%.2f", percentage) + "%</p>\n" +
       "        <p><strong>Grade:</strong> <span class=\"grade-box\">" + grade + "</span></p>\n" +
       "        <div id=\"chartContainer\" style=\"height: 300px; width: 100%; margin-top: 20px;\"></div>\n" +
       "    </div>\n" +
       "    <script src=\"https://cdn.canvasjs.com/canvasjs.min.js\"></script>\n" +
       "    <script>\n" +
       "        window.onload = function() {\n" +
       "            var chart = new CanvasJS.Chart(\"chartContainer\", {\n" +
       "                animationEnabled: true,\n" +
       "                backgroundColor: \"transparent\",\n" +
       "                data: [{\n" +
       "                    type: \"pie\",\n" +
       "                    showInLegend: true,\n" +
       "                    legendText: \"{label}\",\n" +
       "                    dataPoints: [" + chartData.toString() + "]\n" +
       "                }]\n" +
       "            });\n" +
       "            chart.render();\n" +
       "        };\n" +
       "    </script>\n" +
       "</body>\n" +
       "</html>";

    }

    private static void saveToFile(String fileName, String content) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content);
        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }
}