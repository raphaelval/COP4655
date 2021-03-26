package com.example.compoundweatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of weather data
    private final String[] dateArray;
    private final String[] tempArray;
    private final String[] minArray;
    private final String[] maxArray;
    private final String[] descArray;
    private final String[] windSArray;
    private final String[] windDArray;
    private final String[] humidArray;

    public CustomListAdapter(Activity context, String[] dateArrayParam, String[] tempArrayParam,
                             String[] minArrayParam, String[] maxArrayParam, String[] descArrayParam,
                             String[] windSArrayParam, String[] windDArrayParam, String[] humidArrayParam){

        super(context,R.layout.listview_row , dateArrayParam);

        this.context=context;
        this.dateArray = dateArrayParam;
        this.tempArray = tempArrayParam;
        this.minArray = minArrayParam;
        this.maxArray = maxArrayParam;
        this.descArray = descArrayParam;
        this.windSArray = windSArrayParam;
        this.windDArray = windDArrayParam;
        this.humidArray = humidArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView histDateView = (TextView) rowView.findViewById(R.id.histDateView);
        TextView histTempView = (TextView) rowView.findViewById(R.id.histTempView);
        TextView histMinView = (TextView) rowView.findViewById(R.id.histMinView);
        TextView histMaxView = (TextView) rowView.findViewById(R.id.histMaxView);
        TextView histDescView = (TextView) rowView.findViewById(R.id.histDescView);
        TextView histWindSView = (TextView) rowView.findViewById(R.id.histWindSView);
        TextView histWindDView = (TextView) rowView.findViewById(R.id.histWindDView);
        TextView histHumidView = (TextView) rowView.findViewById(R.id.histHumidView);
        //Set an imageView reference:
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        histDateView.setText(dateArray[position]);
        histTempView.setText(tempArray[position]);
        histMinView.setText(minArray[position]);
        histMaxView.setText(maxArray[position]);
        histDescView.setText(descArray[position]);
        histWindSView.setText(windSArray[position]);
        histWindDView.setText(windDArray[position]);
        histHumidView.setText(humidArray[position]);
        //Set an image in imageView:
        //imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };
}
