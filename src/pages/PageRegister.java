package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.*;

import java.util.ArrayList;

import static pages.Homepage.success;
import static pages.PageLogin.loginError;

public class PageRegister implements Page {
    private static PageRegister instance = null;

    private PageRegister() {
    }

    /**
     * getInstance() method for Singleton design pattern
     */
    public static PageRegister getInstance() {
        if (instance == null) {
            instance = new PageRegister();
        }
        return instance;
    }

    @Override
    public void navigateToHere(Database database) {
        database.setLoggedUser(null);
        database.getDisplayedMovieList().clear();
    }

    public void register(final Database database, final Action action, final ArrayNode out) {
        infiniteLoop:
        while (true) {
            int userIdx = 0;
            while (userIdx < database.getUsers().size()) {
                if (action.getCredentials().getName()
                        .equals(database.getUsers().get(userIdx).getCredentials().getName())) {
                    loginError(database, out);
                    break infiniteLoop;
                }
                userIdx++;
            }
            User newOne = new User();
            newOne.setCredentials(new Credentials(action.getCredentials()));
            database.getUsers().add(newOne);
            database.setLoggedUser(new User(newOne));
            PageLogin.enterAcc(database);
            success(database, out);
            break;
        }
    }
}
