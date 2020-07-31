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
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.*;
import com.intellij.psi.util.InheritanceUtil;
import com.yz.android.codecompletion.internal.AbstractRichStringBasedPostfixTemplate;
import com.yz.android.codecompletion.utils.AndroidClassName;
import com.yz.android.codecompletion.utils.AndroidPostfixTemplatesUtils;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import static com.yz.android.codecompletion.utils.AndroidClassName.TEXT_UTILS;
import static com.yz.android.codecompletion.utils.AndroidClassName.TEXT_UTILS_NAME;


/**
 * Postfix template for android TextUtils class.
 *
 * @author zhangyf
 */
public class TextUtilsIsEmptyTemplate extends AbstractRichStringBasedPostfixTemplate {

    public static final Condition<PsiElement> IS_NON_NULL_STRING = new Condition<PsiElement>() {
        @Override
        public boolean value(PsiElement element) {
            return InheritanceUtil.isInheritor(((PsiExpression) element).getType(), "java.lang.CharSequence") && !AndroidPostfixTemplatesUtils.isAnnotatedNullable(element);
        }
    };

    public TextUtilsIsEmptyTemplate() {
        this("isempty");
    }

    public TextUtilsIsEmptyTemplate(@NotNull String alias) {
        super(alias, "TextUtils.isEmpty(expr)", IS_NON_NULL_STRING);
    }

    @Override
    protected void onTemplateFinished(TemplateManager manager, Editor editor, Template template) {
        // nothing
        // Prevent complete statement
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "if ("+getStaticPrefix(TEXT_UTILS_NAME, "isEmpty", element) + "($expr$)$END$"+") {"
                +"\n\n"
                +"}";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("expr", new TextExpression(expr.getText()), false);
    }

    @Override
    protected void importString(@NotNull PsiElement expr, @NotNull Editor editor) {
        super.importString(expr, editor);
        final PsiFile file = expr.getContainingFile();
        final PsiJavaFile javaFile = (PsiJavaFile) file;
        final PsiImportList importList = javaFile.getImportList();
        final PsiImportStatement[] importStatements = importList.getImportStatements();
        boolean isImportTextUtil = false;
        for (PsiImportStatement importStatement : importStatements) {
            String ip = importStatement.getQualifiedName();
            if(ip.equals(TEXT_UTILS.getClassName())) {
                isImportTextUtil = true;
                continue;
            }
        }
        String insertStr = null;
        Document document = editor.getDocument();
        int firstLineEndIndex =  document.getLineEndOffset(1);
        if(!isImportTextUtil) {
            insertStr = "\nimport "+TEXT_UTILS.getClassName()+";";
        }
        if(!TextUtils.isEmpty(insertStr)) {
            document.insertString(firstLineEndIndex,insertStr);
        }
    }
}
