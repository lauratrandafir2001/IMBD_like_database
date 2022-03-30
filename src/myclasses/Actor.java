package myclasses;
import actor.ActorsAwards;
import java.util.ArrayList;
import java.util.Map;
import fileio.ActorInputData;



public class Actor  {

        private String name;
        private String careerDescription;
        private Map<ActorsAwards, Integer> awards;
        private ArrayList<Video> videos;
        private ArrayList<String> tempVideos;
        private int awardsNumber;
        private int awardsSum;



    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.careerDescription = actor.getCareerDescription();
        this.awards = actor.getAwards();
        this.tempVideos = actor.getFilmography();
        this.awardsNumber = this.getAwardsSum();
        this.videos = new ArrayList<Video>();
        this.awardsSum = 0;
    }
    /**
     * returns a map with the award and how many pieces of that award the actor got
     */

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    /**
     * returns the actor's name
     */


    public String getName() {
        return name;
    }
    /**
     * returns the actor's career description
     */

    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     * returns the awards number
     */

    public int getAwardsNumber() {
        return awardsNumber;
    }
    /**
     * returns a specific sum of awards
     */

    public int getAwardsSum() {
        int sum = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : this.getAwards().entrySet()) {
            sum = sum + entry.getValue();
    }
        return sum;
    }

    /**
     * adds the video from database
     */


    public void parseTempVideos(final Database db) {
        for (String tempVideo : tempVideos) {
            if (db.getVideo(tempVideo) != null) {
                videos.add(db.getVideo(tempVideo));
            }
        }
    }
    /**
     * parsese the videos
     */

    public void parseTemp(final Database db) {
        this.parseTempVideos(db);
    }
    /**
     * returns videos
     */


    public ArrayList<Video> getVideos() {
        return videos;
    }
    /**
     * returns the actor's rating
     */


    public Double getRating(final Database db) {
        double sum = 0.0;
        int k = 0;
        ArrayList<Video> list = getVideos();
        if (this.videos.size() == 0) {
            return 0.0;
        }
        for (Video video: this.videos) {
            if (video.getRating() == -1) {
                k++;
                continue;
            }
                sum += video.getRating();
        }
        double average = 0;
        average = sum / (this.videos.size() - k);
        return average;
    }
}
