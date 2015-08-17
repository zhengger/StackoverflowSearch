package bruno.stackrest.Utilities;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bruno.stackrest.POJOs.ResponseStackREST;
import bruno.stackrest.POJOs.Topic;
import roboguice.RoboGuice;

/**
 * Created by Bruno on 2015-08-16.
 */
public class DataUtilities {

    @Inject
    public DataUtilities(Context context) {
        RoboGuice.injectMembers(context, this);
    }

    public List<Topic> convertServerResponseToTopicList(ResponseStackREST serverResponse, String searchTerm) {
        List<Topic> ListTopic = new ArrayList<>();
        int NumberOfSearchReturns = serverResponse.getItems().length;

        for (int i = 0; i < NumberOfSearchReturns -1; i++) {
            ListTopic.add(new Topic(
                            searchTerm,
                            serverResponse.getItems()[i].getOwner().getDisplay_name(),
                            serverResponse.getItems()[i].getOwner().getProfile_image(),
                            serverResponse.getItems()[i].getAnswer_count(),
                            serverResponse.getItems()[i].getTitle(),
                            serverResponse.getItems()[i].getLink())
            );
        }
        return ListTopic;
    }
}
