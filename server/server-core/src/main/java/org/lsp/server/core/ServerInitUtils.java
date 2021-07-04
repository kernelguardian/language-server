/*
 * Copyright (c) 2021, Nadeeshaan Gunasinghe, Nipuna Marcus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lsp.server.core;

import org.eclipse.lsp4j.CodeActionOptions;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.DefinitionOptions;
import org.eclipse.lsp4j.DocumentLinkOptions;
import org.eclipse.lsp4j.DocumentOnTypeFormattingOptions;
import org.eclipse.lsp4j.DocumentSymbolOptions;
import org.eclipse.lsp4j.ExecuteCommandOptions;
import org.eclipse.lsp4j.HoverOptions;
import org.eclipse.lsp4j.ReferenceOptions;
import org.eclipse.lsp4j.RenameOptions;
import org.eclipse.lsp4j.SaveOptions;
import org.eclipse.lsp4j.SemanticTokensServerFull;
import org.eclipse.lsp4j.SemanticTokensWithRegistrationOptions;
import org.eclipse.lsp4j.SignatureHelpOptions;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
import org.eclipse.lsp4j.WorkspaceFoldersOptions;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.lsp.server.core.semantictoken.SemanticTokensProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Holds the server initializing utilities.
 *
 * @since 1.0.0
 */
public class ServerInitUtils {
    private ServerInitUtils() {
    }

    /**
     * Get the document sync options.
     *
     * @return {@link TextDocumentSyncOptions}
     */
    public static TextDocumentSyncOptions getDocumentSyncOption() {
        TextDocumentSyncOptions syncOptions = new TextDocumentSyncOptions();
        SaveOptions saveOptions = new SaveOptions(true);
        syncOptions.setChange(TextDocumentSyncKind.Full);
        syncOptions.setOpenClose(true);
        syncOptions.setWillSave(true);
        syncOptions.setWillSaveWaitUntil(true);
        syncOptions.setSave(saveOptions);

        return syncOptions;
    }

    /**
     * Get the options for the textDocument/completion request.
     *
     * @return {@link CompletionOptions}
     */
    public static CompletionOptions getCompletionOptions() {
        CompletionOptions completionOptions = new CompletionOptions();

        // List of trigger characters.
        List<String> triggerCharacters = Arrays.asList(".", ">");

        completionOptions.setResolveProvider(true);
        completionOptions.setTriggerCharacters(triggerCharacters);
        completionOptions.setWorkDoneProgress(true);

        return completionOptions;
    }

    public static RenameOptions getRenameOptions() {
        RenameOptions renameOptions = new RenameOptions();
        // Set the prepare support from the server
        renameOptions.setPrepareProvider(true);

        return renameOptions;
    }

    public static DocumentOnTypeFormattingOptions getOnTypeFormatOptions() {
        DocumentOnTypeFormattingOptions options =
                new DocumentOnTypeFormattingOptions();
        options.setFirstTriggerCharacter("}");
        options.setMoreTriggerCharacter(Collections.singletonList(";"));
        
        return options;
    }

    public static ReferenceOptions getReferenceOptions() {
        ReferenceOptions options = new ReferenceOptions();
        options.setWorkDoneProgress(true);
        return options;
    }

    public static DefinitionOptions getDefinitionOptions() {
        DefinitionOptions options = new DefinitionOptions();

        return options;
    }

    public static DocumentLinkOptions getDocumentLinkOptions() {
        DocumentLinkOptions options = new DocumentLinkOptions();
        options.setResolveProvider(true);
        return options;
    }

    public static ExecuteCommandOptions getExecCommandOptions() {
        ExecuteCommandOptions options = new ExecuteCommandOptions();
        options.setCommands(Arrays.asList(
                "ADD_DOC",
                "CREATE_VAR"));

        return options;
    }

    public static DocumentSymbolOptions getDocumentSymbolOptions() {
        DocumentSymbolOptions options = new DocumentSymbolOptions();
        options.setLabel("Ballerina Default");

        return options;
    }

    public static SemanticTokensWithRegistrationOptions getSemanticTokenOptions() {
        SemanticTokensWithRegistrationOptions options = new SemanticTokensWithRegistrationOptions();
        SemanticTokensServerFull serverFull = new SemanticTokensServerFull();
        serverFull.setDelta(false);
        options.setLegend(SemanticTokensProvider.SEMANTIC_TOKENS_LEGEND);
        options.setFull(serverFull);

        return options;
    }

    public static CodeActionOptions getCodeActionOptions() {
        CodeActionOptions options = new CodeActionOptions();
        options.setResolveProvider(true);

        return options;
    }

    public static WorkspaceFoldersOptions getWorkspaceFolderOptions() {
        WorkspaceFoldersOptions options = new WorkspaceFoldersOptions();
        options.setChangeNotifications(Either.forRight(true));

        return options;
    }

    public static SignatureHelpOptions getSignatureHelpOptions() {
        SignatureHelpOptions options = new SignatureHelpOptions();
        options.setTriggerCharacters(Arrays.asList("(", ","));

        return options;
    }
    
    public static HoverOptions getHoverOptions() {
        HoverOptions options = new HoverOptions();
        
        return options;
    }
}
