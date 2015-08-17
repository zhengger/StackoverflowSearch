package bruno.stackrest.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

import bruno.stackrest.POJOs.Topic;
import bruno.stackrest.Utilities.C;


/**
 * Created by Bruno on 2015-08-16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private Dao<Topic, Integer> topicDao = null;

    private Context context;

    public DatabaseHelper(Context context) {
//        super(context, Environment.getExternalStorageDirectory() + File.separator + C.Database.DATABASE_NAME, null, 25);  //TODO: For development purposes, storing in the external storage is fine.  For production, this should be stored in the private app folder.  Remember to toggle on WRITE_EXTERNAL_STORAGE in the manifest!
        super(context, C.Database.DATABASE_NAME, null, 25);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource){
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Topic.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion){
        //Do stuff
    }

    public Dao<Topic, Integer> getTopicDataDao() {
        if (topicDao == null) {
            try {
                topicDao = getDao(Topic.class);
            } catch (Exception e) {
                System.out.println("Exception caught :" + e);
            }
        }
        return topicDao;
    }
}
