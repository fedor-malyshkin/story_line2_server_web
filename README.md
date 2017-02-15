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
"short_name" : "БНКоми",
  "id" : "asbd1"
}, {
  "domain" : "7x7-journal.ru",
  "name" : "Межрегиональный интернет-журнал \"7x7\"",
  "short_name" : "\"7x7\"",  
  "id" : "asbd2"
},
....
{
  "domain" : "komiinform.ru",
  "name" : "Информационное агенство Комиинформ",
  "short_name" : "Комиинформ",  
  "id" : "asbd2"
} ]
```

#### Запрос заголовков новостных статей
- Метод: GET
- URL : `http://endpoint/news_headers/{source-domain}` (`http://endpoint/news_headers/bnkomi.ru`)
- Дополнительные параметры:
	- `count` - кол-во статей/заголовков для выдачи

- Ответ:
```{json}
[ {
  "title" : "Новость 1",
  "date" : "1970-01-01T00:00:00.001Z",
  "name" : "bnkomi.ru",
  "id" : "asbd1"
}, {
  "title" : "Новость 2",
  "date" : "1970-01-01T00:00:00.002Z",
  "name" : "bnkomi.ru",
  "id" : "asbd2"
}, {
  "title" : "Новость 3",
  "date" : "1970-01-01T00:00:00.003Z",
  "name" : "bnkomi.ru",
  "id" : "asbd3"
} ]
```

#### Запрос статьи
- Метод: GET
- URL : `http://endpoint/news_articles/{article_id}` (`http://endpoint/news_articles/1234`)
- Ответ:
```{json}
{
  "content" : "Content of Новость 3",
  "path" : "https://www.bnkomi.ru/data/news/59446/",
  "title" : "Новость 3",
  "date" : "1970-01-01T00:00:00.003Z",
  "processing_date" : "1970-01-01T00:00:00.030Z",
  "name" : "bnkomi.ru",
  "id" : "asbd3"
}```
