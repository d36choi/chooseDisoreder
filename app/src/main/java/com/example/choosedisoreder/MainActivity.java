package com.example.choosedisoreder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.choosedisoreder.databinding.ActivityMainBinding;
import com.example.choosedisoreder.databinding.RecyclerviewItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding Binding;
    public ArrayList<String> list = new ArrayList<>();

    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); // splash
        super.onCreate(savedInstanceState);

        Binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT!!! %d", i)) ;
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.

        Binding.recycler1.setLayoutManager(new LinearLayoutManager(this)); ;
        Binding.recycler1.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                RecyclerviewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.recyclerview_item, parent, false);
                return new CustViewHolder(binding);
            }


            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                CustViewHolder holder1 = (CustViewHolder) holder;
                holder1.setData(list.get(position));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(Binding.recycler1);

    }
    class CustViewHolder extends RecyclerView.ViewHolder {

        private RecyclerviewItemBinding mBinding;

        public CustViewHolder(RecyclerviewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setData(String data) {
            mBinding.tvQuestion.setText(data);
        }
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            showToast("on Move");
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // 삭제되는 아이템의 포지션을 가져온다
            final int position = viewHolder.getAdapterPosition();
            // 데이터의 해당 포지션을 삭제한다
            showToast("on remove " + list.remove(position));
            if(swipeDir==ItemTouchHelper.LEFT)
            {

            }
            if(swipeDir==ItemTouchHelper.RIGHT)
            {

            }
            // 어댑터에게 알린다
            Binding.recycler1.getAdapter().notifyItemRemoved(position);
        }
    };
    private void showToast(String msg) {
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}