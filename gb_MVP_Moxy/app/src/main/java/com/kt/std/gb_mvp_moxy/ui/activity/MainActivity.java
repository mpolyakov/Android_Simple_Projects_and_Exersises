package com.kt.std.gb_mvp_moxy.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kt.std.gb_mvp_moxy.R;
import com.kt.std.gb_mvp_moxy.mvp.model.image.IImageLoader;
import com.kt.std.gb_mvp_moxy.mvp.presenter.MainPresenter;
import com.kt.std.gb_mvp_moxy.mvp.view.MainView;
import com.kt.std.gb_mvp_moxy.ui.adapter.CountriesRvAdapter;
import com.kt.std.gb_mvp_moxy.ui.image.PicassoImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.rl_loading)
    RelativeLayout loadingRelativeLayout;

    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    CountriesRvAdapter adapter;

    IImageLoader<ImageView> imageLoader = new PicassoImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter createPresenter() {
        return new MainPresenter(AndroidSchedulers.mainThread());
    }


    @Override
    public void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountriesRvAdapter(presenter.getCountriesListPresenter());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        loadingRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void setUsername(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url) {
        imageLoader.loadInto(url, avatarImageView);
    }
}


