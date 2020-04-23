package com.example.testlistener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

@BindView(R.id.message)
public TextView  message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createListeners(this);
    }


    @OnClick(R.id.button)
    public void button1(View view){
        Toast.makeText(this,"button1 was clicked" +view.toString(),Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.button2)
    public void button2(View view){
        Toast.makeText(this,"button2 was clicked" +view.toString(),Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.button3)
    public void button3(View view){
        Toast.makeText(this,"button3 was clicked" +view.toString(),Toast.LENGTH_LONG).show();
    }


    @SuppressLint("SetTextI18n")
    @OnClick(R.id.makeMessage)
    public void makeMessage(View view){
        message.setText("Hello annotation");
    }



    private void createListeners(final AppCompatActivity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try{
                BindView bindView = field.getAnnotation(BindView.class);
                assert bindView != null;
                field.setAccessible(true);
                field.set(activity,activity.findViewById(bindView.value()));
            }catch (Exception ignore){

            }
        }
        Method[] methods = activity.getClass().getDeclaredMethods();


        for (final Method method : methods) {
            try{
                OnClick onClick = method.getAnnotation(OnClick.class);
                assert onClick != null;
                View viewById = activity.findViewById(onClick.value());
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(activity,v);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception ignore){

            }
        }
    }

}
