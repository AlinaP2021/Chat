package client;

import server.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {
    public class BotSocketThread extends Client.SocketThread {
        @Override
        protected void processIncomingMessage(String message) {
            if (message == null) return;
            ConsoleHelper.writeMessage(message);
            String[] parts = message.split(": ");
            if (parts.length == 2) {
                SimpleDateFormat dateFormat = null;
                if (parts[1].equalsIgnoreCase("дата")) {
                    dateFormat = new SimpleDateFormat("d.MM.YYYY");
                } else if (parts[1].equalsIgnoreCase("день")) {
                    dateFormat = new SimpleDateFormat("d");
                } else if (parts[1].equalsIgnoreCase("месяц")) {
                    dateFormat = new SimpleDateFormat("MMMM");
                } else if (parts[1].equalsIgnoreCase("год")) {
                    dateFormat = new SimpleDateFormat("YYYY");
                } else if (parts[1].equalsIgnoreCase("время")) {
                    dateFormat = new SimpleDateFormat("H:mm:ss");
                } else if (parts[1].equalsIgnoreCase("час")) {
                    dateFormat = new SimpleDateFormat("H");
                } else if (parts[1].equalsIgnoreCase("минуты")) {
                    dateFormat = new SimpleDateFormat("m");
                } else if (parts[1].equalsIgnoreCase("секунды")) {
                    dateFormat = new SimpleDateFormat("s");
                }
                if (dateFormat != null) {
                    sendTextMessage("Информация для " + parts[0] + ": " + dateFormat.format(Calendar.getInstance().getTime()));
                }
            }
        }

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (Math.random()*100);
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected Client.SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
