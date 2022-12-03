package com.tamer.vlsm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.vlsm.model.Address;

import java.util.List;

public class ResultSubnetAdapter extends RecyclerView.Adapter<ResultSubnetAdapter.ResultViewHolder>{
    public ResultSubnetAdapter(List<Address> addresses, Context context) {
        this.addresses = addresses;
        this.context = context;
    }

    List<Address> addresses;
    Context context;

    @NonNull
    @Override
    public ResultSubnetAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subnet_result_card, parent, false);
        return new ResultSubnetAdapter.ResultViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ResultSubnetAdapter.ResultViewHolder holder, int position) {
        Address address = addresses.get(position);
        holder.subnet_name.setText(address.getName());
        holder.host_range.setText(address.getFirst_address() + " - " + address.getLast_address());
        holder.network_address.setText(address.getNetwork_address());
        holder.broadcast_address.setText(address.getBroadcast_address());
        holder.allocated.setText("Allocated: "+ address.getAllocated()+" ("+(int)address.getAllocation()+"%"+")");
        holder.required.setText("Required: "+address.getRequired());
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView network_address, broadcast_address,allocated,required,host_range,subnet_name;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            subnet_name = itemView.findViewById(R.id.subnet_name);
            network_address = itemView.findViewById(R.id.network_address);
            broadcast_address = itemView.findViewById(R.id.broadcast_address);
            allocated = itemView.findViewById(R.id.allocated);
            required = itemView.findViewById(R.id.required);
            host_range = itemView.findViewById(R.id.host_range);
        }
    }
}
