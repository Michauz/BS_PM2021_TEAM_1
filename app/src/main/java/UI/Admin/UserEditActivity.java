package UI.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import Adapters.CloudFireStore;
import app.msda.qna.R;

public class UserEditActivity extends AppCompatActivity {
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        Update();
    }

    public void newSubjectLine(DocumentSnapshot doc, String subject) {
        try {
            ArrayList<String> userSubjects = (ArrayList<String>) doc.get("subjects");
            //Params var for all views.
            RelativeLayout.LayoutParams params;
            //New line of a pending supporter with settings.
            LinearLayout newline = new LinearLayout(this);
            newline.setOrientation(LinearLayout.HORIZONTAL);
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            newline.setLayoutParams(params);
            //Button as post
            ToggleButton subjectButton = new ToggleButton(this);
            if (userSubjects.contains(subject))
                subjectButton.setChecked(true);
            else
                subjectButton.setChecked(false);
            subjectButton.setHint(subject);
            newline.addView(subjectButton);

            TextView subjectName = new TextView(this);
            subjectName.setText(subject);
            newline.addView(subjectName);
            ((LinearLayout) findViewById(R.id.subjectList)).addView(newline);
        } catch (Exception e) {
        }
    }

    public void Submit(View view) {
        DocumentReference userDoc = CloudFireStore.getInstance().collection("users").document(email);
        int permission = 0;
        if (((RadioButton) findViewById(R.id.radioButton1)).isChecked()) permission = 0;
        else if (((RadioButton) findViewById(R.id.radioButton2)).isChecked()) permission = 1;
        else if (((RadioButton) findViewById(R.id.radioButton3)).isChecked()) permission = 2;
        userDoc.update("permission", permission);
        LinearLayout subjectList = (LinearLayout) findViewById(R.id.subjectList);
        ArrayList<String> subjects = new ArrayList<>();
        for (int i = 0; i < subjectList.getChildCount(); i++) {
            if (((ToggleButton) ((LinearLayout) subjectList.getChildAt(i)).getChildAt(0)).isChecked()) {
                subjects.add(((ToggleButton) ((LinearLayout) subjectList.getChildAt(i)).getChildAt(0)).getHint().toString());
            }
        }
        userDoc.update("subjects", subjects);
        finish();
    }

    private void Update() {
        ((TextView) findViewById(R.id.userName)).setText(email);
        CloudFireStore.getInstance().collection("users").document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) { // get the reply ID from DB
                                switch (document.getLong("permission").intValue()) {
                                    case 0:
                                        ((RadioButton) findViewById(R.id.radioButton1)).setChecked(true);
                                        ((RadioButton) findViewById(R.id.radioButton2)).setChecked(false);
                                        ((RadioButton) findViewById(R.id.radioButton3)).setChecked(false);
                                        break;
                                    case 1:
                                        ((RadioButton) findViewById(R.id.radioButton1)).setChecked(false);
                                        ((RadioButton) findViewById(R.id.radioButton2)).setChecked(true);
                                        ((RadioButton) findViewById(R.id.radioButton3)).setChecked(false);
                                        break;
                                    case 2:
                                        ((RadioButton) findViewById(R.id.radioButton1)).setChecked(false);
                                        ((RadioButton) findViewById(R.id.radioButton2)).setChecked(false);
                                        ((RadioButton) findViewById(R.id.radioButton3)).setChecked(true);
                                        break;
                                }
                                CloudFireStore.getInstance().collection("vars").document("subjects").get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot allSubjectDoc = task.getResult();
                                                    if (document.exists()) {
                                                        ArrayList<String> allSubjects = (ArrayList<String>) allSubjectDoc.get("subjectList");
                                                        for (String subject : allSubjects) {
                                                            newSubjectLine(document, subject);
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    public static String getEmail() {
        return email;
    }
}