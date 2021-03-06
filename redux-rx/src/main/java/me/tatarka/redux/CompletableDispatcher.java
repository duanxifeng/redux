package me.tatarka.redux;

import rx.Completable;
import rx.Subscription;

public class CompletableDispatcher extends Dispatcher<Completable, Subscription> {

    @Override
    public Subscription dispatch(Completable action) {
        return action.subscribe();
    }
}
