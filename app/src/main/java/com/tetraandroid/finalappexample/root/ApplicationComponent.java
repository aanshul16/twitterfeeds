package com.tetraandroid.finalappexample.root;

import com.tetraandroid.finalappexample.http.Communicator;
import com.tetraandroid.finalappexample.tweets.TwitterTweetsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, Communicator.class,})
public interface ApplicationComponent {

    void inject(TwitterTweetsActivity target);

}
