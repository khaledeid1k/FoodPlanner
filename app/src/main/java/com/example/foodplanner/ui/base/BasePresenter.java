package com.example.foodplanner.ui.base;

import com.example.foodplanner.data.repository.state.Failed;
import com.example.foodplanner.data.repository.state.StateOfResponse;
import com.example.foodplanner.data.repository.state.Success;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class BasePresenter {

    public <T> void applySchedulersAndPostUIStates(
            Single<StateOfResponse<T>> stateOfResponseSingle
            , BasePresenterView<T> basePresenterView
    ) {
        basePresenterView.showLoading();

        stateOfResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SingleObserver<>() {

                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                basePresenterView.showLoading();
                            }

                            @Override
                            public void onSuccess(@NonNull StateOfResponse<T> tStateOfResponse) {
                                if (tStateOfResponse instanceof Success) {
                                    T data = ((Success<T>) tStateOfResponse).getData();
                                    basePresenterView.showData(data);
                                } else if (tStateOfResponse instanceof Failed) {
                                    basePresenterView.showError(((Failed<T>) tStateOfResponse).getMessage());
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                basePresenterView.showError(e.getMessage());

                            }
                        }
                );


    }
}


