package app.wemob.blodo.adapter;

/**
 * Created by admin on 11/5/2016.
 */

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.wemob.blodo.BlodoDashboard;
import app.wemob.blodo.R;
import app.wemob.blodo.data.BlodoDonor;
import app.wemob.blodo.utils.Validator;

/**
 * Created by Belal on 10/29/2015.
 */
public class DonorRequestAdapter extends RecyclerView.Adapter<DonorRequestAdapter.ViewHolder> {

    List<BlodoDonor> donors;
    BlodoDashboard parentobj;

    public DonorRequestAdapter(BlodoDashboard parent,ArrayList<BlodoDonor> donorslist){
        super();

        donors=donorslist;
        parentobj=parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blodo_donor_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BlodoDonor list =  donors.get(position);
        holder.txtName.setText(list.getName());
        holder.txtBgroup.setText("Blood Group Required : "+list.getBgroup());
        holder.txtCity.setText(list.getCity());
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(list.getMobile());
            }
        });
        SharedPreferences userpreferences=parentobj.getSharedPreferences("blodouser",parentobj.MODE_PRIVATE);
        if(userpreferences.getInt("status",0)!=2)
        {
            holder.imgCall.setEnabled(false);
            Validator.showToast(parentobj,"You must verify for calling");
        }
        else
        {
            holder.imgCall.setEnabled(true);
        }

    }

    private void call(String mob) {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:0800000000"));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(parentobj, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(parentobj,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {     //have got permission
            try{
                parentobj.startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(parentobj,"Unknown Error",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtName;
        public TextView txtBgroup;
        public TextView txtCity;
        public ImageButton imgCall;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtBgroup = (TextView) itemView.findViewById(R.id.txtbgroup);
            txtCity = (TextView) itemView.findViewById(R.id.txtplace);
            imgCall=(ImageButton)itemView.findViewById(R.id.btncall);


        }

    }
}
