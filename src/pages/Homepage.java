package pages;

public class Homepage implements Page {
    private static Homepage instance = null;

    private Homepage() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static Homepage getInstance() {
        if (instance == null) {
            instance = new Homepage();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
