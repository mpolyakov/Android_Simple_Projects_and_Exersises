package com.kt.std.ipartnertest.view;


import moxy.MvpView;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = OneExecutionStateStrategy.class)
public interface MainView extends MvpView{
    void init();
    void showMessage(String text);
    void updateList();
    void showLoading();
    void hideLoading();
    void saveSession(String session);

    void showNoResponseDialog();

    void openNote(String body);
}
