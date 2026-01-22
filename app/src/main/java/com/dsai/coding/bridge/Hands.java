package com.dsai.coding.bridge;

import com.vcspace.plugins.Editor;
import com.vcspace.plugins.PluginContext;

public class Hands {
    public static void applyCode(PluginContext context, String newCode) {
        Editor editor = context.getEditor();
        if (editor != null && newCode != null && !newCode.isEmpty()) {
            // Mengganti seluruh isi editor dengan kode hasil optimasi AI
            editor.setText(newCode);
            context.toast("Kode berhasil diperbarui oleh DS-AI");
        } else {
            context.toast("Gagal menerapkan kode: Editor tidak aktif");
        }
    }
}
