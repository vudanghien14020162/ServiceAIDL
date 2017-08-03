package com.example.hien.serviceaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNumberOne, edtNumberTwo;

    private TextView tvResult;

    private ServiceAIDL mService;

    private ServiceConnection mConnect;

    private IMyCal mCalculation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        initComponents();
        setEvents();
    }

    private void findViewByIds() {
        edtNumberOne = (EditText) findViewById(R.id.edt_number_one);
        edtNumberTwo = (EditText)findViewById(R.id.edt_number_two);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    //tao let noi



    private void initComponents() {

        mConnect = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                //lay ra Imycal ben kia
                mCalculation = IMyCal.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        Intent intent = new Intent(this, ServiceAIDL.class);

        //flag tu dong ket noi
        bindService(intent, mConnect, Context.BIND_AUTO_CREATE);


    }

    private void setEvents() {

        findViewById(R.id.btn_cal).setOnClickListener(this);

        findViewById(R.id.btn_cal_sub).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String sNumberOne = edtNumberOne.getText().toString();
        String sNumberTwo = edtNumberTwo.getText().toString();

        switch (view.getId()){
            case R.id.btn_cal:
                if(sNumberOne.equals("") || sNumberTwo.equals("")){
                    showMsg("Yêu cầu nhập đày đủ số!!!");
                    return;
                }

                int numberOne = Integer.parseInt(sNumberOne);
                int numberTwo = Integer.parseInt(sNumberTwo);

                try {
                    int sum = mCalculation.sum(numberOne, numberTwo);
                    tvResult.setText(sum + "");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_cal_sub:
                if(sNumberOne.equals("") || sNumberTwo.equals("")){
                    showMsg("Yêu cầu nhập đày đủ số!!!");
                    return;
                }

                numberOne = Integer.parseInt(sNumberOne);
                numberTwo = Integer.parseInt(sNumberTwo);

                try {
                    int digit = mCalculation.sub(numberOne, numberTwo);
                    tvResult.setText(digit + "");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;

            default: break;
        }
    }

    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {

        unbindService(mConnect);

        super.onDestroy();
    }
}
