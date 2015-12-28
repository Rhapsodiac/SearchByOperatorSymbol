package co.bhcc.googlelikeapro;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Taylor on 12/15/2015.
 */
public class SearchFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        EditText bar = (EditText)rootView.findViewById(R.id.etSearch);
        //didn't get soft keyboard working
        return rootView;
    }




}
