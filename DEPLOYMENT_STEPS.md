# 🚀 GitHub ilə App Platform Deployment - Addım-Addım

## ✅ Hazır Məlumatlar

### DigitalOcean Spaces:
```
Access Key: DO801RX6BENQ9P774P82
Secret Key: TCyAh5bq9wi4Wv1KnyltIV+Td+KSbEuOk9jSJ3TAGw4
Region: fra1
Bucket: profile-pics-2026
Endpoint: https://fra1.digitaloceanspaces.com
```

### PostgreSQL Database:
```
Host: db-postgresql-fra1-18257-do-user-23913793-0.d.db.ondigitalocean.com
Port: 25060
Database: defaultdb
Username: doadmin
Password: AVNS_tv8xNfDlcD9jtjppHO1
SSL Mode: require
```

**Connection String:**
```
jdbc:postgresql://db-postgresql-fra1-18257-do-user-23913793-0.d.db.ondigitalocean.com:25060/defaultdb?sslmode=require
```

---

## 📝 ADDIM 1: GitHub Repository Yaratmaq

### 1.1 GitHub-da yeni repo:
1. https://github.com/new ünvanına keçin
2. **Repository name:** `cloud-media-uploader`
3. **Description:** `Profile image upload service with DigitalOcean Spaces`
4. **Public** seçin (və ya Private)
5. ❌ **Initialize this repository with:** heç nə seçməyin
6. **Create repository** düyməsinə basın

### 1.2 Local proyekti push etmək:

Terminal-da:

```bash
cd /Users/fatimakarimli/cloud-media-uploader

# Git initialize
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

**✅ Yoxlama:** GitHub-da repository-də fayllar görünməlidir.

---

## 📝 ADDIM 2: App Platform-da App Yaratmaq

### 2.1 App Platform-a keçin:
1. DigitalOcean dashboard
2. Sol menyudan **"Apps"**
3. **"Create App"** düyməsinə basın

### 2.2 Source seçimi:
1. **"GitHub"** seçin
2. **"Manage Access"** klikləyin
3. GitHub account-unuzu authorize edin
4. **"Only select repositories"** seçin
5. `cloud-media-uploader` repository-ni seçin
6. **"Install & Authorize"** düyməsinə basın

### 2.3 Repository konfiqurasiyası:
1. Repository: **`cloud-media-uploader`**
2. Branch: **`main`**
3. Source Directory: `/` (boş saxlayın)
4. Autodeploy: **✅ işarələyin** (kod dəyişəndə avtomatik deploy)
5. **"Next"** düyməsinə basın

---

## 📝 ADDIM 3: Resources Konfiqurasiyası

### 3.1 Web Service konfiqurasiyası:

DigitalOcean avtomatik detect edəcək:
- **Name:** `cloud-media-uploader`
- **Type:** Web Service
- **Environment:** Production

**"Edit Plan"** düyməsinə basın və yoxlayın:

**Build Command:**
```bash
./gradlew clean build -x test
```

**Run Command:**
```bash
java -jar build/libs/cloud-media-uploader-0.0.1-SNAPSHOT.jar
```

**HTTP Port:** `8080`

**Health Check:**
- HTTP Path: `/health`

**Instance Size:** Basic (512 MB RAM / 1 vCPU) - $5/month

**"Back"** düyməsinə basın.

### 3.2 Environment Variables:

**"Environment Variables"** bölməsində **"Edit"** klikləyin.

#### Spaces Credentials (5 dənə):

```
Key: DO_SPACES_ACCESS_KEY
Value: DO801RX6BENQ9P774P82
Type: Secret (encrypt işarələyin)
```

```
Key: DO_SPACES_SECRET_KEY
Value: TCyAh5bq9wi4Wv1KnyltIV+Td+KSbEuOk9jSJ3TAGw4
Type: Secret (encrypt işarələyin)
```

```
Key: DO_SPACES_REGION
Value: fra1
Type: Plain Text
```

```
Key: DO_SPACES_BUCKET
Value: profile-pics-2026
Type: Plain Text
```

```
Key: DO_SPACES_ENDPOINT
Value: https://fra1.digitaloceanspaces.com
Type: Plain Text
```

#### Database Credentials (3 dənə):

```
Key: DATABASE_URL
Value: jdbc:postgresql://db-postgresql-fra1-18257-do-user-23913793-0.d.db.ondigitalocean.com:25060/defaultdb?sslmode=require
Type: Secret (encrypt işarələyin)
```

```
Key: DATABASE_USERNAME
Value: doadmin
Type: Plain Text
```

```
Key: DATABASE_PASSWORD
Value: AVNS_tv8xNfDlcD9jtjppHO1
Type: Secret (encrypt işarələyin)
```

**Cəmi: 8 environment variable**

**"Save"** düyməsinə basın.

**"Next"** düyməsinə basın.

---

## 📝 ADDIM 4: App Info

### 4.1 App məlumatları:
- **App Name:** `cloud-media-uploader`
- **Region:** Frankfurt (FRA1) - **VACİB: Spaces ilə eyni region!**
- **Project:** first-project (və ya istədiyiniz)

**"Next"** düyməsinə basın.

---

## 📝 ADDIM 5: Review və Deploy

### 5.1 Final Review:

Yoxlayın:
- ✅ GitHub repo: `cloud-media-uploader`
- ✅ Branch: `main`
- ✅ Autodeploy: Enabled
- ✅ Build command: `./gradlew clean build -x test`
- ✅ Run command: `java -jar build/libs/...`
- ✅ Environment variables: 8 dənə
- ✅ Region: FRA1
- ✅ Monthly cost: ~$5

### 5.2 Deploy:

**"Create Resources"** düyməsinə basın! 🚀

---

## 📝 ADDIM 6: Deployment İzləmək

### 6.1 Build Status:

Deployment başlayacaq (5-10 dəqiqə):

1. **Building** - Gradle build işləyir
2. **Deploying** - Container yaradılır
3. **Live** - Aplikasiya hazırdır ✅

### 6.2 Logs izləmək:

**"Activity"** tab → **"View Logs"**

Uğurlu build:
```
✅ BUILD SUCCESSFUL in 2m 15s
✅ Deploying...
✅ Health check passed
✅ App is live
```

### 6.3 URL əldə etmək:

Dashboard-da:
```
https://cloud-media-uploader-xxxxx.ondigitalocean.app
```

Kopyalayın! 📋

---

## 📝 ADDIM 7: Test Etmək

### 7.1 Health Check:

Terminal-da:
```bash
curl https://cloud-media-uploader-xxxxx.ondigitalocean.app/health
```

**Gözlənilən cavab:** `OK`

### 7.2 Fayl yükləmək:

```bash
curl -X POST https://cloud-media-uploader-xxxxx.ondigitalocean.app/upload \
  -F "file=@/path/to/image.jpg"
```

**Gözlənilən cavab:**
```json
{
  "id": 1,
  "fileName": "image.jpg",
  "fileUrl": "https://profile-pics-2026.fra1.digitaloceanspaces.com/uuid-here.jpg",
  "contentType": "image/jpeg",
  "fileSize": 245678,
  "uploadedAt": "2026-02-04T22:39:06"
}
```

### 7.3 Şəkli brauzer-də açmaq:

Response-dakı `fileUrl`-i kopyalayıb brauzer-də açın.

✅ Şəkil görünməlidir!

### 7.4 Database yoxlamaq:

DataGrip və ya psql ilə:

```sql
-- Connection məlumatları
Host: db-postgresql-fra1-18257-do-user-23913793-0.d.db.ondigitalocean.com
Port: 25060
Database: defaultdb
User: doadmin
Password: AVNS_tv8xNfDlcD9jtjppHO1
SSL: require

-- Query
SELECT * FROM profile_images ORDER BY uploaded_at DESC;
```

✅ Record görünməlidir!

---

## 🐛 Troubleshooting

### Build uğursuz olarsa:

**1. Logs yoxlayın:**
```
App Platform → Activity → Build Logs
```

**2. Ümumi problemlər:**

❌ **Gradle wrapper permission:**
```bash
# Local-da test edin
chmod +x gradlew
git add gradlew
git commit -m "Fix: Gradle wrapper permissions"
git push
```

❌ **Java version:**
```
Ensure Java 17 is used
```

❌ **Dependencies:**
```bash
# Local-da test edin
./gradlew clean build
```

### Spaces upload xətası:

**1. Environment variables yoxlayın:**
```
Settings → cloud-media-uploader → Environment Variables
```

Bütün 5 Spaces variable var?

**2. Bucket permissions:**
```
Spaces → profile-pics-2026 → Settings
File Listing: Public
```

### Database connection xətası:

**1. Connection string formatı:**
```
jdbc:postgresql://host:25060/db?sslmode=require
```

⚠️ `sslmode=require` vacibdir!

**2. SSL certificate:**
```
App Platform avtomatik handle edir
```

---

## ✅ Deployment Checklist

### Əvvəl:
- [x] GitHub repository yaradılıb
- [x] Kod push edilib
- [x] Spaces yaradılıb
- [x] Access Keys alınıb
- [x] PostgreSQL hazırdır
- [x] Environment variables hazırlanıb

### Sonra:
- [ ] Build uğurlu olub
- [ ] Health check işləyir (`/health`)
- [ ] File upload işləyir (`/upload`)
- [ ] Şəkil Spaces-də görünür
- [ ] Database-də record var
- [ ] Public URL açılır

---

## 🎓 Növbəti Addımlar

### 1. Custom Domain (istəyə görə):
```
App Platform → Settings → Domains
Add: upload.yourdomain.com
```

### 2. Monitoring:
```
App Platform → Insights
- CPU usage
- Memory usage
- Request count
```

### 3. Scaling (lazım olsa):
```
App Platform → Settings → Resources
Increase instance count
```

### 4. CI/CD:
```
Kod dəyişikliyi → git push → Avtomatik deploy ✅
```

---

## 💰 Xərc

**Aylıq:**
- App Platform (Basic): $5
- Spaces (250GB): $5
- PostgreSQL (Basic): $15

**Cəmi:** ~$25/month

---

## 📞 Kömək

**Problemlə qarşılaşsanız:**
1. App Platform logs yoxlayın
2. Environment variables yoxlayın
3. Local-da test edin: `./gradlew bootRun --args='--spring.profiles.active=dev'`

**Uğurlar!** 🚀
