package app.wemob.blodo.adapter;

/**
 * Created by admin on 11/5/2016.
 */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
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
public class DonorCardAdapter extends RecyclerView.Adapter<DonorCardAdapter.ViewHolder> {

    List<BlodoDonor> donors;
    BlodoDashboard parentObj;

    public DonorCardAdapter(BlodoDashboard parent, ArrayList<BlodoDonor> donorslist) {
        super();

        donors = donorslist;
        parentObj = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blodo_donor_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    private void call(String mob) {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:"+mob));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(parentObj, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(parentObj,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {     //have got permission
            try{
                parentObj.startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(parentObj,"Unknown Error",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BlodoDonor list =  donors.get(position);
        holder.txtName.setText(list.getName());
        holder.txtBgroup.setText("Blood Group : "+list.getBgroup());
        holder.txtCity.setText(list.getCity());
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(list.getMobile());
            }
        });
        SharedPreferences userpreferences=parentObj.getSharedPreferences("blodouser",parentObj.MODE_PRIVATE);
        if(userpreferences.getInt("status",0)!=2)
        {
            holder.imgCall.setEnabled(false);
            Validator.showToast(parentObj,"You must verify for calling");
        }
        else
        {
            holder.imgCall.setEnabled(true);
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
