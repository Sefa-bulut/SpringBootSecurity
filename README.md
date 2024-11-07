# Spring Boot Security
- Bu proje, Spring Boot tabanlı bir uygulamada, kullanıcıların kimlik doğrulamasını (login işlemi) gerçekleştirebilmesini ve belirli kaynaklara veya işlemlere erişim için yetki kontrolü yapabilmesini sağlar. JWT, kullanıcı kimlik doğrulaması sağlandıktan sonra güvenli bir şekilde kullanıcı oturumunun yönetilmesini ve yetkilendirilmiş işlemlerin yapılmasını sağlar.
## Özellikler
- **Kimlik Doğrulama (Authentication):** Kullanıcıların giriş yaparak kimliklerini doğrulaması.
- **Yetkilendirme (Authorization):** Kullanıcıların rollerine göre hangi işlemleri yapabileceği veya hangi verilere erişebileceği.
- **JWT (JSON Web Token):** Kullanıcı giriş yaptıktan sonra güvenli bir token oluşturularak, sonraki isteklerde kimlik doğrulama için kullanılması.
- **Spring Security:** Güvenli bir web uygulaması için kimlik doğrulama ve yetkilendirme yönetimi.
## Proje Endpointleri
- /home
- /user/home
- /admin/home
- /authenticate
- /register/user
