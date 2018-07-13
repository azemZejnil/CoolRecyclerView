package com.notifications.youtube.azem.wiserecycler;

import com.notifications.youtube.azem.wiserecycler.model.DataSourceInterface;
import com.notifications.youtube.azem.wiserecycler.model.ListItem;
import com.notifications.youtube.azem.wiserecycler.controller.Controller;
import com.notifications.youtube.azem.wiserecycler.view.ViewInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    DataSourceInterface dataSource;

    @Mock
    ViewInterface view;

    Controller controller;

    private static final ListItem testItem= new ListItem(
            "testString","testMessage",R.color.BLUE);

    @Before
    public void setUpTest(){
        MockitoAnnotations.initMocks(this);
        controller= new Controller(view, dataSource);
    }


    @Test
    public void onGetDataListSuccessful() throws Exception {
        ArrayList<ListItem> listOfData= new ArrayList<>();
        listOfData.add(testItem);

        Mockito.when(dataSource.getListOfData())
                .thenReturn(listOfData);

        controller.getListFromDataSource();

        Mockito.verify(view).setUpAdapterAndView(listOfData);

    }

    // @Test
    // public void onGetListDataUnsuccessful() {
    /**************************
     *
     * Unit Test Homework:
     *
     * Implement the "View", so that when we don't recieve a List, it shows some kind of
     * error message to the user. This is common practice that you should learn!
     *
     * I've written some hints you'll have to decipher into Java code:
     *************************/
    //1 Set up your Mock dataSource

    //2 Call the method you wish to test on the Controller

    //3 Verify that the View has been told to show a message (I'd suggest showing a Toast for now)

    //Profit???

    // }


    @Test
    public void onListItemClicked() {
        controller.onListItemClick(testItem);

        Mockito.verify(view).startDetailActivity(
                testItem.getDateAndTime(),
                testItem.getMessage(),
                testItem.getColorResource()
                );
    }



}