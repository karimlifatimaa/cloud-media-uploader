# DigitalOcean App Platform Deployment Guide

## Ön Hazırlıq

### 1. DigitalOcean Spaces Yaratmaq

1. DigitalOcean dashboard-a daxil olun
2. **Spaces** bölməsinə keçin
3. **Create a Space** düyməsinə klikləyin
4. Aşağıdakı məlumatları daxil edin:
   - **Region**: Frankfurt (fra1) və ya istədiyiniz region
   - **Space Name**: məsələn `images-storage`
   - **CDN**: İstəyə görə aktivləşdirə bilərsiniz
5. **Create a Space** düyməsinə basın

### 2. Spaces Access Keys Əldə Etmək

1. **API** bölməsinə keçin
2. **Spaces Keys** tab-ına keçin
3. **Generate New Key** düyməsinə basın
4. Key-ə ad verin (məs: `uploader-key`)
5. **Access Key** və **Secret Key**-i qeyd edin (Secret Key yalnız bir dəfə göstərilir!)

### 3. PostgreSQL Database

Connection məlumatlarını hazır saxlayın:
- Database Host
- Database Port
- Database Name
- Database Username
- Database Password

## App Platform-da Deployment

### Variant 1: GitHub ilə (Tövsiyə olunur)

1. **GitHub Repository Yaratmaq**
   ```bash
   cd /Users/username/cloud-media-uploader
   git init
   git add .
   git commit -m "Initial commit: Profile image upload service"
   git branch -M main
   git remote add origin https://github.com/YOUR_USERNAME/cloud-media-uploader.git
   git push -u origin main
   ```

2. **App Platform-da App Yaratmaq**
   - DigitalOcean dashboard → **Apps** → **Create App**
   - **GitHub** seçin və repository-ni bağlayın
   - `cloud-media-uploader` repository-ni seçin
   - Branch: `main`

3. **Build Settings**
   - Build Command: `./gradlew clean build -x test`
   - Run Command: `java -jar build/libs/cloud-media-uploader-0.0.1-SNAPSHOT.jar`
   - HTTP Port: `8080`

4. **Environment Variables Əlavə Etmək**
   
   **Database** (Mövcud PostgreSQL-dən):
   ```
   DATABASE_URL=jdbc:postgresql://YOUR_DB_HOST:25060/YOUR_DB_NAME?sslmode=require
   DATABASE_USERNAME=doadmin
   DATABASE_PASSWORD=YOUR_DB_PASSWORD
   ```

   **DigitalOcean Spaces**:
   ```
   DO_SPACES_ACCESS_KEY=YOUR_ACCESS_KEY
   DO_SPACES_SECRET_KEY=YOUR_SECRET_KEY
   DO_SPACES_REGION=fra1
   DO_SPACES_BUCKET=profile-images-storage
   DO_SPACES_ENDPOINT=https://fra1.digitaloceanspaces.com
   ```

5. **Health Check**
   - HTTP Path: `/health`

6. **Deploy**
   - **Create Resources** düyməsinə basın
   - Deployment başlayacaq (5-10 dəqiqə çəkə bilər)

### Variant 2: app.yaml ilə

1. `app.yaml` faylını redaktə edin:
   - GitHub repo URL-ni dəyişdirin
   - Spaces credentials əlavə edin
   - Database məlumatlarını yeniləyin

2. DigitalOcean CLI ilə deploy edin:
   ```bash
   doctl apps create --spec app.yaml
   ```

## Test Etmək

### 1. Application URL-ni Əldə Etmək

Deployment tamamlandıqdan sonra App Platform sizə URL verəcək:
```
https://cloud-media-uploader-xxxxx.ondigitalocean.app
```

### 2. Health Check

```bash
curl https://your-app-url.ondigitalocean.app/health
```

Cavab: `OK`

### 3. Fayl Yükləmək

```bash
curl -X POST https://your-app-url.ondigitalocean.app/upload \
  -F "file=@/path/to/your/image.jpg"
```

Cavab nümunəsi:
```json
{
  "id": 1,
  "fileName": "profile.jpg",
  "fileUrl": "https://fra1.digitaloceanspaces.com/profile-images-storage/uuid-here.jpg",
  "contentType": "image/jpeg",
  "fileSize": 245678,
  "uploadedAt": "2026-02-04T22:03:25"
}
```

### 4. Yüklənmiş Şəkilləri Görmək

```bash
curl https://your-app-url.ondigitalocean.app/images
```

### 5. Şəkli Brauzer-də Açmaq

Response-dakı `fileUrl`-i brauzer-də açın və şəklin göründüyünü yoxlayın.

### 6. Database-i Yoxlamak

DataGrip və ya psql ilə PostgreSQL-ə bağlanın:

```sql
SELECT * FROM profile_images ORDER BY uploaded_at DESC;
```

## Troubleshooting

### Build Uğursuz Olarsa

1. App Platform logs-u yoxlayın
2. Gradle version-u yoxlayın
3. Java version-un 17 olduğunu təsdiq edin

### Spaces Upload Xətası

1. Access Key və Secret Key-in düzgün olduğunu yoxlayın
2. Bucket name-in düzgün olduğunu yoxlayın
3. Region-un düzgün olduğunu yoxlayın
4. Spaces-in public access-ə icazə verdiyini yoxlayın

### Database Connection Xətası

1. DATABASE_URL formatını yoxlayın (SSL mode lazımdır)
2. Credentials-lərin düzgün olduğunu yoxlayın
3. Database-in App Platform ilə eyni region-da olduğunu yoxlayın

## Monitoring

### Logs Görmək

App Platform dashboard → Your App → Runtime Logs

### Metrics

App Platform dashboard → Your App → Insights

## Növbəti Addımlar

1. ✅ Custom domain əlavə etmək
2. ✅ CDN aktivləşdirmək (Spaces üçün)
3. ✅ Autoscaling konfiqurasiya etmək
4. ✅ Monitoring və alerting quraşdırmak
5. ✅ CI/CD pipeline təkmilləşdirmək
