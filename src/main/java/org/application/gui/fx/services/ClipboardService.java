package org.application.gui.fx.services;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ClipboardService {
    private final Clipboard clipboard;

    public ClipboardService() {
        this.clipboard = Clipboard.getSystemClipboard();
    }

    public boolean copyToClipboard(String content) {
        if (content == null || content.isBlank()) {
            return false;
        }
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(content);
        clipboard.setContent(clipboardContent);
        return true;
    }
}
