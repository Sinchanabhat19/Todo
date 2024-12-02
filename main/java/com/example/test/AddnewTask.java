package com.example.test;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.model.todomodel;
import com.example.test.utils.databasehelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddnewTask extends BottomSheetDialogFragment {
    public static final String TAG ="AddnewTask" ;
    private EditText meditText;
    private Button saveButton;
    private databasehelper mydb;
    public static AddnewTask newInstance(){
        return new AddnewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.addnewtask,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meditText=view.findViewById(R.id.editext);
        saveButton=view.findViewById(R.id.buttonsave);
        mydb=new databasehelper(getActivity());
        boolean isUpdate=false;
        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task=bundle.getString("task");
            meditText.setText(task);
            if(task.length()>0)
            {
                saveButton.setEnabled(false);
            }

        }
        meditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                {
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.GRAY);

                }else {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=meditText.getText().toString();
                if(finalIsUpdate){
                    mydb.updateTask(bundle.getInt("id"),text);

                }else{
                    todomodel item=new todomodel();
                    item.setTask(text);
                    item.setStatus(0);
                    mydb.insertTask(item);

                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }


    }
}
