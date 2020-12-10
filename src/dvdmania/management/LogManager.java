package dvdmania.management;

public class LogManager {

    private static LogManager instance = null;

    private LogManager() {
    }

    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }

        return instance;
    }
}
