package myclasses;

import entertainment.Season;
import fileio.ActionInputData;
import java.util.ArrayList;


public class Commands extends Action {


    /**
     * Filters used for selecting videos
     *
     * @param action
     */
    public Commands(final ActionInputData action) {
        super(action);
    }

    /**
     * put a given video into the user's favorites list
     */
    public static String favorites(final Database db, final String videoName, final Action action) {
        ArrayList<User> list = db.getUsersList();
        Video video = db.getVideo(videoName);
        for (User user:list) {
            if (user.getUserName().equals(action.getUsername())) {
                if (user.getHistory().containsKey(video)) {   //if the user already saw the video
                    if (user.getFavVideos().contains(video)) {
                        return "error -> " + video.title + " is already in favourite list";
                    }
                    if (!user.getFavVideos().contains(video)) {  // if the movie is not in
                        user.getFavVideos().add(video);            //favorites list
                        video.favListShows = video.getFavListShows() + 1;
                        return "success -> " + video.title + " was added as favourite";
                    }
                } else {
                    return "error -> " + video.title + " is not seen";
                }
            }
        }
        return null;

    }
    /**
     * gives rating to a movie
     */

    public static String ratingMovies(final Database db, final String movieName,
                                      final Action action, final Double grade) {
        ArrayList<User> list = db.getUsersList();
        Movies  movie = db.getMovie(movieName);
        for (User user:list) {
            if (user.getUserName().equals(action.getUsername())) {
                if (user.getHistory().containsKey(movie)) {
                    if (user.getRatingMoviesHistory().contains(movie)) {
                        return "error -> " + movie.getTitle() + " has been already rated";
                    } else {
                        user.getRatingMoviesHistory().add(movie);
                        movie.getRatingList().add(grade);
                        return "success -> " + movie.getTitle() + " was rated with " + grade
                                +  " by " + user.getUserName();
                    }
                } else {
                    return "error -> " + movie.getTitle() + " is not seen";
                    }
                }
        }
        return null;
    }

    /**
     * gives rating to a season
     */

    public static String ratingSeasons(final Database db, final String showName,
                                       final Action action, final Integer seasonNumber,
                                       final Double grade) {
        ArrayList<User> list = db.getUsersList();
        Shows show = db.getShows(showName);
        Season season = show.getSeasons().get(seasonNumber - 1);

        for (User user:list) {
            if (user.getUserName().equals(action.getUsername())) {
                if (user.getHistory().containsKey(show)) {
                    if (user.getRatingSeasonsHistory().contains(season)) {
                        return "error -> " + show.getTitle() + " has been already rated";
                    } else {
                        user.getRatingSeasonsHistory().add(season);
                        season.getRatings().add(grade);
                        System.out.print(show.getTitle() + season.getRatings().get(0));
                        return "success -> " + show.getTitle() + " was rated with " + grade
                                + " by " + user.getUserName();
                    }
                } else {
                    return "error -> " + show.getTitle() + " is not seen";
                }
            }
        }
        return null;
    }



    /**
     * marks the video as viewed and takes care of the user's history list(a map in our case)
     */

    public static String view(final Database db, final String videoName, final Action action) {
        ArrayList<User> list = db.getUsersList();
        Video video = db.getVideo(videoName);
        for (User user : list) {
            if (user.getUserName().equals((action.getUsername()))) {
                if (user.getHistory().containsKey(video)) {
                    user.getHistory().put(video, user.getHistory().get(video) + 1);
                    return "success -> " + video.title + " was viewed with total views of "
                            + user.getHistory().get(video);
                } else {
                    user.getHistory().put(video, 1);
                    return "success -> " + video.title + " was viewed with total views of "
                            + user.getHistory().get(video);
                }
            }
        }
        return null;
    }

}
