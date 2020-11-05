package ch.noseryoung.invist;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import ch.noseryoung.invist.model.User;
import ch.noseryoung.invist.persistence.AppDatabase;
import ch.noseryoung.invist.persistence.UserDao;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "ch.noseryoung.invist.HomeActivity";
    private UserDao userDao;
    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        Menu m = navigationView.getMenu();
        MenuItem menuItem = m.add(R.string.logout).setIcon(R.drawable.logoutarrow);

        //Get the userDao
        userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        fillProperties(navigationView);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences invistPrefs = getSharedPreferences("invistPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = invistPrefs.edit();
                editor.remove("activeUser");
                editor.apply();

                Log.d(TAG, "Logout button clicked");
                finish();
                return false;
            }
        });
    }

    /**
     * Fills in the properties to the Drawer
     *
     * @param navigationView
     */
    @SuppressLint("SetTextI18n")
    private void fillProperties(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        TextView drawerName = (TextView) headerView.findViewById(R.id.drawerName);
        TextView drawerEmail = (TextView) headerView.findViewById(R.id.drawerEmail);
        ImageView drawerUserImage = headerView.findViewById(R.id.draweruserImage);

        SharedPreferences invistPrefs = getSharedPreferences("invistPrefs", MODE_PRIVATE);
        String email = invistPrefs.getString("activeUser", "default");

        User user = userDao.getUser(email);


        drawerName.setText(user.getFirstname() + " " + user.getLastname());
        drawerEmail.setText(user.getEmail());


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}