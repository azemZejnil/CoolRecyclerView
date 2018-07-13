package com.notifications.youtube.azem.wiserecycler.model;

import com.notifications.youtube.azem.wiserecycler.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by azem on 1/28/18.
 */

public class FakeDataSource implements DataSourceInterface {

    private Random random;

    private final String[] datesAndTimes = {
            "06/01/2017",
            " 04/12/2017",
            "12/02/2017",
            "09/7/2018",
    };

    private final String[] messages = {
            "Random text 1",
            "Random text 2",
            "Random text 3",
            "Random text 4"
    };

    private final int[] drawables = {
            R.drawable.green_drawable,
            R.drawable.red_drawable,
            R.drawable.blue_drawable,
            R.drawable.yellow_drawable
    };

    public FakeDataSource() {
        random= new Random();
    }

    @Override
    public List<ListItem> getListOfData() {
        ArrayList<ListItem> listOfData= new ArrayList<>();
        for(int i = 0; i<4;i++){
            listOfData.add(createNewListItem());
        }
        return listOfData;
    }

    @Override
    public ListItem createNewListItem() {
        int randOne  = random.nextInt(4);
        int randTwo  = random.nextInt(4);
        int randThree  = random.nextInt(4);
        ListItem listItem= new ListItem(datesAndTimes[randOne],
                messages[randTwo],drawables[randThree]);
        return listItem;
    }

    @Override
    public void deleteListItem(ListItem listItem) {

    }

    @Override
    public void insertListItem(ListItem temporaryListItem) {

    }


}
