package ch.noseryoung.invist.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ch.noseryoung.invist.MainActivity;
import ch.noseryoung.invist.R;
import ch.noseryoung.invist.model.User;
import ch.noseryoung.invist.persistence.AppDatabase;
import ch.noseryoung.invist.persistence.UserDao;

public class HomeFragment extends Fragment {

    private UserDao userDao;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userDao = AppDatabase.getAppDb(getActivity().getApplicationContext()).getUserDao();

        SharedPreferences invistPrefs = getActivity().getSharedPreferences("invistPrefs", Context.MODE_PRIVATE);
        String email = invistPrefs.getString("activeUser", "default");

        User user = userDao.getUser(email);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        textView.setText(getString(R.string.hello) + " " + user.getFirstname());


        return root;
    }

}