package com.neoflex.SOLIDbot.constants;

public class SolidBotConstants {
    public static String SOLID_GREETING = "Вас приветствует SOLIDno-бот. Мои возможности вы можете узнать введя команду '/help'";
    public static String SOLID_HELP = "Список валют - /all\n Подписаться на валюту - /subscribe 'figi'\n Отписаться от валюты - /unsubscribe 'figi'\n Начать отслеживание валюты до падения курса на n% - /percent 'figi' n%\n Если вы были ранее подписаны, но подписки не приходят, используйте команду /start";
    public static int SUB_WITH_PERCENT_SYMBOL_POS = 1;
    public static int SUB_WITH_PERCENT_PERCENT_POS = 2;
    public static String SUB_WITH_PERCENT_NO_CANDLE_WARNING ="Валюта не найдена!";
    public static String SUB_WITH_PERCENT_PERCENT_NEG_WARNING = "Процент должен быть больше нуля!";
    public static String SUB_WITH_PERCENT_WRONG_COMMAND_SYNTAX = "Введите на какую валюту вы хотите подписаться, и какой на процент должна уменьшится валюта";
    public static String SUB_WITH_PERCENT_ALREADY_SUBSCRIBED = "У вас уже есть подписка на падение курса с этим процентом!";
    public static String SUB_WITH_PERCENT_SUCCESSFUL_SUB = "Вы успешно подписаны на оповещение по валюте %s, до момента уменьшения ее цены на %d%%";
    public static String PERCENT_COMMAND = "/percent";
    public static String HELP_COMMAND = "/help";

}