package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Action;
import database.Database;
import database.User;

import java.util.ArrayList;

//upgrades page
public final class PageUpgrades implements Page {
    private User userOnPage;
    private static PageUpgrades instance = null;

    private PageUpgrades() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageUpgrades getInstance() {
        if (instance == null) {
            instance = new PageUpgrades();
        }
        return instance;
    }

    @Override
    public void navigateToHere(final Database database) {
        userOnPage = database.getLoggedUser();
    }

    /** buy tokens function */
    public void buyTokens(final Action action, final ArrayNode out) {
        if (Integer.parseInt(userOnPage.getCredentials().getBalance())
                < Integer.parseInt(action.getCount())) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode error = objectMapper.createObjectNode();
            error.put("error", "Error");
            error.putPOJO("currentMoviesList", new ArrayList<>());
            error.putPOJO("currentUser", null);
            out.add(error);
        } else {
            String updatedBalance =
                    Integer.toString(Integer.parseInt(userOnPage.getCredentials().getBalance())
                            - Integer.parseInt(action.getCount()));
            int newTokensCount = userOnPage.getTokensCount() + Integer.parseInt(action.getCount());
            userOnPage.setTokensCount(newTokensCount);
            userOnPage.getCredentials().setBalance(updatedBalance);
        }
    }

    /** buy premium acc function */
    public void buyPremiumAccount(final ArrayNode out) {
        switch (userOnPage.getCredentials().getAccountType()) {
            case "standard" -> {
                if (userOnPage.getTokensCount() < 10) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ObjectNode error = objectMapper.createObjectNode();
                    error.put("error", "Error");
                    error.putPOJO("currentMoviesList", new ArrayList<>());
                    error.putPOJO("currentUser", null);
                    out.add(error);
                } else {
                    userOnPage.getCredentials().setAccountType("premium");
                    int newTokensCount = userOnPage.getTokensCount() - 10;
                    userOnPage.setTokensCount(newTokensCount);
                }
            }
            case "premium" -> {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("error", "Error");
                error.putPOJO("currentMoviesList", new ArrayList<>());
                error.putPOJO("currentUser", null);
                out.add(error);
            }
            default -> System.out.println("wrong acc type");
        }
    }
}
