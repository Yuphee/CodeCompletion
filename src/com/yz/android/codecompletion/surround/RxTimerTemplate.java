/*
 * Copyright (C) 2015 takahirom
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

package com.yz.android.codecompletion.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.yz.android.codecompletion.internal.AbstractRichStringBasedPostfixTemplate;
import com.yz.android.codecompletion.macro.PresenterMacro;
import com.yz.android.codecompletion.utils.AndroidClassName;
import com.yz.android.codecompletion.utils.AndroidPostfixTemplatesUtils;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


/**
 * Postfix template for Observable.timer.
 *
 * @author zhangyf
 */
public class RxTimerTemplate extends AbstractRichStringBasedPostfixTemplate {

    public RxTimerTemplate() {
        this("timer");
    }

    public RxTimerTemplate(@NotNull String alias) {
        super(alias, "Observable.timer(10, TimeUnit.SECONDS)...", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "Observable.timer($num$, TimeUnit.SECONDS)"
                + "\n"
                + ".subscribeOn(Schedulers.io())"
                + "\n"
                + ".observeOn(AndroidSchedulers.mainThread())"
                + "\n"
                + ".subscribe(new Observer<Long>() {"
                +"\n"
                +"@Override"
                +"\n"
                +"public void onSubscribe(Disposable d) {"
                +"\n"
                +"}"
                + "\n"
                + "@Override"
                + "\n"
                + "public void onNext(Long aLong) {"
                + "\n"
                + "}"
                + "\n"
                + "@Override"
                + "\n"
                + "public void onError(Throwable e) {"
                + "\n"
                + "}"
                + "\n"
                + "@Override"
                + "\n"
                + "public void onComplete() {"
                + "\n"
                + "}"
                + "\n"
                + "});";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("num", new TextExpression(expr.getText()), false);
    }

    @Override
    protected void importString(@NotNull PsiElement expr, @NotNull Editor editor) {
        super.importString(expr, editor);
        final PsiFile file = expr.getContainingFile();
        final PsiJavaFile javaFile = (PsiJavaFile) file;
        final PsiImportList importList = javaFile.getImportList();
        final PsiImportStatement[] importStatements = importList.getImportStatements();
        boolean isImportObserver = false;
        boolean isImportTimeUnit = false;
        boolean isImportObservable = false;
        boolean isImportAndroidSchedulers = false;
        boolean isImportDisposable = false;
        boolean isImportSchedulers = false;
        for (PsiImportStatement importStatement : importStatements) {
            String ip = importStatement.getQualifiedName();
            if(ip.equals(AndroidClassName.OBSERVER.getClassName())) {
                isImportObserver = true;
                continue;
            }
            if(ip.equals(AndroidClassName.TIMEUNIT.getClassName())) {
                isImportTimeUnit = true;
                continue;
            }
            if(ip.equals(AndroidClassName.OBSERVABLE.getClassName())) {
                isImportObservable = true;
                continue;
            }
            if(ip.equals(AndroidClassName.ANDROIDSCHEDULERS.getClassName())) {
                isImportAndroidSchedulers = true;
                continue;
            }
            if(ip.equals(AndroidClassName.DISPOSABLE.getClassName())) {
                isImportDisposable = true;
                continue;
            }
            if(ip.equals(AndroidClassName.SCHEDULERS.getClassName())) {
                isImportSchedulers = true;
                continue;
            }
        }
        String insertStr = null;
        Document document = editor.getDocument();
        int firstLineEndIndex =  document.getLineEndOffset(1);
        if(!isImportObserver) {
            insertStr = "\nimport "+AndroidClassName.OBSERVER.getClassName()+";";
        }
        if(!isImportTimeUnit) {
            insertStr += "\nimport "+AndroidClassName.TIMEUNIT.getClassName()+";";
        }
        if(!isImportObservable) {
            insertStr += "\nimport "+AndroidClassName.OBSERVABLE.getClassName()+";";
        }
        if(!isImportAndroidSchedulers) {
            insertStr += "\nimport "+AndroidClassName.ANDROIDSCHEDULERS.getClassName()+";";
        }
        if(!isImportDisposable) {
            insertStr += "\nimport "+AndroidClassName.DISPOSABLE.getClassName()+";";
        }
        if(!isImportSchedulers) {
            insertStr += "\nimport "+AndroidClassName.SCHEDULERS.getClassName()+";";
        }
        if(!TextUtils.isEmpty(insertStr)) {
            document.insertString(firstLineEndIndex,insertStr);
        }
    }
}
