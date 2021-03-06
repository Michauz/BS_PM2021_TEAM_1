package app.msda.qna;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("app.msda.qna", appContext.getPackageName());
    }

    @Test
    public void createUser_isSuccessful() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth.getInstance();
    }
}