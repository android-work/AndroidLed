package com.led.scroller.lite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.led.scroller.lite.R;
import com.led.scroller.lite.Utils;

public class AddActivity extends AppCompatActivity {

    private ImageView add_back;
    private EditText add_edit;
    private TextView add_next;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setState(this);

        setContentView(R.layout.add_layout);

        add_back = findViewById(R.id.add_back);
        add_edit = findViewById(R.id.add_edit);
        add_next = findViewById(R.id.add_next);

        setListener();
    }

    private void setListener() {
        add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                content = editable.toString();
            }
        });

        add_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(add_edit.getText().toString())){
                    Toast.makeText(AddActivity.this,getString(R.string.input_content),0).show();
                    return;
                }
                Intent intent = new Intent(AddActivity.this, SetSizeColorActivity.class);
                intent.putExtra("content",content);
                startActivity(intent);
                finish();
            }
        });
    }
}
