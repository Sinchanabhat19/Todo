package com.example.test.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.AddnewTask;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.model.todomodel;
import com.example.test.utils.databasehelper;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    private List<todomodel> mlist;
    private MainActivity activity;
    private databasehelper mydb;
    public TodoAdapter(databasehelper mydb,MainActivity activity)
    {
        this.activity=activity;
        this.mydb=mydb;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklayout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final todomodel item=mlist.get(position);
        holder.checkbox.setText(item.getTask());
        holder.checkbox.setChecked(toboolean(item.getStatus()));
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mydb.updateStatus(item.getId(),1);
                }
                else{
                    mydb.updateStatus(item.getId(),0);
                }
            }
        });

    }
    public boolean toboolean(int num){
        return num!=0;
    }
    public Context getContext(){
        return  activity;
    }
    public void setTasks(List<todomodel> mlist){
        this.mlist=mlist;
        notifyDataSetChanged();
    }
    public void deletetask(int position){
        todomodel item=mlist.get(position);
        mydb.deleteTask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }
    public void edititem(int position){
        todomodel item=mlist.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());
        AddnewTask  task=new AddnewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(), task.getTag());

    }



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkbox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox=itemView.findViewById(R.id.checkbox);
        }

    }
}
