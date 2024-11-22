Программа предназначена для взаимодействия с воротами гаража/паркинга через сообщения в телеграм канале.

![image](https://github.com/user-attachments/assets/784ecd19-7687-4112-99b6-f4fbba1e19af)

Для полноценной работы программы нужны два бота, один бот используется в программе и отправляет команды в телеграм канал, 
второй в системе умного дома читает сообщения в канале и открывает или закрывает ворота. Поскольку бота невозможно включить 
в телеграм чат, то нужно использовать именно телеграм канал, тогда оба бота смогут общаться между собой.

В программе реализована поддержка AndroidAuto, для открытия дверей, прямо с дисплея автомобиля, не доставая телефон из кармана

![image](https://github.com/user-attachments/assets/499429b7-8cef-45f7-9931-9a93dc085abe)

Для отправки сообщений нужно задать 4 настройки:

![image](https://github.com/user-attachments/assets/84f9b04c-ac9f-4533-863f-bc1796b30eb5)


1. Токен бота, который будет отправлять сообщения в канал. Можно получить у бота BotFather
2. ID чата канала. После добавления обоих ботов в администраторы канала, отправить в него какое-либо сообщение и сделать запрос
   https://api.telegram.org/bot{ТокенБота}/getUpdates
   в ответе можно найти отправленное сообщение и id чата, которое должно быть отрицательным числом, т.к. это канал.
3. Сообщение которое будет отправляться в канал при нажатии на первую кнопку
4. Сообщение которое будет отправляться в канал при нажатии на второую кнопку

Можно отправлять сообщения и с телефона

![image](https://github.com/user-attachments/assets/93601ac7-82dd-4007-a902-915d94c6bbb8)
