package com.example.note_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.note_app.databinding.FragmentNoteHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteHome extends Fragment {
    List<notes> list;
    MyDBHalper dbhelper;
    RecycleAdapter adapter;
   FragmentNoteHomeBinding binding;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteHome.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteHome newInstance(String param1, String param2) {
        NoteHome fragment = new NoteHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentNoteHomeBinding.inflate(getLayoutInflater());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        list = new ArrayList<>();
        dbhelper = new MyDBHalper(getContext());

        adapter = new RecycleAdapter();
        getallNotes();

        binding.RCview.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.RCview.setAdapter(adapter);



    }

    public void getallNotes(){
        list.clear();
        list =  dbhelper.getAllNotes();
        adapter.notifyDataSetChanged();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView title,body;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notes_title);
            body = itemView.findViewById(R.id.notes_body);

        }
    }
    class RecycleAdapter extends RecyclerView.Adapter<viewholder>{

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.note_preview_layout,null);
            viewholder holder = new viewholder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            notes note = list.get(position);
            holder.title.setText(note.getTitle());
            holder.body.setText(note.getBody());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}