package co.bhcc.googlelikeapro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


 public class IconFragment extends Fragment{


        private String title;
        private int page;
        private OperatorList opList;

        public interface OnDataPass {

        void onDataPass(String data);
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

        public static IconFragment newInstance(int sectionNumber, String title) {
            IconFragment fragment = new IconFragment();
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

            View rootView = inflater.inflate(R.layout.fragment_icon, container, false);
            TableLayout view = (TableLayout)rootView.findViewById(R.id.operTable);

            refreshOperatorList(view);

            return rootView;
        }

        public void refreshOperatorList(TableLayout view){
            final Context context = getActivity().getApplicationContext();
            OperatorList db = new OperatorList(context);
            ArrayList<Operator> opList = db.getOperators();

            //Queue and Iterator for Operator containment
            Queue<Operator> q = new LinkedList<>();
            Iterator<Operator> iter = opList.iterator();

            //initial tablerow
            TableRow table = new TableRow(context);
            table.setPadding(50, 20, 50, 20);
            view.addView(table);

            while(iter.hasNext()) {
                Operator oper = iter.next();
                q.add(oper);

                if (q.size() == 3 || !iter.hasNext())
                {
                    while(q.peek()!= null) { //loop is a bit scary
                        Operator tempOper= q.poll();
                        Button temp = new Button(context);
                        temp.setId(tempOper.getNumID());
                        temp.setText(tempOper.getSymbol());
                        temp.setTextColor(Color.BLACK);
                        temp.setPadding(80,20,80,20); //WHY DON'T YOU WORK!?!?!?!?
                        temp = ((Button) temp.findViewById(temp.getId()));
                        final Button finalTemp = temp;

                        temp.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View view){

                                dataPasser.onDataPass(finalTemp.getText().toString());
                                Toast.makeText(view.getContext(), "Button clicked operator = " + finalTemp.getText(), Toast.LENGTH_LONG).show();
                            }

                        });

                        temp.setOnLongClickListener(new View.OnLongClickListener(){
                            @Override
                            public boolean onLongClick(View view){
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
                    }
                }
                if (q.isEmpty()){
                    table = new TableRow(context);
                    table.setPadding(50, 20, 50, 20);
                    view.addView(table);
                }
            }
        }
}

