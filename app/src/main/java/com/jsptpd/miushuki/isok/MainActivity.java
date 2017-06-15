package com.jsptpd.miushuki.isok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView section1, section2, section3;
    private Spinner spinnerA, spinnerB, spinnerC;
    private Button launch;
    private String nums[];
    private ArrayAdapter<String> adapter;
    private String A, B, C;
/*存储每次action后的状态*//*采用并发容器*/
    private CopyOnWriteArrayList<State> states;
    private CopyOnWriteArrayList<State> judgeStates;
    private String storedA,storedB,storedC;
    /*建立存储上次显示数据的数据池*/
    private ArrayList<State> pool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        section1 = (TextView) findViewById(R.id.textView_section1);
        section2 = (TextView) findViewById(R.id.textView_section2);
        section3 = (TextView) findViewById(R.id.textView_section3);
        spinnerA = (Spinner) findViewById(R.id.spinner_A);
        spinnerB = (Spinner) findViewById(R.id.spinner_B);
        spinnerC = (Spinner) findViewById(R.id.spinner_C);
        launch = (Button) findViewById(R.id.button_launch);
        states=new CopyOnWriteArrayList<>();
        pool=new ArrayList<>();
        pool.add(new State(0+"",0+"",0+""));//默认的pool中数据0,0,0
        nums = getResources().getStringArray(R.array.ArabicNumerals);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nums);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerA.setAdapter(adapter);
        spinnerA.setSelection(0, true);
        spinnerB.setAdapter(adapter);
        spinnerB.setSelection(0, true);
        spinnerC.setAdapter(adapter);
        spinnerC.setSelection(0, true);

        Toast.makeText(this, "list内有"+states.size()+"个state", Toast.LENGTH_SHORT).show();
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch();
      /*          Toast.makeText(getApplicationContext(), "list内有"+states.size()+"个state", Toast.LENGTH_SHORT).show();
                Log.d("CC","有"+states.size()+"个state对象");*/
            }
        });
    }

  public void launch(){
        //*添加state状态到list，进行state的差异化比较。利用switch case进行渲染*//*
      A = (String) spinnerA.getSelectedItem();
      B = (String) spinnerB.getSelectedItem();
      C = (String) spinnerC.getSelectedItem();
      State currentState=new State(A,B,C);
      if(states.size()==0) {
          states.add(currentState);
          section1.setText(A);
          section2.setText(B);
          section3.setText(C);
          pool.clear();//第一次不需要差异化比较，直接存储到pool中用于下次比较
          pool.add(currentState);
        }else{
          Iterator<State> iterator=states.iterator();
          judgeStates=new CopyOnWriteArrayList<>();
          while(iterator.hasNext()){
              State state=iterator.next();
               judgeStates.add(state);
          }
          if (!judgeStates.contains(currentState)){
              states.add(currentState);
          }
          /*判断显示数据与上次数据的不同，进行差异化设置  此处应该用switch case*/
          State poolState= pool.get(0);
          if (!A.equals(poolState.getSection1())){
              section1.setText(A);
          }
          if (!B.equals(poolState.getSection2())){
              section2.setText(B);
          }
          if (!C.equals(poolState.getSection3())){
              section3.setText(C);
          }
          pool.clear();
          pool.add(currentState);

        }
      Log.d("CC","有"+states.size()+"个state对象");
      Toast.makeText(this, "list内有"+states.size()+"个state", Toast.LENGTH_SHORT).show();
    }
}
