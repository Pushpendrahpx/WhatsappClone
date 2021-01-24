package com.shareable.whatsapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallsFragment extends Fragment {
    String mTitle[] = {"Facebook","Whatsapp","Instagram","Twitter","Youtube","Facebook","Whatsapp","Instagram","Twitter","Youtube"};
    String mDescription[] = {"Facebook Description","Wahsatpp Description","Instagram Description","Twitter Description","Youtube Description","Facebook Description","Wahsatpp Description","Instagram Description","Twitter Description","Youtube Description"};


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CallsFragment() {
        // Required empty public constructor
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallsFragment newInstance(String param1, String param2) {
        CallsFragment fragment = new CallsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Context thiscontext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_calls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(getContext(),mTitle,mDescription);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getContext(),"First One",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Second One",Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setAdapter(adapter);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];

        MyAdapter(Context c, String title[], String description[]){
            super(c,R.layout.call_row,R.id.textView10,title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.call_row,parent,false);
            ImageView images = row.findViewById(R.id.call_user_pic);
            TextView myTitle = row.findViewById(R.id.textView10);
            TextView myDescription = row.findViewById(R.id.textView11);

            images.setImageResource(R.drawable.ic_launcher_foreground);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);




            return row;
        }
    }

}