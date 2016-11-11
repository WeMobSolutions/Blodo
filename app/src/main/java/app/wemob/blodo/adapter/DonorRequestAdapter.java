package app.wemob.blodo.adapter;

/**
 * Created by admin on 11/5/2016.
 */

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.wemob.blodo.BlodoDashboard;
import app.wemob.blodo.R;
import app.wemob.blodo.data.BlodoDonor;

/**
 * Created by Belal on 10/29/2015.
 */
public class DonorRequestAdapter extends RecyclerView.Adapter<DonorRequestAdapter.ViewHolder> {

    List<BlodoDonor> donors;

    public DonorRequestAdapter(BlodoDashboard parent,ArrayList<BlodoDonor> donorslist){
        super();

        donors=donorslist;
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
        BlodoDonor list =  donors.get(position);
        holder.txtName.setText(list.getName());
        holder.txtBgroup.setText(list.getBgroup());
        holder.txtCity.setText(list.getCity());

    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtName;
        public TextView txtBgroup;
        public TextView txtCity;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtBgroup = (TextView) itemView.findViewById(R.id.txtbgroup);
            txtCity = (TextView) itemView.findViewById(R.id.txtplace);

        }
    }
}
