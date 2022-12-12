import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Action;
import database.Database;
import database.Movie;
import database.User;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Database database = objectMapper.readValue(new File(args[0]), Database.class);
        User loggedUser = null;
        ArrayList<Movie> displayedMovieList = new ArrayList<>();
        Page livePage = PageLogout.getInstance();

        ArrayNode out = objectMapper.createArrayNode();

        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", "Error");
        error.putPOJO("currentMoviesList", new ArrayList<>());
        error.putPOJO("currentUser", null);

        for (Action action: database.getActions()) {
            if (action.getType().equals("change page")) {
                if (action.getPage().equals("login")
                        && livePage.equals(PageLogout.getInstance())) {
                    livePage = PageLogin.getInstance();
                    livePage.navigateToHere();
                } else if (action.getPage().equals("register")
                        && livePage.equals(PageLogout.getInstance())) {
                    livePage = PageRegister.getInstance();
                    livePage.navigateToHere();
                } else if (action.getPage().equals("logout")) {
                    livePage = PageLogout.getInstance();
                    livePage.navigateToHere();
                } else if (action.getPage().equals("movies")
                        && (livePage.equals(Homepage.getInstance())
                        || livePage.equals(PageMovies.getInstance())
                        || livePage.equals(PageSeeDetails.getInstance())
                        || livePage.equals(PageUpgrades.getInstance()))) {
                    livePage = PageMovies.getInstance();
                    livePage.navigateToHere();
                } else if (action.getPage().equals("see details")
                        && (livePage.equals(PageMovies.getInstance())
                        || livePage.equals(PageSeeDetails.getInstance()) )) {
                    livePage = PageSeeDetails.getInstance();
                    livePage.navigateToHere();
                } else if (action.getPage().equals("upgrades")
                        && (livePage.equals(Homepage.getInstance())
                        || livePage.equals(PageMovies.getInstance())
                        || livePage.equals(PageSeeDetails.getInstance())
                        || livePage.equals(PageUpgrades.getInstance()))) {
                    livePage = PageUpgrades.getInstance();
                    livePage.navigateToHere();
                } else {
                    out.add(error);
                }
            } else {
                if (action.getFeature().equals("login")
                        && livePage.equals(PageLogin.getInstance())) {
                    //PageLogin.getInstance().login();
                } else if (action.getFeature().equals("register")
                        && livePage.equals(PageRegister.getInstance())) {
                    //PageRegister.getInstance().register();
                } else if (action.getFeature().equals("search")
                        && livePage.equals(PageMovies.getInstance())) {
                    //PageMovies.getInstance().search();
                } else if (action.getFeature().equals("filter")
                        && livePage.equals(PageMovies.getInstance())) {
                    //PageMovies.getInstance().filter();
                } else if (action.getFeature().equals("purchase")
                        && livePage.equals(PageSeeDetails.getInstance())) {
                    //PageSeeDetails.getInstance().purchase();
                } else if (action.getFeature().equals("watch")
                        && livePage.equals(PageSeeDetails.getInstance())) {
                    //PageSeeDetails.getInstance().watch();
                } else if (action.getFeature().equals("like")
                        && livePage.equals(PageSeeDetails.getInstance())) {
                    //PageSeeDetails.getInstance().like();
                } else if (action.getFeature().equals("rate")
                        && livePage.equals(PageSeeDetails.getInstance())) {
                    //PageSeeDetails.getInstance().rate();
                } else if (action.getFeature().equals("buy tokens")
                        && livePage.equals(PageUpgrades.getInstance())) {
                    //PageUpgrades.getInstance().buyTokens();
                } else if (action.getFeature().equals("buy premium account")
                        && livePage.equals(PageUpgrades.getInstance())) {
                    //PageUpgrades.getInstance().buyPremiumAccount();
                } else {
                    out.add(error);
                }
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), out);
    }
}
