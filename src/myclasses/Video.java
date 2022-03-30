package myclasses;
import fileio.ShowInput;
import java.util.ArrayList;

public abstract class Video {
    /**
     *video's title
     */

    public String title;
    /**
     *video's premiere year
     */
    private int premiere;
    /**
     *video's genre
     */
    private ArrayList<String> genre;
    /**
     *video's cast used for parsing
     */
    private ArrayList<String> tempCast;
    /**
     *actual cast
     */
    private ArrayList<Actor> actorsCast;
    /**
     *video's duration
     */
    private int duration;
    /**
     * the appearance in other's favorites list
     */
    public int favListShows;


    public Video(final ShowInput showInput) {
        this.title = showInput.getTitle();
        this.premiere = showInput.getYear();
        this.genre = showInput.getGenres();
        this.tempCast = showInput.getCast();
        this.actorsCast = new ArrayList<>();
        this.favListShows = 0; //the number of appereance in users fav lists

    }
    /**
     * returns the video title
     */

    public String getTitle() {
        return title;
    }
    /**
     * adds the actor's cast in the actorsCast
     */

    public void parseTempCast(final Database db) {
        for (String temp_actor:this.tempCast) {
            this.actorsCast.add(db.getActor(temp_actor));
        }
    }
    /**
     * returns the premiere year
     */

    public int getPremiere() {
        return premiere;
    }
    /**
     * returns the genre
     */

    public ArrayList<String> getGenre() {
        return genre;
    }

    /**
     * returns a String ArrayList of the tempCast
     */

    public ArrayList<String> getTempCast() {
        return tempCast;
    }
    /**
     * returns the actual actors cast an ArrayList of Actors
     */

    public ArrayList<Actor> getActorsCast() {
        return actorsCast;
    }
    /**
     * abstract method for rating
     */

    public abstract Double getRating();
    /**
     * abstract method for duration
     */

    public abstract int getDuration();
    /**
     * returns the appearance of the video in users favorite list
     */

    public int getFavListShows() {
        return favListShows;
    }

}
