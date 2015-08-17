package bruno.stackrest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.List;

import bruno.stackrest.POJOs.Topic;
import bruno.stackrest.R;


public class TopicAdapter extends ArrayAdapter<Topic> {

    private Context context;
    private List<Topic> mList;

    public TopicAdapter(Context context, int resource, List<Topic> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_searchresult, parent, false);
            holder = new ViewHolder();

            holder.PosterName = (TextView) convertView.findViewById(R.id.poster_name);
            holder.AnswerCount = (TextView) convertView.findViewById(R.id.answers_number) ;
            holder.PostTitle = (TextView) convertView.findViewById(R.id.thread_name) ;
            holder.PosterPicture = (ImageView) convertView.findViewById(R.id.picture_poster);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Topic TopicItem = mList.get(position);

        holder.PosterName.setText(Jsoup.parse(TopicItem.getdisplayName()).text());
        holder.AnswerCount.setText(TopicItem.getanswerCount());
        holder.PostTitle.setText(Jsoup.parse(TopicItem.getTopicTitle() ).text());
        Picasso.with(getContext())
                .load(TopicItem.getUserImageURL())
                .resize(80, 80)
                .into(holder.PosterPicture);

        return convertView;
    }

    private static class ViewHolder {
        public TextView PosterName;
        public TextView AnswerCount;
        public TextView PostTitle;
        public ImageView PosterPicture;
    }


}
