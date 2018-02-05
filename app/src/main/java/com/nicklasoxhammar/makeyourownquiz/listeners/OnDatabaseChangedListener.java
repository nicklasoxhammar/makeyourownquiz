package com.nicklasoxhammar.makeyourownquiz.listeners;

/**
 * Created by Nick on 2018-02-05.
 */

public interface OnDatabaseChangedListener {

    void onNewDatabaseEntryAdded();
    void onDatabaseEntryRenamed();
}
