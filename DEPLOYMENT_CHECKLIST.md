# 🚀 Deployment Checklist

## ✅ Hazır Olanlar

- [x] Spring Boot aplikasiyası yaradıldı
- [x] DigitalOcean Spaces konfiqurasiyası
  - Space: `profile-pics-2026`
  - Region: `fra1`
  - Access Key: `DO801RX6BENQ9P774P82`
- [x] PostgreSQL konfiqurasiyası
  - Host: `db-postgresql-fra1-18257-do-user-23913793-0.d.db.ondigitalocean.com`
  - Database: `defaultdb`
- [x] Local test uğurlu
  - ✅ Health check işləyir
  - ✅ File upload işləyir
  - ✅ Database-də saxlanır
  - ✅ Spaces-də görünür
- [x] Postman collection yaradıldı
- [x] GitHub Actions CI/CD pipeline yaradıldı

---

## 📋 İndi Edilməli Olanlar

### 1️⃣ GitHub Repository Yaratmaq

```bash
# Terminal-da
cd /Users/fatimakarimli/cloud-media-uploader

# Git initialize
git init

# Bütün faylları əlavə et
git add .

# İlk commit
git commit -m "Initial commit: Profile image upload service with CI/CD"

# Main branch
git branch -M main
```

**Sonra:**
1. https://github.com/new açın
2. Repository adı: `cloud-media-uploader`
3. Public seçin
4. **Create repository** düyməsinə basın

```bash
# Remote əlavə et (YOUR_USERNAME dəyişdirin!)
git remote add origin https://github.com/YOUR_USERNAME/cloud-media-uploader.git

# Push et
git push -u origin main
```

---

### 2️⃣ DigitalOcean Access Token Yaratmaq

**GitHub Actions üçün lazımdır:**

1. DigitalOcean → **API** → **Tokens**
2. **Generate New Token**
3. Name: `github-actions-deploy`
4. **Write** scope seçin
5. **Generate Token**
6. ⚠️ Token-i kopyalayın!

---

### 3️⃣ GitHub Secret Əlavə Etmək

1. GitHub repo → **Settings** → **Secrets and variables** → **Actions**
2. **New repository secret**
3. Name: `DIGITALOCEAN_ACCESS_TOKEN`
4. Secret: (token-i yapışdırın)
5. **Add secret**

---

### 4️⃣ DigitalOcean App Platform-da App Yaratmaq

**2 Variant:**

#### Variant A: GitHub ilə (Tövsiyə olunur)

1. App Platform → **Create App**
2. **GitHub** seçin
3. Repository: `cloud-media-uploader`
4. Branch: `main`
5. **Autodeploy**: ✅ Enable
6. **Environment Variables** əlavə edin:
   ```
   DO_SPACES_ACCESS_KEY = DO801RX6BENQ9P774P82
   DO_SPACES_SECRET_KEY = TCyAh5bq9wi4Wv1KnyltIV+Td+KSbEuOk9jSJ3TAGw4
   DO_SPACES_REGION = fra1
   DO_SPACES_BUCKET = profile-pics-2026
   DO_SPACES_ENDPOINT = https://fra1.digitaloceanspaces.com
   ```
7. **Create Resources**

#### Variant B: GitHub Actions ilə

GitHub-a push etdikdən sonra GitHub Actions avtomatik deploy edəcək (əgər App Platform-da app artıq yaradılıbsa).

---

### 5️⃣ GitHub Actions Workflow-u Yoxlamaq

Push etdikdən sonra:

1. GitHub repo → **Actions** tab
2. **CI/CD Pipeline** workflow-u görün
3. Progress izləyin:
   - ✅ Build and Test
   - ✅ Deploy (main branch-da)

---

### 6️⃣ Production Test

App deploy olduqdan sonra:

```bash
# Health check
curl https://cloud-media-uploader-xxxxx.ondigitalocean.app/health

# File upload
curl -X POST https://cloud-media-uploader-xxxxx.ondigitalocean.app/upload \
  -F "file=@/path/to/image.jpg"
```

---

## 📚 Əlavə Sənədlər

- [`DEPLOYMENT_STEPS.md`](file:///Users/fatimakarimli/cloud-media-uploader/DEPLOYMENT_STEPS.md) - Ətraflı deployment təlimatı
- [`GITHUB_ACTIONS.md`](file:///Users/fatimakarimli/cloud-media-uploader/GITHUB_ACTIONS.md) - CI/CD setup guide
- [`INTELLIJ_RUN.md`](file:///Users/fatimakarimli/cloud-media-uploader/INTELLIJ_RUN.md) - Local development guide
- [`README.md`](file:///Users/fatimakarimli/cloud-media-uploader/README.md) - Layihə haqqında
- [`postman_collection.json`](file:///Users/fatimakarimli/cloud-media-uploader/postman_collection.json) - API test collection

---

## 🎯 Növbəti Addımlar (Prioritet sırası)

1. **GitHub repository yaratmaq və push etmək**
2. **DigitalOcean Access Token yaratmaq**
3. **GitHub Secret əlavə etmək**
4. **App Platform-da app yaratmaq**
5. **GitHub Actions workflow-u yoxlamaq**
6. **Production test etmək**

---

## 💡 Qeydlər

### Environment Variables

**Local development:**
- `application.properties` və `application-dev.properties`-də real dəyərlər var
- Birbaşa run edə bilərsiniz

**Production (App Platform):**
- Dashboard-da environment variables əlavə edin
- GitHub Actions avtomatik deploy edəcək

### CI/CD Flow

```
Code Push → GitHub Actions → Build → Test → Deploy → Live ✅
```

### Təhlükəsizlik

⚠️ **DİQQƏT:** 
- `application.properties` faylında real credentials var
- Bu yalnız təlim məqsədilə edilib
- Production-da `.gitignore`-a əlavə edin və environment variables istifadə edin

---

## ✅ Final Checklist

- [ ] GitHub repository yaradılıb
- [ ] Kod push edilib
- [ ] DigitalOcean Access Token yaradılıb
- [ ] GitHub Secret əlavə edilib
- [ ] App Platform-da app yaradılıb
- [ ] Environment variables konfiqurasiya edilib
- [ ] GitHub Actions workflow işləyir
- [ ] Production-da test edilib
- [ ] Şəkil yüklənib və görünür

---

**Uğurlar!** 🚀
