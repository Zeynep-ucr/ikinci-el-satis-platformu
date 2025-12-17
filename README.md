# 📌 İkinci El Satış Platformu – VTYS Projesi

Bu proje, **Veri Tabanı Yönetim Sistemleri (VTYS)** dersi kapsamında geliştirilmiş bir **ikinci el satış platformu** masaüstü uygulamasıdır.  
Uygulama; ürün ilanlarının listelenebildiği, filtrelenebildiği, eklenip düzenlenebildiği ve **kullanıcı bazlı favorilere eklenebildiği** bir sistem sunmaktadır.

---

## 🎯 Projenin Amacı

Bu projenin temel amacı:

- Gerçek hayata uygun bir **ilişkisel veri tabanı mimarisi** tasarlamak,
- **3. Normal Form (3NF)** kurallarına uygun normalizasyon uygulamak,
- Java tabanlı bir masaüstü uygulama ile **Microsoft SQL Server** arasında bağlantı kurmak,
- CRUD işlemlerini (Create, Read, Update, Delete) uygulamak,
- **Transaction, View, Stored Procedure** gibi ileri VTYS kavramlarını kullanmak,
- JOIN, GROUP BY, HAVING ve Aggregate fonksiyonlar içeren gelişmiş SQL sorguları yazmaktır.

---

## 🗂️ Kullanılan Teknolojiler

- **Java (JavaFX)** – Masaüstü uygulama geliştirme  
- **Microsoft SQL Server** – İlişkisel veri tabanı  
- **JDBC (Java Database Connectivity)** – Java–SQL bağlantısı  
- **Draw.io** – ER Diyagramı tasarımı  
- **IntelliJ IDEA** – Geliştirme ortamı  
- **GitHub** – Versiyon kontrolü  

---

## 🧱 Veri Tabanı Yapısı

Veri tabanı, normalizasyon kurallarına uygun şekilde tasarlanmıştır. Kullanılan tablolar:

- **Users** → Kullanıcı bilgileri (admin / sabit kullanıcı yapısı)
- **Categories** → Ürün kategorileri  
- **Cities** → Şehir bilgileri  
- **Districts** → İlçe bilgileri  
- **Urunler** → Ürün ilanları  
- **Favorites** → Kullanıcıların favorilediği ürünler  

📌 **Önemli Not:**
- Her ürün bir kullanıcıya aittir  
  (`Urunler.user_id → Users.user_id`)
- Favoriler, kullanıcı–ürün ilişkisi üzerinden tutulmaktadır  
  (`Favorites.user_id → Users.user_id`, `Favorites.urun_id → Urunler.id`)

Tablolar arasında **Primary Key (PK)** ve **Foreign Key (FK)** ilişkileri tanımlanarak veri bütünlüğü sağlanmıştır.

📌 **ER Diyagramı**, rapor klasörü içerisinde yer almaktadır.

---

## 📁 Proje Klasör Yapısı

```text
ikincil-el-satis-platformu/
│
├── database/
│   └── ikinciel_vtys.sql
│
├── kod/
│   └── ikinciElOtomasyon/
│       ├── controller/
│       ├── dao/
│       ├── db/
│       ├── model/
│       ├── service/
│       ├── session/
│       └── HelloApplication.java
│
├── rapor/
│   └── rapor.pdf
│
└── README.md
```
## 🛠️ Kurulum ve Çalıştırma

### 1️⃣ Veri Tabanı Kurulumu

- `database/ikinciel_vtys.sql` dosyasını  
  **SQL Server Management Studio (SSMS)** üzerinden çalıştırın.
- Oluşturulan veri tabanı adı: **IkinciElDB**

---

### 2️⃣ Java Uygulaması

- `kod/ikinciElOtomasyon` klasörünü **IntelliJ IDEA** ile açın.
- JavaFX ayarlarının aktif olduğundan emin olun.
- JDBC bağlantı ayarlarını kontrol edin (sunucu, port, kullanıcı bilgileri).
- Uygulamayı çalıştırın.

---

## 🖥️ Kullanıcı Arayüzü Özellikleri

- Ürün ilanlarını **kart yapısı** ile listeleme  
- Kategoriye göre filtreleme  
- Şehir / ilçe bazlı filtreleme  
- Fiyat aralığına göre filtreleme  
- İlan tarihine göre filtreleme  
- Yeni ürün ekleme  
- Mevcut ürünleri düzenleme ve silme  
- **Kullanıcı bazlı favori ekleme / çıkarma**  
  (favoriler veri tabanında kalıcı olarak saklanmaktadır)  
- **Asenkron veri çekme** sayesinde arayüz donmadan çalışma  

---

## 🧪 SQL ve VTYS Özellikleri

Projede aşağıdaki **VTYS özellikleri** aktif olarak kullanılmıştır:

- **DDL**
  - CREATE TABLE  
  - ALTER TABLE  
- **DML**
  - INSERT  
  - UPDATE  
  - DELETE  
- **Transaction**
  - COMMIT  
  - ROLLBACK  
- **View**
- **Stored Procedure**
- **JOIN** (INNER JOIN)
- **GROUP BY**, **HAVING**
- **Aggregate Fonksiyonlar**
  - COUNT  
  - SUM  
  - AVG  
  - MAX  
  - MIN  

---

## 📌 Genel Değerlendirme

Bu proje sayesinde:

- İlişkisel veri tabanı tasarımı,  
- Normalizasyon kurallarının uygulanması,  
- Java–SQL entegrasyonu,  
- Katmanlı mimari  
  (**DAO – Service – Controller – Model**),  
- VTYS ileri seviye kavramları  

uygulamalı olarak gerçekleştirilmiştir.

Geliştirilen sistem, hem **akademik gereksinimleri** karşılamakta  
hem de **gerçek hayatta kullanılabilecek** bir ikinci el satış otomasyonu altyapısı sunmaktadır.
