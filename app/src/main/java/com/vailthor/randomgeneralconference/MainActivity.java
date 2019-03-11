package com.vailthor.randomgeneralconference;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    //Max history size 500
    ArrayList<Integer> history;
    private static final String TAG = "MyActivity";
    final String PREFS_NAME = "MyPrefsFile";
    AutoCompleteTextView authorsView;
    private Talk currentTalk;
    final int CURRENT_YEAR = 2018;

/*
    todo Get WebView working (has something to do with internet permission)
    todo insure history is working as intended
    todo get delete history button working
    todo make landscape work
    todo get tags in frequency list
 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        TextView talkV = findViewById(R.id.talkText);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "talks_db").build();
        history = new ArrayList<>();


        if (settings.getBoolean("first_time", true)) {
            boolean done = DatabaseInitializer.populateAsync(db, getApplicationContext());
            settings.edit().putBoolean("history", true).apply();
            settings.edit().putBoolean("report", true).apply();
            settings.edit().putBoolean("read", false).apply();
            settings.edit().putBoolean("first_time", false).apply();
            history.add(-1);
            //talkV.setText("Creating Database Please Wait");
            //checkIni(done);
        }
        else
        {
            history = getSavedHistory();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String authors[] = {"Gordon B. Hinckley","Thomas S. Monson","James E. Faust","Henry B. Eyring","Boyd K. Packer","L. Tom Perry","Russell M. Nelson","M. Russell Ballard","Dallin H. Oaks","Spencer W. Kimball","Dieter F. Uchtdorf","N. Eldon Tanner","Ezra Taft Benson","Marion G. Romney","David B. Haight","Richard G. Scott","Robert D. Hales","Joseph B. Wirthlin","Neal A. Maxwell","Jeffrey R. Holland","Howard W. Hunter","Marvin J. Ashton","Bruce R. McConkie","David A. Bednar","D. Todd Christofferson","Victor L. Brown","Mark E. Petersen","Neil L. Andersen","Quentin L. Cook","F. Michael Watson","LeGrand Richards","Barbara B. Smith","H. Burke Peterson","Wilford G. Edling","Marion D. Hanks","Loren C. Dunn","Vaughn J. Featherstone","A. Theodore Tuttle","Franklin D. Richards","Francis M. Gibbons","Harold B. Lee","Hartman Rector+ Jr.","Elaine S. Dalton","Paul H. Dunn","Rex D. Pinegar","Julie B. Beck","J. Thomas Fyans","Richard C. Edgley","Theodore M. Burton","H. David Burton","Robert L. Simpson","J. Richard Clarke","Ronald A. Rasband","Elaine L. Jack","Joseph Anderson","Bonnie D. Parkin","Charles A. Didier","S. Dilworth Young","Delbert L. Stapley","Dean L. Larsen","Sterling W. Sill","Keith B. McMullin","Henry D. Taylor","Bernard P. Brockbank","Carlos E. Asay","William Grant Bangerter","Adney Y. Komatsu","John H. Vandenberg","James A. Cullimore","Barbara W. Winder","Eldred G. Smith","Gary E. Stevenson","Dwan J. Young","John H. Groberg","Dale G. Renlund","Robert L. Backman","Susan W. Tanner","Joseph Fielding Smith","Ted E. Davis","Brook P. Hales","Ardeth G. Kapp","James M. Paramore","William R. Bradford","Chieko N. Okazaki","O. Leslie Stone","Gene R. Cook","Robert W. Cantwell","Mary N. Cook","Earl C. Tingey","Aileen H. Clyde","Hugh W. Pinnock","Yoshihiko Kikuchi","Glenn L. Pace","ElRay L. Christiansen","Linda K. Burton","Jacob de Jager","Margaret D. Nadauld","F. Burton Howard","Sharon G. Larsen","Silvia H. Allred","Barbara Thompson","Ronald E. Poelman","Ulisses Soares","Virginia U. Jensen","F. Enzio Busche","Anne C. Pingree","Donald L. Hallstrom","William H. Bennett","Sheri L. Dew","L. Whitney Clayton","Virginia H. Pearce","Carol B. Thomas","Kathleen H. Hughes","Robert E. Wells","Jack H. Goaslind","Ann M. Dibb","Bonnie L. Oscarson","Elaine A. Cannon","Mary Ellen Smoot","Harold G. Hillam","Carole M. Stephens","L. Aldin Porter","Ted E. Brewerton","Lynn G. Robbins","Royden G. Derrick","Michaelene P. Grassli","Claudio R. M. Costa","Richard J. Maynes","Angel Abrea","George P. Lee","W. Craig Zwick","Rosemary M. Wixom","Jay E. Jensen","David E. Sorensen","Craig C. Christensen","Kevin R. Jergensen","Steven E. Snow","Merrill J. Bateman","Derek A. Cuthbert","Dennis B. Neuenschwander","Linda S. Reeves","Walter F. González","Tad R. Callister","Gérald Caussé","Monte J. Brough","Joe J. Christensen","Carlos H. Amado","Marlin K. Jensen","Carol F. McConkie","Patricia P. Pinegar","Dean M. Davies","W. Eugene Hansen","Gerrit W. Gong","Janette Hales Beckham","Hans B. Ringger","Alexander B. Morrison","John K. Carmack","Cheryl A. Esplin","Francisco J. Viñas","Ben B. Banks","Cecil O. Samuelson Jr.","Kenneth Johnson","G. Homer Durham","Coleen K. Menlove","Spencer J. Condie","Bruce D. Porter","John B. Dickson","Joanne B. Doxey","Ruth B. Wright","Neill F. Marriott","Janette C. Hales","Wesley L. Jones","L. Lionel Kendrick","Carl B. Pratt","F. Melvin Hammond","Alvin R. Dyer","Paul B. Pieper","Lance B. Wickman","Sheldon F. Child","Cheryl C. Lant","Shayne M. Bowen","Claudio D. Zivic","Benjamín De Hoyos","David F. Evans","Milton R. Hunter","Mervyn B. Arnold","Joy F. Evans","Paul V. Johnson","Gary J. Coleman","C. Scott Grow","W. Christopher Waddell","Robert K. Dellenbach","Gayle M. Clegg","Jean A. Stevens","Jean B. Bingham","William R. Walker","Christoffel Golden Jr.","Shirley W. Thomas","Joy D. Jones","Rex C. Reeve","Devin G. Durrant","Stanley G. Ellis","Lynn A. Mickelsen","Robert C. Oaks","Jayne B. Malan","John M. Madsen","Sydney S. Reynolds","H. Bryan Richards","Lloyd P. George","Carlos A. Godoy","Gardner H. Russell","Michael John U. Teh","Anthony D. Perkins","Lawrence E. Corbridge"};
        authorsView = findViewById(R.id.autoAuthors);
        authorsView.setThreshold(1);
        //authorsView.showDropDown();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, authors);
        authorsView.setAdapter(adapter);
        List<String> years = new ArrayList<>();
        years.add("None");
        for (int i = CURRENT_YEAR; i > 1970; i--)
            years.add("" + i);

        final Spinner sp1 = findViewById(R.id.yearSpin);
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, years);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);




        FloatingActionButton fabGo = findViewById(R.id.fabGo);
        FloatingActionButton fabGen = findViewById(R.id.fabGen);
        fabGen.setOnClickListener(generate);
        fabGo.setOnClickListener(goToTalk);
        Log.d(TAG, "Testing");
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
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            boolean readInApp = settings.getBoolean("read",false);
            if (currentTalk != null && !readInApp) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentTalk.getURL()));
                startActivity(browserIntent);
                addTalkToHistory();

            }
            else if (currentTalk != null && readInApp) {
                Intent intent = new Intent(v.getContext(), TalkView.class);
                intent.putExtra("URL", currentTalk.getURL());
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "No Current Talk", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

    private void addTalkToHistory() {
        if (history.get(0) >= 500)
            history.remove(history.size()-1);
        Log.d(TAG, "addTalkToHistory: History #" + history.get(0));
        history.add(currentTalk.getId());

    }
    /*
    private static AppDatabase buildDatabase(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "talks_db").build();
        DatabaseInitializer.populateAsync(db, context);
        return db;
    }*/

    public void changeTalk(Talk talk) {
        if (talk != null) {
            TextView talkV = findViewById(R.id.talkText);
            currentTalk = talk;
            String month = "October";
            if (talk.getMonth() == 4)
                month = "April";
            String text = "Current Talk:\n" + talk.getTitle() + "\n" + talk.getAuthor() +
                    "\n" + month + " " + talk.getYear();
            talkV.setText(text);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "No Talks Found", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private ArrayList<Integer> getSavedHistory()
    {
        ArrayList<Integer> intHistory = new ArrayList<>();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Set<String> history = settings.getStringSet("savedHistory", new HashSet<>(Arrays.asList("-1")));
        Log.d(TAG, "SavedHistory: " + history);
        for (String id : history) {
            intHistory.add(Integer.parseInt(id));
        }
        return intHistory;
    }

    private void saveHistory(ArrayList<Integer> history) {
        HashSet<String> historyToSave = new HashSet<>();
        for (int id : history) {
            historyToSave.add(Integer.toString(id));
        }
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        settings.edit().putStringSet("savedHistory", historyToSave).apply();
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
            Intent intent = new Intent(this, Settings.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            Log.d(TAG, "onOptionsItemSelected: ");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void checkIni(final boolean done) {
        class CheckIni extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                while (!done) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void) {
                changeTalk();
            }
        }
    }*/

    private void getTalk() {
        class GetTalk extends AsyncTask<Void,Void,Talk> {

            @Override
            protected Talk doInBackground(Void... voids) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                boolean historyCheck = settings.getBoolean("history", false);
                boolean reportCheck = settings.getBoolean("report", false);
                AutoCompleteTextView authorView = findViewById(R.id.autoAuthors);
                AutoCompleteTextView tagView = findViewById(R.id.autoTags);
                Spinner yearSpinner = findViewById(R.id.yearSpin);
                RadioGroup monthGroup = findViewById(R.id.monthGroup);

                String author = authorView.getText().toString();
                String tag = tagView.getText().toString().toLowerCase();
                if (!tag.equals(""))
                    tag = "%" + tag + "%";
                String yearString = yearSpinner.getSelectedItem().toString();
                int monthButton = monthGroup.getCheckedRadioButtonId();
                String monthText = ((RadioButton)findViewById(monthButton)).getText().toString();
                int month = 0;
                if (monthText.equals("April"))
                    month = 4;
                else if(monthText.equals("October"))
                    month = 10;
                int year = 0;
                if (!yearString.equals("None"))
                    year = Integer.parseInt(yearString);
                boolean report = false;


                Talk t = null;
                Log.d(TAG, "Fields " + author + " " + tag + " " + year + " " + month);
                if(historyCheck && reportCheck) {
                    int[] historyArray = new int[history.size()];
                    int count = 0;
                    for (int id :history)
                        historyArray[count++] = id;

                    if (!author.equals("") && month == 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthor(author, report, historyArray);
                    else if (!author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandMonth(author, month, report, historyArray);
                    else if (!author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandYear(author, year, report, historyArray);
                    else if (!author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandTag(author, tag, report, historyArray);
                    else if (!author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandDate(author, year, month, report, historyArray);
                    else if (!author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getWithAllReport(author, year, month, tag, report, historyArray);
                    else if (!author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorYearTag(author, year, tag, report, historyArray);
                    else if (!author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorMonthTag(author, month, tag, report, historyArray);
                    else if (author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByYear(year, report, historyArray);
                    else if (author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByDate(year, month, report, historyArray);
                    else if (author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearandTag(year, tag, report, historyArray);
                    else if (author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearMonthTag(year, month, tag, report, historyArray);
                    else if (author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByMonthandTag(month, tag, report, historyArray);
                    else if (author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByMonth(month, report, historyArray);
                    else if (author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByTag(tag, report, historyArray);
                    else {
                        Log.d(TAG, "In getTalk both true");
                        t = db.talkDao().getRandomTalk(report, historyArray);
                    }
                }
                else if (reportCheck) {
                    if (!author.equals("") && month == 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthor(author, report);
                    else if (!author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandMonth(author, month, report);
                    else if (!author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandYear(author, year, report);
                    else if (!author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandTag(author, tag, report);
                    else if (!author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandDate(author, year, month, report);
                    else if (!author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getWithAllReport(author, year, month, tag, report);
                    else if (!author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorYearTag(author, year, tag, report);
                    else if (!author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorMonthTag(author, month, tag, report);
                    else if (author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByYear(year, report);
                    else if (author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByDate(year, month, report);
                    else if (author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearandTag(year, tag, report);
                    else if (author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearMonthTag(year, month, tag, report);
                    else if (author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByMonthandTag(month, tag, report);
                    else if (author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByMonth(month, report);
                    else if (author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByTag(tag, report);
                    else {
                        t = db.talkDao().getRandomTalkReport(report);
                    }
                }
                else if (historyCheck) {
                    int[] historyArray = new int[history.size()];
                    int count = 0;
                    for (int id :history)
                        historyArray[count++] = id;

                    if (!author.equals("") && month == 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthor(author, historyArray);
                    else if (!author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandMonth(author, month, historyArray);
                    else if (!author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandYear(author, year, historyArray);
                    else if (!author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandTag(author, tag, historyArray);
                    else if (!author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandDate(author, year, month, historyArray);
                    else if (!author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getWithAllNoChecks(author, year, month, tag, historyArray);
                    else if (!author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorYearTag(author, year, tag, historyArray);
                    else if (!author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorMonthTag(author, month, tag, historyArray);
                    else if (author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByYear(year, historyArray);
                    else if (author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByDate(year, month, historyArray);
                    else if (author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearandTag(year, tag, historyArray);
                    else if (author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearMonthTag(year, month, tag, historyArray);
                    else if (author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByMonthandTag(month, tag, historyArray);
                    else if (author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByMonth(month, historyArray);
                    else if (author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByTag(tag, historyArray);
                    else {
                        t = db.talkDao().getRandomTalkHistory(historyArray);
                    }
                }
                else {
                    if (!author.equals("") && month == 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthor(author);
                    else if (!author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandMonth(author, month);
                    else if (!author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandYear(author, year);
                    else if (!author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandTag(author, tag);
                    else if (!author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByAuthorandDate(author, year, month);
                    else if (!author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getWithAllNoChecks(author, year, month, tag);
                    else if (!author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorYearTag(author, year, tag);
                    else if (!author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByAuthorMonthTag(author, month, tag);
                    else if (author.equals("") && month == 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByYear(year);
                    else if (author.equals("") && month != 0 && year != 0 && tag.equals(""))
                        t = db.talkDao().getTalkByDate(year, month);
                    else if (author.equals("") && month == 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearandTag(year, tag);
                    else if (author.equals("") && month != 0 && year != 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByYearMonthTag(year, month, tag);
                    else if (author.equals("") && month != 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByMonthandTag(month, tag);
                    else if (author.equals("") && month != 0 && year == 0 && tag.equals(""))
                        t = db.talkDao().getTalkByMonth(month);
                    else if (author.equals("") && month == 0 && year == 0 && !tag.equals(""))
                        t = db.talkDao().getTalkByTag(tag);
                    else {
                        Random rand = new Random();
                        int randNum = rand.nextInt(3771) + 1;
                        t = db.talkDao().getTalkByID(randNum);
                    }
                }

                if (t != null)
                    Log.d(TAG, "Printing talk " + t.getReport());

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

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        saveHistory(history);
        db.close();
    }
}
