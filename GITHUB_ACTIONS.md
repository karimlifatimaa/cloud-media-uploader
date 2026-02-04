# GitHub Actions CI/CD Setup Guide

## 🎯 Nə Edir?

Bu GitHub Actions workflow hər dəfə kod push etdikdə:

1. ✅ **Build** - Gradle ilə proyekti build edir
2. ✅ **Test** - Unit testləri işə salır
3. ✅ **Deploy** - Main branch-a push olduqda avtomatik DigitalOcean-a deploy edir

---

## 📋 Setup Addımları

### 1️⃣ DigitalOcean Access Token Yaratmaq

#### A. DigitalOcean-da token yaradın:
1. DigitalOcean dashboard → **API** (sol menyu)
2. **Tokens** tab → **Generate New Token**
3. Token adı: `github-actions-deploy`
4. **Write** scope seçin (read + write)
5. **Generate Token** düyməsinə basın
6. ⚠️ **Token-i kopyalayın** (yalnız bir dəfə göstərilir!)

#### B. GitHub-da secret əlavə edin:
1. GitHub repository → **Settings**
2. **Secrets and variables** → **Actions**
3. **New repository secret** düyməsinə basın
4. Name: `DIGITALOCEAN_ACCESS_TOKEN`
5. Secret: (kopyaladığınız token-i yapışdırın)
6. **Add secret** düyməsinə basın

---

### 2️⃣ Workflow Faylını Push Etmək

```bash
cd /Users/fatimakarimli/cloud-media-uploader

# Git initialize (əgər edilməyibsə)
git init

# Bütün faylları əlavə et
git add .

# Commit
git commit -m "feat: Add CI/CD pipeline with GitHub Actions"

# GitHub-da repo yaradın və push edin
git remote add origin https://github.com/YOUR_USERNAME/cloud-media-uploader.git
git branch -M main
git push -u origin main
```

---

### 3️⃣ Workflow İzləmək

Push etdikdən sonra:

1. GitHub repository → **Actions** tab
2. **CI/CD Pipeline** workflow-u görəcəksiniz
3. Son run-u klikləyib progress izləyə bilərsiniz

**Workflow stages:**
- ✅ Build and Test (hər push-da)
- ✅ Deploy (yalnız main branch-a push olduqda)

---

## 🔄 Workflow Necə İşləyir?

### Trigger Events:

**Push to main:**
```
Build → Test → Deploy ✅
```

**Push to development:**
```
Build → Test (deploy yox)
```

**Pull Request:**
```
Build → Test (deploy yox)
```

---

## 📊 Workflow Steps

### Job 1: Build and Test

1. **Checkout code** - Kodu GitHub-dan götürür
2. **Setup JDK 17** - Java 17 quraşdırır
3. **Grant permissions** - gradlew-ə execute icazəsi verir
4. **Build** - `./gradlew clean build -x test`
5. **Test** - `./gradlew test`
6. **Upload artifacts** - JAR faylını saxlayır

### Job 2: Deploy (yalnız main branch)

1. **Checkout code** - Kodu yenidən götürür
2. **Deploy to App Platform** - DigitalOcean-a deploy edir

---

## 🎓 Tələbələr Üçün Qeydlər

### CI/CD Nədir?

**CI (Continuous Integration):**
- Hər kod dəyişikliyi avtomatik build və test olunur
- Problemlər erkən aşkar edilir

**CD (Continuous Deployment):**
- Test uğurlu olarsa, avtomatik production-a deploy olunur
- Manual deployment lazım deyil

### GitHub Actions Nədir?

GitHub-un built-in CI/CD platforması:
- ✅ Pulsuz (public repo-lar üçün)
- ✅ YAML ilə konfiqurasiya
- ✅ Marketplace-də hazır action-lar

---

## 🐛 Troubleshooting

### Build uğursuz olarsa:

**Actions tab-da logs yoxlayın:**
```
Actions → Failed workflow → Build and Test job → Logs
```

**Ümumi problemlər:**
- ❌ Java version uyğunsuzluğu
- ❌ Gradle wrapper icazəsi
- ❌ Test failure

### Deploy uğursuz olarsa:

**Yoxlayın:**
1. DigitalOcean token düzgündür?
2. App Platform-da app yaradılıb?
3. App adı `cloud-media-uploader` düzgündür?

---

## 🚀 Deployment Sonra

### Avtomatik Deployment:

```bash
# Kod dəyişikliyi edin
git add .
git commit -m "feat: New feature"
git push origin main

# GitHub Actions avtomatik:
# 1. Build edəcək
# 2. Test edəcək
# 3. Deploy edəcək ✅
```

### Status Badge Əlavə Etmək:

README.md-ə əlavə edin:

```markdown
![CI/CD](https://github.com/YOUR_USERNAME/cloud-media-uploader/workflows/CI%2FCD%20Pipeline/badge.svg)
```

---

## 📝 Workflow Customization

### Test-ləri skip etmək (development üçün):

```yaml
- name: Build with Gradle
  run: ./gradlew clean build -x test
```

### Başqa branch-lara deploy:

```yaml
if: github.ref == 'refs/heads/staging'
```

### Notification əlavə etmək:

Slack, Discord, Email notification action-ları əlavə edə bilərsiniz.

---

## ✅ Checklist

Deployment əvvəl:

- [ ] DigitalOcean Access Token yaradılıb
- [ ] GitHub Secret əlavə edilib
- [ ] Workflow faylı commit edilib
- [ ] GitHub repository yaradılıb
- [ ] App Platform-da app yaradılıb

Deployment sonra:

- [ ] GitHub Actions workflow işləyir
- [ ] Build uğurludur
- [ ] Test-lər pass olur
- [ ] Deploy uğurludur
- [ ] App Platform-da app live-dır
