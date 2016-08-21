package kr.ac.cbnu.fullstack.simpledictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JiyoungPark on 2016. 5. 3..
 */
public class SearchAdapter extends BaseAdapter{

    private ArrayList<SearchData> source;
    private LayoutInflater layoutInflater;

    public SearchAdapter(Context context, ArrayList<SearchData> source) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.source = source;
    }

    @Override
    public int getCount() {
        return (source != null) ? source.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return ((source != null) && (position >= 0 && position < source.size()) ? source.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SearchData data = (SearchData) getItem(position);
        SearchHolder searchHolder;

        if (convertView == null) {

            searchHolder = new SearchHolder();
            convertView = layoutInflater.inflate(R.layout.layout_item, parent, false);

            searchHolder.textView_word = (TextView) convertView.findViewById(R.id.textView_word);
            searchHolder.textView_mean = (TextView) convertView.findViewById(R.id.textView_mean);

            convertView.setTag(searchHolder);

        } else { searchHolder = (SearchHolder) convertView.getTag(); }

        searchHolder.textView_word.setText(data.getWord());
        searchHolder.textView_mean.setText(data.getMean() + "\n(검색 횟수 : " + data.getCount() + " 회)");

        return convertView;
    }
}
