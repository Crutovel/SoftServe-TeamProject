package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.service.MessageByLocaleService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

  private MessageSource messageSource;

  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String getMessage(String id) {
    return getMessage(id, null);
  }

  @Override
  public String getMessage(String id, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(id, args, locale);
  }
}
