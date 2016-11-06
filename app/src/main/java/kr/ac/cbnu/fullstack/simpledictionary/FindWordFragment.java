package kr.ac.cbnu.fullstack.simpledictionary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FindWordFragment extends Fragment {

    View view;
    SearchData data;
    EditText editText_word;
    Button button_search;
    ListView listView_result;
    ArrayList<SearchData> source;
    SearchAdapter searchAdapter = null;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_word, container, false);

        initModel();
        initView();
        initController();
        initListener();

        return view;
    }

    private void initModel() {

        source = new ArrayList<>();
    }

    private void initView() {

        view = View.inflate(getContext(), R.layout.fragment_find_word, null);

        listView_result = (ListView) view.findViewById(R.id.listView_result);
        editText_word = (EditText) view.findViewById(R.id.editText_word);
        button_search = (Button) view.findViewById(R.id.button_search);
        /*
        editText_word = (EditText) findViewById(R.id.editText_word);
        button_search = (Button) findViewById(R.id.button_search);
        button_load = (Button) findViewById(R.id.button_load);
        button_save = (Button) findViewById(R.id.button_save);
        button_clear = (Button) findViewById(R.id.button_clear);
        radioGroup_align = (RadioGroup) findViewById(R.id.radioGroup_align);
        listView_result = (ListView) findViewById(R.id.listView_result);

        listView_result.setFocusable(false);*/
    }

    private void initController() {

        searchAdapter = new SearchAdapter(view.getContext(), source);
        listView_result.setAdapter(searchAdapter);
    }

    private void initListener() {

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText_word.getText())) {
                    Toast.makeText(getActivity(), "검색어를 입력하세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                data = new SearchData();
                data.setWord(editText_word.getText().toString().toLowerCase());
                data.setMean("");
                data.setDate();

                for (int i = 0; i < source.size(); i++) {
                    // 기존에 검색된 결과라면 있는 결과를 보여줌
                    if ((data.getWord().equals(source.get(i).getWord()) && source.get(i).getMean() != null)) {
                        source.get(i).setCount();
                        source.get(i).getCount();
                        source.add(0, source.get(i));
                        source.remove(i + 1);
                        searchAdapter.notifyDataSetChanged();
                        editText_word.setText("");

                        return;
                    }
                }

                // Daum Dictionary에서 단어 뜻 가져오기
                AsyncTask<String, Void, String> mTask = new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {

                        final String DAUM_DICTIONARY_URL = "http://small.dic.daum.net/search.do?q=";

                        final String PARSING_TAG1 = "<ul class=\"list_search\">";
                        final String PARSING_TAG2 = "</ul>";

                        String regex1 = "\\<.*?\\>";
                        String regex2 = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";

                        URL url = null;
                        String response = "";

                        try {
                            url = new URL(DAUM_DICTIONARY_URL + data.getWord() + "&dic=eng");

                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
                            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);

                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                            while ((response = bufferedReader.readLine()) != null) {
                                if (response.contains(PARSING_TAG1)) {
                                    while (!response.contains(PARSING_TAG2)) {
                                        data.setMean(data.getMean() + response);
                                        Log.e("data.mean : ", response);
                                        response = bufferedReader.readLine();
                                    }
                                    Log.e("data.mean : ", response);
                                    break;
                                }
                            }

                            data.setMean(data.getMean().replaceAll(regex1, ""));
                            data.setMean(data.getMean().replaceAll(regex2, ""));
                            data.setMean(data.getMean().replaceAll("\t", ""));

                        } catch (IOException e) {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPreExecute();
                        for (int i = 0; i < source.size(); i++) {
                            // load된 데이터에 mean이 존재하지 않을 때 추가
                            if ((source.get(i).getWord().equals(data.getWord()))) {
                                source.get(i).getCount();
                                source.get(i).setMean(data.getMean());
                                source.add(0, source.get(i));
                                source.remove(i + 1); // 기존 listview에 있던 data 삭제
                                searchAdapter.notifyDataSetChanged();
                                editText_word.setText("");
                                return;
                            }
                        }

                        // 처음 검색한 단어일 경우 listview에 추가
                        source.add(0, data);
                        searchAdapter.notifyDataSetChanged();
                        editText_word.setText("");
                    }
                };

                mTask.execute();
            }
        });
    }


}
