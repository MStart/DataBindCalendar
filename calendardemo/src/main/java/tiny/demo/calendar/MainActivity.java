package tiny.demo.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tiny.gcv.BaseSelectedDateDecorator;
import com.tiny.gcv.CalendarDay;
import com.tiny.gcv.GridCalenderView;
import com.tiny.gcv.SelectedDateDecorator;
import com.tiny.gcv.SelectedDateDecoratorFactory;

import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    GridCalenderView gcv;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gcv= (GridCalenderView) findViewById(R.id.gcv);
        button= (Button) findViewById(R.id.btn_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            List<CalendarDay> list=gcv.getSelectedDays();
                showToast(Arrays.toString(list.toArray()));
            }
        });
        gcv.setSelectedDateDecoratorFactory(new SelectedDateDecoratorFactory() {
            @NonNull
            @Override
            public BaseSelectedDateDecorator create(RecyclerView recyclerView) {
                return new SelectedDateDecorator(recyclerView,getResources().getDrawable(R.drawable.icon_me_selected));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.normal:
                gcv.setSelectedMode(GridCalenderView.Mode.Normal);
                break;
            case R.id.single:
                gcv.setSelectedMode(GridCalenderView.Mode.Single);
                break;
            case R.id.multi:
                gcv.setSelectedMode(GridCalenderView.Mode.Multi);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
