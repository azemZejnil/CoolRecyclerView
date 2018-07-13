package com.notifications.youtube.azem.wiserecycler.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.notifications.youtube.azem.wiserecycler.R;
import com.notifications.youtube.azem.wiserecycler.model.FakeDataSource;
import com.notifications.youtube.azem.wiserecycler.model.ListItem;
import com.notifications.youtube.azem.wiserecycler.controller.Controller;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListActivity extends AppCompatActivity implements ViewInterface, View.OnClickListener {

    private static final String EXTRA_DATE_AND_TIME = "EXTRA_DATE_AND_TIME";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_DRAWABLE = "EXTRA_DRAWABLE";

    Button fab;

    private List<ListItem> listOfData;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView= (RecyclerView)findViewById(R.id.rec_list_activity);
        layoutInflater=getLayoutInflater();

        fab = (Button)findViewById(R.id.fab_create_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.createNewListItem();
            }
        });

        controller=new Controller(this, new FakeDataSource());

    }

    @Override
    public void startDetailActivity(String dateAndTime, String message, int colorResource, View viewRoot) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(EXTRA_DATE_AND_TIME, dateAndTime);
        i.putExtra(EXTRA_MESSAGE, message);
        i.putExtra(EXTRA_DRAWABLE, colorResource);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setEnterTransition(new Fade(Fade.OUT));

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this,
                            new Pair<View, String>(viewRoot.findViewById(R.id.imv_list_item_circle),
                                    getString(R.string.transition_drawable)),
                            new Pair<View, String>(viewRoot.findViewById(R.id.lbl_message),
                                    getString(R.string.transition_message)),
                            new Pair<View, String>(viewRoot.findViewById(R.id.lbl_date_and_time),
                                    getString(R.string.transition_time_and_date)));

            startActivity(i, options.toBundle());


        } else {
            startActivity(i);
        }
    }


    @Override
    public void setUpAdapterAndView(List<ListItem> listOfData) {
        this.listOfData= listOfData;
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        //new GridLayoutManager
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter= new CustomAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration= new DividerItemDecoration(
                recyclerView.getContext(),linearLayoutManager.getOrientation());
        itemDecoration.setDrawable(ContextCompat.getDrawable(
                ListActivity.this,R.drawable.divider_white
        ));
        recyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void addNewListItemToView(ListItem newItem) {
        listOfData.add(newItem);
        adapter.notifyItemInserted(listOfData.size()-1);

        recyclerView.smoothScrollToPosition(listOfData.size()-1);
    }

    @Override
    public void deleteListItem(int position) {
        listOfData.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showUndoSnackBar() {
        Snackbar.make(findViewById(R.id.root_list_activity),
                getString(R.string.action_delete_item),
                Snackbar.LENGTH_LONG)
        .setAction(R.string.action_undo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onUndoConfirmed();
            }
        })
        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                controller.onSnackbarTimeout();
            }
        }).show();
    }

    @Override
    public void insertListItem(int temporaryListItemPosition, ListItem temporaryListItem) {
        listOfData.add(temporaryListItemPosition,temporaryListItem);
        adapter.notifyItemInserted(temporaryListItemPosition);

    }

    @Override
    public void onClick(View view) {
        int viewId= view.getId();
        if(viewId==R.id.fab_create_new_item){
            controller.createNewListItem();
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.item_data,parent,false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            ListItem currentItem= listOfData.get(position);

            Drawable color = new ColorDrawable(currentItem.getColorResource());
            Drawable image = getResources().getDrawable(currentItem.getColorResource());
            LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
            holder.colorCircle.setImageDrawable(ld);

            holder.message.setText(currentItem.getMessage());
            holder.date.setText(currentItem.getDateAndTime());

            holder.progressBar.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private CircleImageView colorCircle;
            private ViewGroup container;
            private TextView message;
            private TextView date;
            private ProgressBar progressBar;

            public CustomViewHolder(View itemView) {
                super(itemView);
                this.colorCircle = (CircleImageView) itemView.findViewById(R.id.imv_list_item_circle);
                this.container= itemView.findViewById(R.id.root_list_item);
                this.message= itemView.findViewById(R.id.lbl_message);
                this.date= itemView.findViewById(R.id.lbl_date_and_time);
                this.progressBar= itemView.findViewById(R.id.pro_item_data);
                this.container.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                ListItem listItem= listOfData.get(this.getAdapterPosition());
                controller.onListItemClick(listItem,view);
            }
        }
    }

    private ItemTouchHelper.Callback createHelperCallback(){
        ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                controller.onListItemSwiped(position,listOfData.get(position));
            }
        };
        return simpleCallback;
    }
}
