package app.wemob.blodo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.wemob.blodo.BlodoDashboard;
import app.wemob.blodo.R;
import app.wemob.blodo.data.BlodoDonor;

/**
 * Created by admin on 11/7/2016.
 */

public class FaqCardAdapter  extends RecyclerView.Adapter<FaqCardAdapter.ViewHolder> {

    List<String[]> Faqs;

    public FaqCardAdapter(BlodoDashboard parent, ArrayList<String[]> faqslist){
        super();

        Faqs=faqslist;
    }

    @Override
    public FaqCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_card_view, parent, false);
        FaqCardAdapter.ViewHolder viewHolder = new FaqCardAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FaqCardAdapter.ViewHolder holder, int position) {
        String[] list =  Faqs.get(position);
        holder.txtQuestion.setText(list[0]);
        holder.txtAnswer.setText(Html.fromHtml(list[1]));


    }

    @Override
    public int getItemCount() {
        return Faqs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtQuestion;
        public TextView txtAnswer;

        public ViewHolder(View itemView) {
            super(itemView);

            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            txtAnswer = (TextView) itemView.findViewById(R.id.txtAnswer);


        }
    }
}
