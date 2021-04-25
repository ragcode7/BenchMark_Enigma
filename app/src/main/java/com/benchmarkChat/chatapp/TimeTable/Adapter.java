package com.benchmarkChat.chatapp.TimeTable;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.benchmarkChat.chatapp.R;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    int count;
    FragmentManager fragmentManager;
    DataHolder[] dataHolders;
    int type;

    public Adapter(final Activity activity, int count, FragmentManager fragmentManager, DataHolder[] dataHolders, int type) {
        this.activity = activity;
        this.count = count;
        this.fragmentManager = fragmentManager;
        this.dataHolders = dataHolders;
        this.type = type;

    }

    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


    final int TYPE_TITLE = 1283;
    final int TYPE_OTHER = 9697;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        if(viewType == TYPE_TITLE)
            viewHolder = new TitleData(inflater.inflate(R.layout.title_layout, null));

        else
            viewHolder = new ItemData(inflater.inflate(R.layout.item, null));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position <7){
            ((TitleData)holder).titleView.setText(days[position]);
        }

        else{
            Log.e("TAG", "onBindViewHolder: " + position );
            ((ItemData)holder).title.setText("(Empty)");
            ((ItemData)holder).subTitle.setText("");
            ((ItemData)holder).timing.setText("");
            ((ItemData)holder).itemView.setVisibility(View.VISIBLE);
            ((ItemData)holder).cardView.setCardBackgroundColor(Color.rgb(3,218,197));


            if(dataHolders[position - 7] != null){
                Log.e("TAG", "onBindViewHolder: " + dataHolders );
                if(dataHolders[position - 7].getRecess()){
                    Log.e("TAG", "onBindViewHolder: " + dataHolders[position - 7].getTitle() );
                    ((ItemData)holder).title.setText(dataHolders[position - 7].getTitle());
                    ((ItemData)holder).subTitle.setText(dataHolders[position - 7].getSubTitle());
                    ((ItemData)holder).timing.setText(dataHolders[position - 7].getStartTime() + " - " + dataHolders[position - 7].getEndTime());
                }

                else{
                    ((ItemData)holder).title.setText("Interval");
                    ((ItemData)holder).subTitle.setText("");
                    ((ItemData)holder).timing.setText(dataHolders[position - 7].getStartTime() + " - " + dataHolders[position - 7].getEndTime());
                    ((ItemData)holder).cardView.setCardElevation(0);
                    ((ItemData)holder).cardView.setCardBackgroundColor(Color.WHITE);

                }

            }
        }

    }

    @Override
    public int getItemCount() {
        return 7 * (count + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if(position <7)
            return TYPE_TITLE;
        else
            return TYPE_OTHER;
    }


    private class TitleData extends RecyclerView.ViewHolder {
        TextView titleView;
        public TitleData(View inflate) {
            super(inflate);
            titleView = inflate.findViewById(R.id.title);
        }
    }

    private class ItemData extends RecyclerView.ViewHolder {

        TextView title;
        TextView subTitle;
        TextView timing;
        CardView cardView;

        public ItemData(View inflate) {
            super(inflate);

            title = inflate.findViewById(R.id.title);
            subTitle = inflate.findViewById(R.id.sub_title);
            timing = inflate.findViewById(R.id.timing);
            cardView = inflate.findViewById(R.id.card_view);

            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(type == 0){

                    if((getAdapterPosition() - 7) < 7){
                        SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment("-1:-1", getAdapterPosition() - 7, Adapter.this);
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Log.e("Adapter", "onClick: " + getAdapterPosition());
                        settingsDialogFragment.show(ft, null);
                    }
                    else {
                        if (dataHolders[(getAdapterPosition() - 14)] == null) {
                            Toast.makeText(activity, "Please fill previous slot to fill this one", Toast.LENGTH_SHORT).show();
                        } else {
                            SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment(dataHolders[(getAdapterPosition() - 14)].getEndTime(), getAdapterPosition() - 7, Adapter.this);
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Log.e("Adapter", "onClick: " + getAdapterPosition());
                            settingsDialogFragment.show(ft, null);

                        }
                    }
                    }

                }
            });
        }
    }
}
