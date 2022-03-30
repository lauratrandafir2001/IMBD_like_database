package myclasses;
import fileio.SerialInputData;
import entertainment.Season;
import java.util.ArrayList;

public class Shows extends Video {
    /**
     * an array list of seasons
     */

    private ArrayList<Season> seasons;
    /**
     * the duration of the video
     */

    private int duration;
    /**
     * how many views the show has
     */

    public Integer viewsNo = 0;

    public Shows(final SerialInputData showInput) {
        super(showInput);
        this.seasons = showInput.getSeasons();
    }
    /**
     * returns the rating of the show based on the rating of each season
     */

    public Double getRating() {
        Double averageRating = (double) 0;

        for (Season season : seasons) {
            averageRating = averageRating + getSeasonAverageRating(season);
        }

        return averageRating / seasons.size();
    }

    /**
     * returns the rating of one season
     */


    public Double getSeasonAverageRating(final Season season) {
        if (season.getRatings().size() == 0) {
            return (double) 0;
        }

        Double averageRating = (double) 0;
        for (Double rating : season.getRatings()) {
            averageRating += rating;
        }

        return averageRating / season.getRatings().size();
    }
    /**
     * returns the the views number
     */
    public Integer getViewsNo() {
        return viewsNo;
    }
    /**
     * returns the duration
     */

    public int getDuration() {
        for(Season season: this.seasons) {
             duration = duration + season.getDuration();
        }
        return duration;
    }
    /**
     * returns the ArrayList of seasons
     */

    public ArrayList<Season> getSeasons() {
        return seasons;
    }


}
