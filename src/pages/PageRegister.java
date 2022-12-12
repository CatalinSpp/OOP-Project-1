package pages;

public class PageRegister implements Page {
    private static PageRegister instance = null;

    private PageRegister() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageRegister getInstance() {
        if (instance == null) {
            instance = new PageRegister();
        }
        return instance;
    }

    @Override
    public void navigateToHere() {

    }
}
