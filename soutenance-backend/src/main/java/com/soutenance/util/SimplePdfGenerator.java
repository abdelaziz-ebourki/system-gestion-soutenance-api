package com.soutenance.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class SimplePdfGenerator {

    private SimplePdfGenerator() {
    }

    public static byte[] singlePage(String title, List<String> lines) {
        StringBuilder content = new StringBuilder("BT\n/F1 16 Tf\n50 780 Td\n(")
            .append(escape(title))
            .append(") Tj\n/F1 11 Tf\n0 -28 Td\n");
        for (String line : lines) {
            content.append("(").append(escape(line)).append(") Tj\n0 -18 Td\n");
        }
        content.append("ET");

        List<String> objects = List.of(
            "<< /Type /Catalog /Pages 2 0 R >>",
            "<< /Type /Pages /Kids [3 0 R] /Count 1 >>",
            "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>",
            "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>",
            "<< /Length " + content.length() + " >>\nstream\n" + content + "\nendstream"
        );

        StringBuilder pdf = new StringBuilder("%PDF-1.4\n");
        List<Integer> offsets = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            offsets.add(pdf.length());
            pdf.append(i + 1).append(" 0 obj\n").append(objects.get(i)).append("\nendobj\n");
        }
        int xref = pdf.length();
        pdf.append("xref\n0 ").append(objects.size() + 1).append("\n");
        pdf.append("0000000000 65535 f \n");
        for (Integer offset : offsets) {
            pdf.append(String.format("%010d 00000 n \n", offset));
        }
        pdf.append("trailer\n<< /Size ").append(objects.size() + 1).append(" /Root 1 0 R >>\n");
        pdf.append("startxref\n").append(xref).append("\n%%EOF");
        return pdf.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    private static String escape(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }
}
