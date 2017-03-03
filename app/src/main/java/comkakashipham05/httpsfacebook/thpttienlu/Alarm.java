package comkakashipham05.httpsfacebook.thpttienlu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 22/02/2017.
 */

public class Alarm extends BroadcastReceiver {

    private DatabaseReference mDatabase;


    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
