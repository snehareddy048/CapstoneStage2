package com.example.snehaanand.multisearch.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.snehaanand.multisearch.R;
import com.example.snehaanand.multisearch.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolBarFragment extends Fragment {
    ImageButton favorite;

    public ToolBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tool_bar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favorite = (ImageButton) getActivity().findViewById(R.id.favorite_btn);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra(Utils.SORT_STRING, Utils.FAVORITE);
                startActivity(intent);
            }
        });
    }
}
