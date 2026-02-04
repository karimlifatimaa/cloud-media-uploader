# GitHub ilə DigitalOcean App Platform Deployment

## 📋 Ön Hazırlıq

### ✅ Artıq Hazır Olanlar:
- ✅ DigitalOcean Spaces: `profile-pics-2026` (fra1)
- ✅ Access Key: `DO801RX6BENQ9P774P82`
- ✅ Secret Key: `TCyAh5bq9wi4Wv1KnyltIV+Td+KSbEuOk9jSJ3TAGw4`
- ✅ Spring Boot aplikasiyası

### 📝 Lazım Olan:
- PostgreSQL connection məlumatları
- GitHub account

---

## 🚀 Addım-Addım Deployment

### 1️⃣ GitHub Repository Yaratmaq

#### A. GitHub-da yeni repo yaradın:
1. GitHub.com-a daxil olun
2. Sağ yuxarıda **"+"** → **"New repository"**
3. Repository adı: `cloud-media-uploader`
4. **Public** və ya **Private** seçin
5. ❌ README, .gitignore əlavə etməyin (artıq var)
6. **"Create repository"** düyməsinə basın

#### B. Local proyekti GitHub-a push edin:

```bash
cd /Users/fatimakarimli/cloud-media-uploader

# Git initialize (əgər edilməyibsə)
git init

# Bütün faylları əlavə et
git add .

# İlk commit
git commit -m "Initial commit: Profile image upload service with DigitalOcean Spaces"

# Main branch
git branch -M main

# Remote əlavə et (YOUR_USERNAME dəyişdirin!)
git remote add origin https://github.com/YOUR_USERNAME/cloud-media-uploader.git

# Push et
git push -u origin main
```

---

### 2️⃣ DigitalOcean App Platform-da App Yaratmaq

#### A. App Platform-a keçin:
1. DigitalOcean dashboard → **"Apps"** (sol menyu)
2. **"Create App"** düyməsinə basın

#### B. GitHub bağlantısı:
1. **"GitHub"** seçin
2. **"Manage Access"** → GitHub account-unuzu bağlayın
3. `cloud-media-uploader` repository-ni seçin
4. Branch: **`main`**
5. **"Next"** düyməsinə basın

#### C. Resources konfiqurasiyası:
**Avtomatik detect edəcək:**
- Type: **Web Service**
- Name: `cloud-media-uploader`

**Edit Plan** düyməsinə basın və yoxlayın:
- Build Command: `./gradlew clean build -x test`
- Run Command: `java -jar build/libs/cloud-media-uploader-0.0.1-SNAPSHOT.jar`
- HTTP Port: `8080`

**"Next"** düyməsinə basın

---

### 3️⃣ Environment Variables Əlavə Etmək

**"Environment Variables"** bölməsində:

#### A. DigitalOcean Spaces:
```
DO_SPACES_ACCESS_KEY = DO801RX6BENQ9P774P82
DO_SPACES_SECRET_KEY = TCyAh5bq9wi4Wv1KnyltIV+Td+KSbEuOk9jSJ3TAGw4
DO_SPACES_REGION = fra1
DO_SPACES_BUCKET = profile-pics-2026
DO_SPACES_ENDPOINT = https://fra1.digitaloceanspaces.com
```

⚠️ **Secret Key-i "Encrypt" seçin!**

#### B. PostgreSQL Database:
```
DATABASE_URL = jdbc:postgresql://YOUR_DB_HOST:25060/YOUR_DB_NAME?sslmode=require
DATABASE_USERNAME = doadmin
DATABASE_PASSWORD = YOUR_DB_PASSWORD
```

**Və ya Managed Database istifadə edin:**
- **"Add Resource"** → **"Database"**
- Mövcud PostgreSQL-i seçin
- Avtomatik environment variables əlavə olunacaq

---

### 4️⃣ App Info və Region

- **App Name:** `cloud-media-uploader`
- **Region:** Frankfurt (FRA1) - Spaces ilə eyni region!
- **"Next"** düyməsinə basın

---

### 5️⃣ Review və Deploy

#### A. Review:
- ✅ GitHub repo: `cloud-media-uploader`
- ✅ Branch: `main`
- ✅ Environment variables: 8 dənə
- ✅ Region: FRA1
- ✅ Plan: Basic ($5/month)

#### B. Deploy:
**"Create Resources"** düyməsinə basın

⏳ **Deployment 5-10 dəqiqə çəkəcək**

---

### 6️⃣ Deployment Status İzləmək

#### Build Logs:
1. App dashboard-da **"Activity"** tab-ına keçin
2. **"Building"** statusunu görəcəksiniz
3. Logs-u real-time izləyə bilərsiniz

#### Uğurlu deployment:
```
✅ Build successful
✅ Deploying...
✅ Live
```

---

## 🧪 Test Etmək

### 1. Application URL-ni əldə edin:

App dashboard-da:
```
https://cloud-media-uploader-xxxxx.ondigitalocean.app
```

### 2. Health Check:

```bash
curl https://your-app-url.ondigitalocean.app/health
```

**Cavab:** `OK`

### 3. Fayl Yükləmək:

```bash
curl -X POST https://your-app-url.ondigitalocean.app/upload \
  -F "file=@/path/to/image.jpg"
```

**Cavab nümunəsi:**
```json
{
  "id": 1,
  "fileName": "profile.jpg",
  "fileUrl": "https://profile-pics-2026.fra1.digitaloceanspaces.com/uuid-here.jpg",
  "contentType": "image/jpeg",
  "fileSize": 245678,
  "uploadedAt": "2026-02-04T22:33:15"
}
```

### 4. Şəkli Brauzer-də Açmaq:

Response-dakı `fileUrl`-i kopyalayıb brauzer-də açın.

### 5. Database Yoxlamaq:

PostgreSQL-ə bağlanın (DataGrip və ya psql):

```sql
SELECT * FROM profile_images ORDER BY uploaded_at DESC;
```

---

## 🔄 Yenilənmələr (CI/CD)

### Kod dəyişikliyi etdikdə:

```bash
git add .
git commit -m "Feature: Added image validation"
git push origin main
```

✅ **Avtomatik deploy olacaq!** (Deploy on push aktivdir)

---

## 🐛 Troubleshooting

### Build uğursuz olarsa:

1. **Logs yoxlayın:**
   - App Platform → Activity → Build Logs

2. **Ümumi problemlər:**
   - ❌ Gradle wrapper icazəsi: `chmod +x gradlew`
   - ❌ Java version: Java 17 olmalıdır
   - ❌ Dependencies: `./gradlew build` local-da test edin

### Spaces upload xətası:

1. **Environment variables yoxlayın:**
   - Settings → Environment Variables
   - Bütün 5 Spaces variable var?

2. **Bucket permissions:**
   - Spaces dashboard → Settings
   - File Listing: Public olmalıdır

### Database connection xətası:

1. **Connection string formatı:**
   ```
   jdbc:postgresql://host:25060/db?sslmode=require
   ```
   ⚠️ `sslmode=require` vacibdir!

2. **Firewall:**
   - Database → Settings → Trusted Sources
   - App Platform IP-ni əlavə edin

---

## 📊 Monitoring

### Runtime Logs:
```
App Platform → Runtime Logs
```

Real-time log stream:
```
2026-02-04 22:33:15 INFO  File uploaded: image.jpg
2026-02-04 22:33:16 INFO  Saved to database: ID 1
```

### Metrics:
```
App Platform → Insights
```

- CPU usage
- Memory usage
- Request count
- Response time

---

## 💰 Qiymət

**Minimum konfiqurasiya:**
- App Platform (Basic): **$5/month**
- Spaces (250GB): **$5/month**
- PostgreSQL (Basic): **$15/month**

**Cəmi:** ~$25/month

---

## 🎓 Tələbələr Üçün Qeydlər

### Development vs Production:

**Local (development):**
```properties
# application-dev.properties
digitalocean.spaces.access-key=DO801RX6BENQ9P774P82  # ← Real dəyər
```

**Production (App Platform):**
```properties
# application.properties
digitalocean.spaces.access-key=${DO_SPACES_ACCESS_KEY}  # ← Environment variable
```

### Niyə Environment Variables?

1. ✅ **Təhlükəsizlik:** Secret-lər kodda görünmür
2. ✅ **Çeviklik:** Hər mühitdə fərqli dəyərlər
3. ✅ **Best Practice:** Industry standard

---

## 📚 Əlavə Resurslar

- [DigitalOcean App Platform Docs](https://docs.digitalocean.com/products/app-platform/)
- [Spaces API Reference](https://docs.digitalocean.com/reference/api/spaces-api/)
- [Spring Boot Deployment Guide](https://spring.io/guides/gs/spring-boot-docker/)

---

## ✅ Checklist

Deployment əvvəl yoxlayın:

- [ ] GitHub repository yaradılıb
- [ ] Kod push edilib
- [ ] Spaces yaradılıb və Access Keys alınıb
- [ ] PostgreSQL hazırdır
- [ ] Environment variables hazırlanıb
- [ ] Build local-da uğurludur (`./gradlew build`)

Deployment sonra yoxlayın:

- [ ] Build uğurlu olub
- [ ] Health check işləyir
- [ ] File upload işləyir
- [ ] Şəkil Spaces-də görünür
- [ ] Database-də record var
- [ ] Public URL açılır
