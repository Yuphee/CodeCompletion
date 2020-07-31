/*
 * Copyright (C) 2014 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yz.android.codecompletion.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.siyeh.ig.psiutils.ClassUtils;

/**
 * Collection of static strings representing android class names.
 */
public enum AndroidClassName {
    TOAST("android.widget.Toast"),
    LOG("android.util.Log"),
    CONTEXT("android.content.Context"),
    TEXT_UTILS("android.text.TextUtils"),
    TEXT_UTILS_NAME("TextUtils"),
    VIEW("android.view.View"),
    TIMEUNIT("java.util.concurrent.TimeUnit"),
    OBSERVER("io.reactivex.Observer"),
    OBSERVABLE("io.reactivex.Observable"),
    ANDROIDSCHEDULERS("io.reactivex.android.schedulers.AndroidSchedulers"),
    DISPOSABLE("io.reactivex.disposables.Disposable"),
    SCHEDULERS("io.reactivex.schedulers.Schedulers"),
    PRESENTER("BasePresenter"),
    FRAGMENT("android.support.v4.app.Fragment"),
    ACTIVITY("android.app.Activity"),
    SNACKBAR("android.support.design.widget.Snackbar");


    private final String fqClassName;

    AndroidClassName(String fqClassName) {
        this.fqClassName = fqClassName;
    }

    public String getClassName() {
        return fqClassName;
    }

    public PsiClass getPsiClass(PsiElement context) {
        return ClassUtils.findClass(fqClassName, context);
    }

    public String getQualifiedStaticMethodName(String methodName) {
        return fqClassName + "." + methodName;
    }

    @Override
    public String toString() {
        return fqClassName;
    }
}
