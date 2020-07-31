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
import com.intellij.codeInsight.template.macro.ClassNameCompleteMacro;
import com.intellij.codeInsight.template.macro.ClassNameMacro;
import com.intellij.codeInsight.template.macro.DescendantClassesEnumMacro;
import com.intellij.codeInsight.template.macro.QualifiedClassNameMacro;
import com.intellij.ide.macro.ClasspathEntryMacro;
import com.intellij.ide.macro.ClasspathMacro;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.yz.android.codecompletion.internal.AbstractRichStringBasedPostfixTemplate;
import com.yz.android.codecompletion.macro.PresenterMacro;
import com.yz.android.codecompletion.macro.TagMacro;
import com.yz.android.codecompletion.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;
import io.reactivex.Observer;

import static com.yz.android.codecompletion.utils.AndroidClassName.PRESENTER;


/**
 * Postfix template for android mvp init.
 *
 * @author zhangyf
 */
public class PresenterTemplate extends AbstractRichStringBasedPostfixTemplate {

    public PresenterTemplate() {
        this("pit");
    }

    public PresenterTemplate(@NotNull String alias) {
        super(alias, "presenter = new xxPresenterImpl(); presenter.attachView(this);", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "$presenter$ = new $fieldClass$();" + "\n" + "$presenter$.attachView(this);";
//        try {
//            Project project = element.getProject();
//            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
//            final PsiExpression expression = elementFactory.createExpressionFromText(element.getText(), element);
//            final PsiType type = expression.getType();
//            final PsiType[] superTypes = type.getSuperTypes();
//            if (superTypes.length != 0 && superTypes[0].getPresentableText().equals(PRESENTER.getClassName())) {
//                return "$presenter$ = new $fieldClass$();" + "\n" + "$presenter$.attachView(this);";
//            }else {
//                return "$presenter$";
//            }
//        } catch (Exception e) {
//            return "$presenter$";
//        }
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("presenter", new TextExpression(expr.getText()), false);
    }

    @Override
    protected void setVariables(@NotNull Template template, @NotNull PsiElement element) {
        MacroCallNode node = new MacroCallNode(new PresenterMacro(element.getText()));
        template.addVariable("fieldClass", node, new ConstantNode(""), false);
    }
}
