### SiteMapCreator Программа создания карты сайта.
Приложение в многопоточном режиме формирует карту заданного сайта и пишет её в файл.

Для начала конфигурирования карты сайта необходимо указать адрес в формате: http(s)://something.*

Приложение обходит все страницы заданного сайта, собирает все ссылки на внутренние ресурсы, игнорируя повторяющиеся
ссылки, ссылки на внешние ресурсы. После завершения парсинга все полученные ссылки записываются в txt файл.
иерархия ссылок в файле создается при помощи табуляции, с учетом уровня вложенности ссылки.