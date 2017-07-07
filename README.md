# Code style in idea

## Google code style

Чтобы подключить google code style для автоматического редактирования кода в Idea необходимо:
1. Скачать файл: https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
2. Зайти в Idea: Settings/Editor/Code style, возле Scheme нажать Manage...,
выбрать Import/Intellij Idea Code Style XML, выбрать скачанный файл intellij-java-google-style.xml
3. Выбрать Scheme GoogleStyle, нажать OK

## CheckStyle plugin

Если решим использовать плагин CheckStyle для проверки:
1. Установить в Idea плагин: Settings/Plugins/Browse repositories..., выбираем CheckStyle-IDEA, устанавливаем, перезагружаем Idea
2. Скачать файл: https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml
3. В Idea заходим: Settings/Other Settings/Checkstyle/Configuration File, добавляем скачанный файл google_checks.xml и выбираем его
