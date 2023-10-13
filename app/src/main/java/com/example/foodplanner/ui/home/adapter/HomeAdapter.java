package com.example.foodplanner.ui.home.adapter;

import static com.example.foodplanner.utils.Constants.UserId;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.ui.home.HomePresenter;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.utils.Constants;

import java.util.ArrayList;
import java.util.List;




public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    ArrayList<DataItem> dataItemList;
    HomeInteractionListener homeInteractionListener;
    Context context;
    HomePresenter presenter;
    OnClickHomeHorizontalItem onClickHomeHorizontalItem;

    public HomeAdapter(
            Context context,
            ArrayList<DataItem> dataItemList,
            HomeInteractionListener homeInteractionListener,
            OnClickHomeHorizontalItem onClickHomeHorizontalItem) {
        this.dataItemList = dataItemList;
       this.homeInteractionListener = homeInteractionListener;
        this.context = context;
        this.onClickHomeHorizontalItem = onClickHomeHorizontalItem;
    }


    @Override
    public int getItemViewType(int position) {
        if (dataItemList.get(position) instanceof HeaderItem) {
            return Constants.HEADER_ITEM;
        }
        return Constants.TAG_ITEM;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeViewHolder homeViewHolder;
        if (viewType == Constants.HEADER_ITEM) {
            homeViewHolder = new HeaderViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.head_of_home, parent, false
                    ));
        } else {
            homeViewHolder = new ItemViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.item_home, parent, false
                    ));
        }

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        DataItem dataItem = dataItemList.get(position);


        if(holder instanceof HeaderViewHolder){
            if (dataItem instanceof HeaderItem) {
                Meal meal = ((HeaderItem) dataItem).getMeals();
                ((HeaderViewHolder) holder).nameMeaOfDay.setText(
                        meal.getStrMeal()
                );
                ((HeaderViewHolder) holder).countryMeaOfDay.setText(
                        meal.getStrArea()
                );
                Glide.with(context).load(meal.getStrMealThumb()).error(
                        R.drawable.no_result_search
                ).into(((HeaderViewHolder) holder).imageMealOfDay);
                holder.itemView.setOnClickListener(view -> homeInteractionListener.navigateToShowAll(dataItem,view));
                ((HeaderViewHolder) holder).logout.setOnClickListener(
                        view -> homeInteractionListener.logout(view)
                );
                if(UserId.equals("")){((HeaderViewHolder) holder).logout.setVisibility(View.GONE);}

            }
        } else if(holder instanceof ItemViewHolder){
             if (dataItem instanceof CategoriesItem) {
                Tag<List<CategoryWithDetails>> tag = ((CategoriesItem) dataItem).getTag();
                String title = tag.getTitle();
                ((ItemViewHolder) holder).tileOfList.setText(title);
                ItemsAdapter itemsAdapter = new ItemsAdapter(context,
                       dataItem,onClickHomeHorizontalItem,Constants.VIEW_TYPE_LINER
                        );
                ((ItemViewHolder) holder).recycleItemHome.setAdapter(
                        itemsAdapter
                );
                 ((ItemViewHolder) holder).showAll.setOnClickListener(
                         view -> {
                             homeInteractionListener.navigateToShowAll(dataItem,view);
                         }
                 );

            }
            else if  (dataItem instanceof MealsItem) {
                Tag<List<Meal>> tag = ((MealsItem) dataItem).getTag();
                String title = tag.getTitle();
                ((ItemViewHolder) holder).tileOfList.setText(title);
                ItemsAdapter itemsAdapter = new ItemsAdapter(context,
                        dataItem,onClickHomeHorizontalItem,Constants.VIEW_TYPE_LINER
                );
                ((ItemViewHolder) holder).recycleItemHome.setAdapter(
                        itemsAdapter
                );
                ((ItemViewHolder) holder).showAll.setOnClickListener(
                        view -> {
                            homeInteractionListener.navigateToShowAll(dataItem,view);
                        }
                );

            }
             else if (dataItem instanceof CountriesItem) {
                Tag<List<Country>> tag = ((CountriesItem) dataItem).getTag();
                String title = tag.getTitle();
                ((ItemViewHolder) holder).tileOfList.setText(title);
                ItemsAdapter itemsAdapter = new ItemsAdapter(context,
                       dataItem,onClickHomeHorizontalItem,Constants.VIEW_TYPE_LINER
                        );
                ((ItemViewHolder) holder).recycleItemHome.setAdapter(
                        itemsAdapter
                );
                 ((ItemViewHolder) holder).showAll.setOnClickListener(
                         view -> {
                             homeInteractionListener.navigateToShowAll(dataItem,view);
                         }
                 );

            }

        }


    }

    @Override
    public int getItemCount() {
        return dataItemList==null ? 0:dataItemList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    public class HeaderViewHolder extends HomeViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        ImageView imageMealOfDay = itemView.findViewById(R.id.img_meal_day);
        ImageView logout = itemView.findViewById(R.id.log_out);
        TextView nameMeaOfDay = itemView.findViewById(R.id.name_meal_day);
        TextView countryMeaOfDay = itemView.findViewById(R.id.country_meal_day);
    }

    public class ItemViewHolder extends HomeViewHolder {
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView tileOfList = itemView.findViewById(R.id.title_list);
        RecyclerView recycleItemHome = itemView.findViewById(R.id.recycle_item_home);
        TextView showAll = itemView.findViewById(R.id.show_more);


    }

    public void updateData(ArrayList<DataItem> newData) {
        dataItemList=newData;
        notifyDataSetChanged();
    }
}
