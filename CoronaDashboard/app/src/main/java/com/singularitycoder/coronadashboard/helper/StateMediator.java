package com.singularitycoder.coronadashboard.helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class StateMediator<T, E, V, K> {

    @Nullable
    private T dataObject;

    @NonNull
    private E status;

    @Nullable
    private V message;

    @Nullable
    private K key;

    public void set(@Nullable T dataObject, @NonNull E status, @Nullable V message, @Nullable K key) {
        this.dataObject = dataObject;
        this.status = status;
        this.message = message;
        this.key = key;
    }

    public final T getData() {
        return dataObject;
    }

    @NonNull
    public final E getStatus() {
        return status;
    }

    @Nullable
    public final V getMessage() {
        return message;
    }

    @Nullable
    public final K getKey() {
        return key;
    }
}
