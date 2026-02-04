# Cloud Media Uploader

Profil şəkillərini DigitalOcean Spaces-də saxlayan və metadata-nı PostgreSQL-də qeyd edən **stateless** Spring Boot servisi.

## 🎯 Məqsəd

Bu layihə **stateless architecture** prinsipini nümayiş etdirir. Fayllar server daxilində deyil, obyekt yaddaşında (DigitalOcean Spaces) saxlanır. Bu yanaşma server restart olduqda məlumat itkisinin qarşısını alır.

## 🚀 Xüsusiyyətlər

- ✅ Profil şəkillərini yükləmək (JPEG, PNG, WebP)
- ✅ DigitalOcean Spaces-də saxlamaq (S3-compatible API)
- ✅ Metadata-nı PostgreSQL-də saxlamaq
- ✅ Public URL qaytarmaq
- ✅ Fayl validasiyası (tip və ölçü)
- ✅ Global exception handling
- ✅ Health check endpoint

## 📋 Tələblər

- Java 17+
- Gradle 8+
- DigitalOcean Account
- DigitalOcean Spaces (yaradılmalıdır)
- PostgreSQL Database (DigitalOcean Managed və ya local)

## 🛠️ Texnologiyalar

- **Spring Boot 3.5.10** - Backend framework
- **Spring Data JPA** - Database ORM
- **PostgreSQL** - Production database
- **H2** - Development database
- **AWS SDK S3** - DigitalOcean Spaces integration
- **Lombok** - Boilerplate code reduction

## 📦 Quraşdırma

### 1. Repository-ni Clone Etmək

```bash
git clone https://github.com/YOUR_USERNAME/cloud-media-uploader.git
cd cloud-media-uploader
```

### 2. Dependencies Yükləmək

```bash
./gradlew build
```

### 3. Local Development

Development profile ilə işə salmaq (H2 database):

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

Application `http://localhost:8080` ünvanında işə düşəcək.

### 4. Production Configuration

Production üçün environment variables təyin edin:

```bash
export DATABASE_URL=jdbc:postgresql://your-db-host:5432/your-db
export DATABASE_USERNAME=your-username
export DATABASE_PASSWORD=your-password
export DO_SPACES_ACCESS_KEY=your-access-key
export DO_SPACES_SECRET_KEY=your-secret-key
export DO_SPACES_REGION=fra1
export DO_SPACES_BUCKET=your-bucket-name
export DO_SPACES_ENDPOINT=https://fra1.digitaloceanspaces.com
```

Sonra aplikasiyanı işə salın:

```bash
./gradlew bootRun
```

## 🔌 API Endpoints

### 1. Fayl Yükləmək

```bash
POST /upload
Content-Type: multipart/form-data
```

**Request:**
```bash
curl -X POST http://localhost:8080/upload \
  -F "file=@/path/to/image.jpg"
```

**Response:**
```json
{
  "id": 1,
  "fileName": "profile.jpg",
  "fileUrl": "https://fra1.digitaloceanspaces.com/your-bucket/uuid-here.jpg",
  "contentType": "image/jpeg",
  "fileSize": 245678,
  "uploadedAt": "2026-02-04T22:03:25"
}
```

### 2. Bütün Şəkilləri Görmək

```bash
GET /images
```

**Response:**
```json
[
  {
    "id": 1,
    "fileName": "profile.jpg",
    "fileUrl": "https://...",
    "contentType": "image/jpeg",
    "fileSize": 245678,
    "uploadedAt": "2026-02-04T22:03:25"
  }
]
```

### 3. Health Check

```bash
GET /health
```

**Response:**
```
OK
```

## 🚢 Deployment

DigitalOcean App Platform-da deploy etmək üçün ətraflı təlimatlar:

👉 [DEPLOYMENT.md](DEPLOYMENT.md) faylına baxın

## 🧪 Test Etmək

### Unit Tests

```bash
./gradlew test
```

### Manual Test

1. Aplikasiyanı işə salın
2. Postman və ya curl ilə `/upload` endpoint-inə fayl göndərin
3. Response-dakı `fileUrl`-i brauzer-də açın
4. Database-də record-u yoxlayın

## 📁 Layihə Strukturu

```
cloud-media-uploader/
├── src/main/java/com/example/cloudmediauploader/
│   ├── config/
│   │   └── S3Config.java                 # S3 client konfiqurasiyası
│   ├── controller/
│   │   └── UploadController.java         # REST endpoints
│   ├── dto/
│   │   ├── UploadResponse.java           # Response DTO
│   │   └── ErrorResponse.java            # Error DTO
│   ├── entity/
│   │   └── ProfileImage.java             # JPA entity
│   ├── exception/
│   │   └── GlobalExceptionHandler.java   # Exception handling
│   ├── repository/
│   │   └── ProfileImageRepository.java   # JPA repository
│   ├── service/
│   │   └── StorageService.java           # File upload logic
│   └── CloudMediaUploaderApplication.java
├── src/main/resources/
│   ├── application.properties            # Production config
│   └── application-dev.properties        # Development config
├── app.yaml                              # DigitalOcean App Platform config
├── DEPLOYMENT.md                         # Deployment guide
└── README.md
```

## 🔒 Təhlükəsizlik

- ✅ Fayl tipi validasiyası (yalnız şəkillər)
- ✅ Fayl ölçüsü limiti (10MB)
- ✅ Unique filename generation (UUID)
- ✅ Database credentials environment variables-da
- ✅ Spaces credentials SECRET kimi saxlanır

## 📝 Qeydlər

### Niyə Stateless?

Server restart olduqda və ya scale olduqda:
- ❌ **Stateful**: Server daxilindəki fayllar silinir
- ✅ **Stateless**: Fayllar obyekt yaddaşında qalır, heç nə itmir

### DigitalOcean Spaces vs S3

DigitalOcean Spaces AWS S3 ilə uyğun API istifadə edir. Bu layihədə AWS SDK S3 istifadə olunur, lakin custom endpoint ilə DigitalOcean Spaces-ə bağlanır.

## 🤝 Töhfə

Pull request-lər qəbul olunur. Böyük dəyişikliklər üçün əvvəlcə issue açın.

## 📄 Lisenziya

MIT

## 👨‍💻 Müəllif

Fatima Karimli
