package Core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class YWLFTbot extends TelegramLongPollingBot {
    String f_id = "AgACAgIAAxkBAAIDd15Wx63lNrb21kG9VZAPsrYWR5JFAAKnrDEbgp64Sv0yjTXv1mlgaGpcDwAEAQADAgADbQADcPoFAAEYBA";
    String token = "968066485:AAEI0yena98eZJmjWOwxY-fDmlJczjytUGQ";
    String botUserName = "YWLFT_bot";
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        //String f_id = "AgACAgIAAxkBAAIDd15Wx63lNrb21kG9VZAPsrYWR5JFAAKnrDEbgp64Sv0yjTXv1mlgaGpcDwAEAQADAgADbQADcPoFAAEYBA";
        long chat_id = message.getChatId();
        sendMessage.setChatId(chat_id);
        String comand = message.getText();
        long id = message.getFrom().getId();
        if (update.hasMessage() && message.hasText()) {
            System.out.println("Прийнято <--" + comand);

            String text = null;
            switch (comand) {
                case "/start":
                    text = "Привіт Ви зайшли в телеграм бот @YWLFT_bot. На даний момент " +
                            "ведуться технічні операції. Бажаю терпіння можете використовувати у тестовому " +
                            "режимі такі команди як \n" +
                            "/myname -   Для отримання вашого імені    \n" +
                            "/mylastname -  для отримання прізвища      \n" +
                            "/myfullname - для отримання повного імені \n" +
                            "/pic - для отримання фото. \n" +
                            "Всі данні беруться з вашаго облікового акаунта Telegram";
                    break;
                case "/myname":
                    text = message.getFrom().getFirstName();
                    break;
                case "/mylastname":
                    if (message.getFrom().getLastName() == null)
                        text = "Прізвище не вказане, id - " + message.getFrom().getId();
                    else text = message.getFrom().getLastName();
                    break;
                case "/myfullname":
                    String first = message.getFrom().getFirstName();
                    String second = message.getFrom().getLastName();
                    text = first + " " + (second == null ? "" : second);
                    break;
                case "/id":
                    text = String.valueOf(message.getFrom().getId());
                    break;
                case "/pic":
                    // User sent /pic
                    text = "на діслано фото команда pic була використана " + chat_id;
                    System.out.println("Видає фото з id ----" + f_id);
                    sendPic(chat_id, f_id);
                    break;
                case "/markup":
                    System.out.println("markup");
                    sendMessage = sendInlineKeyBoardMessage2(update.getMessage().getChatId());
                        //execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                        //execute(sendInlineKeyBoardMessage2(update.getMessage().getChatId()));
                    text = sendMessage.getText();
                    break;
                default:
                    text = "Потрібно вибрати команду (/якась_команда)";
                    break;
            }

            System.out.print("Відправлено -->" + text);
            System.out.println("  (" + (message.getFrom().getUserName() == null ? text : message.getFrom().getUserName()) + ")");
            System.out.println("___________________________________");

            if (text != null)
            sendMessage.setText(text);

            try { execute(sendMessage);  } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        else if(update.hasCallbackQuery()){
            System.out.println("CALLBACK Button");
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            SendMessage message1 = new SendMessage();
            message1.setChatId(chat_id);
            if (call_data.equals("update_msg_text")) {
                message1.setText("Вітаю чекайте на рекомендації, ваш id --> " + id);
            }

            else if (call_data.equals("update_msg_text2")) {
                message1.setText("Вітаю чекайте на рекомендації, ваш id --> " + id);
            }
            else {
                System.out.println("Exit");
                 return;
            }
            try {
                execute(message1);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // Message contains photo
            // Message contains photo
            // Set variables

            // Array with photo objects with different sizes
            // We will get the biggest photo from that array
            List<PhotoSize> photos = update.getMessage().getPhoto();
            // Know file_id
            f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
            System.out.println("f_id міняється " + f_id);
            // Know photo width
            int f_width = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();
            // Know photo height
            int f_height = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();
            // Set photo caption
            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
            SendPhoto msg = new SendPhoto()
                    .setChatId(chat_id)
                    .setPhoto(f_id)
                    .setCaption(caption);
            try {
                execute(msg); // Call method to send the photo with caption
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {     return botUserName;  }
    @Override
    public String getBotToken() {  return token; }
    private void sendPic(long chat_id, String f_id) {
        String caption = f_id;
        SendPhoto msg = new SendPhoto()
                .setChatId(chat_id)
                .setPhoto(f_id)
                .setCaption(caption);
        try {
            execute(msg); // Call method to send the photo
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тик");
        inlineKeyboardButton1.setCallbackData("Button \"Тик\" has been pressed");
        inlineKeyboardButton2.setText("Тик2");
        inlineKeyboardButton2.setCallbackData("Button \"Тик2\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"));
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Приклад").setReplyMarkup(inlineKeyboardMarkup);
    }
    public static SendMessage sendInlineKeyBoardMessage2(long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("Чи встановили ви розширення 'You were looking for that' у браузера chrome ?");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Так").setCallbackData("update_msg_text"));
        rowInline.add(new InlineKeyboardButton().setText("Встановити").setCallbackData("update_msg_text2"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }
}