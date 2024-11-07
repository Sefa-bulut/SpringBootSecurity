# Spring Boot Security with JWT
- Bu projede, Spring Boot ve Spring Security kullanarak JWT (JSON Web Token) tabanlı kimlik doğrulama ve yetkilendirme mekanizmasını uyguladım. Günümüzde, mikro servisler ve API tabanlı uygulamaların güvenliğini sağlamak için token-based authentication (token tabanlı kimlik doğrulama) giderek daha yaygın hale gelmektedir. Bu projede, JWT kullanarak güvenli bir kullanıcı kimlik doğrulama süreci geliştirmeyi amaçladım.
- Bu doğrultuda projenin amacı, kullanıcıların sisteme giriş yaparken güvenli bir şekilde kimlik doğrulaması yapabilmesini sağlamak ve sisteme her girişte yeni bir JWT token alarak bu token'la sistemde korunan kaynaklara erişim yetkisi kazandırmaktır. Bu proje, temel seviyede JWT kullanarak kullanıcı doğrulama işlemlerini gerçekleştirirken aynı zamanda Spring Security ile yetkilendirme süreçlerinin de entegre etmeyi hedeflemiştir.
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
