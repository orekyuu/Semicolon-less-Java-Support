package net.orekyuu.semicolonless.inspection;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.tree.IElementType;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class SemicolonUsedInspection extends BaseInspection {
    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Should not use semicolons";
    }

    @NotNull
    @Override
    protected String buildErrorString(Object... infos) {
        return "';' is prohibited in semicolon-less java.";
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new BaseInspectionVisitor() {

            @Override
            public void visitJavaToken(PsiJavaToken token) {
                super.visitJavaToken(token);
                IElementType tokenType = token.getTokenType();

                //token
                if (tokenType == JavaTokenType.SEMICOLON) {
                    registerError(token);
                }

                //string
                if (tokenType == JavaTokenType.STRING_LITERAL) {
                    if (token.getText().contains(";")) {
                        registerError(token);
                    }
                }

                //char
                if (tokenType == JavaTokenType.CHARACTER_LITERAL) {
                    if (token.getText().contains(";")) {
                        registerError(token);
                    }
                }
            }

            @Override
            public void visitComment(PsiComment comment) {
                super.visitComment(comment);
                //semicolon
                if (comment.getText().contains(";")) {
                    registerError(comment);
                }
            }
        };
    }
}
