package app.msda.qna;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import Adapters.Permissions;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PermissionsUT {
    private MainActivity mainActivity;
   /* @Before
    public void createClass() {
        mainActivity = new MainActivity();
    }

    @Test
    public void cameraPermissionGrantedCheck() {
        Context context = getInstrumentation().getTargetContext().getApplicationContext();
        Permissions.askForCameraPerm(mainActivity);
        assertEquals(mainActivity.getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA), PackageManager.PERMISSION_GRANTED);
    }

    @Test
    public void cameraPermissionDeniedCheck() {
        Context context = getInstrumentation().getTargetContext().getApplicationContext();
        Permissions.askForCameraPerm(mainActivity);
        assertEquals(mainActivity.getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA), PackageManager.PERMISSION_DENIED);
    }
/*------------------------------------------------------------------------------*/
  /*  @Test
    public void filesPermissionGrantedCheck() {
        Context context = getInstrumentation().getTargetContext().getApplicationContext();
        Permissions.askForFilesPerm(mainActivity);
        assertEquals(mainActivity.getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA), PackageManager.PERMISSION_GRANTED);
    }

    @Test
    public void filesPermissionDeniedCheck() {
        Context context = getInstrumentation().getTargetContext().getApplicationContext();
        Permissions.askForFilesPerm(mainActivity);
        assertEquals(mainActivity.getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA), PackageManager.PERMISSION_DENIED);
    }*/
}