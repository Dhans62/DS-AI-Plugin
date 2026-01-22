package com.dsai.coding.bridge;

import com.vcspace.plugins.Editor;
import com.vcspace.plugins.PluginContext;

public class Eyes {
    public static String getContextCode(PluginContext context) {
        Editor editor = context.getEditor();
        if (editor == null) return "";
        
        // Mengambil seluruh teks di editor aktif
        String code = editor.getText();
        return (code != null) ? code : "";
    }
}
