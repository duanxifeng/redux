package me.tatarka.redux.sample;

import me.tatarka.redux.*;

public class CounterSample {

    // reducers

    static final Reducer<Increment, Integer> increment = (action, state) -> state + 1;
    static final Reducer<Add, Integer> add = (action, state) -> state + action.value;

    static final Reducer<Action, Integer> counter = Reducers.<Action, Integer>matchClass()
            .when(Increment.class, increment)
            .when(Add.class, add);

    // actions

    interface Action {}

    static class Increment implements Action {
        @Override
        public String toString() {
            return "Increment";
        }
    }

    static class Add implements Action {
        final int value;

        Add(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Add(" + value + ")";
        }
    }

    public static void main(String[] args) {
        SimpleStore<Integer> store = new SimpleStore<>(0);
        Dispatcher<Action, Action> dispatcher = Dispatcher.forStore(store, counter)
                .chain(new LogMiddleware<>(store));
        ObserveStore.observable(store).subscribe(count -> System.out.println("state: " + count));
        dispatcher.dispatch(new Increment());
        dispatcher.dispatch(new Add(2));
    }
}
