package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.config.LocaleProvider;

@Component
@SuppressWarnings("unused")
public class SimpleMessageLocalizationService implements MessageLocalizationService {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public SimpleMessageLocalizationService(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getMessage(String messageCode, String... args){
        return messageSource.getMessage(messageCode, args, localeProvider.getLocale());
    }
}
