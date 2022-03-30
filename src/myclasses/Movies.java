package myclasses;
import fileio.MovieInputData;
import java.util.ArrayList;


public class Movies extends Video {
    private Integer duration;
    private ArrayList<Double> ratingList;
    public Integer viewsNo = 0;
    public Movies(final MovieInputData inputMovie) {
        super(inputMovie);
        this.duration = inputMovie.getDuration();
        this.ratingList = new ArrayList<>();

    }
    /**
     * returns the duration of the movie
     */
    public int getDuration() {
        return duration;
    }
    /**
     * returns the number of views
     */

    public Integer getViewsNo() {
        return viewsNo;
    }
    /**
     * returns the rating of the movie
     */


    public Double getRating() {
        Double sum = 0.0;
        if (ratingList == null) {
            return (double) -1.0;
        }
        for (Double rating:ratingList) {
            sum = sum + rating;
        }
        if (this.ratingList.size() == 0) {
            return -1.0;
        }
        Double average = (double) (sum / (this.ratingList.size()));
        return average;
    }
    /**
     * returns the ratings given to the movie
     */
    public ArrayList<Double> getRatingList() {
        return ratingList;
    }

}
