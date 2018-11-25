package mvp.cn.rx;


import java.util.ArrayList;
import java.util.List;

import mvp.cn.common.MvpBasePresenter;
import mvp.cn.common.MvpView;
import mvp.cn.rx.scheduler.AndroidSchedulerTransformer;
import rx.Observable;
import rx.Subscriber;

/**
 * A presenter for RxJava, that assumes that only one Observable is subscribed by this presenter.
 * The idea is, that you make your (chain of) Observable and pass it to {@link
 * <code>observeOn()</code>
 *
 * @author Fahim Karim
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public abstract class MvpRxSimplePresenter<V extends MvpView>
        extends MvpBasePresenter<V> {


    protected List<Subscriber> subscriberList = new ArrayList<>();


    /**
     * Subscribes the presenter himself as subscriber on the observable
     *
     * @param observable The observable to subscribe
     */
    public void getNetWork(Observable observable, Subscriber sb) {
//        unsubscribe();
        subscriberList.add(sb);
        observable = applyScheduler(observable);
        observable.subscribe(sb);
    }

    /**
     * <code>observeOn()</code>. As default it uses {@link AndroidSchedulerTransformer}. Override
     * this
     * method if you want to provide your own scheduling implementation.
     */
    protected <BEAN extends Object> Observable<BEAN> applyScheduler(Observable observable) {
        return observable.compose(new AndroidSchedulerTransformer<BEAN>());
    }


    /**
     * Unsubscribes the subscriber and set it to null
     */
    protected void unsubscribe() {
        for (Subscriber sb : subscriberList) {
            if (sb != null && !sb.isUnsubscribed()) {
                sb.unsubscribe();
                sb = null;
            }
        }
        subscriberList.clear();
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            unsubscribe();
        }
    }
}