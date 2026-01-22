package com.dsai.coding.bridge;

import com.vcspace.plugins.Editor;
import com.vcspace.plugins.PluginContext;
import com.vcspace.plugins.editor.Position;

public class Hands {
    public static void applyCode(PluginContext context, String newCode) {
        Editor editor = context.getEditor();
        if (editor != null && newCode != null) {
            try {
                // Strategi: Ganti dari posisi (0,0) sampai posisi kursor saat ini 
                // atau lebih amannya gunakan replaceText jika kita tahu Range-nya.
                // Karena kita ingin mengganti SEMUA, kita gunakan trik replaceText.
                
                String oldText = editor.getText();
                // Kita asumsikan mengganti seluruh teks dengan menggunakan replaceText 
                // dari awal (0,0) sampai posisi terakhir teks lama.
                
                // Namun, cara paling simpel di API ini untuk "Inject" adalah insertText 
                // di posisi kursor, atau replaceText jika lu punya Range-nya.
                
                // Mari kita gunakan insertText saja agar aman dari crash Range
                editor.insertText(editor.getCursorPosition(), "\n\n/* AI GENERATED CODE */\n" + newCode);
                
                context.toast("Kode AI berhasil disisipkan di posisi kursor!");
            } catch (Exception e) {
                context.log("Hands Error: " + e.getMessage());
                context.toast("Gagal memodifikasi teks.");
            }
        }
    }
}
