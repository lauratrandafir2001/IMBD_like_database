package myclasses;

import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;



public class Recommendation extends Action {
    public Recommendation(final ActionInputData action) {
        super(action);
    }
    /**
     * returns the first video not seen by the user
     */

    public static String standard(final Database db, final Action action) {
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> list = db.getUsersList();
        for (User user : list) {
            if (user.getUserName().equals(action.getUsername())) {
                for (Video video1 : videoList) {
                    if (!user.getHistory().containsKey(video1)) {
                        return "StandardRecommendation result: " + video1.getTitle();
                    }
                }
            }

        }
        return "StandardRecommendation cannot be applied!";
    }
    /**
     * returns the best rated video not seen by the user
     */

    public static String bestUnseen(final Database db, final Action action) {
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> usersList = db.getUsersList();
        videoList.sort((Video video1, Video video2) ->
                Double.compare(video1.getRating(), video2.getRating()));
        for (User user : usersList) {
            if (user.getUserName().equals(action.getUsername())) {
                for (Video video1 : videoList) {
                    if (!user.getHistory().containsKey(video1)) {
                        return "BestRatedUnseenRecommendation result: " + video1.getTitle();
                    }
                }
            }

        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * returns a hashmap with each genre and the number of views
     */

    public static Map<String, Integer> popularGenre(final Database db, final Action action) {
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> usersList = db.getUsersList(); //map for genre and number of total views
        Map<String, Integer> genreViews = new HashMap<String, Integer>() { };
        for (User user : usersList) {
            for (Video video1 : videoList) {
                if (user.getHistory().containsKey(video1)) { //we create the hashmap and update it
                    for (int i = 0; i < video1.getGenre().size(); i++) {
                        if (genreViews.containsKey(video1.getGenre().get(i))) {
                            genreViews.replace(video1.getGenre().get(i),
                                    genreViews.get(video1.getGenre().get(i))
                                            + user.getHistory().get(video1));
                        } else {
                            genreViews.put(video1.getGenre().get(i),
                                    user.getHistory().get(video1));
                        }
                    }
                }
            }
        }
        //we sort the map in order to get the most popular genre

        Map<String, Integer> result = genreViews.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return result;

    }
    /**
     * returns the video from the most popular genre, that is not seen by the user
     */

    public static String popular(final Database db, final Action action) {
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> usersList = db.getUsersList();
        Map<String, Integer> genreViews = popularGenre(db, action);
        genreViews.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        String genre = genreViews.keySet().stream().findFirst().get();//primul cel mai popular gen
        for (User user : usersList) {
            if (user.getUserName().equals(action.getUsername())) {
                if (user.getSubscription().equals("BASIC")) {
                    return "PopularRecommendation cannot be applied!";
                }
                for (Map.Entry<String, Integer> entry : genreViews.entrySet()) {
                    for (Video video: videoList) {
                        if (video.getGenre().contains(entry.getKey())) {
                            if (!user.getHistory().containsKey(video)) {
                                return "PopularRecommendation result: " + video.getTitle();
                            }
                        }
                    }
                }
            }
        }
       return "PopularRecommendation cannot be applied!";
    }

    /**
     * returns the video not seen by the user that has the most appearance in users fav list
     */



    public static String favorite(final Database db, final Action action) {
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> list = db.getUsersList();
        Collections.sort(videoList, new SortVideosByFav().reversed());

        for (User user : list) {
            if (user.getUserName().equals(action.getUsername())) {
                if (user.getSubscription().equals("BASIC")) {
                    return "FavoriteRecommendation cannot be applied!";
                }
                for (Video video1 : videoList) {
                    if (!user.getHistory().containsKey(video1)) {
                        return "FavoriteRecommendation result: " + video1.getTitle();
                    }
                }
            }

        }
        return "FavoriteRecommendation cannot be applied!";
    }
    /**
     *compares videos by the number of time they show up in users fav list
     */

    static class SortVideosByFav implements Comparator<Video> {
        public int compare(final Video video1, final Video video2) {
            return (int) (video1.getFavListShows() - video2.getFavListShows());
        }
    }
    /**
     * returns all the videos not seen by the user from a given genre
     */
    public static String search(final Database db, final Action action, final String genre) {
        ArrayList<Video> videoList = db.getVideoList();
        ArrayList<User> list = db.getUsersList();
        ArrayList<Video> genreVideos = new ArrayList<>();
        for (User user : list) {
            if (user.getUserName().equals(action.getUsername())) {
                if (user.getSubscription().equals("BASIC")) {
                    return "SearchRecommendation cannot be applied!";
                }
                for (Video video1 : videoList) {
                    if (!user.getHistory().containsKey(video1)) {
                        for (int i = 0; i < video1.getGenre().size(); i++) {
                            if (video1.getGenre().get(i).equals(genre)) {
                                genreVideos.add(video1);
                            }
                        }
                    }

                }
            }
        }
       Collections.sort(genreVideos, new SortSearchRec());
        genreVideos.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));

        ArrayList<String> video_names = new ArrayList<>();
        for (Video video : genreVideos) {
            video_names.add(video.getTitle());

        }
        if (video_names.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + video_names;
    }

    /**
     * compare videos by rating and then by name
     */

    static class SortSearchRec implements Comparator<Video> {
        public int compare(final Video video1, final Video video2) {

            if (Double.compare(video1.getRating(), video2.getRating()) == 0) {
                return video1.getTitle().compareTo(video2.getTitle());
            } else {
                return Double.compare(video1.getRating(), video2.getRating());
            }

        }
    }

}
