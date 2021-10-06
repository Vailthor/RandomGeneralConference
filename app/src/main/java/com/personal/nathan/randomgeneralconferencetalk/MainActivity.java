package com.personal.nathan.randomgeneralconferencetalk;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.personal.nathan.randomgeneralconferencetalk.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * Class for selecting of talks, either randomly or filtered.
 */
public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    //Max history size 500
    LinkedHashMap<Integer, String> history;
    private static final String TAG = "MyActivity";
    final String PREFS_NAME = "MyPrefsFile";
    AutoCompleteTextView authorsView;
    AutoCompleteTextView tagsView;
    private Talk currentTalk;
    boolean justCreated = true;
    //Needs to be updated everytime talks are added to the database
    final int CURRENT_YEAR = 2021;
    final String CURRENT_DATE = "30-Oct-2021";
    final double NUM_TALKS = 3988.0;
    // To Update




    @Override
    /**
     * Sets up the Main Generating Activity, if the app is starting for the first time it will initialize the database.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "talks_db").build();
        history = new LinkedHashMap<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //lists for keywords and authors (in order of occurrence)
        String authors[] = {"Gordon B. Hinckley","Thomas S. Monson","James E. Faust","Henry B. Eyring","Boyd K. Packer","L. Tom Perry","Russell M. Nelson","M. Russell Ballard","Dallin H. Oaks","Spencer W. Kimball","Dieter F. Uchtdorf","N. Eldon Tanner","Ezra Taft Benson","Marion G. Romney","David B. Haight","Richard G. Scott","Robert D. Hales","Joseph B. Wirthlin","Neal A. Maxwell","Jeffrey R. Holland","Howard W. Hunter","Marvin J. Ashton","Bruce R. McConkie","David A. Bednar","D. Todd Christofferson","Victor L. Brown","Mark E. Petersen","Neil L. Andersen","Quentin L. Cook","F. Michael Watson","LeGrand Richards","Barbara B. Smith","H. Burke Peterson","Wilford G. Edling","Marion D. Hanks","Loren C. Dunn","Vaughn J. Featherstone","A. Theodore Tuttle","Franklin D. Richards","Francis M. Gibbons","Harold B. Lee","Hartman Rector+ Jr.","Elaine S. Dalton","Paul H. Dunn","Rex D. Pinegar","Julie B. Beck","J. Thomas Fyans","Richard C. Edgley","Theodore M. Burton","H. David Burton","Robert L. Simpson","J. Richard Clarke","Ronald A. Rasband","Elaine L. Jack","Joseph Anderson","Bonnie D. Parkin","Charles A. Didier","S. Dilworth Young","Delbert L. Stapley","Dean L. Larsen","Sterling W. Sill","Keith B. McMullin","Henry D. Taylor","Bernard P. Brockbank","Carlos E. Asay","William Grant Bangerter","Adney Y. Komatsu","John H. Vandenberg","James A. Cullimore","Barbara W. Winder","Eldred G. Smith","Gary E. Stevenson","Dwan J. Young","John H. Groberg","Dale G. Renlund","Robert L. Backman","Susan W. Tanner","Joseph Fielding Smith","Ted E. Davis","Brook P. Hales","Ardeth G. Kapp","James M. Paramore","William R. Bradford","Chieko N. Okazaki","O. Leslie Stone","Gene R. Cook","Robert W. Cantwell","Mary N. Cook","Earl C. Tingey","Aileen H. Clyde","Hugh W. Pinnock","Yoshihiko Kikuchi","Glenn L. Pace","ElRay L. Christiansen","Linda K. Burton","Jacob de Jager","Margaret D. Nadauld","F. Burton Howard","Sharon G. Larsen","Silvia H. Allred","Barbara Thompson","Ronald E. Poelman","Ulisses Soares","Virginia U. Jensen","F. Enzio Busche","Anne C. Pingree","Donald L. Hallstrom","William H. Bennett","Sheri L. Dew","L. Whitney Clayton","Virginia H. Pearce","Carol B. Thomas","Kathleen H. Hughes","Robert E. Wells","Jack H. Goaslind","Ann M. Dibb","Bonnie L. Oscarson","Elaine A. Cannon","Mary Ellen Smoot","Harold G. Hillam","Carole M. Stephens","L. Aldin Porter","Ted E. Brewerton","Lynn G. Robbins","Royden G. Derrick","Michaelene P. Grassli","Claudio R. M. Costa","Richard J. Maynes","Angel Abrea","George P. Lee","W. Craig Zwick","Rosemary M. Wixom","Jay E. Jensen","David E. Sorensen","Craig C. Christensen","Kevin R. Jergensen","Steven E. Snow","Merrill J. Bateman","Derek A. Cuthbert","Dennis B. Neuenschwander","Linda S. Reeves","Walter F. González","Tad R. Callister","Gérald Caussé","Monte J. Brough","Joe J. Christensen","Carlos H. Amado","Marlin K. Jensen","Carol F. McConkie","Patricia P. Pinegar","Dean M. Davies","W. Eugene Hansen","Gerrit W. Gong","Janette Hales Beckham","Hans B. Ringger","Alexander B. Morrison","John K. Carmack","Cheryl A. Esplin","Francisco J. Viñas","Ben B. Banks","Cecil O. Samuelson Jr.","Kenneth Johnson","G. Homer Durham","Coleen K. Menlove","Spencer J. Condie","Bruce D. Porter","John B. Dickson","Joanne B. Doxey","Ruth B. Wright","Neill F. Marriott","Janette C. Hales","Wesley L. Jones","L. Lionel Kendrick","Carl B. Pratt","F. Melvin Hammond","Alvin R. Dyer","Paul B. Pieper","Lance B. Wickman","Sheldon F. Child","Cheryl C. Lant","Shayne M. Bowen","Claudio D. Zivic","Benjamín De Hoyos","David F. Evans","Milton R. Hunter","Mervyn B. Arnold","Joy F. Evans","Paul V. Johnson","Gary J. Coleman","C. Scott Grow","W. Christopher Waddell","Robert K. Dellenbach","Gayle M. Clegg","Jean A. Stevens","Jean B. Bingham","William R. Walker","Christoffel Golden Jr.","Shirley W. Thomas","Joy D. Jones","Rex C. Reeve","Devin G. Durrant","Stanley G. Ellis","Lynn A. Mickelsen","Robert C. Oaks","Jayne B. Malan","John M. Madsen","Sydney S. Reynolds","H. Bryan Richards","Lloyd P. George","Carlos A. Godoy","Gardner H. Russell","Michael John U. Teh","Anthony D. Perkins","Lawrence E. Corbridge"};
        String tags[] = {"Jesus Christ","Faith","Service","Family","Obedience","Priesthood","Love","Missionary Work","Testimony","Holy Ghost","Prayer","Spirituality","Adversity","Atonement","Repentance","Plan of Salvation","Prophets","Covenants","Morality","Example","Temples","Agency","Children","Joy","Youth","Restoration","Marriage","Book of Mormon","Women","Teaching","Conversion","Home","Scripture Study","Commandments","Parenthood","Peace","Scriptures","Joseph Smith","Welfare","Forgiveness","God the Father","Truth","Charity","Education","Preparation","Sacrifice","Gratitude","Humility","Activation","Resurrection","Honesty","Church Growth","Temple Work","Relief Society","Discipleship","Temptation","Leadership","Satan","Ordinances","Sacrament","Sin","Family History","Endurance","Media","Commitment","Church Leaders","General Conference","Blessings","Death","Motherhood","Word of Wisdom","Unity","Work","Responsibility","Hope","Tithing","Worthiness","Courage","Healing","Church Organization","Fellowshipping","Pioneers","Standards","Fatherhood","Self-Reliance","Divine Nature","Church Callings","Trust","Priorities","Individual Worth","Sabbath","Young Women","Friendship","Church History","Dedication","Financial Management","Duty","Parents","Kindness","Evil","Family Home Evening","Attitude","Patience","Fasting","Character","Prophecy","Compassion","Goals","Self-Esteem","Safety","Apostasy","Pride","Bishops","Worldliness","Music","Authority","Perspective","Respect","Accountability","Worship","Home Teaching","Communication","Abuse","Loyalty","Mercy","Reverence","Self-Control","Godhead","Councils","Opposition","Disabilities","Freedom","Criticism","Quorum of the Twelve Apostles","Generosity","Revelation","Ministering","Success","Judging","Conscience","Time Management","Miracles","Tolerance","Last Days","Fear","Couple Missionaries","Brotherhood","Kingdom of God","Church Meetings","Single Members","Premortal Existence","Talents","House of Israel","Elderly","Consecration","Neighbors","Creation","Contention","Greed","Grace","Patriarchal Blessings","Values","Temple Square","Profanity","Church Membership","Anger","Zion","Abortion","Employment","Wisdom","Listening","Visiting Teaching","Church Doctrine","Government","Divorce","Primary","Curriculum","Baptism","Social Services","Christianity","Justice","Angels","Ezra Taft Benson","Addiction","Aaronic Priesthood","Religion","Laws","Scouting","Homosexuality","Sisterhood","Mission of the Church","Loneliness","Howard W. Hunter","Gordon B. Hinckley","Pornography","Virtue","Technology","Health","Habits","First Presidency","Patriotism","Adam and Eve","Seminary","Bible","Fall","Wealth","Quorums of Seventy","Articles of Faith","U.S. Constitution","False Doctrines","Native Americans","Name of Church","Peer Pressure","Violence","Brigham Young","Poverty","Light of Christ","Priesthood Quorums","Disciplinary Councils","Discipline","Heroes","Spencer W. Kimball","Institute","Literacy","Dating","Covetousness","Religious Freedom","Melchizedek Priesthood","Military","Dispensations","Good Samaritan","Idol Worship","Offense","Womanhood","Integrity","Righteousness","Mortality","Meekness","Second Coming","Easter","Languages","Passover","Foreordination","Excellence","New Testament","Nonmembers","Gambling","Athletics","Tabernacle Choir","Sunday School","Understanding","Church Attendance","Learning","Eternal Life","Sacredness","Confidence","Heavenly Father","Promptings","Temple","Gathering"};
        authorsView = findViewById(R.id.autoAuthors);
        authorsView.setThreshold(1);

        ArrayAdapter<String> authorsAdapt = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, authors);
        authorsView.setAdapter(authorsAdapt);
        tagsView = findViewById(R.id.autoTags);
        tagsView.setThreshold(1);
        ArrayAdapter<String> tagsAdapt = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, tags);
        tagsView.setAdapter(tagsAdapt);


        List<String> years = new ArrayList<>();
        years.add("None");
        for (int i = CURRENT_YEAR; i > 1970; i--)
            years.add("" + i);

        final Spinner sp1 = findViewById(R.id.yearSpin);
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, years);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);

        //listener for when a year is selected from the spinner, changes what authors can be seen in the list
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!justCreated)
                    setSpinners();
                else
                    justCreated = false;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        FloatingActionButton fabGo = findViewById(R.id.fabGo);
        FloatingActionButton fabGen = findViewById(R.id.fabGen);
        fabGen.setOnClickListener(generate);
        fabGo.setOnClickListener(goToTalk);

        //if the app is running for the first time setup  the database and settings
        if (settings.getBoolean("first_time", true)) {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            initializeDatabase(db, getApplicationContext());
            settings.edit().putBoolean("history", true).apply();
            settings.edit().putBoolean("report", true).apply();
            settings.edit().putBoolean("read", false).apply();
            settings.edit().putString("datePopulated", CURRENT_DATE).apply();
            settings.edit().putBoolean("first_time", false).apply();
        }
        else {


            //compare Current_Date to add new talks - will be used as new talks need to be added
            String nonInitDate =  "11-Jan-1900";
            String populationDate = settings.getString("datePopulated", nonInitDate);
            Date current;
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

            Date popDate;
            try {
                current = df.parse(CURRENT_DATE);
                popDate = df.parse(populationDate);
                Log.d(TAG, "onCreate: " + popDate);
                if (populationDate == nonInitDate) {
                    initializeDatabase(db, getApplicationContext());
                    settings.edit().putString("datePopulated", df.format(CURRENT_DATE)).apply();
                }
                if (popDate.before(current)) {
                    Log.d(TAG, "onCreate: Adding New Talks");
                    populateNew(db, getApplicationContext());
                }
                else
                    setSpinners();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            getSavedHistory();
            //Log.d(TAG, "onCreate: Read - " + db.getOpenHelper().getReadableDatabase().getVersion());
            //db.getOpenHelper().getReadableDatabase().needUpgrade()
            //Log.d(TAG, "onCreate: Write -" + db.getOpenHelper().getWritableDatabase().getVersion());


        }

    }

    public void monthChanged(View view) {
        setSpinners();
    }

    /**
     * Will get a new talk when the button is pressed
     */
    private View.OnClickListener generate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getTalk(-1);
        }
    };
    /**
     * Open the current talk, identify where the talk should be opened and add it to the history.
     */
    private View.OnClickListener goToTalk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressBar progress = findViewById(R.id.progressBar);
            if (progress.getVisibility() != View.VISIBLE) {
                //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                //boolean readInApp = settings.getBoolean("read", false);
                if (currentTalk != null) {
                    addTalkToHistory();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentTalk.getURL()));
                    startActivity(browserIntent);


                }
//                else if (currentTalk != null && readInApp) {
//                    addTalkToHistory();
//                    Intent intent = new Intent(v.getContext(), TalkView.class);
//                    intent.putExtra("URL", currentTalk.getURL());
//                    startActivity(intent);
//                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No Current Talk", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Please Wait till Population is Done", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

    /**
     * Adds the talk to the history, if the history is over 201 delete the oldest talk
     */
    private void addTalkToHistory() {
        if (history.size() >= 201) {
            history.remove(history.size()-1);
        }
        if (!history.containsKey(currentTalk.getId())) {
            history.put(currentTalk.getId(), currentTalk.getTitle());
        }
    }

    /**
     * Sets the given talk as the current talk, both the variable and the talk seen by the user.
     * @param talk The new current talk
     */
  public void changeTalk(Talk talk) {
        if (talk != null) {
            currentTalk = talk;
            String month = "October";
            if (talk.getMonth() == 4)
                month = "April";
            String text = "Current Talk:\n" + talk.getTitle() + "\n" + talk.getAuthor() +
                    "\n" + month + " " + talk.getYear();
            changeTalk(text);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "No Talks Found", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Changes the talkText view to be the 'change' text.
     * @param change text that will be used
     */
    public void changeTalk(String change) {
        TextView talkV = findViewById(R.id.talkText);
        talkV.setText(change);
    }

    /**
     * Gets the history that is saved in shared preferences
     */
    private void getSavedHistory()
    {
        ArrayList<Integer> intHistory = new ArrayList<>();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        Set<String> savedHistory = settings.getStringSet("savedHistory", new ArraySet<>(Arrays.asList("-1")));
        history.clear();
        if (!savedHistory.contains("-1")) {
            for (String i : savedHistory) {
                String[] text = i.split("@");
                history.put(Integer.parseInt(text[0]), text[1]);
            }
        }
    }

    /**
     * Saves the history in shared preferences.
     * @param history the history to be saved
     */
    private void saveHistory(LinkedHashMap<Integer, String> history) {
        ArraySet<String> historyToSave = new ArraySet<>();
        if (history.size() == 0)
            historyToSave.add("-1");
        else {
            for (int id : history.keySet()) {
                historyToSave.add(Integer.toString(id) + "@" + history.get(id));
            }
        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        settings.edit().putStringSet("savedHistory", historyToSave).apply();
    }

    @Override
    /**
     * Creates the options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    /**
     * Opens the necessary activity when options is chosen. If the database is still populating the user will have to wait.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle act on bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        ProgressBar progress = findViewById(R.id.progressBar);
        if (progress.getVisibility() != View.VISIBLE) {
            //noinspection SimplifiableIfStatement
            if (itemId == R.id.action_settings) {
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            }

            if (itemId == R.id.action_history) {
                Intent intent = new Intent(this, History.class);
                Bundle extras = new Bundle();
                ArrayList<String> toSendHist = new ArrayList<>();
                if (!history.isEmpty()) {
                    for (int i : history.keySet()) {
                        toSendHist.add(history.get(i) + "@" + i);
                    }
                }
                extras.putStringArrayList("talksHistory", toSendHist);
                intent.putExtras(extras);
                startActivityForResult(intent, 1);
            }
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Wait till Population is Done", Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    /**
     * Used to delete history when the history activity completes. Also sets the current talk if chosen.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        if(resultCode == RESULT_OK) {

            ArrayList<Integer> toDelete;
            if (extras != null) {
                toDelete = extras.getIntegerArrayList("toDelete");
                if (toDelete.get(0).equals(-1)) {
                    history.clear();

                }
                else if (toDelete.size() > 0) {
                    for (int i : toDelete) {
                        history.remove(i);
                    }
                }
                saveHistory(history);
            }
        }
        try {
            int idPosition = extras.getInt("id");
            if (idPosition > 0) {
                getTalk(idPosition);
                //goToTalk();
            }
        }
        catch (NullPointerException e) {
            ;
        }
    }

    /**
     * Gets a talk from the database using the parameters specified by the user. (Author, Keyword/Tag, Year, Month, History, Report/Sustaining.)
     * @param id id of the talk if needed
     */
    private void getTalk(final int id) {
        class GetTalk extends AsyncTask<Void,Void,Talk> {

            @Override
            protected Talk doInBackground(Void... voids) {
                if (id >= 0) {
                    return db.talkDao().getTalkByID(id);
                }
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
                if(historyCheck && reportCheck) {
                    int[] historyArray = new int[history.size()];
                    int count = 0;
                    for (int id : history.keySet())
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
                    for (int id : history.keySet())
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

    /**
     * Shows or hides the progress bar, only used when initializing the database.
     * @param vis used to give visibility
     */
    public void changeProgressVisibility(int vis) {
        ProgressBar progress = findViewById(R.id.progressBar);
        if (vis == 0) {
            progress.setVisibility(View.INVISIBLE);
        }
        else if (vis == 2) {
            progress.setVisibility(View.VISIBLE);
            changeTalk("Database Updating");
        }
        else {
            progress.setVisibility(View.VISIBLE);
            changeTalk("Database Populating");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Initialize the database with all the talks in assets/talks.csv.
     * @param database the database that is being initialized
     * @param context the context of the application
     */
    private void initializeDatabase(final AppDatabase database, final Context context) {
        class PopulateDbAsync extends AsyncTask<Void, Integer, Void> {

            private final AppDatabase mDb = database;
            private Context ct = context;
            int orientation;
            String[] tArr;

            @Override
            protected void onPreExecute() {
                changeProgressVisibility(1);
                orientation = getRequestedOrientation();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            }
            @Override
            protected Void doInBackground(final Void... params) {
                //read from csv file
                AssetManager assetManager = ct.getAssets();
                BufferedReader reader = null;
                try {
                    InputStream is = assetManager.open("talks.csv");
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    reader.readLine();
                    String text = null;
                    int count = 1;
                    while ((text = reader.readLine()) != null) {
                        publishProgress(count);
                        count++;
                        tArr = text.split(",");
                        tArr[0] = tArr[0].replace('+', ',');
                        tArr[1] = tArr[1].replace('+', ',');
                        //Log.d(TAG, "doInBackground: " + Arrays.toString(tArr));
                        boolean report = false;
                        if (tArr[4].equals("T")) {
                            report = true;
                        }
                        Talk t = new Talk(tArr[0], tArr[1], Integer.parseInt(tArr[2]), Integer.parseInt(tArr[3]), report, tArr[5], tArr[6]);
                        mDb.talkDao().insert(t);

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                    }

                }
                SharedPreferences settings = ct.getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putBoolean("first_time", false).apply();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                changeProgressVisibility(0);
                setRequestedOrientation(orientation);
                setSpinners();

                changeTalk("Done Creating Database\n\n" + getString(R.string.how_to_use));

            }

            @Override
            protected void onProgressUpdate(Integer... count) {
                ProgressBar progress = findViewById(R.id.progressBar);
                int percent = (int)((count[0] / 3771.0) * 100.0);

                progress.setProgress(percent);

            }

        }
        PopulateDbAsync sync = new PopulateDbAsync();
        sync.execute();
    }

    public void changeSpinners(String[] authors, String[] tags) {
        authorsView = findViewById(R.id.autoAuthors);
        authorsView.setThreshold(1);

        ArrayAdapter<String> authorsAdapt = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, authors);
        authorsView.setAdapter(authorsAdapt);
        tagsView = findViewById(R.id.autoTags);
        tagsView.setThreshold(1);
        ArrayAdapter<String> tagsAdapt = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, tags);
        tagsView.setAdapter(tagsAdapt);
    }

    private void setSpinners() {
        class SetAuthorandTags extends AsyncTask<Void,Void,List<String[]>> {
            protected List<String[]> doInBackground(Void... voids) {
                Spinner yearSpinner = findViewById(R.id.yearSpin);
                RadioGroup monthGroup = findViewById(R.id.monthGroup);
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

                List<String> tagList;
                List<String> authorsList;
                if (year != 0 && month == 0) {
                    tagList = db.talkDao().getTagsYear(year);
                    authorsList = db.talkDao().getAuthorsYear(year);
                }
                else if (year == 0 && month != 0) {
                    tagList = db.talkDao().getTagsMonth(month);
                    authorsList = db.talkDao().getAuthorsMonth(month);
                }
                else if (year != 0 && month != 0) {
                    tagList = db.talkDao().getTagsYearMonth(year,month);
                    authorsList = db.talkDao().getAuthorsYearMonth(year, month);
                }
                else {
                    tagList = db.talkDao().getTags();
                    authorsList = db.talkDao().getAuthors();
                }

                Map<String, Integer> orderedTagList = new HashMap<>();
                Map<String, Integer> orderedAuthorList = new HashMap<>();

                for (String tagArray : tagList) {
                    List<String> tempTags = Arrays.asList(tagArray.split("\\+"));
                    for (String tag : tempTags) {
                        tag = tag.replace("[","");
                        tag = tag.replace("]","");
                        tag = tag.replace("\"","");
                        if (orderedTagList.containsKey(tag))
                            orderedTagList.put(tag,orderedTagList.get(tag) + 1);
                        else
                            orderedTagList.put (tag,1);
                    }
                }

                for (String author : authorsList) {
                    if (orderedAuthorList.containsKey(author))
                        orderedAuthorList.put(author,orderedAuthorList.get(author) + 1);
                    else
                        orderedAuthorList.put (author,1);
                }

                orderedTagList.keySet().remove("");
                orderedTagList = sortByComparator(orderedTagList, false);

                orderedAuthorList.keySet().remove("");
                orderedAuthorList = sortByComparator(orderedAuthorList, false);

                String[] authors = orderedAuthorList.keySet().toArray(new String[orderedAuthorList.keySet().size()]);
                String[] tags = orderedTagList.keySet().toArray(new String[orderedTagList.keySet().size()]);

                List<String[]> valuesToPass = new ArrayList<String[]>();
                valuesToPass.add(authors);
                valuesToPass.add(tags);
                return valuesToPass;
            }
            @Override
            protected void onPostExecute(List<String[]> passedValues) {
                String[] authors = passedValues.get(0);
                String[] tags = passedValues.get(1);
                changeSpinners(authors,tags);
            }
        }
        SetAuthorandTags setAT = new SetAuthorandTags();
        setAT.execute();
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

        List<Map.Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * To be used when new talks are to be added.
     */
    private void populateNew(final AppDatabase database, final Context context) {
        class UpdateDbAsync extends AsyncTask<Void, Integer, Void> {

            private final AppDatabase mDb = database;
            private Context ct = context;
            int orientation;
            String[] tArr;
            double numTalks = 1000;

            @Override
            protected void onPreExecute() {
                changeProgressVisibility(2);
                orientation = getRequestedOrientation();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            }
            @Override
            protected Void doInBackground(final Void... params) {
                //read from csv file
                AssetManager assetManager = ct.getAssets();
                BufferedReader reader = null;
                try {
                    InputStream is = assetManager.open("talks.csv");
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    reader.readLine();
                    String text = null;
                    int count = 1;
                    int talksAdded = 0;
                    while ((text = reader.readLine()) != null) {
                        publishProgress(count);
                        count++;
                        tArr = text.split(",");
                        tArr[0] = tArr[0].replace('+', ',');
                        //if talk exists in the database end this iteration
                        boolean talkExist = db.talkDao().checkTalkExists(tArr[0]);
                        if (!talkExist) {
                            Log.d(TAG, "doInBackground: " + talkExist);
                            tArr[1] = tArr[1].replace('+', ',');
                            //Log.d(TAG, "doInBackground: " + Arrays.toString(tArr));
                            boolean report = false;
                            if (tArr[4].equals("T")) {
                                report = true;
                            }
                            Talk t = new Talk(tArr[0], tArr[1], Integer.parseInt(tArr[2]), Integer.parseInt(tArr[3]), report, tArr[5], tArr[6]);
                            mDb.talkDao().insert(t);
                            talksAdded++;
                        }

                    }
                    numTalks = count;
                    Log.d(TAG, "doInBackground: New Talks Added - " + talksAdded);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                    }

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                changeProgressVisibility(0);
                setRequestedOrientation(orientation);
                setSpinners();
                SharedPreferences settings = ct.getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putString("datePopulated", CURRENT_DATE).apply();
                changeTalk("Done Updating Database\n\n" + getString(R.string.how_to_use));

            }

            @Override
            protected void onProgressUpdate(Integer... count) {
                ProgressBar progress = findViewById(R.id.progressBar);
                int percent = (int)((count[0] / NUM_TALKS) * 100.0);

                progress.setProgress(percent);

            }

        }
        UpdateDbAsync sync = new UpdateDbAsync();
        sync.execute();
    }

    /**
     * Stop the activity and save the history.
     */
    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        saveHistory(history);
        db.close();
    }
}
