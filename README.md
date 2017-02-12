## Основной интерфейс для общения со storm кластером.

### Основные интерфейсы endpoint'а
#### Запрос категорий
- Метод: GET
- URL : `http://endpoint/categories`
- Ответ:
```{json}
[ {
  "name" : "auto",
  "id" : "asbd1"
}, {
  "name" : "Культура",
  "id" : "asbd2"
},
...
{
  "name" : "Технологии",
  "id" : "asbd3"
} ]
```

#### Запрос источников данных
- Метод: GET
- URL : `http://endpoint/sources`
- Ответ:
```{json}
[ {
  "domain" : "bnkomi.ru",
  "name" : "Информационное агентство БНКоми",
  "id" : "asbd1"
}, {
  "domain" : "7x7-journal.ru",
  "name" : "Межрегиональный интернет-журнал \"7x7\"",
  "id" : "asbd2"
},
....
{
  "domain" : "komiinform.ru",
  "name" : "Информационное агенство Комиинформ",
  "id" : "asbd2"
} ]
```
