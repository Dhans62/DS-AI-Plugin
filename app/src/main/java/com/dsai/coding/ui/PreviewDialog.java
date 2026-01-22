package com.dsai.coding.ui;

import com.vcspace.plugins.PluginContext;
import com.dsai.coding.bridge.Hands;

public class PreviewDialog {
    public static void show(PluginContext context, String plan, String newCode) {
        // Dialog konfirmasi sesuai dokumentasi VCS
        context.showDialog(
            "Rencana Perubahan DS-AI",
            plan,
            "Terapkan",
            "Batal",
            () -> { 
                Hands.applyCode(context, newCode); 
            },
            () -> { 
                context.toast("Perubahan dibatalkan"); 
            }
        );
    }
}
