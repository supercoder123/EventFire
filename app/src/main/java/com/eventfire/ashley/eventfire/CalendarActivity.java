package com.eventfire.ashley.eventfire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.squareup.timessquare.CalendarPickerView.SelectionMode.MULTIPLE;
import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;


public class CalendarActivity extends AppCompatActivity {


    private Button displayDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");



        displayDates= (Button) findViewById(R.id.btn_show_dates);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .inMode(MULTIPLE);

        displayDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Date> calDates=calendar.getSelectedDates();
                int dura=calDates.size();
                String datesString = TextUtils.join(",\n",calDates);

                Toast.makeText(CalendarActivity.this, datesString, Toast.LENGTH_SHORT).show();
                addEventActivity.dateText.setText(datesString);
                addEventActivity.duration.setText(dura+" days");

               /* Bundle dateBundle=new Bundle();
                dateBundle.putString("Date",datesString);
                Intent intent=new Intent(CalendarActivity.this,addEventActivity.class);
                intent.putExtras(dateBundle);
*/

                /*Intent intent = new Intent();
                intent.putExtra("Date", datesString);
                setResult(RESULT_OK, intent);*/
                finish();

            }
        });


    }
}
