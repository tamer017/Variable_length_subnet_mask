package com.tamer.vlsm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tamer.vlsm.R;
import com.tamer.vlsm.model.InputSubnet;

import java.util.List;

public class InputSubnetAdapter extends RecyclerView.Adapter<InputSubnetAdapter.InputViewHolder> {

    private static Context context;
    private List<InputSubnet> inputSubnets;

    public InputSubnetAdapter(Context context, List inputSubnets) {
        this.context = context;
        this.inputSubnets = inputSubnets;

    }

    @NonNull
    @Override
    public InputViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subnet_input_card, parent, false);
        return new InputViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull InputViewHolder holder, @SuppressLint("RecyclerView") int position) {

    }

    @Override
    public int getItemCount() {
        return inputSubnets.size();
    }
    public void addSubnet(InputSubnet inputSubnet){
        inputSubnets.add(inputSubnet);
        notifyItemInserted(getItemCount()-1);
    }
    public void setInputSubnet(List<InputSubnet> inputSubnets) {
        this.inputSubnets = inputSubnets;
        notifyDataSetChanged();
    }

    public static class InputViewHolder extends RecyclerView.ViewHolder {
        EditText number_of_hosts, subnet_name;
        ImageButton delete;
        InputSubnetAdapter adapter;

        public InputViewHolder(@NonNull View itemView) {
            super(itemView);
            number_of_hosts = itemView.findViewById(R.id.number_of_hosts);
            subnet_name = itemView.findViewById(R.id.subnet_name);
            delete = itemView.findViewById(R.id.delete);
            subnet_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }


                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(getAdapterPosition()>-1)
                    adapter.inputSubnets.get(getAdapterPosition()).setName(s.toString());
                }
            });

            number_of_hosts.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }


                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().isEmpty())
                            if(getAdapterPosition()>-1)
                            adapter.inputSubnets.get(getAdapterPosition()).setNeededHosts(Integer.parseInt(s.toString()));

                }
            });

            delete.setOnClickListener(view -> {
                if(getAdapterPosition()>-1) {
                    subnet_name.getText().clear();
                    number_of_hosts.getText().clear();
                    System.out.println(getAdapterPosition());
                    adapter.inputSubnets.remove(getAdapterPosition());
                    adapter.notifyItemRemoved(getAdapterPosition());
                }

            });
        }

        public InputViewHolder linkAdapter(InputSubnetAdapter inputSubnetAdapter) {
            this.adapter = inputSubnetAdapter;
            return this;
        }
    }
}