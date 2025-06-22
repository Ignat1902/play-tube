# Приложение для просмотра видео

Этот репозиторий содержит исходный код приложения для просмотра видео с Pixabay.com. Данное приложение является демонстрационным.

## Использованный стек технологий:

- **Kotlin**
- **Coroutines**
- **Glide**
- **Retrofit**
- **Dagger2**
  
## Скриншоты приложения

- ### Главный экран со списком видео
    <img src="screenshots/main_screen.jpg" alt="Main screen" height="500"/>
    <img src="screenshots/loading.jpg" alt="Loading state" height="500"/>
    <img src="screenshots/network_error.jpg" alt="Network error" height="500"/>

- ### Экран видеоплеера
    <img src="screenshots/portrait_orientation.jpg" alt="Video player portrait orientation" height="500"/>
    <img src="screenshots/landscape_orientation.jpg" alt="Video player landscape orientation" width = "700"/>

## Примечания
- приложение поддерживает светлую и темную темы
- в качестве API использовался pixabay.com
- для работы приложения нужно использовать VPN, т.к API без него не работает
- функция shuffled() имитирует обновление списка при свайпе вниз
