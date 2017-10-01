## Основной интерфейс для общения со storm кластером.

### Основные интерфейсы endpoint'а
#### Запрос категорий
- Метод: GET
- URL : `http://endpoint/categories`
- Ответ:
```json
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
```json
[ {
  "name" : "bnkomi.ru",
  "title" : "Информационное агентство БНКоми",
  "title_short" : "БНКоми"
}, {
  "name" : "7x7-journal.ru",
  "title" : "Межрегиональный интернет-журнал \"7x7\"",
  "title_short" : "\"7x7\""
},
....
{
  "name" : "komiinform.ru",
  "title" : "Информационное агенство Комиинформ",
  "title_short" : "Комиинформ"
} ]
```

#### Запрос заголовков новостных статей
- Метод: GET
- URL : `http://endpoint/news_headers/{source-domain}` (`http://endpoint/news_headers/bnkomi.ru`)
- Дополнительные параметры:
	- `count` - кол-во статей/заголовков для выдачи

- Ответ:
```json
[ {
  "title" : "Новость 1",
  "publication_date" : "1970-01-01T00:00:00.001Z",
  "source" : "bnkomi.ru",
  "id" : "asbd1"
}, {
  "title" : "Новость 2",
  "publication_date" : "1970-01-01T00:00:00.002Z",
  "source" : "bnkomi.ru",
  "id" : "asbd2"
}, {
  "title" : "Новость 3",
  "publication_date" : "1970-01-01T00:00:00.003Z",
  "source" : "bnkomi.ru",
  "id" : "asbd3"
} ]
```

#### Запрос статьи
- Метод: GET
- URL : `http://endpoint/news_articles/{article_id}` (`http://endpoint/news_articles/1234`)
- Ответ:
```json
{
  "content" : "Content of Новость 3",
  "path" : "https://www.bnkomi.ru/data/news/59446/",
  "title" : "Новость 3",
  "publication_date" : "1970-01-01T00:00:00.003Z",
  "processing_date" : "1970-01-01T00:00:00.030Z",
  "source" : "bnkomi.ru",
  "id" : "asbd3"
}```

#### Запрос картинок статьи
- Метод: GET
- URL : `http://endpoint/news_articles/{article_id}/images` (`http://endpoint/news_articles/1234/images`)
- Дополнительные параметры:
	- `index` - номер изображения в статье (пока по умолчанию - 0)
	- `w` - желаемая ширина изображения (должно использоваться обязательно с 'op')
	- `h` - желаемая высота изображения (должно использоваться обязательно с 'op')
	- `op` - дополнительная операция над изображением (масшатабирование+образка/масштабирование) "crop"/"scale" (должно использоваться обязательно с 'h'/'w')
- Ответ:
	- поток данных, с MIME-типом фрмата изображения
	- редирект на оригинальную картинку на странице статьи
