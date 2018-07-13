package com.notifications.youtube.azem.wiserecycler.view;

import android.view.View;

import com.notifications.youtube.azem.wiserecycler.model.ListItem;

import java.util.List;

/**
 * Created by azem on 1/28/18.
 */

public interface ViewInterface {

    void startDetailActivity(String dateAndTime, String message, int colorResource,View viewRoot);
    void setUpAdapterAndView(List<ListItem> listOfData);

    void addNewListItemToView(ListItem newItem);


    void deleteListItem(int position);

    void showUndoSnackBar();

    void insertListItem(int temporaryListItemPosition, ListItem temporaryListItem);
}
