# vaccination-microservices
![image](https://user-images.githubusercontent.com/68801099/170131567-ad53e89c-f29b-4edd-99b9-0ff44310cde6.png)

Требования к API:<br>
/api/v1/person - вызовы должны проксироваться на микросервис Person.<br>
/api/v1/qr - проксируется на QR микросервис.<br>
/api/v1/medical - проксирует на medical.<br><br>
GET /api/v1/info/{passport} - метод агрегирующий информацию по клиенту от всех существующих микросвервисов по его паспорту.<br>
