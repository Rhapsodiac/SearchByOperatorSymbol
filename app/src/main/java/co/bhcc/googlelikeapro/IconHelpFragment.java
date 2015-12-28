package co.bhcc.googlelikeapro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import static android.graphics.Color.BLACK;

/**
 * Created by taylo on 12/15/2015.
 */
public class IconHelpFragment extends Fragment {
    private String title;
    private int page;
    private OperatorList opList;

    public interface OnDataPass{
        public void onDataPass(String data);
    }

    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity a){
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }
    public void passData(String data) {
        dataPasser.onDataPass(data);
    }


    public static IconHelpFragment newInstance(int sectionNumber, String title) {
        IconHelpFragment fragment = new IconHelpFragment();
        Bundle args = new Bundle();
        args.putInt("int", sectionNumber);
        args.putString("string", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("int", 0);
        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_icon_help, container, false);

        TableLayout view = (TableLayout)rootView.findViewById(R.id.helpTable);

        refreshOperatorList(view);

        return rootView;
    }

    public void refreshOperatorList(TableLayout view){
        final Context context = getActivity().getApplicationContext();
        OperatorList db = new OperatorList(context);
        ArrayList<Operator> opList = db.getOperators();

        Iterator<Operator> iter = opList.iterator();

        while(iter.hasNext()){
            TableRow table = new TableRow(context);
            table.setGravity(Gravity.TOP);
            view.addView(table);
            Operator oper = iter.next();
            Button temp = new Button(context);
            TextView tvTemp = new TextView(context);

            temp.setId(oper.getNumID());
            temp.setText(oper.getSymbol());
            temp.setTextColor(BLACK);
            tvTemp.setText(oper.getNote().toString());
            tvTemp.setTextColor(BLACK);
            tvTemp.setGravity(Gravity.TOP);
            tvTemp.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            temp = ((Button) temp.findViewById(temp.getId()));

            final Button finalTemp = temp;

            temp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    dataPasser.onDataPass(finalTemp.getText().toString());

                    Toast.makeText(view.getContext(), "Button clicked operator = " + finalTemp.getText(), Toast.LENGTH_LONG).show();
                }

            });

            temp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog diag = new AlertDialog.Builder(getActivity()).create();
                    diag.setTitle("Enter Text");
                    final EditText input = new EditText(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    diag.setView(input);
                    input.setTextColor(Color.BLACK);
                    input.setText(finalTemp.getText().toString());
                    input.setSelection(finalTemp.getText().toString().length());
                    diag.setButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataPasser.onDataPass(input.getText().toString());
                        }
                    });
                    diag.show();
                    return true;

                }
            });

            table.addView(temp);
            table.addView(tvTemp);
        }
    }
}
