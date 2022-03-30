package myclasses;
import entertainment.Season;
import fileio.UserInputData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    /**
     * the username
     */
    private String userName;
    /**
     * type of subscription
     */
    private String subscription;
    /**
     * the user's favorites video list
     */
    private ArrayList<Video> favVideos;
    /**
     * the user temp_video list used for parsing
     */
    private ArrayList<String> favVideosTemp;
    /**
     * the user's history
     */
    private Map<Video, Integer> history;
    /**
     * the temp history used for parsing
     */
    private Map<String, Integer> historyTemp;
    /**
     * the movies rated by the user
     */
    private ArrayList<Movies> ratingMoviesHistory;
    /**
     * the seasons rated by the user
     */
    private ArrayList<Season> ratingSeasonsHistory;


    public User(UserInputData inputUser) {
        this.userName = inputUser.getUsername();
        this.subscription = inputUser.getSubscriptionType();
        this.favVideosTemp = inputUser.getFavoriteMovies();
        this.historyTemp = inputUser.getHistory();
        this.history = new HashMap<Video, Integer>();
        this.favVideos = new ArrayList<>();
        this.ratingMoviesHistory = new ArrayList<>();
        this.ratingSeasonsHistory = new ArrayList<>();
    }
    /**
     * method used for parsing the videos from database
     */

    public void parseTempVideos(final Database db) {
        for (String temp_video:this.favVideosTemp) {
            this.favVideos.add(db.getVideo(temp_video));
        }
    }
    /**
     * method used for parsing the map used for history
     */

    public void parseTempHistory(final Database db) {
        for (String temp_video:this.historyTemp.keySet()) {
            this.history.put(db.getVideo(temp_video), this.historyTemp.get(temp_video));
        }
    }
    /**
     * this method calls the parsing methods
     */


    public void parseTemp(final Database db) {
        this.parseTempHistory(db);
        this.parseTempVideos(db);
    }
    /**
     * returns the username
     */


    public String getUserName() {
        return userName;
    }
    /**
     * returns the history
     */


    public Map<Video, Integer> getHistory() {
        return this.history;
    }
    /**
     * returns the user favorites list
     */


    public ArrayList<Video> getFavVideos() {
        return this.favVideos;
    }

    /**
     * method that returns the rating history for movies
     */

    public ArrayList<Movies> getRatingMoviesHistory() {
        return ratingMoviesHistory;
    }

    /**
     * method that returns the rating history for seasons
     */


    public ArrayList<Season> getRatingSeasonsHistory() {
        return ratingSeasonsHistory;
    }
    /**
     * method that returns the type of subscription
     */


    public String getSubscription() {
        return subscription;
    }
}
