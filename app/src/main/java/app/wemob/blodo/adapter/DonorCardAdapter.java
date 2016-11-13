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
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mob));
            if (ActivityCompat.checkSelfPermission(parentObj, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                parentObj.startActivity(callIntent);
                return;
            }

        } catch (ActivityNotFoundException e) {
            Log.e("Some error occured", "Call failed", e);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BlodoDonor list =  donors.get(position);
        holder.txtName.setText(list.getName());
        holder.txtBgroup.setText(list.getBgroup());
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
