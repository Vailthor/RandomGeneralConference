package com.vailthor.randomgeneralconference;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private static final String TAG = "MyActivity";
    final String PREFS_NAME = "MyPrefsFile";
    AutoCompleteTextView authorsView;
    private Talk currentTalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "talks_db").build();
        if (settings.getBoolean("first_time", true)) {
            DatabaseInitializer.populateAsync(db, getApplicationContext());
            settings.edit().putBoolean("first_time", false).apply();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String authors[] = {"Gordon B. Hinckley","Thomas S. Monson","James E. Faust","Henry B. Eyring","Boyd K. Packer","L. Tom Perry","Russell M. Nelson","M. Russell Ballard","Dallin H. Oaks","Spencer W. Kimball","Dieter F. Uchtdorf","N. Eldon Tanner","Ezra Taft Benson","Marion G. Romney","David B. Haight","Richard G. Scott","Robert D. Hales","Joseph B. Wirthlin","Neal A. Maxwell","Jeffrey R. Holland","Howard W. Hunter","Marvin J. Ashton","Bruce R. McConkie","David A. Bednar","D. Todd Christofferson","Victor L. Brown","Mark E. Petersen","Neil L. Andersen","Quentin L. Cook","F. Michael Watson","LeGrand Richards","Barbara B. Smith","H. Burke Peterson","Wilford G. Edling","Marion D. Hanks","Loren C. Dunn","Vaughn J. Featherstone","A. Theodore Tuttle","Franklin D. Richards","Francis M. Gibbons","Harold B. Lee","Hartman Rector+ Jr.","Elaine S. Dalton","Paul H. Dunn","Rex D. Pinegar","Julie B. Beck","J. Thomas Fyans","Richard C. Edgley","Theodore M. Burton","H. David Burton","Robert L. Simpson","J. Richard Clarke","Ronald A. Rasband","Elaine L. Jack","Joseph Anderson","Bonnie D. Parkin","Charles A. Didier","S. Dilworth Young","Delbert L. Stapley","Dean L. Larsen","Sterling W. Sill","Keith B. McMullin","Henry D. Taylor","Bernard P. Brockbank","Carlos E. Asay","William Grant Bangerter","Adney Y. Komatsu","John H. Vandenberg","James A. Cullimore","Barbara W. Winder","Eldred G. Smith","Gary E. Stevenson","Dwan J. Young","John H. Groberg","Dale G. Renlund","Robert L. Backman","Susan W. Tanner","Joseph Fielding Smith","Ted E. Davis","Brook P. Hales","Ardeth G. Kapp","James M. Paramore","William R. Bradford","Chieko N. Okazaki","O. Leslie Stone","Gene R. Cook","Robert W. Cantwell","Mary N. Cook","Earl C. Tingey","Aileen H. Clyde","Hugh W. Pinnock","Yoshihiko Kikuchi","Glenn L. Pace","ElRay L. Christiansen","Linda K. Burton","Jacob de Jager","Margaret D. Nadauld","F. Burton Howard","Sharon G. Larsen","Silvia H. Allred","Barbara Thompson","Ronald E. Poelman","Ulisses Soares","Virginia U. Jensen","F. Enzio Busche","Anne C. Pingree","Donald L. Hallstrom","William H. Bennett","Sheri L. Dew","L. Whitney Clayton","Virginia H. Pearce","Carol B. Thomas","Kathleen H. Hughes","Robert E. Wells","Jack H. Goaslind","Ann M. Dibb","Bonnie L. Oscarson","Elaine A. Cannon","Mary Ellen Smoot","Harold G. Hillam","Carole M. Stephens","L. Aldin Porter","Ted E. Brewerton","Lynn G. Robbins","Royden G. Derrick","Michaelene P. Grassli","Claudio R. M. Costa","Richard J. Maynes","Angel Abrea","George P. Lee","W. Craig Zwick","Rosemary M. Wixom","Jay E. Jensen","David E. Sorensen","Craig C. Christensen","Kevin R. Jergensen","Steven E. Snow","Merrill J. Bateman","Derek A. Cuthbert","Dennis B. Neuenschwander","Linda S. Reeves","Walter F. González","Tad R. Callister","Gérald Caussé","Monte J. Brough","Joe J. Christensen","Carlos H. Amado","Marlin K. Jensen","Carol F. McConkie","Patricia P. Pinegar","Dean M. Davies","W. Eugene Hansen","Gerrit W. Gong","Janette Hales Beckham","Hans B. Ringger","Alexander B. Morrison","John K. Carmack","Cheryl A. Esplin","Francisco J. Viñas","Ben B. Banks","Cecil O. Samuelson Jr.","Kenneth Johnson","G. Homer Durham","Coleen K. Menlove","Spencer J. Condie","Bruce D. Porter","John B. Dickson","Joanne B. Doxey","Ruth B. Wright","Neill F. Marriott","Janette C. Hales","Wesley L. Jones","L. Lionel Kendrick","Carl B. Pratt","F. Melvin Hammond","Alvin R. Dyer","Paul B. Pieper","Lance B. Wickman","Sheldon F. Child","Cheryl C. Lant","Shayne M. Bowen","Claudio D. Zivic","Benjamín De Hoyos","David F. Evans","Milton R. Hunter","Mervyn B. Arnold","Joy F. Evans","Paul V. Johnson","Gary J. Coleman","C. Scott Grow","W. Christopher Waddell","Robert K. Dellenbach","Gayle M. Clegg","Jean A. Stevens","Jean B. Bingham","William R. Walker","Christoffel Golden Jr.","Shirley W. Thomas","Joy D. Jones","Rex C. Reeve","Devin G. Durrant","Stanley G. Ellis","Lynn A. Mickelsen","Robert C. Oaks","Jayne B. Malan","John M. Madsen","Sydney S. Reynolds","H. Bryan Richards","Lloyd P. George","Carlos A. Godoy","Gardner H. Russell","Michael John U. Teh","Anthony D. Perkins","Lawrence E. Corbridge"};
        authorsView = findViewById(R.id.authAuthors);
        authorsView.setThreshold(1);
        //authorsView.showDropDown();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, authors);
        authorsView.setAdapter(adapter);

        String years[] = {"None","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018"};

        final Spinner sp1 = findViewById(R.id.yearSpin);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, years);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);




        FloatingActionButton fabGo = findViewById(R.id.fabGo);
        FloatingActionButton fabGen = findViewById(R.id.fabGen);
        fabGen.setOnClickListener(generate);
        fabGo.setOnClickListener(goToTalk);
        Log.d(TAG, "Testing!");
    }

    private View.OnClickListener generate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getTalk();
        }
    };

    private View.OnClickListener goToTalk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentTalk != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentTalk.getURL()));
                startActivity(browserIntent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "No Current Talk", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };
    /*
    private static AppDatabase buildDatabase(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "talks_db").build();
        DatabaseInitializer.populateAsync(db, context);
        return db;
    }*/

    public void changeTalk(Talk talk) {
        TextView title = findViewById(R.id.titleView);
        currentTalk = talk;
        String month = "October";
        if (talk.getMonth() == 4)
            month = "April";
        String text = "Current Talk:\n" + talk.getTitle() + "\n" + talk.getAuthor() +
                "\n" + month + " " + talk.getYear();
        title.setText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle act on bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTalk() {
        class GetTalk extends AsyncTask<Void,Void,Talk> {

            @Override
            protected Talk doInBackground(Void... voids) {
                Random rand = new Random();
                int randNum = rand.nextInt(3771) + 1;
                Talk t = db.talkDao().getTalkByID(randNum);
                Log.d(TAG, "Printing talk #" + randNum + " " + t.getAuthor());
                return t;
            }

            @Override
            protected void onPostExecute(Talk talk) {
                changeTalk(talk);
            }
        }
        GetTalk gt = new GetTalk();
        gt.execute();
    }
}
