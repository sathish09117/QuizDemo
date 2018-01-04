package smdeveloper.online.quizdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import smdeveloper.online.quizdemo.Interface.ItemClickListener;
import smdeveloper.online.quizdemo.Model.Category;
import smdeveloper.online.quizdemo.ViewHolder.CategoryViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment {

    View myFragment;
    /* FirebaseDatabase database;
      DatabaseReference category;
      TextView txtFullName;
      RecyclerView recycler_menu;
      RecyclerView.LayoutManager layoutManager;*/
    RecyclerView listcategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;

    public static FragmentCategory newInstance() {

        FragmentCategory categoryFragment = new FragmentCategory();
        return categoryFragment;
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_category,container,false);   // Inflate the layout for this fragment

        //set Name for User
      /*  View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txt_FullName);
        txtFullName.setText(Common.currentUser.getName());

        //Load for Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        loadMenu();*/
        listcategory = (RecyclerView)myFragment.findViewById(R.id.list_category);
        listcategory.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        listcategory.setLayoutManager(layoutManager);
        listcategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadCategories();
        return myFragment;
    }

    private void loadCategories() {
        adapter =  new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories
        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.categroy_name.setText(model.getName());
                Picasso.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.category_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listcategory.setAdapter(adapter);
    }

}
