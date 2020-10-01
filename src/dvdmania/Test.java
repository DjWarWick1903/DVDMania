package dvdmania;

public class Test {
    public static void main(String[] args) {
        ConnectionManager.writeToFile("jdbc:mysql://localhost:3306/dvdmania", "root", "robertmaster1");
    }
}
