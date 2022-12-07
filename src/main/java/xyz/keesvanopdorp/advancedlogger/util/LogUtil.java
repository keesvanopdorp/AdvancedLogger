package xyz.keesvanopdorp.advancedlogger.util;

import xyz.keesvanopdorp.advancedlogger.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

public class LogUtil {
    private final String logFolderPath;
    private final Main plugin = Main.getInstance();
    public static final DateFormat LOG_FILE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public LogUtil(final String logFolderPath) {
        this.logFolderPath = logFolderPath;
    }

    public FileOutputStream getLogFileForToday() throws IOException {
        String dateString = this.LOG_FILE_DATE_FORMAT.format(new Date());
        File logFile = new File(String.format("%s/%s.txt", this.logFolderPath, dateString));
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        return new FileOutputStream(logFile, true);
    }

    public void printMessage(final String message) {
        String logFormat = this.plugin.getConfiguration().getString("log-format");
        String logDateFormat = this.plugin.getConfiguration().getString("log-time-format");
        DateFormat format = new SimpleDateFormat(logDateFormat);
        String date = format.format(new Date());
        try {
            String formattedMessage = String.format(logFormat, date, message);
            this.plugin.getLogger().log(INFO, message);
            FileOutputStream out = this.getLogFileForToday();
            out.write(formattedMessage.getBytes());
            out.close();
        } catch (IOException e) {
            this.plugin.getLogger().log(SEVERE, e.getMessage());
        }
    }
}
