package bruno.stackrest.Retrofit;


import java.util.Map;

import bruno.stackrest.POJOs.ResponseStackREST;
import bruno.stackrest.Utilities.C;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface StackoverflowRetrofitInterface {

    @GET(C.StackoverflowServer.SEARCH_GET_REQUEST)
    ResponseStackREST getFeed(@QueryMap Map testMap);
}
