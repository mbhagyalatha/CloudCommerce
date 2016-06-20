import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.cloudcommerce.app.datamodels.EnvironmentSettings;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by developer on 29/04/16.
 */
public class CloudCommerceApplication extends Application {

    private static Context appContext;
    private static CloudCommerceApplication instance;
    public static String version;
    public static final int PROD = 0;
    public static final int STAGE = 1;
    public static final int TEST = 2;
    public static int env = STAGE;
    private static EnvironmentSettings envSettings;
    public static String environment;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();
        setAppContext(getApplicationContext());
        loadEnvironmentValues();
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CloudCommerceApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        CloudCommerceApplication.appContext = appContext;
    }

    private void loadEnvironmentValues() {
        Log.v("loadEnvironmentValues", "loadEnvironmentValues=");
        try {
            String settingsFile = null;
            switch (env) {
                case PROD:
                    settingsFile = "app_settings_prod.json";
                    environment = "prod";
                    break;
                case STAGE:
                    settingsFile = "app_settings_stage.json";
                    environment = "stage";
                    break;
                case TEST:
                    settingsFile = "app_settings_test.json";
                    environment = "test";
                    break;
            }
            InputStream inputStream = this.getAssets().open(settingsFile);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            envSettings = gson.fromJson(jsonReader, EnvironmentSettings.class);

        } catch (IOException e) {
            Log.d("EnvironmentSettings", "Error loading env values", e);
        }
    }

    public static EnvironmentSettings getEnvSettings() {
        return envSettings;
    }
}
