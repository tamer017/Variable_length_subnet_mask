package com.tamer.vlsm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tamer.vlsm.model.Address;
import com.tamer.vlsm.model.InputSubnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Button submit_button;
    ImageButton clear_ip;
    EditText byte1, byte2, byte3, byte4, cidr;
    InputSubnetAdapter adapter;
    ExtendedFloatingActionButton extendedFloatingActionButton;
    ArrayList<InputSubnet> inputSubnets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        submit_button = findViewById(R.id.submit_button);
        byte1 = findViewById(R.id.byte1);
        byte2 = findViewById(R.id.byte2);
        byte3 = findViewById(R.id.byte3);
        byte4 = findViewById(R.id.byte4);
        cidr = findViewById(R.id.cidr);
        clear_ip = findViewById(R.id.clear_ip);
        extendedFloatingActionButton = findViewById(R.id.add_subnet);
        recyclerView=findViewById (R.id.input_recyclerview);
        inputSubnets = new ArrayList<>();
        adapter=new InputSubnetAdapter(this, inputSubnets);
        recyclerView.setLayoutManager (new LinearLayoutManager(this));
        recyclerView.setAdapter (adapter);



        extendedFloatingActionButton.setOnClickListener(view -> {
            adapter.addSubnet(new InputSubnet("", 0));
        });
        clear_ip.setOnClickListener(view -> {
            byte1.getText().clear();
            byte2.getText().clear();
            byte3.getText().clear();
            byte4.getText().clear();
            cidr.getText().clear();;
        });
        submit_button.setOnClickListener(view -> {
            if (byte1.getText().toString().isEmpty() || Integer.parseInt(byte1.getText().toString()) > 255 ||
                    byte2.getText().toString().isEmpty() || Integer.parseInt(byte2.getText().toString()) > 255 ||
                    byte3.getText().toString().isEmpty() || Integer.parseInt(byte3.getText().toString()) > 255 ||
                    byte4.getText().toString().isEmpty() || Integer.parseInt(byte4.getText().toString()) > 255) {
                Toast.makeText(MainActivity.this, "Please enter valid IP", Toast.LENGTH_SHORT).show();
            } else if (cidr.getText().toString().isEmpty() || Integer.parseInt(cidr.getText().toString()) > 32)
                Toast.makeText(MainActivity.this, "Please enter valid CIDR", Toast.LENGTH_SHORT).show();
            else if (inputSubnets.size() == 0) {
                Toast.makeText(MainActivity.this, "Please add at least one subnet", Toast.LENGTH_SHORT).show();
            } else {
                boolean valid = true;
                for (int index = 0; index < inputSubnets.size(); index++) {
                    InputSubnet subnet = inputSubnets.get(index);
                    if (subnet.getName().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter a name for subnet : " + index, Toast.LENGTH_SHORT).show();
                        valid = false;
                        break;
                    }
                    if (subnet.getNeededHosts() == 0) {
                        Toast.makeText(MainActivity.this, "Please enter a size for subnet : " + index, Toast.LENGTH_SHORT).show();
                        valid = false;
                        break;
                    }
                    if (subnet.getNeededHosts() < 2) {
                        Toast.makeText(MainActivity.this, "For subnet : " + index + " you need to have at least 2 addresses because of the network and broadcast addresses", Toast.LENGTH_LONG).show();
                        valid = false;
                        break;
                    }

                }
                if (valid) {
                String ip = byte1.getText().toString() + "."
                        + byte2.getText().toString() + "."
                        + byte3.getText().toString() + "."
                        + byte4.getText().toString();
                int CIDR = Integer.parseInt(cidr.getText().toString());
                List<Address> addresses = getAddresses(ip,CIDR);
                Intent intent = new Intent(MainActivity.this, Results.class);
                intent.putExtra("LIST", (Serializable) addresses);
                startActivity(intent);
            }
        }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // find the number of addresses available per each subnet.
    public  int[] allocatedAddresses(int[] required_hosts) {
        int[] allocated_addresses = new int[required_hosts.length];
        for (int i = 0; i < required_hosts.length; i++) {
            required_hosts[i] += 2;
            allocated_addresses[i] = (int) (Math.ceil(Math.log(required_hosts[i]) / Math.log(2)));
            allocated_addresses[i] = (int) Math.pow(2, allocated_addresses[i]);
        }
        return allocated_addresses;
    }

    // number of available addresses in the main network.
    public  int availableAddresses(int cidr) {
        return (int) Math.pow(2, 32 - cidr);
    }

    public  List<Address> getAddresses(String main_address, int cidr) {
        // number of available addresses in the main network.
        int size = inputSubnets.size();
        int[] required_hosts = new int[size];
        String[] names = new String[size];
        for (int index = 0; index < inputSubnets.size(); index++) {
            required_hosts[index] = inputSubnets.get(index).getNeededHosts();
            names[index] = inputSubnets.get(index).getName();
        }
        Arrays.sort(required_hosts);
        int number_of_addresses = availableAddresses(cidr);
        int[] allocated_addresses = allocatedAddresses(required_hosts);
        if (!isValid(number_of_addresses, allocated_addresses))
            return null;
        int currentIp = Address.convertQuartetToBinaryString(main_address);
        List<Address> addresses = new ArrayList<Address>();
        for (int i = allocated_addresses.length - 1; i >= 0; i--) {
            int _cidr = 32 - (int) (Math.log(allocated_addresses[i]) / Math.log(2));
            int network_address = currentIp;
            int broadcast_address = currentIp + allocated_addresses[i] - 1;
            int required = required_hosts[i]-2;
            int allocated = allocated_addresses[i]-2;
            addresses.add(new Address(names[i], network_address, broadcast_address, _cidr, required,
                    allocated));
            currentIp += allocated_addresses[i];
        }
        return addresses;

    }

    private  boolean isValid(int number_of_addresses, int[] subnets_hosts) {
        int sum = 0;
        for (int subnet : subnets_hosts) {
            sum += subnet;
        }
        return sum <= number_of_addresses;
    }

}