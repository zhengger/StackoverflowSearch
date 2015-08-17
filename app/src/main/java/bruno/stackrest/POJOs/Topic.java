package bruno.stackrest.POJOs;

import com.j256.ormlite.field.DatabaseField;

import bruno.stackrest.Utilities.C;

/**
 * Created by Bruno on 2015-04-03.
 */
public class Topic {

    @DatabaseField(columnName = C.Database.TopicsTable.ColumnNames.SEARCH_TERM)
    private String searchTerm;

    @DatabaseField(columnName = C.Database.TopicsTable.ColumnNames.DISPLAY_NAME)
    private String displayName;

    @DatabaseField(columnName = C.Database.TopicsTable.ColumnNames.USER_IMAGE_URL)
    private String userImageURL;

    @DatabaseField(columnName = C.Database.TopicsTable.ColumnNames.ANSWER_COUNT)
    private String answerCount;

    @DatabaseField(columnName = C.Database.TopicsTable.ColumnNames.TOPIC_TITLE)
    private String topicTitle;

    @DatabaseField(columnName = C.Database.TopicsTable.ColumnNames.TOPIC_LINK)
    private String topicLink;



    public String getSearchTerm()   {  return searchTerm;  }
    public String getdisplayName()   {  return displayName;  }
    public String getUserImageURL()   {  return userImageURL;  }
    public String getanswerCount()   {  return answerCount;  }
    public String getTopicTitle()          {  return topicTitle;  }
    public String getTopicLink()           {  return topicLink;  }

    public Topic(String searchTerm, String displayName, String userImageURL, String answerCount, String topicTitle, String topicLink)  {
        this.searchTerm = searchTerm;
        this.displayName = displayName;
        this.userImageURL = userImageURL;
        this.answerCount = answerCount;
        this.topicTitle = topicTitle ;
        this.topicLink = topicLink ;
    }

    public Topic()  {  // needed by ormlite
    }
}
