package com.notifications.youtube.azem.wiserecycler.model;

import java.util.List;

/**
 * Created by azem on 1/28/18.
 */

public interface DataSourceInterface {
    List<ListItem> getListOfData();

    ListItem createNewListItem();

    void deleteListItem(ListItem listItem);

    void insertListItem(ListItem temporaryListItem);
}
