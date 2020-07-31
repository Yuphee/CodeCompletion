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

package com.yz.android.codecompletion.macro;

import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.ClassNameCompleteMacro;
import com.intellij.codeInsight.template.macro.ClassNameMacro;
import com.intellij.codeInsight.template.macro.MacroUtil;
import com.intellij.codeInsight.template.macro.TypeOfVariableMacro;
import com.intellij.ide.macro.ClasspathMacro;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

import static com.yz.android.codecompletion.utils.AndroidClassName.PRESENTER;

/**
 * macro for presenter parameter.
 *
 * @author zhangyf
 */
public class PresenterMacro extends Macro {

    public String name;

    public String getName() {
        return "presenter";
    }

    public String getPresentableName() {
        return "presenter";
    }

    public PresenterMacro(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public Result calculateResult(Expression[] expressions, ExpressionContext context) {
        String fieldTypeName = "";
        Project project = context.getProject();
        try {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
            final PsiExpression expression = elementFactory.createExpressionFromText(name, context.getPsiElementAtStartOffset());
            final PsiType type = expression.getType();
            fieldTypeName = type.getPresentableText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TextResult(fieldTypeName);
    }

}
