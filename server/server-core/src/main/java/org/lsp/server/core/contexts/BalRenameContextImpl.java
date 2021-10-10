package org.lsp.server.core.contexts;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.api.symbols.Symbol;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.Token;
import io.ballerina.projects.Document;
import io.ballerina.tools.text.LinePosition;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.RenameParams;
import org.lsp.server.api.ClientLogManager;
import org.lsp.server.api.DiagnosticsPublisher;
import org.lsp.server.api.context.LSContext;
import org.lsp.server.api.context.BalRenameContext;
import org.lsp.server.ballerina.compiler.workspace.CompilerManager;
import org.lsp.server.core.compiler.manager.BallerinaCompilerManager;
import org.lsp.server.core.utils.ClientLogManagerImpl;
import org.lsp.server.core.utils.CommonUtils;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BalRenameContextImpl extends BalTextDocumentContextImpl implements BalRenameContext {
    private final LSContext serverContext;
    private final RenameParams params;
    private int cursor = -1;
    private NonTerminalNode nodeAtCursor;
    private Token tokenAtCursor;

    public BalRenameContextImpl(LSContext serverContext, RenameParams params) {
        super(serverContext, params.getTextDocument().getUri());
        this.serverContext = serverContext;
        this.params = params;
    }

    @Override
    public CompilerManager compilerManager() {
        return BallerinaCompilerManager.getInstance(this.serverContext);
    }

    @Override
    public DiagnosticsPublisher diagnosticPublisher() {
        return null;
    }

    @Override
    public List<Symbol> visibleSymbols() {
        Path path = CommonUtils.uriToPath(this.params.getTextDocument().getUri());
        Document currentDoc = this.currentDocument().orElseThrow();
        Position cursorPosition = this.getCursorPosition();
        LinePosition linePosition = LinePosition.from(cursorPosition.getLine(), cursorPosition.getCharacter());
        Optional<SemanticModel> semanticModel = BallerinaCompilerManager.getInstance(this.serverContext)
                .getSemanticModel(path);
        if (semanticModel.isEmpty()) {
            return Collections.emptyList();
        }
        
        return semanticModel.get().visibleSymbols(currentDoc, linePosition);
    }

    @Override
    public void setTokenAtCursor(Token token) {
        this.tokenAtCursor = token;
    }

    @Override
    public Token getTokenAtCursor() {
        if (this.tokenAtCursor == null) {
            throw new RuntimeException("Token at cursor is not set");
        }

        return this.tokenAtCursor;
    }

    @Override
    public void setNodeAtCursor(NonTerminalNode node) {
        this.nodeAtCursor = node;
    }

    @Override
    public NonTerminalNode getNodeAtCursor() {
        if (nodeAtCursor == null) {
            throw new RuntimeException("Node at cursor is not set");
        }

        return this.nodeAtCursor;
    }

    @Override
    public void setCursorPositionInTree(int offset) {
        if (this.cursor > 0) {
            throw new RuntimeException("Cursor cannot set more than once");
        }

        this.cursor = offset;
    }

    @Override
    public int getCursorPositionInTree() {
        if (this.cursor == -1) {
            throw new RuntimeException("Cursor is not set");
        }

        return this.cursor;
    }

    @Override
    public Position getCursorPosition() {
        return this.params.getPosition();
    }

    @Override
    public Path getPath() {
        return CommonUtils.uriToPath(this.params.getTextDocument().getUri());
    }

    @Override
    public RenameParams params() {
        return this.params;
    }


}
