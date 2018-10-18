# Space Gallery
Приложение разработано для Школы мобильной разработки от Яндекса. Приложение получает картинки из [архива](https://apod.nasa.gov/apod/archivepix.html) НАСА. На стартовом экране отображается Фото дня, а на следующем экране фотографии из архива. Загрузка изображений производится с помощью Picasso. Парсинг реализовон с использованием библиотеки Jsoup в фоновом потоке. При отсутсвии интернета, приложение уведомляет об этом пользователя и продолжает работу при появлении интернета. Ссылки на изображения сохраняются локально, для того чтобы в офлайне изображения могли загрузиться из кеша Picasso.
