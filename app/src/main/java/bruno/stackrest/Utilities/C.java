package bruno.stackrest.Utilities;

/**
 * Created by Bruno on 2015-08-14.
 */
public class C {

    public interface General {
        String URL = "url";
        String STACKOVERFLOW_SEARCH = "Stackoverflow Search";
        String SEARCH_IN_TITLE_PARAMETER = "Search in-title parameter";
    }

    public interface Database {
        String DATABASE_NAME = "StackSearch.db";

        interface TopicsTable {

            String TABLE_NAME = "TopicsSearchResults";

            interface ColumnNames {
                String SEARCH_TERM = "SearchTerm";
                String DISPLAY_NAME = "DisplayName";
                String USER_IMAGE_URL = "UserImageURL";
                String ANSWER_COUNT = "AnswerCount";
                String TOPIC_TITLE = "TopicTitle";
                String TOPIC_LINK = "TopicLink";
            }
        }

    }

    public interface StackoverflowServer {
        double VERSION = 2.2;
        String SERVER_URL = "https://api.stackexchange.com/";
        String ENDPOINT = SERVER_URL + VERSION;

        String SEARCH_GET_REQUEST = "/search?order=desc&sort=activity&site=stackoverflow";
        String QUERY_IN_TITLE = "intitle=" ;
    }

    public interface SearchService {

        String SEARCH_SERVICE_RESULT = "Search Service Result";

        enum SearchServiceResult {
            DOWNLOAD_SUCCESSFUL,
            DOWNLOAD_FAILED,
            ZERO_SEARCH_RESULTS,
            DATABASE_ERROR
        }
    }


}
