package com.elementsculmyca.ec19_app.UI.ClubEventListPage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.PrizeModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TimingsModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.HomePage.ClubEventModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubEventListActivity extends AppCompatActivity {
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    List<EventLocalModel> data;
    ArrayList<EventDataModel> eventList;
    private ProgressBar bar;
    private String clubName;
    private ApiInterface apiInterface;
    private TextView clubDisplayName,clubDescpTextView;
    EventsDao_Impl dao;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_event_list);
        dao=new EventsDao_Impl(AppDatabase.getAppDatabase(ClubEventListActivity.this));
        clubName = getIntent().getStringExtra("clubname");
        backButton = findViewById(R.id.iv_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String displayName = getIntent().getStringExtra("clubdisplay");
        bar = (ProgressBar)findViewById(R.id.pb);
        clubDisplayName=findViewById(R.id.tv_category_name_heading);
        if(displayName.equals("Lit-Deb")){
            clubDisplayName.setText("Literary");
        }else if(displayName.equals("Photography & Designing"))
            clubDisplayName.setText("Photography");
        else {
            clubDisplayName.setText(displayName);
        }

        clubDescpTextView = findViewById(R.id.tv_category_descp_heading);

        if (clubName.equals("Manan")) {
            clubDescpTextView.setText("Eat. Sleep. Code. Repeat.");
        } else if (clubName.equals("Ananya")) {
            clubDescpTextView.setText("We can break the world into words.");
        } else if (clubName.equals("Vividha")) {
            clubDescpTextView.setText("Dramatics is what keeps you in the seats");
        } else if (clubName.equals("Jhalak")) {
            clubDescpTextView.setText("A good snapshot keeps a moment from running away");
        } else if (clubName.equals("Eklavya")) {
            clubDescpTextView.setText("If you can't have fun there is no sense of doing it. So, ask yourself, 'Am I having fun?'");
        } else if (clubName.equals("IEEE")) {
            clubDescpTextView.setText("People who are crazy enough to think they can change the world are the ones who do!");
        } else if (clubName.equals("Mechnext")) {
            clubDescpTextView.setText("Blood, Sweat and Tears? Nah! Blood, Swear and Gears. ;)");
        } else if (clubName.equals("Microbird")) {
            clubDescpTextView.setText("Oh, come on! You are going to compile codes for some MNC all your life anyway. Try hands-on these robotic beasts this year!");
        } else if (clubName.equals("Nataraja")) {
            clubDescpTextView.setText("Dance dance dance till your feet will follow your heart.");
        } else if (clubName.equals("SAE")) {
            clubDescpTextView.setText("We create! We destroy! But when we screw, even metals cry.");
        } else if (clubName.equals("Samarpan")) {
            clubDescpTextView.setText("Electricity is really just organized lightning");
        } else if (clubName.equals("Srijan")) {
            clubDescpTextView.setText("The principles of true art is not to portray, but to evoke");
        } else if (clubName.equals("Taranuum")) {
            clubDescpTextView.setText("When words fail, music speaks");
        } else if (clubName.equals("Vivekanand Manch")) {
            clubDescpTextView.setText("Inspired by Swami Vivekanand this is the category where cultural and fun activities fuse with social values. Witness the Social Bonanza.");
        } else if(clubName.equals("Niramayam")){
            clubDescpTextView.setText("mann shant chitt shant aatma prasan");
        }
        recyclerView=findViewById(R.id.events_list);
        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        getEventsByClubName(clubName);
    }
    void getEventsByClubName(String clubName) {
        eventList=new ArrayList<>();
        data = dao.getEventByClubName(clubName);
        for (int i = 0; i < data.size(); i++) {
            PrizeModel prizes= new PrizeModel();
            List<String> prizeList = Arrays.asList(data.get(i).getPrizes().split("%"));
            if(prizeList.size()==1){
                prizes.setPrize1(prizeList.get(0));
            }else if(prizeList.size()==2){
                prizes.setPrize1(prizeList.get(0));
                prizes.setPrize2(prizeList.get(1));
            }else if(prizeList.size()==3){
                prizes.setPrize1(prizeList.get(0));
                prizes.setPrize2(prizeList.get(1));
                prizes.setPrize3(prizeList.get(2));
            }
            TimingsModel time= new TimingsModel(data.get(i).getStartTime(),data.get(i).getEndTime());

            EventDataModel event= new EventDataModel( data.get( i ).getId(),
                    data.get( i ).getTitle() + "",
                    data.get( i ).getClubname() + "",
                    data.get( i ).getCategory() + "",
                    data.get( i ).getDesc() + "",
                    data.get( i ).getRules() + "",
                    data.get( i ).getVenue() + "",
                    data.get( i ).getPhotolink() + "",
                    data.get( i ).getFee(),
                    time,
                    prizes,
                    data.get(i).getEventType() + "",
                    data.get(i).getHitcount() );
            eventList.add(event);
        }
        eventAdapter = new EventAdapter(eventList,ClubEventListActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClubEventListActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(eventAdapter);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
