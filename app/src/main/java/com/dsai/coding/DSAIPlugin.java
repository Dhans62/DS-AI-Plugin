package com.dsai.coding;

import com.vcspace.plugins.Plugin;
import com.vcspace.plugins.PluginContext;
import com.dsai.coding.bridge.Eyes;
import com.dsai.coding.network.AIClients;
import com.dsai.coding.network.ResponseParser;
import com.dsai.coding.ui.PreviewDialog;

public class DSAIPlugin implements Plugin {

    @Override
    public void onPluginLoaded(PluginContext context) {
        context.log("DS-AI Online: Senior Engineering Mode");

        // Mendaftarkan Menu Utama
        context.addMenu("DS-AI: Analisis Kode", 100, () -> {
            showModelSelector(context);
        });
    }

    private void showModelSelector(PluginContext context) {
        // Dialog Pertama: Memilih Otak AI
        context.showDialog(
            "Pilih Otak DS-AI",
            "Gunakan model mana untuk analisis ini?",
            "Gemini-3", // Tombol Positif
            "DeepSeek / GLM", // Tombol Negatif
            () -> { executeProcess(context, "GEMINI"); },
            () -> { 
                // Sub-menu untuk pilihan model lainnya
                context.showDialog("Opsi Lain", "Pilih model alternatif:", "DeepSeek", "GLM-4.7",
                () -> { executeProcess(context, "DEEPSEEK"); },
                () -> { executeProcess(context, "GLM"); });
            }
        );
    }

    private void executeProcess(PluginContext context, String model) {
        String code = Eyes.getContextCode(context);
        
        if (code.isEmpty()) {
            context.toast("Mata gagal melihat kode: Editor Kosong!");
            return;
        }

        context.toast("DS-AI (" + model + ") sedang berpikir...");

        // Prompt Engineering untuk Senior Engineer
        String prompt = "Berperanlah sebagai Senior Software Engineer. Analisis kode berikut. " +
                       "Berikan respon dalam format JSON mentah dengan dua kunci: " +
                       "'rencana' (penjelasan langkah perbaikan) dan 'kode_baru' (seluruh kode yang sudah diperbaiki). " +
                       "KODE: " + code;

        AIClients.request(context, model, prompt, new AIClients.Callback() {
            @Override
            public void onSuccess(String result) {
                String plan = ResponseParser.extract(result, model);
                // Dalam implementasi nyata, kita butuh ekstraksi kode yang lebih presisi
                // Untuk saat ini, kita tampilkan di Preview Dialog
                PreviewDialog.show(context, "Rencana: " + plan, result); 
            }

            @Override
            public void onError(String error) {
                context.toast("Error AI: " + error);
            }
        });
    }
}
