package bruno.stackrest.Utilities;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import bruno.stackrest.Databases.DatabaseHelper;
import bruno.stackrest.POJOs.Topic;
import roboguice.RoboGuice;

/**
 * Created by Bruno on 2015-08-16.
 */
public class DatabaseUtilities {

    private DatabaseHelper databaseHelper = null;
    private Dao<Topic, Integer> topicDao ;

    private Context context ;

    @Inject
    public DatabaseUtilities(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
        topicDao = this.getHelper().getTopicDataDao();
    }

    public void saveSearchResults (List<Topic> listTopic) throws SQLException {
        for(Topic topic: listTopic) {
            topicDao.create(topic);
        }
    }

    public void deleteSearchResultsByInTitle (String searchTerm) throws SQLException {
        DeleteBuilder<Topic, Integer> deleteBuilder = topicDao.deleteBuilder();
        deleteBuilder.where().eq(C.Database.TopicsTable.ColumnNames.SEARCH_TERM, searchTerm);
        deleteBuilder.delete();
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public List<Topic> getListTopicByInTitle (String searchTerm) throws SQLException{
        List<Topic> listTopic = topicDao.queryForEq(C.Database.TopicsTable.ColumnNames.SEARCH_TERM, searchTerm);
        return listTopic;

    }
}
