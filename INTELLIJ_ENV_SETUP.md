# IntelliJ IDEA - Environment Variables Setup Guide

## 📋 Tələbələr Üçün: Environment Variables Necə Əlavə Edilir

### Addım 1: Run Configuration Açmaq

**2 yol var:**

#### Yol 1: Yuxarıdan
1. Yuxarıda **Run** menyusundan **Edit Configurations...** seçin

#### Yol 2: Toolbar-dan
1. Yuxarıda sağda **CloudMediaUploaderApplication** yanındakı dropdown-u açın
2. **Edit Configurations...** seçin

---

### Addım 2: Environment Variables Bölməsini Tapmaq

Run/Debug Configurations pəncərəsində:

1. Sol tərəfdə **Spring Boot** → **CloudMediaUploaderApplication** seçin
2. Sağ tərəfdə **Modify options** linkini klikləyin (yuxarıda)
3. Açılan menyudan **Environment variables** seçin

**Və ya:**

Əgər **Environment variables** field-i artıq görünürsə, birbaşa ora keçin.

---

### Addım 3: Environment Variables Əlavə Etmək

**Environment variables** field-inin yanındakı **folder icon** 📁 klikləyin.

**Environment Variables** dialog açılacaq.

---

### Addım 4: Variables Daxil Etmək

**2 format var:**

#### Format 1: Semicolon-separated (Tövsiyə olunur)

Bir sətirdə, `;` ilə ayrılmış:

```
DATABASE_URL=jdbc:postgresql://host:25060/db?sslmode=require;DATABASE_USERNAME=doadmin;DATABASE_PASSWORD=yourpassword;DO_SPACES_ACCESS_KEY=yourkey;DO_SPACES_SECRET_KEY=yoursecret;DO_SPACES_REGION=fra1;DO_SPACES_BUCKET=yourbucket;DO_SPACES_ENDPOINT=https://fra1.digitaloceanspaces.com
```

#### Format 2: Ayrı-ayrı (Daha oxunaqlı)

Hər variable üçün **+** düyməsinə basıb ayrı-ayrı əlavə edin:

| Name | Value |
|------|-------|
| `DATABASE_URL` | `jdbc:postgresql://host:25060/db?sslmode=require` |
| `DATABASE_USERNAME` | `doadmin` |
| `DATABASE_PASSWORD` | `yourpassword` |
| `DO_SPACES_ACCESS_KEY` | `yourkey` |
| `DO_SPACES_SECRET_KEY` | `yoursecret` |
| `DO_SPACES_REGION` | `fra1` |
| `DO_SPACES_BUCKET` | `yourbucket` |
| `DO_SPACES_ENDPOINT` | `https://fra1.digitaloceanspaces.com` |

---

### Addım 5: Tətbiq Etmək

1. **OK** düyməsinə basın (Environment Variables dialog)
2. **Apply** düyməsinə basın (Run Configuration)
3. **OK** düyməsinə basın

---

### Addım 6: Run Etmək

İndi **Run** ▶️ düyməsinə basın!

Aplikasiya environment variables-ı oxuyacaq və işə düşəcək.

---

## 🎓 Tələbələr Üçün Qeydlər

### Niyə Environment Variables?

**Pis yol:**
```properties
# application.properties
spring.datasource.password=mySecretPassword123
```
❌ Kod GitHub-a push olduqda, hamı şifrəni görür!

**Yaxşı yol:**
```properties
# application.properties
spring.datasource.password=${DATABASE_PASSWORD}
```
✅ Şifrə yalnız local kompüterdə və ya production server-də saxlanır

---

### Production-da Necə Olur?

**Local (IntelliJ):**
- Run Configuration → Environment variables

**Production (DigitalOcean App Platform):**
- App Settings → Environment Variables
- Eyni variable-ları oraya əlavə edirik

---

### Default Values (Əlavə)

Spring Boot-da default value istifadə edə bilərsiniz:

```properties
spring.datasource.password=${DATABASE_PASSWORD:defaultPassword}
```

☝️ Əgər `DATABASE_PASSWORD` yoxdursa, `defaultPassword` istifadə olunur.

**Amma:** Production-da default value istifadə etməyin! Təhlükəsizlik riski!

---

## 🔍 Troubleshooting

### Problem: "Could not resolve placeholder"

**Xəta:**
```
Could not resolve placeholder 'DATABASE_URL' in value "${DATABASE_URL}"
```

**Həll:**
1. Environment variables düzgün əlavə edilib?
2. Run Configuration-da **Environment variables** field-i doldurulub?
3. Aplikasiyanı yenidən run edin

---

### Problem: Environment variables görünmür

**Həll:**
1. **Modify options** → **Environment variables** seçin
2. Field görünəcək

---

## 📸 Screenshot-lar

### 1. Run Configuration Açmaq
![Run Configuration](screenshot1.png)

### 2. Modify Options
![Modify Options](screenshot2.png)

### 3. Environment Variables Field
![Environment Variables](screenshot3.png)

### 4. Variables Əlavə Etmək
![Add Variables](screenshot4.png)

---

## ✅ Checklist

Tələbələr üçün yoxlama siyahısı:

- [ ] Run Configuration açdım
- [ ] Environment variables field-ini tapdım
- [ ] Bütün 8 variable-ı əlavə etdim
- [ ] Apply və OK düymələrinə basdım
- [ ] Aplikasiyanı run etdim
- [ ] Xəta yoxdur, aplikasiya işə düşdü

---

## 💡 Best Practices

1. **Heç vaxt** credentials-ı kodda yazmayın
2. **Həmişə** environment variables istifadə edin
3. **Production-da** real credentials, **development-da** test credentials
4. **`.gitignore`-a** `.env` fayllarını əlavə edin
5. **GitHub-a push etməzdən əvvəl** credentials-ı yoxlayın

---

**Uğurlar!** 🚀
