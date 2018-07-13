package com.notifications.youtube.azem.wiserecycler.controller;

import android.view.View;

import com.notifications.youtube.azem.wiserecycler.model.DataSourceInterface;
import com.notifications.youtube.azem.wiserecycler.model.ListItem;
import com.notifications.youtube.azem.wiserecycler.view.ViewInterface;

/**
 * Created by azem on 1/28/18.
 */

public class Controller {

    private ListItem temporaryListItem;
    private int temporaryListItemPosition;

    private ViewInterface viewInterface;
    private DataSourceInterface dataSource;

    public Controller(ViewInterface viewInterface, DataSourceInterface dataSource) {
        this.viewInterface = viewInterface;
        this.dataSource = dataSource;
        getListFromDataSource();
    }

    public void getListFromDataSource() {
        viewInterface.setUpAdapterAndView(dataSource.getListOfData());
    }

    public void onListItemClick(ListItem testItem, View viewRoot) {
        viewInterface.startDetailActivity(
                testItem.getDateAndTime(),
                testItem.getMessage(),
                testItem.getColorResource(),
                viewRoot);
    }

    public void createNewListItem() {
        ListItem newItem  = dataSource.createNewListItem();
        viewInterface.addNewListItemToView(newItem);
    }

    public void onListItemSwiped(int position, ListItem listItem) {
        dataSource.deleteListItem(listItem);
        viewInterface.deleteListItem(position);

        temporaryListItemPosition=position;
        temporaryListItem=listItem;

        viewInterface.showUndoSnackBar();
    }

    public void onUndoConfirmed() {
        if(temporaryListItem!=null){
            dataSource.insertListItem(temporaryListItem);
            viewInterface.insertListItem(temporaryListItemPosition,temporaryListItem);

            temporaryListItem=null;
            temporaryListItemPosition=0;
        }
        else{

        }
    }

    public void onSnackbarTimeout() {

    }
}
