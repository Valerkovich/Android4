# Android4
Постановка завдання.

Розробити Android-додаток, який взаємодіє з віддаленими сервісами
відкритих даних, шляхом виконання мережевих запитів:
Загальні умови:
1) Додаток має виконувати мережеві запити до віддаленого сервісу на
отримання даних у форматі JSON за допомогою клієнтів OkHttp
та/або Retrofit.
2) Отримані дані мають кешуватися в локальній базі даних, на
випадок втрати з’єднання з мережею. Якщо сервіс або мережа
недоступні, то дані відображаються в інтерфейсі додатку з
локальної бази даних. При запиті даних має відбуватись перевірка
на відповідність даних в локальній базі. Якщо дані з віддаленого
сервісу змінились, то необхідно оновити дані в локальній базі.
3) Додаток має складатись з двох екранів. Перший екран показує дані
списком. При натисненні на будь-який елемент списку запускається
інший екран з детальною інформацією при вибраний елемент.

Загальна інформація про країни Європи (столиця, код, рівень
доходів).
 
Документація:
https://datahelpdesk.worldbank.org/knowledgebase/articles/898614-aggregate-api-queries

API-метод:
http://api.worldbank.org/v2/country