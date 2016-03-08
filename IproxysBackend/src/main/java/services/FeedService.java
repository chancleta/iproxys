package services;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import models.Company;
import models.Employee;
import models.Feed;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by lupena on 2/25/2016.
 */
public class FeedService {

    private static FeedService feedService;

    private List<Feed> feedList;
    private int offset;


    private FeedService(){
        offset = 0;
        setFeedStartUpList();
    }

    public static FeedService getInstance(){
        if(feedService == null){
            feedService = new FeedService();
        }
        return feedService;
    }

    public List<Feed> getFeedList(){
        return feedList;
    }

    public List<Feed> getFecthFeedList(int amount){
        List<Feed> subList = feedList.subList(offset, offset + amount);
        offset += amount;
        return subList;
    }

    private void setFeedStartUpList(){
        feedList = new ArrayList<>();

        int amountOfFeedsToCreate = (int) (Math.random() * (150 - 100)) + 100;// A random amount between 150 and 100

        IntStream.range(0, amountOfFeedsToCreate).forEach(
            creationIndex -> {
                Fairy fairy = Fairy.create();
                Person randomPersonData = fairy.person();
                Feed feed = new Feed();
                feed.setName(randomPersonData.fullName());
                feed.setId(CompanyService.getInstance().getRandomID());

                int randomImage =  (int) (Math.random() * (964 - 1)) + 1;
                feed.setImgURL("https://unsplash.it/600/600?image=" + randomImage );
                feedList.add(feed);
            }
        );

    }
}
