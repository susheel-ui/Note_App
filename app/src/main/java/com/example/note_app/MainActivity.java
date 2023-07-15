  package com.example.note_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.note_app.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

  public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
      MyDBHalper dbhelper;
    List<notes> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        dbhelper = new MyDBHalper(MainActivity.this);

        setContentView(binding.getRoot());
        list = new ArrayList<>();
        // db work

        binding.btmnavbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.BNAddNote:
                        loadFragment(new Add_Note(),false);
                        break;
                    case R.id.BNhome:
                        loadFragment(new NoteHome(),false);
                        break;
                    default:
                        Log.d(TAG, "onNavigationItemSelected: default is clicked");
                        break;

                }
                return true;
            }
        });



    }

      @Override
      protected void onStart() {
          super.onStart();

          loadFragment(new NoteHome(),true);

      }

      public void loadFragment(Fragment ft, boolean flag){

          FragmentManager manager = getSupportFragmentManager();
          androidx.fragment.app.FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.fragment_container,new HomeFragment());
          if(flag){
              transaction.add(binding.NotePreviewRC.getId(),ft);
          }else{
              transaction.replace(binding.NotePreviewRC.getId(),ft);
          }

          transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


          transaction.commit();
      }


}