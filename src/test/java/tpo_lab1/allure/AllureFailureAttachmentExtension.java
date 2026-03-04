package tpo_lab1.allure;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class AllureFailureAttachmentExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        context.getExecutionException().ifPresent(throwable -> {
            String content = formatFailure(throwable);
            Allure.addAttachment(
                    "Ошибка теста",
                    "text/plain; charset=utf-8",
                    new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)),
                    "txt"
            );
        });
    }

    private static String formatFailure(Throwable t) {
        StringWriter sw = new StringWriter();
        sw.append("Сообщение: ").append(t.getMessage() != null ? t.getMessage() : "").append("\n\n");
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
