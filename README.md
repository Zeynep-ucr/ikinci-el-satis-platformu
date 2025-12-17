# 📌 İkinci El Satış Platformu – VTYS Projesi

Bu proje, **Veri Tabanı Yönetim Sistemleri (VTYS)** dersi kapsamında geliştirilmiş bir ikinci el satış platformu uygulamasıdır.  
Uygulama; kullanıcıların ürün ilanlarını görüntüleyebildiği, filtreleyebildiği ve favorilerine ekleyebildiği bir masaüstü sistemdir.

---

## 🎯 Projenin Amacı

Bu projenin amacı;

- Gerçek hayata uygun bir ilişkisel veri tabanı tasarlamak,
- Veri tabanı normalizasyonu uygulamak,
- Java tabanlı bir uygulama ile SQL Server arasında bağlantı kurmak,
- CRUD işlemlerini (Create, Read, Update, Delete) uygulamak,
- İleri seviye SQL sorguları (JOIN, GROUP BY, HAVING, AGGREGATE) yazmaktır.

---

## 🗂️ Kullanılan Teknolojiler

- Java (JavaFX) – Masaüstü uygulama geliştirme  
- Microsoft SQL Server – Veri tabanı  
- JDBC – Java–SQL bağlantısı  
- Draw.io – ER diyagramı  
- GitHub – Versiyon kontrolü  

---
## 🧱 Veri Tabanı Yapısı

Veri tabanı normalize edilmiş şekilde tasarlanmıştır. Kullanılan tablolar:

- **Categories** → Ürün kategorileri  
- **Cities** → Şehir bilgileri  
- **Districts** → İlçe bilgileri  
- **Urunler** → Ürün ilanları  
- **Favorites** → Favoriye eklenen ürünler  

Tablolar arasında **Primary Key (PK)** ve **Foreign Key (FK)** ilişkileri kurulmuştur.

📌 **ER diyagramı** rapor klasörü içinde yer almaktadır.

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
│
├── rapor/
│   └── rapor.pdf
│
└── README.md
```

## 🛠️ Kurulum ve Çalıştırma

### 1️⃣ Veri Tabanı

- `database/ikinciel_vtys.sql` dosyasını **SQL Server Management Studio (SSMS)** üzerinden çalıştırın.
- Veri tabanı adı: **IkinciElDB**

### 2️⃣ Java Uygulaması

- `kod/ikinciElOtomasyon` klasörünü **IntelliJ IDEA** ile açın.
- JavaFX ayarlarının aktif olduğundan emin olun.
- Uygulamayı çalıştırın.

---

## 🖥️ Kullanıcı Arayüzü Özellikleri

- Ürün listeleme  
- Kategoriye göre filtreleme  
- Şehir / ilçe bazlı filtreleme  
- Fiyat aralığına göre filtreleme  
- Tarihe göre filtreleme  
- Ürün ekleme / silme / güncelleme  
- Favori ekleme (veri tabanı kalıcı)  

---

## 🧪 SQL Özellikleri

Projede;

- DDL (CREATE TABLE)  
- DML (INSERT, UPDATE, DELETE)  
- VIEW  
- Stored Procedure  
- Transaction  
- JOIN, GROUP BY, HAVING  
- Aggregate Fonksiyonlar (COUNT, AVG, MAX, MIN)  

kullanılmıştır.

---





